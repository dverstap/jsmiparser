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
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiConstants;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.util.problem.ProblemEventHandler;
import org.jsmiparser.util.problem.DefaultProblemEventHandler;
import org.jsmiparser.parser.SmiDefaultParser;
import org.jsmiparser.phase.file.FileParserOptions;

import java.net.URL;
import java.net.URISyntaxException;
import java.io.File;

public abstract class MibTestCase extends TestCase {

    private SmiMib m_mib;

    private SmiType m_integer32;

    protected SmiMib getMib() {
        if (m_mib == null) {
            // this isn't entirely clean; but it will be better with JUnit 4 @BeforeClass
            parseMib();
        }
        return m_mib;
    }

    private void parseMib() {
        ProblemEventHandler problemEventHandler = new DefaultProblemEventHandler();
        SmiDefaultParser parser = new SmiDefaultParser(problemEventHandler);
        parser.init();

        FileParserOptions options = (FileParserOptions) parser.getLexerPhase().getOptions();


        URL mibsURL = getClass().getClassLoader().getResource("libsmi-0.4.5/mibs/ietf");
        File mibsDir = null;
        try {
            mibsDir = new File(mibsURL.toURI());
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        }
        options.addFile(new File(mibsDir, "SNMPv2-SMI"));
        options.addFile(new File(mibsDir, "SNMPv2-TC"));
        options.addFile(new File(mibsDir, "SNMPv2-CONF"));
        options.addFile(new File(mibsDir, "SNMPv2-MIB"));

        for (String resource : getResources()) {
            URL mibURL = getClass().getClassLoader().getResource(resource);
            try {
                File mibFile = new File(mibURL.toURI());
                options.addFile(mibFile);
            } catch (URISyntaxException e) {
                fail(e.getMessage());
            }
        }
        m_mib = parser.parse();

    }

    public abstract String[] getResources();

    public SmiType getInteger32() {
        if (m_integer32 == null) {
            m_integer32 = getMib().findType("Integer32");
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

    protected void showOverview() {
        for (SmiModule module : m_mib.getModules()) {
            for (SmiSymbol symbol : module.getSymbols()) {
                String msg = module.getId() + ": " + symbol.getId() + ": " + symbol.getClass().getSimpleName();
                if (symbol instanceof SmiOidValue) {
                    SmiOidValue oidValue = (SmiOidValue) symbol;
                    msg += ": " + oidValue.getOid();
                }
                System.out.println(msg);
            }
        }
    }

}
