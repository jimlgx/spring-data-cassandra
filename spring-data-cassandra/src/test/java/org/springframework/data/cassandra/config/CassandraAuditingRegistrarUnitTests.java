/*
 * Copyright 2019-2025 the original author or authors.
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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Unit tests for {@link CassandraAuditingRegistrar}.
 *
 * @author Mark Paluch
 */
@ExtendWith(MockitoExtension.class)
class CassandraAuditingRegistrarUnitTests {

	private CassandraAuditingRegistrar registrar = new CassandraAuditingRegistrar();

	@Mock AnnotationMetadata metadata;
	@Mock BeanDefinitionRegistry registry;

	@Test // DATACASS-4
	void rejectsNullAnnotationMetadata() {
		assertThatIllegalArgumentException().isThrownBy(() -> registrar.registerBeanDefinitions(null, registry));
	}

	@Test // DATACASS-4
	void rejectsNullBeanDefinitionRegistry() {
		assertThatIllegalArgumentException().isThrownBy(() -> registrar.registerBeanDefinitions(metadata, null));
	}
}
