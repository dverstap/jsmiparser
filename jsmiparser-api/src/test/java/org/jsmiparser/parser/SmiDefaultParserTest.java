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
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.file.FileParserOptions;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.util.problem.DefaultProblemEventHandler;
import org.jsmiparser.util.problem.ProblemEventHandler;

import java.io.File;

public class SmiDefaultParserTest extends TestCase {

    public void testParser() throws PhaseException {

        ProblemEventHandler problemEventHandler = new DefaultProblemEventHandler();
        SmiDefaultParser parser = new SmiDefaultParser(problemEventHandler);
        parser.init();
        FileParserOptions options = (FileParserOptions) parser.getFileParserPhase().getOptions();
        initFileParserOptions(options);

        SmiMib mib = parser.parse();
        assertNotNull(mib);
        assertEquals(216, mib.getModules().size());

        assertEquals(1522, mib.getTypes().size());

        // TODO: none of the node conversions are implemented yet
        assertEquals(0, mib.getTables().size());

        //SmiMib mib2 = parser.parse();
        //assertEquals(mib1, mib2);

        /*
        int errorCount = problemEventHandler.getSeverityCount(ProblemSeverity.ERROR);

        MibMerger mibMerger = new MibMerger(problemReporterFactory);
        SmiMib mib3 = mibMerger.merge(mib1, mib2);
        assertEquals(errorCount, problemEventHandler.getErrorCount());
        */

    }

    private void initFileParserOptions(FileParserOptions options) {
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

}
