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
package be.atbash.ee.security.octopus.book.ex5.server;

import be.c4j.ee.security.model.UserPrincipal;
import be.c4j.ee.security.realm.OctopusPermissions;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {

    @Inject
    private UserPrincipal userPrincipal;


    @Path("/user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @OctopusPermissions("demo")
    //@PermitAll
    public Data getUserData() {
        Data data = new Data();
        data.setUserName(userPrincipal.getUserName());
        return data;
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
