/*
 * Copyright 2018 Rudy De Busscher (www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.atbash.ee.security.octopus.examples;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@DataSourceDefinition(
        // global to circumvent https://java.net/jira/browse/GLASSFISH-21447
        name = "java:global/MyDS",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE"
)

@Singleton
@Startup
public class DatabaseSetup {

    @Resource(lookup = "java:global/MyDS")
    private DataSource dataSource;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void init() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);

        executeUpdate(dataSource, "DROP TABLE IF EXISTS caller");
        executeUpdate(dataSource, "DROP TABLE IF EXISTS caller_groups");

        executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS caller(name VARCHAR(64) PRIMARY KEY, password VARCHAR(255))");
        executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS caller_groups(caller_name VARCHAR(64), group_name VARCHAR(64))");

        executeUpdate(dataSource, "INSERT INTO caller VALUES('reza', '" + passwordHash.generate("secret1".toCharArray()) + "')");
        executeUpdate(dataSource, "INSERT INTO caller VALUES('alex', '" + passwordHash.generate("secret2".toCharArray()) + "')");
        executeUpdate(dataSource, "INSERT INTO caller VALUES('arjan', '" + passwordHash.generate("secret3".toCharArray()) + "')");
        executeUpdate(dataSource, "INSERT INTO caller VALUES('werner', '" + passwordHash.generate("secret4".toCharArray()) + "')");

        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('reza', 'foo')");
        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('reza', 'bar')");
        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('reza', 'role1')");

        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('alex', 'foo')");
        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('alex', 'kaz')");
        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('alex', 'role2')");

        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('arjan', 'foo')");
        executeUpdate(dataSource, "INSERT INTO caller_groups VALUES('werner', 'foo')");
    }

    @PreDestroy
    public void destroy() {
        try {
            executeUpdate(dataSource, "DROP TABLE IF EXISTS caller");
            executeUpdate(dataSource, "DROP TABLE IF EXISTS caller_groups");
        } catch (Exception e) {
            // silently ignore, concerns in-memory database
        }
    }

    private void executeUpdate(DataSource dataSource, String query) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
