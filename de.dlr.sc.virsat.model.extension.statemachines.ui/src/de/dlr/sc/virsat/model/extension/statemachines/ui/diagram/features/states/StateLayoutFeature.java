/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.statemachines.ui.diagram.features.states;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

import de.dlr.sc.virsat.graphiti.ui.diagram.feature.VirSatLayoutFeature;
import de.dlr.sc.virsat.model.extension.statemachines.model.State;
import de.dlr.sc.virsat.model.extension.statemachines.model.StateMachine;

/**
 * Layout feature for states. Adjusts the internal shapes to match
 * changes such as size changes.
 *
 */
public class StateLayoutFeature extends VirSatLayoutFeature  {
	/**
	 * Default constructor
	 * @param fp the diagram type provider
	 */
	public StateLayoutFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean layout(ILayoutContext context) {
		ContainerShape cs = (ContainerShape) context.getPictogramElement();
		
		State state = (State) getBusinessObjectForPictogramElement(cs);
		StateMachine sm = state.getParentCaBeanOfClass(StateMachine.class);
		
		int csWidth = cs.getGraphicsAlgorithm().getWidth();
		int csHeight = cs.getGraphicsAlgorithm().getHeight();
		
		Shape ellipseShape = cs.getChildren().get(StateAddFeature.INDEX_ELLIPSE);
		Shape initialArrowShape = cs.getChildren().get(StateAddFeature.INDEX_INITIAL_ARROW);
		Shape textShape = cs.getChildren().get(StateAddFeature.INDEX_TEXT);
		
		int ellipseOffset = state.equals(sm.getInitialState()) ? initialArrowShape.getGraphicsAlgorithm().getWidth() : 0;
		int ellipsWidth = csWidth - ellipseOffset;
		
		Graphiti.getGaService().setLocationAndSize(
				ellipseShape.getGraphicsAlgorithm(), 
				ellipseOffset, 
				0,
				ellipsWidth, 
				csHeight
		);

		initialArrowShape.getGraphicsAlgorithm().setY(
				(csHeight - initialArrowShape.getGraphicsAlgorithm().getHeight()) / 2
		);
		
		Graphiti.getGaService().setLocationAndSize(
				textShape.getGraphicsAlgorithm(), 
				ellipseOffset, 
				0,
				ellipsWidth, 
				csHeight
		);
		
		return true;
	}
}
