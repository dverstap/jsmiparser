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
package org.jsmiparser.phase.file.antlr;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.jsmiparser.phase.file.FileParserPhase;
import org.jsmiparser.phase.file.ModuleParser;
import org.jsmiparser.phase.file.FileParserOptions;
import org.jsmiparser.phase.lexer.LexerModule;
import org.jsmiparser.phase.lexer.LexerPhase;
import org.jsmiparser.util.problem.DefaultProblemEventHandler;
import org.jsmiparser.util.problem.DefaultProblemReporterFactory;
import org.jsmiparser.util.problem.ProblemEventHandler;
import org.jsmiparser.parser.SmiDefaultParser;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiTextualConvention;
import org.jsmiparser.smi.SmiTable;
import org.jsmiparser.smi.SmiRow;
import org.jsmiparser.smi.SmiAttribute;
import org.jsmiparser.smi.SmiConstants;
import org.jsmiparser.smi.StatusV2;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URISyntaxException;

public class IFParseTest extends TestCase {

    private static final Logger m_log = Logger.getLogger(IFParseTest.class);

    /**
     *
     */
    public IFParseTest() {
        super();
    }

    public void testModule_definition() throws RecognitionException, TokenStreamException {


        LexerModule lm = createLexerModule();

        SMIParser parser = new SMIParser(lm.createTokenStream());
        FileParserPhase fileParserPhase = new FileParserPhase(new DefaultProblemReporterFactory(new DefaultProblemEventHandler()));


        ModuleParser fileParser = new ModuleParser(fileParserPhase, lm);
        parser.init(fileParser);

        //ASNModule module =
        parser.module_definition();

        /*
                assertEquals("IF-MIB", module.getModuleName());
                assertEquals("IF-MIB", module.getName());
                assertEquals(1, module.getLocation().getLine());

        //		for (ASNAssignment a : module.getAssignments())
        //		{
        //			System.out.println(a.getLine() + ": " + a.getName());
        //		}

                ASNAssignment a = module.findAssignment("ifCompliance");
                assertNotNull(a);
                assertEquals(1741, a.getLocation().getLine());

                if (m_log.isDebugEnabled()) {
                    for (ASNAssignment ta : module.getAssignments()) {
                        m_log.debug(ta.getModule().getIdToken() + " " + ta.getIdToken());
                    }
                }
                assertEquals(99, module.getAssignments().size());

                ASNTypeAssignment ta = module.findTypeAssignment("InterfaceIndexOrZero");
                assertNotNull(ta);
                ASNType entityType = ta.getEntityType();
                assertNotNull(entityType);
                assertEquals(ASNType.Enum.SMITEXTUALCONVENTIONMACRO, entityType.getType());

                ASNTypeAssignment iiAssignment = module.findTypeAssignment("InterfaceIndex");
                SMITextualConventionMacro iiTextualConvention = (SMITextualConventionMacro) iiAssignment.getEntityType();
                ASNDefinedType iiSyntax = (ASNDefinedType) iiTextualConvention.getSyntax();
                assertEquals("Integer32", iiSyntax.getTypeAssignment().getName());
                assertEquals("SNMPv2-SMI", iiSyntax.getTypeAssignment().getModule().getName());

        //		PrintWriter out = new PrintWriter(System.out);
        //		module.print(out);
        //		assertFalse(out.checkError());
        */
    }

    private LexerModule createLexerModule() throws TokenStreamException {
        List<LexerModule> lmList = new ArrayList<LexerModule>();
        InputStream is = new BufferedInputStream(this.getClass().getResourceAsStream("/IF-MIB"));
        SMILexer lexer = new SMILexer(is);
        LexerPhase.lex(lexer, "/IF-MIB", lmList);

        assertEquals(1, lmList.size());
        return lmList.get(0);
    }

    public void test() throws URISyntaxException {

        ProblemEventHandler problemEventHandler = new DefaultProblemEventHandler();
        SmiDefaultParser parser = new SmiDefaultParser(problemEventHandler);
        parser.init();

        FileParserOptions options = (FileParserOptions) parser.getLexerPhase().getOptions();
        URL mibsURL = getClass().getClassLoader().getResource("libsmi-0.4.5/mibs");
        File mibsDir = new File(mibsURL.toURI());
        options.addFile(new File(mibsDir, "ietf/IF-MIB"));
        options.addFile(new File(mibsDir, "ietf/SNMPv2-SMI"));
        options.addFile(new File(mibsDir, "ietf/SNMPv2-TC"));
        options.addFile(new File(mibsDir, "ietf/SNMPv2-CONF"));
        options.addFile(new File(mibsDir, "ietf/SNMPv2-MIB"));
        options.addFile(new File(mibsDir, "iana/IANAifType-MIB"));
//        options.addFile(new File(mibsDir, "ietf/"));

        SmiMib mib = parser.parse();
        assertNotNull(mib);
        //showOverview(mib);

        //XStream xStream = new XStream();
        //xStream.toXML(mib, System.out);

        SmiTextualConvention interfaceIndex = (SmiTextualConvention) mib.findType("InterfaceIndex");
        assertNotNull(interfaceIndex);
        assertEquals("InterfaceIndex", interfaceIndex.getId());
        assertEquals("IF-MIB", interfaceIndex.getModule().getId());
        String source = interfaceIndex.getModule().getIdToken().getLocation().getSource();
        assertTrue(source.contains("IF-MIB"));
        assertEquals(SmiTextualConvention.class, interfaceIndex.getClass());
        assertNotNull(interfaceIndex.getRangeConstraint());
        assertEquals(1, interfaceIndex.getRangeConstraint().size());
        assertSame(SmiConstants.INTEGER_32_TYPE, interfaceIndex.getBaseType());
        assertNull(interfaceIndex.getBaseType().getBaseType());
        assertEquals("d", interfaceIndex.getDisplayHint());
        assertEquals(StatusV2.CURRENT, interfaceIndex.getStatusV2());
        assertTrue(interfaceIndex.getDescription().startsWith("A unique value"));
        assertFalse(interfaceIndex.getDescription().endsWith("\""));
        assertNull(interfaceIndex.getReference());

        SmiTable ifTable = mib.findTable("ifTable");
        assertNotNull(ifTable);
        assertEquals("1.3.6.1.2.1.2.2", ifTable.getOid());

        SmiRow ifEntry = mib.findRow("ifEntry");
        assertNotNull(ifEntry);
        assertEquals("1.3.6.1.2.1.2.2.1", ifEntry.getOid());

        SmiAttribute ifIndex = mib.findAttribute("ifIndex");
        assertNotNull(ifIndex);
        assertEquals("1.3.6.1.2.1.2.2.1.1", ifIndex.getOid());
// TODO
//        assertEquals(SmiTextualConvention.class, ifIndex.getType().getClass());
//        assertSame(interfaceIndex, ifIndex.getType());

        SmiAttribute ifAdminStatus = mib.findAttribute("ifAdminStatus");
        assertNotNull(ifAdminStatus);
        assertEquals("1.3.6.1.2.1.2.2.1.7", ifAdminStatus.getOid());

    }
}
