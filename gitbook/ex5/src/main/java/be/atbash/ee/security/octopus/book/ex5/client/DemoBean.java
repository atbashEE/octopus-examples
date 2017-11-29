/*
 * Copyright 2017 Rudy De Busscher
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
package be.atbash.ee.security.octopus.book.ex5.client;

import be.c4j.ee.security.credentials.authentication.jwt.client.JWTUserToken;
import be.c4j.ee.security.credentials.authentication.jwt.client.rest.OctopusSCSSystemRestClient;
import be.c4j.ee.security.credentials.authentication.jwt.client.rest.OctopusSCSUserRestClient;
import be.c4j.ee.security.exception.OctopusUnauthorizedException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 */
@Model
public class DemoBean {

    @Inject
    private JWTUserToken jwtUserToken;

    @Inject
    private OctopusSCSUserRestClient octopusSCSUserRestClient;

    public String getToken() {
        return jwtUserToken.createJWTUserToken(null, null);
    }

    public String getData() {

        Data data;
        try {
            data = octopusSCSUserRestClient.get("http://localhost:8080/ex5_srv/data/hello/user", Data.class);
        } catch (OctopusUnauthorizedException e) {
            data = new Data();
            data.setUserName(e.getMessage());
        }
        return data.getUserName();

    }

    public static class Data {
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

}
