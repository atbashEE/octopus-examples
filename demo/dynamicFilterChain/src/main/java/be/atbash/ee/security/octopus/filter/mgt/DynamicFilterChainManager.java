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
package be.atbash.ee.security.octopus.filter.mgt;

import be.atbash.ee.security.MaintenanceBean;
import be.atbash.ee.security.octopus.filter.PathMatchingFilter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

/**
 *
 */
@ApplicationScoped
@Specializes
public class DynamicFilterChainManager extends FilterChainManager {

    @Inject
    private MaintenanceBean maintenanceBean;

    private NamedFilterList maintenanceFilterList;

    @Override
    protected void defineChains() {
        // As an example, chain definitions can come from everywhere.
        createChain("/pages/maintenance.xhtml", "np[chain:update:*]");
        createChain("/pages/**", "user");

        createChain("/logout", "logout"); // For the logout functionality

        // Define our special filter list
        // TODO we should also add ef in front of this list
        maintenanceFilterList = new NamedFilterList("maintenance");
        PathMatchingFilter maintenance = (PathMatchingFilter) getFilter("maintenance");
        maintenanceFilterList.add(maintenance);

    }

    @Override
    public NamedFilterList getChain(String chainName) {
        if (!maintenanceBean.isMaintenanceActive() || "/logout".equals(chainName)) {
            // No maintenance of logout URL
            return super.getChain(chainName);
        } else {
            return maintenanceFilterList;
        }
    }
}
