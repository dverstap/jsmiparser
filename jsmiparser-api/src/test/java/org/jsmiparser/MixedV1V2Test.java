/*
 * Copyright 2007 Davy Verstappen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsmiparser;

import junit.framework.TestCase;
import org.jsmiparser.parser.SmiDefaultParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MixedV1V2Test extends TestCase {

    public void testToV2() throws MalformedURLException {
        String[] mibFileNames = { "TOKEN-RING-RMON-MIB", "RFC1271-MIB", "SNMPv2-SMI", "SNMPv2-TC"};
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource(AbstractMibTestCase.LIBSMI_MIBS_URL + "/ietf");
        assertNotNull(resource);
        List<URL> urls = new ArrayList<URL>();
        for (String fn : mibFileNames) {
            URL url = new URL(resource + "/" + fn);
            urls.add(url);
        }

        SmiDefaultParser parser = new SmiDefaultParser();
        parser.getOptions().setConvertV1ImportsToV2(true);
        parser.getFileParserPhase().setInputUrls(urls);
        //URL fixerUrl = PropertiesXRefFixer.class.getResource("v1_to_v2.properties");
        //assertNotNull(fixerUrl);
        //parser.getXRefPhase().setFixer(new PropertiesXRefFixer(fixerUrl));

        parser.parse();

        assertEquals(0, parser.getProblemEventHandler().getTotalCount());

    }

}
