/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.cassandra.config;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.auditing.ReactiveIsNewAwareAuditingHandler;
import org.springframework.data.auditing.config.AuditingBeanDefinitionRegistrarSupport;
import org.springframework.data.auditing.config.AuditingConfiguration;
import org.springframework.data.cassandra.core.mapping.event.ReactiveAuditingEntityCallback;
import org.springframework.data.config.ParsingUtils;
import org.springframework.util.Assert;

/**
 * {@link ImportBeanDefinitionRegistrar} to enable {@link EnableReactiveCassandraAuditing} annotation.
 *
 * @author Mark Paluch
 * @author Christoph Strobl
 * @since 3.1
 */
class ReactiveCassandraAuditingRegistrar extends AuditingBeanDefinitionRegistrarSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableReactiveCassandraAuditing.class;
	}

	@Override
	protected String getAuditingHandlerBeanName() {
		return "reactiveCassandraAuditingHandler";
	}

	@Override
	protected void postProcess(BeanDefinitionBuilder builder, AuditingConfiguration configuration,
			BeanDefinitionRegistry registry) {

		builder.setFactoryMethod("from").addConstructorArgReference("cassandraMappingContext");
	}

	@Override
	protected BeanDefinitionBuilder getAuditHandlerBeanDefinitionBuilder(AuditingConfiguration configuration) {

		Assert.notNull(configuration, "AuditingConfiguration must not be null");

		return configureDefaultAuditHandlerAttributes(configuration,
				BeanDefinitionBuilder.rootBeanDefinition(ReactiveIsNewAwareAuditingHandler.class));
	}

	@Override
	protected void registerAuditListenerBeanDefinition(BeanDefinition auditingHandlerDefinition,
			BeanDefinitionRegistry registry) {

		Assert.notNull(auditingHandlerDefinition, "BeanDefinition must not be null");
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ReactiveAuditingEntityCallback.class);

		builder.addConstructorArgValue(ParsingUtils.getObjectFactoryBeanDefinition(getAuditingHandlerBeanName(), registry));
		builder.getRawBeanDefinition().setSource(auditingHandlerDefinition.getSource());

		registerInfrastructureBeanWithId(builder.getBeanDefinition(), ReactiveAuditingEntityCallback.class.getName(),
				registry);
	}

}
