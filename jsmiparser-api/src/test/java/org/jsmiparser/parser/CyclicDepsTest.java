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
package org.jsmiparser.parser;

import junit.framework.TestCase;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.util.url.ClassPathURLListFactory;

import java.net.URISyntaxException;


public class CyclicDepsTest extends TestCase {

    public void testCyclicDeps() throws URISyntaxException {
        ClassPathURLListFactory urlListFactory = new ClassPathURLListFactory();
        urlListFactory.add("cyclic_deps.txt");

        SmiDefaultParser parser = new SmiDefaultParser();
        parser.getFileParserPhase().setInputUrls(urlListFactory.create());

        try {
            parser.parse();
            // TODO should implement some other cyclic dependency resolution thing fail();
        } catch (SmiException expected) {
            //expected.printStackTrace();
            assertNotNull(expected.getCause());
            //assertTrue(expected.getCause() instanceof DirectedCycleException);
        }
    }

}
