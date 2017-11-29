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
package be.atbash.ee.security.octopus.book.ex5.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 *
 */

public class DebugClientResponseFilter implements ClientResponseFilter {

    private Logger logger = LoggerFactory.getLogger(DebugClientResponseFilter.class);

    private final int maxEntitySize = 1024 * 8;

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (responseContext.hasEntity()) {
            responseContext.setEntityStream(logInboundEntity(responseContext.getEntityStream()));
        }
    }

    private InputStream logInboundEntity(InputStream stream) throws IOException {
        StringBuilder responseBody = new StringBuilder();
        InputStream logStream = stream;
        if (!stream.markSupported()) {
            logStream = new BufferedInputStream(stream);
        }
        logStream.mark(maxEntitySize + 1);
        final byte[] entity = new byte[maxEntitySize + 1];
        final int entitySize = logStream.read(entity);
        responseBody.append(new String(entity, 0, Math.min(entitySize, maxEntitySize), Charset.defaultCharset()));
        if (entitySize > maxEntitySize) {
            responseBody.append("...more...");
        }
        logStream.reset();

        logger.info("REST call body content " + responseBody.toString());
        return logStream;
    }
}
