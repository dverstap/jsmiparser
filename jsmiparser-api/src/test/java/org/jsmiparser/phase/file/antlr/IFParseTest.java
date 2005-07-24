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
import org.jsmiparser.parsetree.asn1.ASNAssignment;
import org.jsmiparser.parsetree.asn1.ASNModule;

import java.io.InputStream;

/**
 * @author davy
 *
 */
public class IFParseTest extends TestCase {

	/**
	 * 
	 */
	public IFParseTest() {
		super();
	}

	public void testModule_definition() throws RecognitionException, TokenStreamException {
		
		InputStream is = this.getClass().getResourceAsStream("/IF-MIB");
		
		SMILexer lexer = new SMILexer(is);
		SMIParser parser = new SMIParser(lexer);
        parser.setSource("/IF-MIB");

        ASNModule module = parser.module_definition();
		
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
		
//		PrintWriter out = new PrintWriter(System.out);
//		module.print(out);
//		assertFalse(out.checkError());
	}
}
