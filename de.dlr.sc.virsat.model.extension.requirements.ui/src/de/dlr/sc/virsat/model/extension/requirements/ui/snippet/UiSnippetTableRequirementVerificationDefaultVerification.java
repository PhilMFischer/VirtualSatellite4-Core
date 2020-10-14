/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.requirements.ui.snippet;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.dlr.sc.virsat.model.dvlm.categories.ATypeDefinition;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.AProperty;
import de.dlr.sc.virsat.model.dvlm.categories.propertydefinitions.ReferenceProperty;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.requirements.model.DefaultVerification;
import de.dlr.sc.virsat.model.extension.requirements.model.RequirementsConfigurationCollection;
import de.dlr.sc.virsat.model.extension.requirements.model.VerificationType;
import de.dlr.sc.virsat.model.extension.requirements.ui.celleditor.DefaultVerificationTypeReferenceCellEditingSupport;
import de.dlr.sc.virsat.uiengine.ui.cellEditor.aproperties.PropertyInstanceEditingSupportFactory;
import de.dlr.sc.virsat.uiengine.ui.dialog.ReferenceSelectionDialog;
import de.dlr.sc.virsat.uiengine.ui.editor.snippets.IUiSnippet;


/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * 
 * 
 */
public class UiSnippetTableRequirementVerificationDefaultVerification extends AUiSnippetTableRequirementVerificationDefaultVerification implements IUiSnippet {
	
	private static final int TABLE_HIGHT = 80;
	private static final String SECTION_HEADING = "Basic Verification Method";
	private static final String SECTION_DESCRIPTION = "Select a verification method from the verification configuration.";
	private static final String DIALOG_DESCRIPTION = "Select a Verification Method";
	public static final String BUTTON_ADD_TEXT = "Add Verification Type";
	
	protected static final String FQN_PROPERTY_VERIFICATION_TYPE = DefaultVerification.FULL_QUALIFIED_CATEGORY_NAME + "." + DefaultVerification.PROPERTY_VERIFICATIONTYPE;
	
	@Override
	protected EditingSupport createEditingSupport(EditingDomain editingDomain, AProperty property) {
		
		if (property instanceof ReferenceProperty) {
			return new DefaultVerificationTypeReferenceCellEditingSupport(editingDomain, columnViewer, (ReferenceProperty) property);
		}
		
		return PropertyInstanceEditingSupportFactory.INSTANCE.createEditingSupportFor(editingDomain, columnViewer, property);
	}
	
	@Override
	protected Table createDefaultTable(FormToolkit toolkit, Composite sectionBody) {
		Table table = super.createDefaultTable(toolkit, sectionBody);
		GridData gridDataTable = (GridData) table.getLayoutData();
		gridDataTable.heightHint = TABLE_HIGHT;
		return table;
	}
	
	@Override
	public Composite createSectionBody(FormToolkit toolkit, String sectionHeading, String sectionDescription,
			int numberColumns) {
		return super.createSectionBody(toolkit, SECTION_HEADING, SECTION_DESCRIPTION, numberColumns);
	}
	
	/**
	 * this method creates the add button and his functionality
	 * 
	 * @param toolkit the toolkit to be used to create the button
	 * @param editingDomain the editing domain an which the button might act on
	 * @param compositeButtons the composite in which the button should be placed
	 */
	protected void createAddButton(FormToolkit toolkit, EditingDomain editingDomain, Composite compositeButtons) {
		if ((style & STYLE_ADD_BUTTON) != 0) {
			Button buttonAdd = toolkit.createButton(compositeButtons, BUTTON_ADD_TEXT, SWT.PUSH);
			buttonAdd.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					Concept activeConcept = acHelper.getConcept(conceptId);
					Command addCommand = createAddCommand(editingDomain, activeConcept);
					editingDomain.getCommandStack().execute(addCommand);
					Collection<?> affectedObjects = addCommand.getAffectedObjects();
					ComposedPropertyInstance arrayInstance = (ComposedPropertyInstance) affectedObjects.iterator()
							.next();

					CategoryAssignment newVerification = arrayInstance.getTypeInstance();
					DefaultVerification verificationBean = new DefaultVerification(newVerification);
					CategoryAssignmentHelper caHelper = new CategoryAssignmentHelper(newVerification);

					ReferencePropertyInstance propertyInstance = (ReferencePropertyInstance) caHelper
							.getPropertyInstance(FQN_PROPERTY_VERIFICATION_TYPE);
					ATypeDefinition referencePropertyType = ((ReferenceProperty) propertyInstance.getType())
							.getReferenceType();

					createAddVerificationDialog(editingDomain, affectedObjects, arrayInstance, verificationBean,
							referencePropertyType);
				}

				

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			checkWriteAccess(buttonAdd);
		}
	}
	
	/**
	 * @param editingDomain the editing domain of the new verification method
	 * @param affectedObjects affected elements of the add operation
	 * @param arrayInstance the array instance of the verification table
	 * @param newVerification the new verification method
	 * @param referencePropertyType the property type of the verification method
	 */
	protected void createAddVerificationDialog(EditingDomain editingDomain, Collection<?> affectedObjects,
			ComposedPropertyInstance arrayInstance, DefaultVerification newVerification,
			ATypeDefinition referencePropertyType) {
		ReferenceSelectionDialog dialog = ReferenceSelectionDialog.createRefernceSelectionDialog(
				Display.getCurrent().getActiveShell(), referencePropertyType, adapterFactory);
		dialog.setInput(model.eResource());

		// select first config collection
		for (StructuralElementInstance sei : CategoryAssignmentHelper.getRepository(arrayInstance)
				.getRootEntities()) {
			if (sei.getType().getFullQualifiedName()
					.equals(RequirementsConfigurationCollection.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME)) {
				dialog.setInitialSelection(sei);
				break;
			}
		}

		dialog.setAllowMultiple(false);
		dialog.setDoubleClickSelects(true);
		dialog.setTitle(DIALOG_DESCRIPTION);

		if (dialog.open() == Dialog.OK) {
			Object selection = dialog.getFirstResult();
			if (selection instanceof CategoryAssignment) {
				initializeVerificationMethod(editingDomain, newVerification, new VerificationType((CategoryAssignment) selection));
			} else if (selection instanceof StructuralElementInstance) {
				List<CategoryAssignment> reqTypesOfSelection = CategoryAssignmentHelper
						.getNestedCategoryAssignments((StructuralElementInstance) selection,
								VerificationType.FULL_QUALIFIED_CATEGORY_NAME);
				if (!reqTypesOfSelection.isEmpty()) {
					initializeVerificationMethod(editingDomain, newVerification, new VerificationType(reqTypesOfSelection.get(0)));
				}
			}
		} else {
			// Clean up
			Command cmd = createDeleteCommand(editingDomain, affectedObjects);
			editingDomain.getCommandStack().execute(cmd);
		}
	}
	
	protected void initializeVerificationMethod(EditingDomain editingDomain, DefaultVerification verification, VerificationType type) {
		editingDomain.getCommandStack().execute(verification.setVerificationType(editingDomain, type));
	}
}
