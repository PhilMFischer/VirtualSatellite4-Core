/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.provider;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.model.dvlm.categories.CategoriesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.PropertydefinitionsFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.ReferenceProperty;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.PropertyinstancesFactory;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;

/**
 * This class tests the DVLMReferencePropertyInstanceItemProvider
 * @author muel_s8
 *
 */

public class DVLMRefererencePropertyInstanceItemProviderTest {

	private ComposedAdapterFactory adapterFactory;
	
	@Before
	public void setup() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	}
	
	@Test
	public void testGetText() {
		DVLMRefererencePropertyInstanceItemProvider dvlmRpiip = new DVLMRefererencePropertyInstanceItemProvider(adapterFactory);
		
		ReferenceProperty rp = PropertydefinitionsFactory.eINSTANCE.createReferenceProperty();
		rp.setName("RP");
		ReferencePropertyInstance rpi = PropertyinstancesFactory.eINSTANCE.createReferencePropertyInstance();
		rpi.setType(rp);
		
		Category cat = CategoriesFactory.eINSTANCE.createCategory();
		rp.setReferenceType(cat);
		
		CategoryInstantiator instantiator = new CategoryInstantiator();
		CategoryAssignment ca = instantiator.generateInstance(cat, "CA");
		rpi.setReference(ca);
		
		final String EXPECTED = "RP -> CA";
		String actual = dvlmRpiip.getText(rpi);
		assertEquals("The output is like expected", EXPECTED, actual);
	}

}
