

/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.dvlm.qudv.provider;



import de.dlr.sc.virsat.model.dvlm.command.UndoableAddCommand;

import de.dlr.sc.virsat.model.dvlm.provider.DVLMEditPlugin;

import de.dlr.sc.virsat.model.dvlm.qudv.Dimension;
import de.dlr.sc.virsat.model.dvlm.qudv.QudvPackage;

import de.dlr.sc.virsat.model.dvlm.roles.RoleManagementCheckCommand;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.command.CommandParameter;

import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemFontProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.dlr.sc.virsat.model.dvlm.qudv.Dimension} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DimensionItemProvider 
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemFontProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DimensionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addSymbolicExpressionPropertyDescriptor(object);
			addFactorPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Symbolic Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSymbolicExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dimension_symbolicExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dimension_symbolicExpression_feature", "_UI_Dimension_type"),
				 QudvPackage.Literals.DIMENSION__SYMBOLIC_EXPRESSION,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Factor feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFactorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dimension_factor_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dimension_factor_feature", "_UI_Dimension_type"),
				 QudvPackage.Literals.DIMENSION__FACTOR,
				 false,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * *********************************
	 * VirSat Specific Code Generation
	 * *********************************
	 * This returns Dimension.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
	
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Dimension")); 
	}
	
	/**
	 * *********************************
	 * VirSat Specific Code Generation
	 * *********************************
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {

		
		
	
	
  	
    	
      	
			String label = ((Dimension)object).getSymbolicExpression();
      	
    	
			return label == null || label.length() == 0 ?
				getString("_UI_Dimension_type") :
				getString("_UI_Dimension_type") + " " + label;
  	
	
	}
	

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Dimension.class)) {
			case QudvPackage.DIMENSION__SYMBOLIC_EXPRESSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}
	
	
	/**
	 * *********************************
	 * VirSat Specific Code Generation
	 * *********************************
 	 * Override to the createAddCommand Method
 	 * <!-- begin-user-doc -->
 	 * <!-- end-user-doc -->
 	 * @generated
 	*/
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,	Collection<?> collection, int index) {
		
		// Override functionality with the undoable ADD Command that performs undo by taking out the collection from the containing list
		// rather than reducing the index and assuming the last objects on the list have been added by the current command
		return new UndoableAddCommand(domain, owner, feature, collection, index);
	}
	
	/**
	 * *********************************
	 * VirSat Specific Code Generation
	 * *********************************
 	 * This pipes the command through our RoleManagmentCheckCommand, so we can check directly if a user is allowed to modify
 	 * <!-- begin-user-doc -->
 	 * <!-- end-user-doc -->
 	 * @generated
 	*/
	@Override
	public Command createCommand(Object object, EditingDomain domain, Class<? extends Command> commandClass, CommandParameter commandParameter) {
		
	    		
		// For all other commands get the original one
		Command originalCommand = super.createCommand(object, domain, commandClass, commandParameter);
				
	    
	    
	    		
	    	
		// A RolemanagementCheckCommand should not necessarily be wrapped into another RoleManagementCheck Command
		if (originalCommand instanceof RoleManagementCheckCommand) {
			return originalCommand;
		} else {
			// And wrap it into our command checking for the proper access rights
			return new RoleManagementCheckCommand(originalCommand, commandParameter);	
		}
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return DVLMEditPlugin.INSTANCE;
	}

}