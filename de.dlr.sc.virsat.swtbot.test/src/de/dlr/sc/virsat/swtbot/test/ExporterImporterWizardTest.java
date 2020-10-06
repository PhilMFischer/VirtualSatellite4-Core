/*******************************************************************************
 * Copyright (c) 2020 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.swtbot.test;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertText;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

import de.dlr.sc.virsat.commons.file.VirSatFileUtils;
import de.dlr.sc.virsat.model.extension.funcelectrical.model.InterfaceEnd;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;

public class ExporterImporterWizardTest extends ASwtBotTestCase {
	
	private static final String TEST_EXPORT_FOLDER = "SWTBOT_TEST_EXPORT_FILES";
	private Path exportFolderPath;
	
	@Override
	public void before() throws Exception {
		super.before();
		exportFolderPath = VirSatFileUtils.createAutoDeleteTempDirectory(TEST_EXPORT_FOLDER);
	}
	
	@Test
	public void testExcelExportImportFEA() {
		SWTBotTreeItem repositoryNavigatorItem = bot.tree().expandNode(SWTBOT_TEST_PROJECTNAME, "Repository");
		SWTBotTreeItem ct = addElement(ConfigurationTree.class, conceptPs, repositoryNavigatorItem);
		SWTBotTreeItem ec = addElement(ElementConfiguration.class, conceptPs, ct);
		SWTBotTreeItem interfaceEnd = addElement(InterfaceEnd.class, conceptFea, ec);
		openEditor(interfaceEnd);
		
		// Open the export menu
		bot.menu("File").menu("Export...").click();
		waitForEditingDomainAndUiThread();
		
		// Go to the Virtual Satellite category and select the exporter
		SWTBotTreeItem virSatExporters = bot.tree().getTreeItem("Virtual Satellite");
		virSatExporters.expand();
		virSatExporters.getNode("Excel Export Wizard").select();
		bot.button("Next >").click();
		waitForEditingDomainAndUiThread();
		
		// Configure the export
		SWTBotTreeItem wizardEC = bot.tree()
				.expandNode(SWTBOT_TEST_PROJECTNAME, "Repository", "CT: ConfigurationTree", "EC: ElementConfiguration");
		wizardEC.select();
		bot.checkBox("Use default template").click();
		bot.comboBox().setText(exportFolderPath.toString());
		
		// Perform the export
		bot.button("Finish").click();
		
		// Cause a change
		assertText(InterfaceEnd.class.getSimpleName(), bot.textWithLabel("Name"));
		rename(interfaceEnd, "changedName");
		assertText("changedName", bot.textWithLabel("Name"));
		
		// Assert that we correctly exported a file
		File excelExportFile = exportFolderPath.resolve("ConfigurationTree.ElementConfiguration.xlsx").toFile();
		assertTrue("A file has been successfully created.", excelExportFile.exists());
		
		// Open the import menu, workaround to shells sometimes not being valid anymore after closing a wizard
		// See https://wiki.eclipse.org/SWTBot/Troubleshooting#WidgetNotFoundException_when_stepping_through_SWTBot_test_in_Eclipse_debugger for details
		bot.shell().activate().bot().menu("File").menu("Import...").click();
		waitForEditingDomainAndUiThread();
		
		// Go to the Virtual Satellite category and select the importer
		SWTBotTreeItem virSatImporters = bot.tree().getTreeItem("Virtual Satellite");
		virSatImporters.expand();
		virSatImporters.getNode("Excel Import Wizard").select();
		bot.button("Next >").click();
		waitForEditingDomainAndUiThread();
		
		// Configure the import
		wizardEC = bot.tree()
				.expandNode(SWTBOT_TEST_PROJECTNAME, "Repository", "CT: ConfigurationTree", "EC: ElementConfiguration");
		wizardEC.select();
		bot.comboBox().setText(excelExportFile.getPath());
		
		// Perform the import
		bot.button("Finish").click();
		
		// Check that the imported name has been applied
		assertText(InterfaceEnd.class.getSimpleName(), bot.textWithLabel("Name"));
	}
	
}