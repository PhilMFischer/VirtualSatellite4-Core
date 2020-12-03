/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.budget.cost.ui.snippet;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.dlr.sc.virsat.model.dvlm.qudv.AQuantityKind;
import de.dlr.sc.virsat.model.dvlm.qudv.SimpleUnit;
import de.dlr.sc.virsat.model.dvlm.qudv.SystemOfQuantities;
import de.dlr.sc.virsat.model.dvlm.qudv.util.QudvModelCommandFactory;
import de.dlr.sc.virsat.model.dvlm.qudv.util.QudvUnitHelper;
import de.dlr.sc.virsat.model.dvlm.units.UnitManagement;
import de.dlr.sc.virsat.project.editingDomain.VirSatEditingDomainRegistry;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 *  Summary of all nested equipment costs
 * 
 */
public class UiSnippetTableCostSummary extends AUiSnippetTableCostSummary {
	@Override
	protected void createButtons(FormToolkit toolkit, EditingDomain editingDomain, Composite sectionBody) {
		
		super.createButtons(toolkit, editingDomain, sectionBody);
		Button addCurrencyButton = toolkit.createButton(sectionBody, "Add Currencies to Model", SWT.PUSH);

		addCurrencyButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				VirSatTransactionalEditingDomain virSatEd = VirSatEditingDomainRegistry.INSTANCE.getEd(model);
				UnitManagement unitManagment = virSatEd.getResourceSet().getUnitManagement();
				List<SystemOfQuantities> systemOfQuantities = unitManagment.getSystemOfUnit().getSystemOfQuantities();
				AQuantityKind dimensionless = QudvUnitHelper.getInstance().getQuantityKindByName(systemOfQuantities.get(0), "Dimensionless");
				SimpleUnit euro = QudvUnitHelper.getInstance().createSimpleUnit("Euro", "€", "European Economic and Monetary Union", " ", dimensionless);
				QudvModelCommandFactory qudvController = new QudvModelCommandFactory(virSatEd);
				Command addEuroCommand = qudvController.addSimpleUnit(unitManagment, euro);
				virSatEd.getCommandStack().execute(addEuroCommand);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
}
