/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.mechanical.catia;

import java.util.Collection;
import java.util.Collections;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.visualisation.model.Visualisation;

/**
 * This class creates the JSON representation of a product structure tree.
 *
 */

public class CatiaExporter {
	
	/**
	 * Main method that creates the JSON representation of a configuration tree
	 * @param configurationTree the configuration tree
	 * @return the json root object
	 */
	public JsonObject transform(ConfigurationTree configurationTree) {
		JsonObject json = new JsonObject();
		
		// TODO Get the Element definitions
		json.put(CatiaProperties.PARTS.getKey(), transformParts(Collections.emptySet())); 
		
		return json;
	}
	
	/**
	 * Creates the JSON representation for a collection of parts
	 * @param parts the parts to transform
	 * @return the json representation
	 */
	public JsonArray transformParts(Collection<? extends IBeanStructuralElementInstance> parts) {
		JsonArray jsonParts = new JsonArray();
		
		for (IBeanStructuralElementInstance part : parts) {
			Visualisation vis = part.getFirst(Visualisation.class);
			
			if (vis != null) {
				JsonObject jsonPart = transformElement(part);
				jsonParts.add(jsonPart);
			}
		}
		
		return jsonParts;
	}
	
	/**
	 * Creates a JSON object for a single virtual satellite element
	 * @param element the element definition
	 * @return the JSON object
	 */
	public JsonObject transformElement(IBeanStructuralElementInstance element) {
		JsonObject jsonElement = new JsonObject();
		jsonElement.put(CatiaProperties.NAME.getKey(), element.getName());
		jsonElement.put(CatiaProperties.UUID.getKey(), element.getUuid());
		return jsonElement;
	}
}
