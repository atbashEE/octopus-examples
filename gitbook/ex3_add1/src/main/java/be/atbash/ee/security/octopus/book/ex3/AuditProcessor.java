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
package be.atbash.ee.security.octopus.book.ex3;

import be.c4j.ee.security.audit.OctopusAuditEvent;
import be.c4j.ee.security.model.UserPrincipal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 *
 */
@ApplicationScoped
public class AuditProcessor {

    public void onPageAccess(@Observes OctopusAuditEvent auditEvent) {
        UserPrincipal principal = (UserPrincipal) auditEvent.getPrincipal();
        String userName = principal == null ? "Anonymous" : principal.getUserName();
        System.out.println(String.format("User %s accessed the resource %s from ip address %s with browser %s",
                userName, auditEvent.getRequestURI(), auditEvent.getRemoteAddress(), auditEvent.getUserAgent()));
    }
}
