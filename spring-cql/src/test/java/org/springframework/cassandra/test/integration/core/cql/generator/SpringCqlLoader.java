package org.springframework.cassandra.test.integration.core.cql.generator;

import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Session;

public class SpringCqlLoader extends ExternalResource {

	private static final Logger log = LoggerFactory.getLogger(SpringCqlLoader.class);

	private ClassPathCQLDataSet dataSet = null;
	private Session session;

	public SpringCqlLoader(ClassPathCQLDataSet dataSet, Session session) {
		this.dataSet = dataSet;
		this.session = session;
	}

	@Override
	protected void before() throws Throwable {

		if (dataSet == null || session == null) {
			return;
		}

		for (String query : dataSet.getCQLStatements()) {
			log.debug(String.format("Loading : %s", query));
			session.execute(query);
		}
	}
}