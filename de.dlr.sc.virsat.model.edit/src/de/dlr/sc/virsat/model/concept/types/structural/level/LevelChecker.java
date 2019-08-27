/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.concept.types.structural.level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.tree.BeanStructuralTreeTraverser;

/**
 * <pre>
 * A class to check elements against a custom tree level structure which are
 * specified by implementations of {@link ILevel}s
 * 
 * An example of such a tree structure is a model that consists of a SYSTEM,
 * SUBSYTEM and EQUIPMENT levels These levels are implemented with the
 * {@link ILevel} interface
 * 
 * System MySatellite
 *  |
 *  ----Subsystem AOCS
 *  |	|
 *  |	----Equipment ReactionWheel1
 *  |	----Equipment ReactionWheel2
 *  |
 *  ----NewElement    <====  This class can check what level this element might be on
 *  	|
 *  	----Equipment Thruster
 * 
 * </pre>
 * 
 * 
 */
public class LevelChecker {

	protected List<ILevel> levels;

	/**
	 * @param levels
	 *            A list that specifies the concrete order of tree levels.
	 * 
	 *            Levels should not overlap - for any element only one level can
	 *            have true as result from the
	 *            {@link ILevel#isOnLevel(IBeanStructuralElementInstance)} method
	 */
	public LevelChecker(List<ILevel> levels) {
		this.levels = levels;
	}

	/**
	 * Checks which levels an element can be on
	 * 
	 * @param bean
	 *            the structural element bean to check
	 * @return a set of applicable levels from the list of levels passed in the
	 *         constructor
	 */
	public Set<ILevel> getApplicableLevels(IBeanStructuralElementInstance bean) {
		Set<ILevel> applicableLevels = new HashSet<>();
		int minLevelIndex = 0;
		int maxLevelIndex = levels.size() - 1;

		// Check if element belongs to level already - then it can by definition of this
		// checker only be on this level
		ILevel currentLevel = getLevelOfBean(bean);
		if (currentLevel != null) {
			applicableLevels.add(currentLevel);
			return applicableLevels;
		}

		// Get first parent with level
		IBeanStructuralElementInstance parent = getFirstParentWithLevel(bean);
		// Get children with level
		List<IBeanStructuralElementInstance> children = getChildrenWithLevel(bean);

		// Check maximum and minimum level index
		if (parent != null) {
			int minLevelFromParent = getMinIndexFromParentLevel(parent);
			int maxLevelFromParent = getMaxIndexFromTreeDistanceOfParent(parent, bean);

			if (minLevelFromParent > minLevelIndex) {
				minLevelIndex = minLevelFromParent;
			}
			if (maxLevelFromParent < maxLevelIndex) {
				maxLevelIndex = maxLevelFromParent;
			}
		}
		for (IBeanStructuralElementInstance child : children) {
			int maxLevelFromChild = getMaxIndexFromRelevantChild(child);
			int minLevelFromChild = getMinIndexFromTreeDistanceOfChild(child, bean);

			if (minLevelFromChild > minLevelIndex) {
				minLevelIndex = minLevelFromChild;
			}
			if (maxLevelFromChild < maxLevelIndex) {
				maxLevelIndex = maxLevelFromChild;
			}
		}

		// Add applicable levels to set
		for (int index = minLevelIndex; index <= maxLevelIndex; index++) {
			applicableLevels.add(levels.get(index));
		}

		return applicableLevels;
	}

	/**
	 * 
	 * @param bean
	 *            the structural element bean to check
	 * @param level
	 *            level from the list of levels passed in the constructor
	 * @return true if the the element can be on this level, false otherwise
	 */
	public boolean checkApplicable(IBeanStructuralElementInstance bean, ILevel level) {
		return getApplicableLevels(bean).contains(level);
	}

	/**
	 * Get the minimum level index from the next parent element with level
	 * 
	 * @param parent
	 *            the next parent with level
	 * @return the minumum level index
	 */
	private int getMinIndexFromParentLevel(IBeanStructuralElementInstance parent) {
		ILevel parentLevel = getLevelOfBean(parent);

		int minLevelIndex = levels.indexOf(parentLevel);

		// If the level cannot be nested (->repeated) then the element needs to be of
		// the next level
		if (!parentLevel.canBeNested()) {
			minLevelIndex += 1;
		}

		return minLevelIndex;
	}

	/**
	 * Get the minimum level index from the next parent element with level
	 * 
	 * @param child
	 *            the next child with level
	 * @return the minumum level index
	 */
	private int getMaxIndexFromRelevantChild(IBeanStructuralElementInstance child) {
		ILevel childLevel = getLevelOfBean(child);

		int maxLevelIndex = levels.indexOf(childLevel);

		// If the level cannot be nested (->repeated) then the element needs to be of
		// the previous level from the order
		if (!childLevel.canBeNested()) {
			maxLevelIndex -= 1;
		}

		return maxLevelIndex;
	}

