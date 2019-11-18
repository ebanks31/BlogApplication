package com.blog.application.config;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriTemplate;

import com.blog.application.exception.BlogException;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

/**
 * This class sets up the configuration for Swagger API Fix for Spring Boot 2.
 */
@Configuration
@ConditionalOnProperty(name = "dirty.fix.enabled", havingValue = "true")
@Profile("!test")
public class SwaggerFixConfig {
	private final static Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

	/**
	 * Because of the new Actuator implementation in Spring Boot 2, all actuator
	 * endpoints are now dynamically mapped to a single handler method:
	 * {@link org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping.OperationHandler#handle(javax.servlet.http.HttpServletRequest, java.util.Map)}
	 * 
	 * This causes 2 issues: - Because the handler method has an @RequestBody
	 * annotated 'body' parameter, this parameter appears in all actuator endpoints
	 * as body parameter, even for GET and HEAD requests (which cannot have a
	 * request body). These endpoints cannot be executed from the Swagger UI page. -
	 * If an Actuator endpoint contains path parameters, these are not available as
	 * input fields on the Swagger UI page, because no @PathParam annotated
	 * arguments are present on the handler method.
	 * 
	 * The Swagger OperationBuilderPlugin below fixes these issues in a somewhat
	 * dirty, but effective way.
	 *
	 * @param typeResolver the type resolver
	 * @return the operation builder plugin
	 */
	@Profile("prod")
	@Bean
	public OperationBuilderPlugin operationBuilderPluginForCorrectingActuatorEndpointsProd(
			final TypeResolver typeResolver) {
		return new OperationBuilderPluginForCorrectingActuatorEndpoints(typeResolver);
	}

	@Profile("dev")
	@Bean
	public OperationBuilderPlugin operationBuilderPluginForCorrectingActuatorEndpointsDEV(
			final TypeResolver typeResolver) {
		return new OperationBuilderPluginForCorrectingActuatorEndpoints(typeResolver);
	}

	/**
	 * The Class OperationBuilderPluginForCorrectingActuatorEndpoints.
	 */
	private static class OperationBuilderPluginForCorrectingActuatorEndpoints implements OperationBuilderPlugin {

		/** The type resolver. */
		private final TypeResolver typeResolver;

		/**
		 * Instantiates a new operation builder plugin for correcting actuator
		 * endpoints.
		 *
		 * @param typeResolver the type resolver
		 */
		OperationBuilderPluginForCorrectingActuatorEndpoints(final TypeResolver typeResolver) {
			this.typeResolver = typeResolver;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * springfox.documentation.spi.service.OperationBuilderPlugin#apply(springfox.
		 * documentation.spi.service.contexts.OperationContext)
		 */
		@Override
		public void apply(final OperationContext context) {
			try {
				removeBodyParametersForReadMethods(context);
			} catch (Exception exception) {
				LOGGER.info("Exception message: {}", exception.getMessage());
			}

			addOperationParametersForPathParams(context);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.plugin.core.Plugin#supports(java.lang.Object)
		 */
		@Override
		public boolean supports(final DocumentationType delimiter) {
			return true;
		}

		/**
		 * Removes the body parameters for read methods.
		 *
		 * @param context the context
		 * @throws Exception
		 */
		private void removeBodyParametersForReadMethods(final OperationContext context) throws Exception {
			if (HttpMethod.GET.equals(context.httpMethod()) || HttpMethod.HEAD.equals(context.httpMethod())) {
				final List<Parameter> parameters = getParameters(context);
				parameters.removeIf(param -> "body".equals(param.getName()));
			}
		}

		/**
		 * Adds the operation parameters for path params.
		 *
		 * @param context the context
		 */
		private void addOperationParametersForPathParams(final OperationContext context) {
			final UriTemplate uriTemplate = new UriTemplate(context.requestMappingPattern());

			final List<Parameter> pathParams = uriTemplate.getVariableNames().stream().map(this::createPathParameter)
					.collect(Collectors.toList());

			context.operationBuilder().parameters(pathParams);
		}

		/**
		 * Creates the path parameter.
		 *
		 * @param pathParam the path param
		 * @return the parameter
		 */
		private Parameter createPathParameter(final String pathParam) {
			return new ParameterBuilder().name(pathParam).description(pathParam).required(true)
					.modelRef(new ModelRef("string")).type(typeResolver.resolve(String.class)).parameterType("path")
					.build();
		}

		/**
		 * Gets the parameters.
		 *
		 * @param context the context
		 * @return the parameters
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		private List<Parameter> getParameters(final OperationContext context) throws BlogException {
			final OperationBuilder operationBuilder = context.operationBuilder();
			try {
				Field paramField = OperationBuilder.class.getDeclaredField("parameters");
				paramField.setAccessible(true);
				return (List<Parameter>) paramField.get(operationBuilder);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new BlogException("Unable to modify parameter field!");
			}
		}
	}
}