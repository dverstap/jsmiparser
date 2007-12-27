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
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiOptions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class IFParseTest extends TestCase {

    /**
     *
     */
    public IFParseTest() {
        super();
    }

    public void testModule_definition() throws RecognitionException, TokenStreamException, URISyntaxException, FileNotFoundException {


        URL resource = this.getClass().getResource("/libsmi-0.4.5/mibs/ietf/IF-MIB");
        File inputFile = new File(resource.toURI());
        InputStream is = new BufferedInputStream(new FileInputStream(inputFile));
        SMILexer lexer = new SMILexer(is);

        SMIParser parser = new SMIParser(lexer);
        parser.init(new SmiMib(new SmiOptions(), null), inputFile.getPath());

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
                        m_log.debug(ta.getModule().getSymbolToken() + " " + ta.getSymbolToken());
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

}
