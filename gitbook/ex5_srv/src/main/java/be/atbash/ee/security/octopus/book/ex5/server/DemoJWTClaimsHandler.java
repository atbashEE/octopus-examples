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

import be.c4j.ee.security.jwt.JWTClaimsHandler;
import be.c4j.ee.security.jwt.SCSUser;
import com.nimbusds.jwt.JWTClaimsSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@ApplicationScoped
public class DemoJWTClaimsHandler implements JWTClaimsHandler {

    @Override
    public boolean claimsAreValid(JWTClaimsSet claimsSet) {
        System.out.println("We have a chance to verify the claims");
        return true;
    }

    @Override
    public Map<String, Object> defineAdditionalUserInfo(SCSUser scsUser) {
        System.out.println("We have a chance to Add userInfo");
        return new HashMap<>();
    }
}
