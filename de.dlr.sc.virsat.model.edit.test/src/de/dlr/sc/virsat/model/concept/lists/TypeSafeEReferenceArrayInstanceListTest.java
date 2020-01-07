/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.concept.lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.concept.list.IBeanList;
import de.dlr.sc.virsat.model.concept.list.TypeSafeEReferenceArrayInstanceList;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyEReference;
import de.dlr.sc.virsat.model.dvlm.categories.CategoriesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.EReferenceProperty;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.PropertydefinitionsFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ArrayInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.EReferencePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.PropertyinstancesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.ConceptsFactory;

public class TypeSafeEReferenceArrayInstanceListTest {

	private CategoryAssignmentHelper helper;
	private Concept testConcept;
	private Category testCategory;
	private EReferenceProperty testProperty;
	private static final String PROPERTY_NAME = "testProperty";
	
	private CategoryAssignment testCA;
	private ArrayInstance propertyInstance;
	
	private CategoryAssignment testPropertyValue;
	
	@Before
	public void setUp() throws Exception {
		testConcept = ConceptsFactory.eINSTANCE.createConcept();
		testCategory = CategoriesFactory.eINSTANCE.createCategory();
		testProperty = PropertydefinitionsFactory.eINSTANCE.createEReferenceProperty();
		testProperty.setName(PROPERTY_NAME);
		testProperty.setArrayModifier(PropertydefinitionsFactory.eINSTANCE.createDynamicArrayModifier());
		
		testConcept.getCategories().add(testCategory);
		testCategory.getProperties().add(testProperty);
		
		
		testCA = CategoriesFactory.eINSTANCE.createCategoryAssignment();
		propertyInstance = PropertyinstancesFactory.eINSTANCE.createArrayInstance();
		propertyInstance.setType(testProperty);
		testProperty.setReferenceType(testCA.eClass());
		testCA.getPropertyInstances().add(propertyInstance);
		helper = new CategoryAssignmentHelper(testCA);
		
		testPropertyValue = CategoriesFactory.eINSTANCE.createCategoryAssignment();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testList() {
		//Create test array instance
		IBeanList<BeanPropertyEReference<CategoryAssignment>> testList = new TypeSafeEReferenceArrayInstanceList<CategoryAssignment>();
		testList.setArrayInstance((ArrayInstance) helper.getPropertyInstance(PROPERTY_NAME));
		
		//Create new value
		BeanPropertyEReference<CategoryAssignment> bean = new BeanPropertyEReference<CategoryAssignment>();
		EReferencePropertyInstance eReferencePropertyInstance = PropertyinstancesFactory.eINSTANCE.createEReferencePropertyInstance();
		eReferencePropertyInstance.setType(testProperty);
		bean.setTypeInstance(eReferencePropertyInstance);
		bean.setValue(testPropertyValue);
		
		//Add new value
		testList.add(bean);
		assertEquals("Bean should have been added", eReferencePropertyInstance.getUuid(), testList.getArrayInstance().getArrayInstances().get(0).getUuid());
		assertEquals("Access via array shoud produce same result", eReferencePropertyInstance.getUuid(), 
				((BeanPropertyEReference<CategoryAssignment>) testList.toArray()[0]).getTypeInstance().getUuid());
		
		assertTrue("Bean should be found in list", testList.contains(bean));
		
		//Remove value
		testList.remove(0);
		assertFalse("Bean should be removed", testList.contains(bean));
		
		//Check indexing
		testList.add(new BeanPropertyEReference<CategoryAssignment>());
		testList.add(new BeanPropertyEReference<CategoryAssignment>());
		testList.add(new BeanPropertyEReference<CategoryAssignment>());
		final int INDEX = 2;
		testList.add(INDEX, bean);
		assertEquals(INDEX, testList.indexOf(testList.get(INDEX)));
		
		testList.removeAll(Collections.singletonList(bean));
		assertFalse("Bean should be removed", testList.contains(bean));
		
		BeanPropertyEReference<CategoryAssignment> bean2 = new BeanPropertyEReference<CategoryAssignment>();
		eReferencePropertyInstance = PropertyinstancesFactory.eINSTANCE.createEReferencePropertyInstance();
		eReferencePropertyInstance.setType(testProperty);
		bean2.setTypeInstance(eReferencePropertyInstance);
		testList.addAll(Collections.singletonList(bean2));
		assertTrue("New bean should be added as part of addAll", testList.contains(bean2));
		
		final int SET_INDEX = 1;
		testList.set(SET_INDEX, bean);
		assertEquals(testList.subList(SET_INDEX, SET_INDEX + 1).get(0).getTypeInstance().getUuid(), bean.getTypeInstance().getUuid());
		
		testList.add(bean);
		testList.retainAll(Collections.singletonList(bean2));
		assertTrue("Bean should be found in list", testList.contains(bean2));
		assertFalse("Bean should be removed", testList.contains(bean));
	}
	
}
