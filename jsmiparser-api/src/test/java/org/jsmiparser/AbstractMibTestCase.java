/*
 * Copyright 2006 Davy Verstappen.
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
import org.apache.log4j.Logger;
import org.jsmiparser.parser.SmiDefaultParser;
import org.jsmiparser.parser.SmiParser;
import org.jsmiparser.smi.SmiConstants;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiObjectType;
import org.jsmiparser.smi.SmiOidNode;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiVersion;
import org.jsmiparser.util.url.AbstractURLListFactory;
import org.jsmiparser.util.url.ClassPathURLListFactory;
import org.springframework.util.StopWatch;

import java.net.URL;
import java.util.List;
import java.util.Set;

public abstract class AbstractMibTestCase extends TestCase {

    private static final Logger m_log = Logger.getLogger(AbstractMibTestCase.class);

    private final SmiVersion m_version;
    private final String[] m_resources;

    private static ThreadLocal<Class<? extends AbstractMibTestCase>> m_testClass = new ThreadLocal<Class<? extends AbstractMibTestCase>>();
    private static ThreadLocal<SmiMib> m_mib = new ThreadLocal<SmiMib>();

    private SmiType m_integer32;
    private SmiType m_counter;
    private SmiDefaultParser m_parser;


    public AbstractMibTestCase(SmiVersion version, String... resources) {
        m_version = version;
        m_resources = resources;
    }

    protected SmiDefaultParser getParser() {
        return m_parser;
    }

    protected SmiMib getMib() {
        // this is a rather ugly hack to mimic JUnit4 @BeforeClass, without having to annotate all test methods:
        if (m_mib.get() == null || m_testClass.get() != getClass()) {
            try {
                SmiParser parser = createParser();
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                SmiMib mib = parser.parse();
                stopWatch.stop();
                m_log.info("Parsing time: " + stopWatch.getTotalTimeSeconds() + " s");

                m_mib.set(mib);
                m_testClass.set(getClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return m_mib.get();
    }

    protected SmiParser createParser() throws Exception {
        ClassPathURLListFactory urlListFactory = new ClassPathURLListFactory("libsmi-0.4.5/mibs/ietf/");

        if (m_version == null || m_version == SmiVersion.V1) {
            urlListFactory.add("RFC1155-SMI");
        }
        if (m_version == null || m_version == SmiVersion.V2) {
            urlListFactory.add("SNMPv2-SMI");
            urlListFactory.add("SNMPv2-TC");
            urlListFactory.add("SNMPv2-CONF");
            urlListFactory.add("SNMPv2-MIB");
        }

        List<URL> urls = urlListFactory.create();
        for (String resource : getResources()) {
            urls.add(getClass().getClassLoader().getResource(resource));
        }

        m_parser = new SmiDefaultParser();
        m_parser.getFileParserPhase().setInputUrls(urls);
        return m_parser;
    }

    public final String[] getResources() {
        return m_resources;
    }

    public SmiType getInteger32() {
        if (m_integer32 == null) {
            m_integer32 = getMib().getTypes().find("Integer32");
            assertSame(SmiConstants.INTEGER_TYPE, m_integer32.getBaseType());
            assertSame(SmiPrimitiveType.INTEGER_32, m_integer32.getPrimitiveType());
            assertEquals(1, m_integer32.getRangeConstraints().size());
            assertEquals(-2147483648, m_integer32.getRangeConstraints().get(0).getMinValue().intValue());
            assertEquals(2147483647, m_integer32.getRangeConstraints().get(0).getMaxValue().intValue());
            assertNull(m_integer32.getSizeConstraints());
            assertNull(m_integer32.getEnumValues());
            assertNull(m_integer32.getBitFields());
            assertNull(m_integer32.getFields());
        }
        return m_integer32;
    }

    public SmiType getCounter() {
        if (m_counter == null) {
            m_counter = getMib().getTypes().find("Counter");
            assertSame(SmiConstants.INTEGER_TYPE, m_counter.getBaseType());
            assertSame(SmiPrimitiveType.COUNTER_32, m_counter.getPrimitiveType());
            assertEquals(1, m_counter.getRangeConstraints().size());
            assertEquals(0, m_counter.getRangeConstraints().get(0).getMinValue().intValue());
            assertEquals(0xFFFFFFFFL, m_counter.getRangeConstraints().get(0).getMaxValue().longValue());
            assertNull(m_counter.getSizeConstraints());
            assertNull(m_counter.getEnumValues());
            assertNull(m_counter.getBitFields());
            assertNull(m_counter.getFields());
        }
        return m_counter;
    }

    protected void showOverview() {
        for (SmiModule module : m_mib.get().getModules()) {
            for (SmiSymbol symbol : module.getSymbols()) {
                String msg = module.getId() + ": " + symbol.getId() + ": " + symbol.getClass().getSimpleName();
                if (symbol instanceof SmiOidValue) {
                    SmiOidValue oidValue = (SmiOidValue) symbol;
                    msg += ": " + oidValue.getOidStr();
                }
                System.out.println(msg);
            }
        }
    }

    protected void checkOidTree(SmiMib mib) {
        //mib.getRootNode().dumpTree(System.out, "");

        int count = 0;
        for (SmiSymbol symbol : mib.getSymbols()) {
            if (symbol instanceof SmiOidValue) {
                SmiOidValue oidValue = (SmiOidValue) symbol;
                //oidValue.dumpAncestors(System.out);
                if (oidValue.getNode() != mib.getRootNode()) {
                    String msg = oidValue.getIdToken().toString();
                    assertNotNull(msg, oidValue.getNode().getParent());

                    SmiOidNode foundOidNode = oidValue.getNode().getParent().findChild(oidValue.getNode().getValue());
                    assertNotNull(msg, foundOidNode);
                    assertSame(msg, oidValue.getNode(), foundOidNode);
                    assertTrue(msg, oidValue.getNode().getParent().contains(oidValue.getNode()));
                }
//                SmiOidValue foundSymbol = findOidSymbol(mib.getRootNode(), symbol.getId());
//                assertNotNull(symbol.getId(), foundSymbol);
//                assertSame(symbol.getId(), symbol, foundSymbol);
                count++;
            }
        }

        //mib.getRootNode().dumpTree(System.out, "");
        int totalChildCount = mib.getRootNode().getTotalChildCount();
        //assertTrue(count + " < " +  totalChildCount, count < totalChildCount);
        //System.out.println("totalChildCount: " + totalChildCount);

        // I don't think you can draw any conclusions based on the count:
        // - due to anonymous oid node, you can have more oid nodes than oid symbols
        // - due to duplicated symbols you can have more symbols than nodes:
        //assertEquals(count + mib.getDummyOidNodesCount(), totalChildCount);
    }

    protected void checkObjectTypeAccessAll(SmiMib mib) {
        for (SmiObjectType objectType : mib.getObjectTypes()) {
            assertNotNull(objectType.getId(), objectType.getAccessAll());
        }
    }

    protected void add(Set<String> paths, String dir, String name) {
        paths.add(dir + "/" + name);
    }
}