	/**
	 * Get the minimum index considering the tree distance of the next relvant child
	 * with level
	 * 
	 * @param child
	 *            the next relevant child with level - the next relevant child is
	 *            the one with the least value of the difference from the level
	 *            index and the tree distance
	 * @param elementToCheck
	 *            the element to check
	 * @return the minimum level index
	 */
	private int getMinIndexFromTreeDistanceOfChild(IBeanStructuralElementInstance child,
			IBeanStructuralElementInstance elementToCheck) {
		ILevel childLevel = getLevelOfBean(child);
		int treeDistance = getTreeDistance(child, elementToCheck);
		int minLevel = levels.indexOf(childLevel) - treeDistance;
		
		// Go through all the levels between the child level and the one with a
		// distance of the tree depth and check if their optional. If so, then the
		// minimum applicable level index decreases as we can also skip the level
		for (int i = levels.indexOf(childLevel) - 1; 
				i >= 0 && i >= levels.indexOf(childLevel) - treeDistance; 
				i--) {
			if (levels.get(i).isOptional()) {
				minLevel--;
			}
		}
		
		return minLevel;
	}

	/**
	 * Get the maximum level index considering the tree distance to the next parent
	 * element with level
	 * 
	 * @param parent
	 *            the next parent element with level
	 * @param elementToCheck
	 *            the element to check
	 * @return the maximum level index
	 */
	private int getMaxIndexFromTreeDistanceOfParent(IBeanStructuralElementInstance parent,
			IBeanStructuralElementInstance elementToCheck) {
		ILevel parentLevel = getLevelOfBean(parent);
		int treeDistance = getTreeDistance(elementToCheck, parent);
		int maxLevel = levels.indexOf(parentLevel) + treeDistance;

		// Go through all the levels between the parent level and the one with a
		// distance of the tree depth and check if their optional. If so, then the
		// maximum applicable level index increases as we can also skip the level
		for (int i = levels.indexOf(parentLevel) + 1; 
				i <= levels.indexOf(parentLevel) + treeDistance && i < levels.size(); 
				i++) {
			if (levels.get(i).isOptional()) {
				maxLevel++;
			}
		}

		return maxLevel;
	}

	/**
	 * Get the level an arbitrary bean is on
	 * 
	 * @param bean
	 *            the bean to get level from
	 * @return the level
	 */
	private ILevel getLevelOfBean(IBeanStructuralElementInstance bean) {
		for (ILevel level : levels) {
			if (level.isOnLevel(bean)) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Get the first parent with level or null if not existing
	 * 
	 * @param bean
	 *            the SEIBean
	 * @return the next parent with level
	 */
	private IBeanStructuralElementInstance getFirstParentWithLevel(IBeanStructuralElementInstance bean) {
		IBeanStructuralElementInstance parent = bean.getParentSeiBean();

		if (parent != null) {
			ILevel level = getLevelOfBean(parent);
			if (level != null) {
				return parent;
			} else {
				return getFirstParentWithLevel(parent);
			}
		} else {
			return null;
		}
	}

	/**
	 * Get the first parent with level or null if not existing
	 * 
	 * @param bean
	 *            the SEIBean
	 * @return the next parent with level
	 */
	private List<IBeanStructuralElementInstance> getChildrenWithLevel(IBeanStructuralElementInstance bean) {
		HasLevelTreeTraverserMatcher matcher = new HasLevelTreeTraverserMatcher(levels);
		new BeanStructuralTreeTraverser().traverse(bean, matcher);

		return matcher.getElementsWithLevel();
	}

	/**
	 * Get the tree distance from one tree element to anther
	 * 
	 * @param startElement
	 *            the element to start from, has to be the one with a higher depth
	 * @param target
	 *            the target element, has to be the higher level element
	 * @return the distance of tree depth levels as int
	 */
	private int getTreeDistance(IBeanStructuralElementInstance startElement, IBeanStructuralElementInstance target) {
		return getTreeDistance(startElement, target, 0);
	}

	/**
	 * Get the tree distance from one tree element to anther
	 * 
	 * @param startElement
	 *            the element to start from, has to be the one with a higher depth
	 * @param target
	 *            the target element, has to be the higher level element
	 * @param startDistance
	 *            the current distance
	 * @return the distance of tree depth levels as int
	 */
	private int getTreeDistance(IBeanStructuralElementInstance startElement, IBeanStructuralElementInstance target,
			int startDistance) {
		int currentDistance = startDistance + 1;
		IBeanStructuralElementInstance parent = startElement.getParentSeiBean();
		if (parent == null) {
			return -1;
		}
		if (parent.equals(target)) {
			return currentDistance;
		} else {
			return getTreeDistance(parent, target, currentDistance);
		}

	}

}
