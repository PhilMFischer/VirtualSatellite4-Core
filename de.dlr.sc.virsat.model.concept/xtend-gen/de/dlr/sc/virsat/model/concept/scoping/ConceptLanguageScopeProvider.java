/**
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package de.dlr.sc.virsat.model.concept.scoping;

import de.dlr.sc.virsat.model.concept.core.Activator;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
@SuppressWarnings("all")
public class ConceptLanguageScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {
  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    return super.getScope(context, reference);
  }
  
  @Override
  public List<ImportNormalizer> getImplicitImports(final boolean ignoreCase) {
    QualifiedName qualifiedNamespace = new IQualifiedNameConverter.DefaultImpl().toQualifiedName(Activator.getPluginId());
    ImportNormalizer coreNamespace = new ImportNormalizer(qualifiedNamespace, true, true);
    ArrayList<ImportNormalizer> implicitImports = new ArrayList<ImportNormalizer>();
    implicitImports.add(coreNamespace);
    return implicitImports;
  }
}
