/*
 * Copyright 2017 Rudy De Busscher (www.atbash.be)
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
package be.atbash.ee.security.octopus.demo.scs.security;

import be.c4j.ee.security.sso.server.client.ClientInfo;
import be.c4j.ee.security.sso.server.client.ClientInfoRetriever;

import javax.enterprise.context.ApplicationScoped;

/**
 * Reurn information about the OpenIdConnect client. Should match the URL parameters from the request.
 */
@ApplicationScoped
public class DemoClientInfo implements ClientInfoRetriever {

    @Override
    public ClientInfo retrieveInfo(String clientId) {
        // OpenIdConnect info
        ClientInfo result = null;
        if ("sso-app-clientId".equals(clientId)) {
            result = defineClientInfo("http://localhost:8080/app1");
            result.setClientSecret("EUuHTrCEXsXbIbelvzzCLp1R1WleGfu+8gGIdJ7VbAE");
            result.setIdTokenSecret("zGhImX5I2BBZQfbOJmO0HOWTLnbNLoVAtwN+h0+bC1gJZZNgmLTx+Y8uP56u/nLz");
        }
        if ("demo-clientId".equals(clientId)) {
            result = defineClientInfo("http://localhost:8080/app2");
            result.setClientSecret("szxK-5_eJjs-aUj-64MpUZ-GPPzGLhYPLGl0wrYjYNVAGva2P0lLe6UGKGM7k8dWxsOVGutZWgvmY3l5oVPO3w");
            result.setIdTokenSecret("hLG4D27gj4PBnn0IynZ72sQiHtQsONyeQLmcbsZPxdkAerRw9iu6JBUVB85zCKklr6REI7ezIWnug3UtD4QwcA");
        }
        return result;

    }

    private ClientInfo defineClientInfo(String callbackURL) {
        ClientInfo result;
        result = new ClientInfo();
        if (callbackURL != null) {
            result.setCallbackURL(callbackURL);
            result.setOctopusClient(true);
        }
        return result;
    }
}
