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
package be.atbash.ee.security.octopus.examples.identitystore;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

/**
 *
 */

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:global/MyDS",
        callerQuery = "select password from caller where name = ?",
        groupsQuery = "select group_name from caller_groups where caller_name = ?",
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Iterations=3072",
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
                "Pbkdf2PasswordHash.SaltSizeBytes=64"

        }
)

@ApplicationScoped
public class CustomStore implements IdentityStore {

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            return validate((UsernamePasswordCredential) credential);
        }

        return NOT_VALIDATED_RESULT;
    }

    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
        if ("rudy".equals(usernamePasswordCredential.getCaller()) && "atbash".equals(usernamePasswordCredential.getPasswordAsString())) {
            return new CredentialValidationResult(
                    new CallerPrincipal(usernamePasswordCredential.getCaller()),
                    new HashSet<>(Arrays.asList("foo", "bar", "role1"))
            );

        }

        return INVALID_RESULT;
    }

}
