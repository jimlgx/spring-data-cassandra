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
package org.springframework.data.cassandra.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

// tag::class[]
@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

	/*
	 * Provide a contact point to the configuration.
	 */
	@Override
	public String getContactPoints() {
		return "localhost";
	}

	/*
	 * Provide a keyspace name to the configuration.
	 */
	@Override
	public String getKeyspaceName() {
		return "mykeyspace";
	}
}
// end::class[]
