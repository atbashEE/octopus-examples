/*
 * Copyright 2018 Rudy De Busscher (www.atbash.be)
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
package be.atbash.ee.security.octopus.filter;

import be.atbash.ee.security.octopus.SecurityUtils;
import be.atbash.ee.security.octopus.config.OctopusJSFConfiguration;
import be.atbash.ee.security.octopus.subject.WebSubject;
import be.atbash.ee.security.octopus.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 */
@ApplicationScoped
public class MaintenanceFilter extends AccessControlFilter {

    @Inject
    private OctopusJSFConfiguration jsfConfiguration;

    @PostConstruct
    public void init() {
        setName("maintenance"); // every filter should have a name, but here it is not used.
        setLoginUrl(jsfConfiguration.getLoginPage()); // Used by the saveRequestAndRedirectToLogin call further on.
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        WebSubject subject = SecurityUtils.getSubject();
        // When maintenance filter is active, only users having this permission should be allowed access to pages.
        return subject.isPermitted("maintenance:access:*");
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        WebSubject subject = SecurityUtils.getSubject();
        if (!(subject.isAuthenticated() || subject.isRemembered())) {
            // Otherwise a user which has the needed permissions can never be login.
            // This of course also allow other users to logon.
            saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        } else {
            // Authenticated, so access is denied for the moment, show the maintenance page.
            WebUtils.issueRedirect(servletRequest, servletResponse, "/maintenance.xhtml");
        }
        return false;
    }
}
