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
package be.atbash.ee.security.octopus.book.ex9;

import be.c4j.ee.security.credentials.authentication.oauth2.OAuth2User;
import be.c4j.ee.security.credentials.authentication.oauth2.info.OAuth2AuthenticationInfoBuilder;
import be.c4j.ee.security.realm.SecurityDataProvider;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import javax.enterprise.context.ApplicationScoped;
import java.util.Base64;

/**
 *
 */
@ApplicationScoped
public class ApplicationSecurityData implements SecurityDataProvider {

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) {
        if (authenticationToken instanceof OAuth2User) {
            OAuth2User user = (OAuth2User) authenticationToken;
            OAuth2AuthenticationInfoBuilder builder = new OAuth2AuthenticationInfoBuilder(user);
            return builder.build();
        }

        return null;
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
