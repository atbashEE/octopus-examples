/*
 * Copyright 2017-2018 Rudy De Busscher
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
package be.atbash.ee.security.octopus.book.ex6;

import be.c4j.ee.security.salt.SaltHashingUtil;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 */

@Startup
@Singleton
public class PopulateDatabase {

    @Inject
    private SaltHashingUtil saltHashingUtil;

    @Inject
    private UserBoundary userBoundary;

    @PostConstruct
    public void init() {

        User user = new User();
        user.setUserName("admin");

        byte[] salt = saltHashingUtil.nextSalt();

        user.setSalt(saltHashingUtil.base64Encode(salt));
        user.setPasswordHash(saltHashingUtil.hash("admin", salt));

        userBoundary.save(user);
    }

}
