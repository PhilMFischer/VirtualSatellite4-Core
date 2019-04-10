/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.concept.generator.tests

import de.dlr.sc.virsat.model.concept.generator.AGeneratorGapGenerator
import de.dlr.sc.virsat.model.concept.generator.ConceptOutputConfigurationProvider
import de.dlr.sc.virsat.model.concept.generator.ImportManager
import de.dlr.sc.virsat.model.concept.generator.util.ConceptGeneratorUtil
import de.dlr.sc.virsat.model.dvlm.categories.ATypeDefinition
import de.dlr.sc.virsat.model.dvlm.categories.CategoriesFactory
import de.dlr.sc.virsat.model.dvlm.categories.Category
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment
import de.dlr.sc.virsat.model.dvlm.concepts.Concept
import org.eclipse.xtext.generator.IFileSystemAccess
import de.dlr.sc.virsat.model.concept.types.factory.BeanRegistry

/**
 * This class is the generator for the category beans of our model extension.
 * The beans will give easier access to the model of categories and properties.
 * Furthermore it will provide strong typing
 */
class GenerateCategoryTests extends AGeneratorGapGenerator<Category> {
	
	static def getConcreteClassName(ATypeDefinition typeDefinition) {
		typeDefinition.name.toFirstUpper + "Test"
	}
	
	static def getAbstractClassName(ATypeDefinition typeDefinition) {
		"A" + typeDefinition.name.toFirstUpper + "Test"
	}
	
	override createConcreteClassFileName(Concept concept, Category conceptPart) {
		"../../" + concept.name + ".test/src/" + concept.packageFolder + "/" + conceptPart.concreteClassName + ".java"
	}
	
	override createAbstractClassFileName(Concept concept, Category conceptPart) {
		"../../" + concept.name + ".test/src-gen/" + concept.packageFolder + "/" + conceptPart.abstractClassName + ".java"
	}
	
	override protected getPackage(Concept concept) {
		concept.name + "." + PACKAGE_FOLDER;
	}
	
	public static val PACKAGE_FOLDER = BeanRegistry.BEAN_PACKAGE_NAME;
	
	override serializeModel(Concept concept, IFileSystemAccess fsa) {
		concept.nonAbstractCategories.forEach[
			// ************************************************************************************
			// Abstract Class
			// ************************************************************************************
			var fileOutputAClass = createAbstractClass(concept, it);
			fsa.generateFile(createAbstractClassFileName(concept, it), ConceptOutputConfigurationProvider.GENERATOR_OUTPUT_ID_SOURCE, fileOutputAClass);

			// ************************************************************************************
			// Concrete Class
			// ************************************************************************************
			var fileOutputClass = createConcreteClass(concept, it);
			fsa.generateFile(createConcreteClassFileName(concept, it), ConceptOutputConfigurationProvider.GENERATOR_OUTPUT_ID_GENERATE_ONCE, fileOutputClass);
		]	
	}

	/**
	 * Declare the package statement of the java file 
	 */
	override declarePackage(String pluginPackage) '''
	package «pluginPackage»;
	'''
	
	// *************************************************************************************
	// * generate imports
	// *************************************************************************************
	
	/**
	 * Write down all the import statements needed by this java file
	 */
	override declareImports(ImportManager importManager, Concept concept, Category conceptPart, String conceptPackage) '''
	// *****************************************************************
	// * Import Statements
	// *****************************************************************

	
	«IF !importManager.importedClasses.empty»
		«FOR clazz : importManager.importedClasses»
			import «clazz»;
		«ENDFOR»
  	«ENDIF»
	'''

	// *************************************************************************************
	// * generate Abstract test Class that will always be regenerated by the generator
	// *************************************************************************************
	
	/**
	 *	Entry method to write the class body 
	 */
	override declareAClass(Concept concept, Category category, ImportManager importManager) '''
	«importManager.register(Exception)»
	«importManager.register(Concept)»
	
	// *****************************************************************
	// * Class Declaration
	// *****************************************************************
	
	import static org.junit.Assert.assertEquals;
	import static org.junit.Assert.assertNotNull;
	import static org.junit.Assert.assertNull;
	
	import org.junit.After;
	import org.junit.Before;
	import org.junit.Test;
	
	«ConceptGeneratorUtil.generateAClassHeader(category)»
	public abstract class A«category.name.toFirstUpper»Test {
		
		protected Concept concept;
		
		@Before
		public void setUp() throws Exception {
			String conceptXmiPluginPath = "«concept.name»/concept/concept.xmi";
			concept = de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader.loadConceptFromPlugin(conceptXmiPluginPath);
		}
	
		@After
		public void tearDown() throws Exception {
		}

		«declareConstructorTests(category, importManager)»
	}
	'''
	
	override declareClass(Concept concept, Category category, ImportManager importManager) '''
	// *****************************************************************
	// * Class Declaration
	// *****************************************************************
	
	«ConceptGeneratorUtil.generateClassHeader(category)»
	public class «category.name.toFirstUpper»Test extends A«category.name.toFirstUpper»Test {
	
	}
	'''

	// *************************************************************************************
	// * generate Constructor test Cases
	// *************************************************************************************

	/**
	 * Method to create the constructor of the java category bean
	 */		
	protected def declareConstructorTests(Category category, ImportManager importManager) '''
	«importManager.register(Concept)»
	«importManager.register(CategoryAssignment)»
	«importManager.register(CategoriesFactory)»
	«importManager.register(category)»
	
	// *****************************************************************
	// * Constructor Test Cases
	// *****************************************************************

	@Test
	public void test«category.name.toFirstUpper»() {
		«category.name.toFirstUpper» test«category.name.toFirstUpper» = new «category.name.toFirstUpper»();

		assertNull("There is no internal DVLM object", test«category.name.toFirstUpper».getTypeInstance());
	}

	@Test
	public void test«category.name.toFirstUpper»Concept() {
		«category.name.toFirstUpper» test«category.name.toFirstUpper» = new «category.name.toFirstUpper»(concept);
		
		assertNotNull("There is an internal DVLM object", test«category.name.toFirstUpper».getATypeInstance());
	}

	@Test
	public void test«category.name.toFirstUpper»CategoryAssignment() {
		CategoryAssignment testCa = CategoriesFactory.eINSTANCE.createCategoryAssignment();
		«category.name.toFirstUpper» test«category.name.toFirstUpper» = new «category.name.toFirstUpper»(testCa);
		
		assertEquals("DVLM object has been set as specified", testCa, test«category.name.toFirstUpper».getTypeInstance());
	}
	'''
}
