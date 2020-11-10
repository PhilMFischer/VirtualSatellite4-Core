/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.server.servlet;

import javax.servlet.Servlet;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;

import de.dlr.sc.virsat.server.auth.filter.DynamicRepositoryFilterBinding;
import de.dlr.sc.virsat.server.dataaccess.TransactionalJsonProvider;
import de.dlr.sc.virsat.server.resources.AccessTestResource;
import de.dlr.sc.virsat.server.resources.AuthTestResource;
import de.dlr.sc.virsat.server.resources.ModelAccessResource;
import de.dlr.sc.virsat.server.resources.WorkspaceAccessResource;
import de.dlr.virsat.external.lib.jersey.servlet.ApplicationServletContainer;
import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.DefaultJaxrsScanner;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * This servlet registers the classes that should be provided as REST resources.
 *
 */
public class VirSatModelAccessServlet extends ApplicationServletContainer implements Servlet {
	
	@Override
	protected Servlet onCreateServlet() {
		ModelAccessRestApplication resourceConfig = new ModelAccessRestApplication();
		// Disable the default json provider
		resourceConfig.property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_SERVER, true);
		return new ServletContainer(resourceConfig);
	}

	private static class ModelAccessRestApplication extends ResourceConfig {
		/**
		 * Registers all relevant Classes: Resources, Filter and Bindings
		 */
		private ModelAccessRestApplication() {
			// Resources
			register(AccessTestResource.class);
			register(WorkspaceAccessResource.class);
			register(AuthTestResource.class);
			register(ModelAccessResource.class);

			// Registering this feature enables jetty to check for java security annotations e.g. roles allowed
			register(RolesAllowedDynamicFeature.class);
			
			// Register our RepositoryFilter via a dynamic binding
			register(DynamicRepositoryFilterBinding.class);
			
			// Register a custom json provider that extends the default moxy provider
			final TransactionalJsonProvider provider = new TransactionalJsonProvider();
			final AbstractBinder binder = new AbstractBinder() {
				@Override
				public void configure() {
					bind(provider).to(TransactionalJsonProvider.class);
				}
			};
			register(binder);
			register(TransactionalJsonProvider.class);
			
			register(ApiListingResource.class);
			register(SwaggerSerializers.class);
			
			// Set a scanner for swagger
			ScannerFactory.setScanner(new DefaultJaxrsScanner());
		}
	}
}
