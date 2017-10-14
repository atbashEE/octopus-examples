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

import be.c4j.ee.security.permission.NamedDomainPermission;
import be.c4j.ee.security.sso.OctopusSSOUser;
import be.c4j.ee.security.sso.server.endpoint.SSOPermissionProvider;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Class defines the permissions which are transferred to the SCS client.
 */
@ApplicationScoped
public class DemoSSOPermissionProvider implements SSOPermissionProvider {


    @Override
    public List<NamedDomainPermission> getPermissionsForApplication(String applicationName) {
        // Permissions for the application, user independent.
        List<NamedDomainPermission> result = new ArrayList<>();

        result.add(new NamedDomainPermission("DEMO_ACCESS", "demo:access:*"));
        result.add(new NamedDomainPermission("DEMO_WRITE", "demo:write:*"));
        result.add(new NamedDomainPermission("SECURITY_ADMIN", "security:admin:*"));
        return result;
    }

    @Override
    public List<NamedDomainPermission> getPermissionsForUserInApplication(String applicationName, OctopusSSOUser octopusSSOUser) {
        // Permissions for the user within that application.
        List<NamedDomainPermission> result = new ArrayList<>();

        if ("admin".equalsIgnoreCase(octopusSSOUser.getUserName())) {
            result.add(new NamedDomainPermission("DEMO_ACCESS", "demo:access:*"));
            result.add(new NamedDomainPermission("DEMO_WRITE", "demo:write:*"));
            result.add(new NamedDomainPermission("SECURITY_ADMIN", "security:admin:*"));
        }
        if ("rudy".equalsIgnoreCase(octopusSSOUser.getUserName())) {
            result.add(new NamedDomainPermission("DEMO_ACCESS", "demo:access:*"));
            result.add(new NamedDomainPermission("DEMO_WRITE", "demo:write:*"));
        }
        if ("test".equalsIgnoreCase(octopusSSOUser.getUserName())) {
            result.add(new NamedDomainPermission("DEMO_ACCESS", "demo:access:*"));
        }

        return result;
    }

}
