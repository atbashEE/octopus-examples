/*
 * Copyright 2019 Rudy De Busscher
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
package be.atbash.ee.security.octopus.book.keys;

import be.atbash.ee.security.octopus.keys.AtbashKey;
import be.atbash.ee.security.octopus.keys.generator.KeyGenerator;
import be.atbash.ee.security.octopus.keys.generator.RSAGenerationParameters;
import be.atbash.ee.security.octopus.keys.reader.KeyResourceType;
import be.atbash.ee.security.octopus.keys.selector.AsymmetricPart;
import be.atbash.ee.security.octopus.keys.writer.KeyWriter;

import java.util.List;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        RSAGenerationParameters generationParameters = new RSAGenerationParameters.RSAGenerationParametersBuilder()
                .withKeyId("uniqueKeyIdentification")
                .build();

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.init();

        List<AtbashKey> keys = keyGenerator.generateKeys(generationParameters);

        AtbashKey privateKey = filterKeys(keys, AsymmetricPart.PRIVATE);

        KeyWriter keyWriter = new KeyWriter();
        byte[] privateBytes = keyWriter.writeKeyResource(privateKey, KeyResourceType.JWK, null, null);

        System.out.println("Private JWK");
        System.out.println(new String(privateBytes));

        AtbashKey publicKey = filterKeys(keys, AsymmetricPart.PUBLIC);

        byte[] pubicKeys = keyWriter.writeKeyResource(publicKey, KeyResourceType.JWK, null, null);

        System.out.println("Public JWK");
        System.out.println(new String(pubicKeys));
    }

    private static AtbashKey filterKeys(List<AtbashKey> keys, AsymmetricPart asymmetricPart) {
        AtbashKey result = null;
        for (AtbashKey key : keys) {
            if (key.getSecretKeyType().getAsymmetricPart() == asymmetricPart) {
                result = key;
            }
        }
        return result;
    }

}
