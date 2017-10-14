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
package be.atbash.ee.security.octopus.demo.scs.app1;

import be.c4j.ee.security.credentials.authentication.jwt.client.rest.OctopusSCSSystemRestClient;
import be.c4j.ee.security.model.UserPrincipal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * CDI bean to show the current logged on user and result from the REST call.
 */
@RequestScoped
@Named
public class UserBean {

    @Inject
    private UserPrincipal userPrincipal;

    @Inject
    //private OctopusSCSUserRestClient restClient;
    private OctopusSCSSystemRestClient restClient;

    public String getUserName() {

        TestData testData = restClient.get("http://localhost:8080/app2/data/hello", TestData.class);
        return userPrincipal.getUserName() + " - remote = " + testData.getName();
    }

    public static class TestData {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
