/*
 * Copyright 2005 Davy Verstappen.
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
import org.apache.log4j.Logger;

public class SmiParserTest extends TestCase {
    private static final Logger m_log = Logger.getLogger(SmiParserTest.class);

    // TODO
    public void testSomething() {
        assertTrue(true);
    }

    /*
    public void testParser() {

        DefaultProblemHandler problemHandler = new DefaultProblemHandler();
        ProblemReporterFactory problemReporterFactory = new DefaultProblemReporterFactory(problemHandler);

        SmiDefaultParser parser = new SmiDefaultParser();

        FileParserPhase fileParserPhase = new FileParserPhase(problemReporterFactory);
        FileParserOptions fileParserOptions = fileParserPhase.getOptions();
        fileParserOptions.setFileParserClass(AntlrFileParser.class);
        parser.addPhase(fileParserPhase);

        OidResolverPhase oidResolverPhase = new OidResolverPhase(problemReporterFactory);
        parser.addPhase(oidResolverPhase);

        MibBuilderPhase mibBuilderPhase = new MibBuilderPhase(problemReporterFactory);
        parser.addPhase(mibBuilderPhase);

        MibQualityCheckerPhase mibQualityCheckerPhase = new MibQualityCheckerPhase(problemReporterFactory);
        parser.addPhase(mibQualityCheckerPhase);

        ConceptualModelBuilderPhase cmBuilderPhase = new ConceptualModelBuilderPhase(problemReporterFactory);
        parser.addPhase(cmBuilderPhase);

        SmiMib mib1 = parser.parse();
        SmiMib mib2 = parser.parse();
        assertEquals(mib1, mib2);

        int errorCount = problemHandler.getErrorCount();
        MibMerger mibMerger = new MibMerger(problemReporterFactory);
        SmiMib mib3 = mibMerger.merge(mib1, mib2);
        assertEquals(errorCount, problemHandler.getErrorCount());


    }

    private void initFileParserOptions(FileParserOptions options) throws FileNotFoundException, RecognitionException, TokenStreamException {
        String mibsVar = System.getenv("MIBS");
        assertNotNull(mibsVar);
        String[] mibDirs = mibsVar.split(":");
        assertEquals(4, mibDirs.length);
        for (String d : mibDirs) {
            File dir = new File(d);
            assertTrue(dir.toString(), dir.exists());
            assertTrue(dir.toString(), dir.isDirectory());

            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];

                if (file.isFile()
                        && !file.getName().startsWith("Makefile")) {
                    options.addFile(file);
                }
            }
        }
    }
    */
}
