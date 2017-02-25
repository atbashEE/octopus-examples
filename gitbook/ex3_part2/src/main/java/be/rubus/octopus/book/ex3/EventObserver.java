/*
 * Copyright © 2017 Rudy De Busscher (${email})
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
package be.rubus.octopus.book.ex3;

import be.c4j.ee.security.event.LogonEvent;
import be.c4j.ee.security.event.LogonFailureEvent;
import be.c4j.ee.security.event.LogoutEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.Date;

/**
 *
 */
@ApplicationScoped
public class EventObserver {

    public void onLogin(@Observes LogonEvent logonEvent) {

        System.out.println(String.format("Log in of %s at %s", logonEvent.getUserPrincipal().getName(), new Date()));
    }

    public void onLogout(@Observes LogoutEvent logoutEvent) {

        System.out.println(String.format("Log out of %s at %s", logoutEvent.getUserPrincipal().getName(), new Date()));
    }

    public void onFailure(@Observes LogonFailureEvent failureEvent) {

        System.out.println(String.format("Invalid password for %s ", failureEvent.getToken().getPrincipal()));
    }

}
