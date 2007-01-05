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

import org.jsmiparser.AbstractMibTestCase;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.phase.file.FileParserOptions;
import org.jsmiparser.smi.SmiAttribute;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiRow;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.smi.SmiTable;
import org.jsmiparser.smi.SmiTextualConvention;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiVarBindField;
import org.jsmiparser.util.jung.DirectedCycleException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

public class SmiDefaultParserTest extends AbstractMibTestCase {

    public SmiDefaultParserTest() {
        super(null);
    }

    public void testLibSmi() throws URISyntaxException {
        URL mibsURL = getClass().getClassLoader().getResource("libsmi-0.4.5/mibs");
        File mibsDir = new File(mibsURL.toURI());

        SmiDefaultParser parser = new SmiDefaultParser();
        FileParserOptions options = (FileParserOptions) parser.getFileParserPhase().getOptions();
        initFileParserOptions(options, mibsDir, "iana", "ietf", "site", "tubs");

        SmiMib mib = parser.parse();
        assertNotNull(mib);
        //showOverview(mib);

        //XStream xStream = new XStream();
        //xStream.toXML(mib, System.out);

        assertEquals(255, mib.getModules().size());
        assertEquals(1888, mib.getTypes().size());
        assertEquals(1265, mib.getTables().size());
        assertEquals(1265, mib.getRows().size());
        assertEquals(12590, mib.getAttributes().size());
        assertEquals(0, mib.getScalars().size());
        assertEquals(0, mib.getColumns().size());

        //checkBridgeMib(mib);
        checkIfMib(mib);

        //SmiMib mib2 = parser.parse();
        //assertEquals(mib1, mib2);

        /*
        int errorCount = problemEventHandler.getSeverityCount(ProblemSeverity.ERROR);

        MibMerger mibMerger = new MibMerger(problemReporterFactory);
        SmiMib mib3 = mibMerger.merge(mib1, mib2);
        assertEquals(errorCount, problemEventHandler.getErrorCount());
        */

        checkJobMonitoringMib(mib);
        checkDlswMib(mib);
        checkDismanPingMib(mib);

        // doesn't work yet because there are duplicates from the v1 and v2 mibs
        //checkOidTree(mib);
    }


    private void checkIfMib(SmiMib mib) {

        SmiModule ifModule = mib.findModule("IF-MIB");
        assertNotNull(ifModule);

        SmiType interfaceIndex = ifModule.findType("InterfaceIndex");
        assertNotNull(interfaceIndex);
        Collection<SmiType> interfaceIndexes = mib.findTypes("InterfaceIndex");
        assertEquals(3, interfaceIndexes.size());
        assertTrue(interfaceIndexes.contains(interfaceIndex));
        assertEquals("InterfaceIndex", interfaceIndex.getId());
        assertEquals("IF-MIB", interfaceIndex.getModule().getId());
        String source = interfaceIndex.getModule().getIdToken().getLocation().getSource();
        assertTrue(source.contains("IF-MIB"));
        assertEquals(SmiTextualConvention.class, interfaceIndex.getClass());
        assertNotNull(interfaceIndex.getRangeConstraints());
        assertEquals(1, interfaceIndex.getRangeConstraints().size());
        assertEquals(SmiVarBindField.INTEGER_VALUE, interfaceIndex.getVarBindField());

        SmiTable ifTable = ifModule.findTable("ifTable");
        assertNotNull(ifTable);
        assertEquals("1.3.6.1.2.1.2.2", ifTable.getOid());
        Collection<SmiTable> ifTables = mib.findTables("ifTable");
        assertEquals(2, ifTables.size());
        assertTrue(ifTables.contains(ifTable));

        SmiRow ifEntry = ifModule.findRow("ifEntry");
        assertNotNull(ifEntry);
        assertEquals("1.3.6.1.2.1.2.2.1", ifEntry.getOid());
        assertSame(ifEntry.getTable(), ifTable);

        SmiAttribute ifAdminStatus = ifModule.findAttribute("ifAdminStatus");
        assertNotNull(ifAdminStatus);
        assertEquals("1.3.6.1.2.1.2.2.1.7", ifAdminStatus.getOid());
    }

    private void checkJobMonitoringMib(SmiMib mib) {
        SmiOidValue jobmonMIB = (SmiOidValue) mib.findSymbol("jobmonMIB");
        assertNotNull(jobmonMIB);
        assertEquals("1.3.6.1.4.1.2699.1.1", jobmonMIB.getOid());
    }

    private void checkDlswMib(SmiMib mib) {
        SmiOidValue nullSymbol = (SmiOidValue) mib.findSymbol("null");
        assertNotNull(nullSymbol);
        assertEquals("0.0", nullSymbol.getOid());
    }

    private void checkDismanPingMib(SmiMib mib) {
        Collection<SmiSymbol> pingMIBs = mib.findSymbols("pingMIB");
        assertEquals(2, pingMIBs.size());

        SmiModule dismanPingModule = mib.findModule("DISMAN-PING-MIB");
        SmiOidValue dismanPingMIB = (SmiOidValue) dismanPingModule.findSymbol("pingMIB");
        assertNotNull(dismanPingMIB);
        assertNotNull(dismanPingMIB.getParent());
        assertEquals(mib.getRootNode(), dismanPingMIB.getRootNode());
        assertEquals(4, dismanPingMIB.getChildren().size());
        assertTrue(pingMIBs.contains(dismanPingMIB));

        //tubsPingMIB.dumpTree(System.out, "");

        SmiModule tubsPingModule = mib.findModule("TUBS-IBR-PING-MIB");
        SmiOidValue tubsPingMIB = (SmiOidValue) tubsPingModule.findSymbol("pingMIB");
        assertNotNull(tubsPingMIB);
        assertNotNull(tubsPingMIB.getParent());
        assertEquals(mib.getRootNode(), tubsPingMIB.getRootNode());
        assertEquals(2, tubsPingMIB.getChildren().size());
        assertTrue(pingMIBs.contains(tubsPingMIB));

    }

/*
    private void showOverview(SmiMib mib) {
        for (SmiModule module : mib.getModules()) {
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
*/

    private void initFileParserOptions(FileParserOptions options, File mibsDir, String... subDirNames) {
        for (String subDirName : subDirNames) {
            File dir = new File(mibsDir, subDirName);
            assertTrue(dir.toString(), dir.exists());
            assertTrue(dir.toString(), dir.isDirectory());

            options.getUsedDirSet().add(dir);
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()
                        && !file.getName().equals("RFC1158-MIB") // obsoleted by RFC-1213
                        && !file.getName().contains("TOTAL")
                        && !file.getName().endsWith("tree")
                        && !file.getName().startsWith("Makefile")
                        && !file.getName().endsWith("~")
                        //&& !v1mibs.contains(file.getName())
                        && !file.getName().endsWith("-orig")) { // TODO parsing -orig should give more errors!
                    options.addFile(file);
                }
            }
        }
    }


    public void testCyclicDeps() throws URISyntaxException {
        URL mibURL = getClass().getClassLoader().getResource("cyclic_deps.txt");
        File mibFile = new File(mibURL.toURI());

        SmiDefaultParser parser = new SmiDefaultParser();
        FileParserOptions options = (FileParserOptions) parser.getFileParserPhase().getOptions();
        options.addFile(mibFile);

        try {
            parser.parse();
            fail();
        } catch (SmiException expected) {
            //expected.printStackTrace();
            assertNotNull(expected.getCause());
            assertTrue(expected.getCause() instanceof DirectedCycleException);
        }
    }

}
