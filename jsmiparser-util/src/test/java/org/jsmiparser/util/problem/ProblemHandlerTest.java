/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.util.problem;

import junit.framework.TestCase;
import org.jsmiparser.util.location.Location;

import java.util.ArrayList;
import java.util.List;

public class ProblemHandlerTest extends TestCase {
    private TestProblemEventHandler m_teh;
    private ExampleProblemReporter m_eh;

    protected void setUp() throws Exception {
        m_teh = new TestProblemEventHandler();

        ProblemReporterFactory factory = new DefaultProblemReporterFactory(this.getClass().getClassLoader(), m_teh);
        m_eh = factory.create(
                ExampleProblemReporter.class);
    }

    public void testSimpleMessage() {
        m_eh.simpleMessage();
        assertNotNull(m_teh.getLastProblemEvent());
        assertEquals("Simple message", m_teh.getLastProblemEvent().getLocalizedMessage());
        assertNull(m_teh.getLastProblemEvent().getLocation());
    }

    public void testListSize() {
        List<String> l = new ArrayList<String>();
        l.add("bla");
        m_eh.reportListSize(l);
        assertEquals("List size = 1", m_teh.getLastProblemEvent().getLocalizedMessage());
        assertNull(m_teh.getLastProblemEvent().getLocation());
    }

    public void testLocation() {
        Location location = new Location("/tmp/test", 77, 20);
        m_eh.simpleLocation(location);

        assertEquals("Simple location message", m_teh.getLastProblemEvent().getLocalizedMessage());
        assertSame(location, m_teh.getLastProblemEvent().getLocation());
    }
}
