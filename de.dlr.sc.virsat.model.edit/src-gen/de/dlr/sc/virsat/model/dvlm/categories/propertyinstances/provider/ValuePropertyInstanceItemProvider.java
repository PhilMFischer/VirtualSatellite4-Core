

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



import de.dlr.sc.virsat.model.dvlm.categories.ATypeInstance;

import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.PropertyinstancesPackage;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ValuePropertyInstance;

import de.dlr.sc.virsat.model.dvlm.command.UndoableAddCommand;

import de.dlr.sc.virsat.model.dvlm.general.IQualifiedName;

import de.dlr.sc.virsat.model.dvlm.inheritance.InheritancePackage;

import de.dlr.sc.virsat.model.dvlm.provider.DVLMEditPlugin;

import de.dlr.sc.virsat.model.dvlm.roles.RoleManagementCheckCommand;

import de.dlr.sc.virsat.model.dvlm.structural.StructuralElement;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;

import de.dlr.sc.virsat.model.dvlm.types.impl.VirSatUuid;

import java.net.URL;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;

import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ValuePropertyInstance} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ValuePropertyInstanceItemProvider extends APropertyInstanceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValuePropertyInstanceItemProvider(AdapterFactory adapterFactory) {
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

			addOverridePropertyDescriptor(object);
			addValuePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Override feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOverridePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_IOverridableInheritanceLink_override_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_IOverridableInheritanceLink_override_feature", "_UI_IOverridableInheritanceLink_type"),
				 InheritancePackage.Literals.IOVERRIDABLE_INHERITANCE_LINK__OVERRIDE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ValuePropertyInstance_value_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ValuePropertyInstance_value_feature", "_UI_ValuePropertyInstance_type"),
				 PropertyinstancesPackage.Literals.VALUE_PROPERTY_INSTANCE__VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * *********************************
	 * VirSat Specific Code Generation
	 * *********************************
	 * This returns ValuePropertyInstance.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
	 
		Object rtrnObj = overlayImage(object, getResourceLocator().getImage("full/obj16/ValuePropertyInstance")); 
		
		// In case we can find a trace to an object typed by IQualifedName we might have an alternative image
		IQualifiedName qualifiedNameObject = null;
		if (object instanceof ATypeInstance) {
			ATypeInstance typeInstance = (ATypeInstance) object;
			qualifiedNameObject = typeInstance.getType();
		} else if (object instanceof StructuralElementInstance) { 
			StructuralElementInstance structuralElementInstance = (StructuralElementInstance) object;
			qualifiedNameObject = structuralElementInstance.getType();			
		} else if (object instanceof StructuralElement) { 
			qualifiedNameObject = (StructuralElement) object;		
		} else if (object instanceof Category) { 
			qualifiedNameObject = (Category) object;		
		}
		
		// In case we could trace an object of IQualifedName we now ask the image registry for an alternative image
		if (qualifiedNameObject != null) {
			String fullQualifiedID = qualifiedNameObject.getFullQualifiedName();
			URL imageUrl = DVLMEditPlugin.getImageRegistry().get(fullQualifiedID);
			if (imageUrl != null) {
				rtrnObj = overlayImage(object, imageUrl);
			}
		}
		return rtrnObj;
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

		
		
	
	
  	
    	
      	
			VirSatUuid labelValue = ((ValuePropertyInstance)object).getUuid();
      	
			String label = labelValue == null ? null : labelValue.toString();
    	
			return label == null || label.length() == 0 ?
				getString("_UI_ValuePropertyInstance_type") :
				getString("_UI_ValuePropertyInstance_type") + " " + label;
  	
	
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

		switch (notification.getFeatureID(ValuePropertyInstance.class)) {
			case PropertyinstancesPackage.VALUE_PROPERTY_INSTANCE__OVERRIDE:
			case PropertyinstancesPackage.VALUE_PROPERTY_INSTANCE__VALUE:
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
		
		// In case we try to set the value we also want to make sure that the override attribute gets set
		if (commandClass == SetCommand.class && commandParameter.getEAttribute() == PropertyinstancesPackage.Literals.VALUE_PROPERTY_INSTANCE__VALUE) {
			Command setOverrideCommand = SetCommand.create(domain, object, InheritancePackage.Literals.IOVERRIDABLE_INHERITANCE_LINK__OVERRIDE, true);
			CompoundCommand setValueAndOverrideCommand = new CompoundCommand("Set Value and Override");
			setValueAndOverrideCommand.append(setOverrideCommand);
			setValueAndOverrideCommand.append(originalCommand);
			return setValueAndOverrideCommand;
	    }
	    		
	    
	    
	    		
	    	
		// A RolemanagementCheckCommand should not necessarily be wrapped into another RoleManagementCheck Command
		if (originalCommand instanceof RoleManagementCheckCommand) {
			return originalCommand;
		} else {
			// And wrap it into our command checking for the proper access rights
			return new RoleManagementCheckCommand(originalCommand, commandParameter);	
		}
	}

}