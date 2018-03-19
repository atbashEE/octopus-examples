/*
 * Copyright 2018 Rudy De Busscher
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
package be.atbash.ee.security.octopus.book.ex8;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;

/**
 * Starts up the embedded Unboundid LDAP server on port 33389 and loads a test directory
 * into it containing the same caller- and roles names as the Database and Embedded idenity
 * stores are using.
 * 
 * @author Arjan Tijms
 *
 */
@Startup
@Singleton
public class LdapSetup {
    
    private InMemoryDirectoryServer directoryServer;

    @PostConstruct
    public void init() {
        try {
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=be");
            config.setListenerConfigs(
                new InMemoryListenerConfig("myListener", null, 33389, null, null, null));

            directoryServer = new InMemoryDirectoryServer(config);

            directoryServer.importFromLDIF(true, 
                new LDIFReader(this.getClass().getResourceAsStream("/test.ldif")));

            directoryServer.startListening();
        } catch (LDAPException e) {
            throw new IllegalStateException(e);
        }
    }
    
    @PreDestroy
    public void destroy() {
        directoryServer.shutDown(true);
    }
    
}
