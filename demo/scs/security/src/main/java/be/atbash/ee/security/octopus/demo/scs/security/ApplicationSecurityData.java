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

import be.c4j.ee.security.model.UserPrincipal;
import be.c4j.ee.security.permission.NamedDomainPermission;
import be.c4j.ee.security.realm.AuthenticationInfoBuilder;
import be.c4j.ee.security.realm.AuthorizationInfoBuilder;
import be.c4j.ee.security.realm.SecurityDataProvider;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import javax.enterprise.context.ApplicationScoped;

/**
 * Supply the required authentication and authorization data to Octopus.
 */
@ApplicationScoped
public class ApplicationSecurityData implements SecurityDataProvider {

    private int principalId = 0;

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) {
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

            AuthenticationInfoBuilder authenticationInfoBuilder = new AuthenticationInfoBuilder();
            authenticationInfoBuilder.principalId(principalId++).name(authenticationToken.getPrincipal().toString());
            authenticationInfoBuilder.userName(authenticationToken.getPrincipal().toString());
            authenticationInfoBuilder.name(authenticationToken.getPrincipal().toString());
            // TODO: Change for production. Here we use username as password
            authenticationInfoBuilder.password(usernamePasswordToken.getUsername());

            return authenticationInfoBuilder.build();
        }

        return null;
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(PrincipalCollection principalCollection) {
        AuthorizationInfoBuilder builder = new AuthorizationInfoBuilder();
        UserPrincipal principal = (UserPrincipal) principalCollection.getPrimaryPrincipal();

        if ("admin".equalsIgnoreCase(principal.getUserName())) {
            builder.addPermission("demo:*:*");
        }
        builder.addPermission(new NamedDomainPermission("DEMO_ACCESS", "DEMO", "ACCESS", "*"));

        return builder.build();
    }

}
