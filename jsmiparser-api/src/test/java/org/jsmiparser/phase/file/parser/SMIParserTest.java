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
package org.jsmiparser.phase.file.parser;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;

import java.io.*;


public class SMIParserTest extends TestCase {

    public void testMibs() throws FileNotFoundException, TokenStreamException, RecognitionException {
        String mibsVar = System.getenv("MIBS");
        assertNotNull(mibsVar);

        String[] mibDirs= mibsVar.split(":");
        assertEquals(4, mibDirs.length);
        for (String d : mibDirs) {
            doTestDir(new File(d));
        }
    }

	private void doTestDir(File dir) throws FileNotFoundException, RecognitionException, TokenStreamException {
        assertTrue(dir.toString(), dir.exists());
        assertTrue(dir.toString(), dir.isDirectory());

        File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			
			if (file.isFile()
					&& !file.getName().startsWith("Makefile"))
			{
				InputStream is = new BufferedInputStream(new FileInputStream(file));
				SMILexer lexer = new SMILexer(is);
				SMIParser parser = new SMIParser(lexer);
				
				parser.module_definition();
			}
		}
	}

}
