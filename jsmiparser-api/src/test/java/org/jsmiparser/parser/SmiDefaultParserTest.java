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
import org.jsmiparser.smi.SmiConstants;
import org.jsmiparser.smi.SmiIndex;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiNamedNumber;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiProtocolType;
import org.jsmiparser.smi.SmiRange;
import org.jsmiparser.smi.SmiReferencedType;
import org.jsmiparser.smi.SmiRow;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.smi.SmiTable;
import org.jsmiparser.smi.SmiTextualConvention;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiVarBindField;
import org.jsmiparser.smi.SmiVariable;
import org.jsmiparser.util.token.HexStringToken;
import org.jsmiparser.util.url.FileURLListFactory;

import java.io.File;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SmiDefaultParserTest extends AbstractMibTestCase {

    public SmiDefaultParserTest() {
        super(null);
    }

    @Override
    protected SmiDefaultParser createParser() throws Exception {
        URL mibsURL = getClass().getClassLoader().getResource("libsmi-0.4.5/mibs");
        File mibsDir = new File(mibsURL.toURI());

        SmiDefaultParser parser = new SmiDefaultParser();
        List<URL> inputUrls = initFileParserOptions(mibsDir, "iana", "ietf", "site", "tubs");
        parser.getFileParserPhase().setInputUrls(inputUrls);
        return parser;
    }

    public void testLibSmi() throws URISyntaxException {
        SmiMib mib = getMib();
        assertNotNull(mib);
        //showOverview(mib);

        // fails because of mixing of different smi versions
        checkOidTree(mib);

        //XStream xStream = new XStream();
        //xStream.toXML(mib, System.out);

        assertEquals(255, mib.getModules().size());
        assertEquals(1888, mib.getTypes().size());
        assertEquals(559, mib.getTextualConventions().size());
        assertEquals(1265, mib.getTables().size());
        assertEquals(1265, mib.getRows().size());
        assertEquals(12590, mib.getVariables().size());
        assertEquals(1465, mib.getScalars().size());
        assertEquals(11125, mib.getColumns().size());
        assertEquals(mib.getVariables().size(), mib.getScalars().size() + mib.getColumns().size());
        assertEquals(18938, mib.getOidValues().size());
        assertEquals(mib.getTables().size() + mib.getRows().size() + mib.getVariables().size(), mib.getObjectTypes().size());

        checkObjectTypeAccessAll(mib);
    }

    public void testNoCycleInStandards() {
        SmiModule rfc1155Smi = getMib().findModule("RFC1155-SMI");
        assertNotNull(rfc1155Smi);
        assertEquals(0, rfc1155Smi.getImports().size());

        SmiModule rfc1212 = getMib().findModule("RFC-1212");
        assertNotNull(rfc1212);
        assertEquals(1, rfc1212.getImports().size()); // debatable: it seems the original does have a DisplayString import
    }


    public void testBitsDefVal() {
        SmiVariable var = getMib().getColumns().find("acctngSelectionType");
        assertNotNull(var);
        assertEquals(SmiPrimitiveType.BITS, var.getPrimitiveType());

        List<SmiNamedNumber> defaultValue = var.getDefaultValue().getBitsValue();
        assertEquals(4, defaultValue.size());
        for (SmiNamedNumber nn : defaultValue) {
            assertNotNull(nn);
        }
    }

    public void testDefaultValueOid() {
        SmiMib mib = getMib();

        SmiOidValue zeroDotZero = mib.getOidValues().find("zeroDotZero");
        assertNotNull(zeroDotZero);
        assertEquals(mib.getRootNode(), zeroDotZero.getNode().getParent().getParent());
        assertEquals(2, zeroDotZero.getOid().length);
        assertEquals(0, zeroDotZero.getOid()[0]);
        assertEquals(0, zeroDotZero.getOid()[1]);

        SmiVariable mioxPeerX25CallParamId = mib.getVariables().find("mioxPeerX25CallParamId");
        assertNotNull(mioxPeerX25CallParamId);
        assertNotNull(mioxPeerX25CallParamId.getDefaultValue());
        assertNotNull(mioxPeerX25CallParamId.getDefaultValue().getOidNode());
        assertSame(zeroDotZero.getNode(), mioxPeerX25CallParamId.getDefaultValue().getOidNode());
    }

    public void testIntegerHexStringRange() {
        SmiMib mib = getMib();
        SmiTextualConvention refreshIntervalTC = mib.getTextualConventions().find("RefreshInterval");
        assertNotNull(refreshIntervalTC);

        assertEquals(1, refreshIntervalTC.getRangeConstraints().size());
        SmiRange range = refreshIntervalTC.getRangeConstraints().get(0);
        assertEquals(new BigInteger("0"), range.getMinValue());

        assertNotNull(range.getEndToken());
        assertTrue(range.getEndToken() instanceof HexStringToken);
        assertEquals(new BigInteger("7FFFFFFF", 16), range.getMaxValue());
    }

    // NetworkAddress is a very special type in SMIv1, which is a CHOICE with only one choice: IpAddress
    public void testNetworkAddressChoice() {
        SmiMib mib = getMib();
        SmiType ipAddress = mib.getTypes().find("RFC1155-SMI", "IpAddress");
        assertNotNull(ipAddress);

        SmiType networkAddress = mib.getTypes().find("RFC1155-SMI", "NetworkAddress");
        assertNotNull(networkAddress);
        assertEquals(SmiType.class, networkAddress.getClass());
        assertSame(ipAddress, networkAddress.getBaseType());

        SmiVariable atNetAddress = mib.getVariables().find("RFC1213-MIB", "atNetAddress");
        assertNotNull(atNetAddress);
        assertSame(networkAddress, atNetAddress.getType());
        assertEquals(SmiPrimitiveType.IP_ADDRESS, atNetAddress.getPrimitiveType());
    }

    public void testTypes() {
        for (SmiType type : getMib().getTypes()) {
            assertFalse(type instanceof SmiReferencedType);
            if (!(type instanceof SmiProtocolType) && type.getFields() == null) {
                assertNotNull(type.getId(), type.getPrimitiveType());
            }
            checkType(type);
        }

        for (SmiVariable variable : getMib().getVariables()) {
            assertNotNull(variable.getId(), variable.getType());
            assertFalse(variable.getId(), variable.getType() instanceof SmiReferencedType);
            assertFalse(variable.getId(), variable.getType() instanceof SmiProtocolType);
            assertNotNull(variable.getId(), variable.getType().getPrimitiveType());
            checkType(variable.getType());
        }
    }

    private void checkType(SmiType type) {
        if (type.getBaseType() != null) {
            checkType(type.getBaseType());
        } else if (type.getFields() == null && !(type instanceof SmiProtocolType)) {
            assertTrue(type.getId(),
                    type == SmiConstants.BITS_TYPE
                            || type == SmiConstants.INTEGER_TYPE
                            || type == SmiConstants.OBJECT_IDENTIFIER_TYPE
                            || type == SmiConstants.OCTET_STRING_TYPE);
        }
    }

    public void testIpNetToMediaNetAddress() {
        SmiVariable var = getMib().getVariables().find("IP-MIB", "ipNetToMediaNetAddress");
        assertNotNull(var);
        assertEquals(SmiPrimitiveType.IP_ADDRESS, var.getPrimitiveType());
    }

    public void testAtmAcctngRecordCrc16() {
        SmiVariable var = getMib().getVariables().find("atmAcctngRecordCrc16");
        assertNotNull(var);
        assertEquals(SmiPrimitiveType.OCTET_STRING, var.getPrimitiveType());
        assertNotNull(var.getSizeConstraints());


    }

    public void testFindMethods() {
        SmiMib mib = getMib();
        List<SmiSymbol> pingMibs = mib.getSymbols().findAll("pingMIB");
        assertEquals(2, pingMibs.size());

        try {
            mib.getSymbols().find("pingMIB");
            fail();
        } catch (IllegalArgumentException expected) {
            // do nothing comment to shut up IntelliJ
        }

        try {
            mib.getSymbols().find(null, "pingMIB");
            fail();
        } catch (IllegalArgumentException expected) {
            // do nothing comment to shut up IntelliJ
        }

        try {
            mib.getSymbols().find("IF-MIBBBBB", "pingMIB");
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        assertNull(mib.getSymbols().find("IF-MIB", "pingMIB"));

        SmiOidValue dismanPingMIB = mib.getOidValues().find("DISMAN-PING-MIB", "pingMIB");
        assertNotNull(dismanPingMIB);
        assertEquals("DISMAN-PING-MIB", dismanPingMIB.getModule().getId());
        assertEquals(80, dismanPingMIB.getLastOidComponent().getValueToken().getValue());

        SmiOidValue tubsIbrPingMIB = mib.getOidValues().find("TUBS-IBR-PING-MIB", "pingMIB");
        assertNotNull(tubsIbrPingMIB);
        assertEquals("TUBS-IBR-PING-MIB", tubsIbrPingMIB.getModule().getId());
        assertEquals(8, tubsIbrPingMIB.getLastOidComponent().getValueToken().getValue());
    }

    public void testUnits() {
        SmiModule ipModule = getMib().findModule("IP-MIB");
        SmiVariable ipReasmTimeout = ipModule.findVariable("ipReasmTimeout");
        assertNotNull(ipReasmTimeout);
        assertNotNull(ipReasmTimeout.getUnitsToken());
        assertEquals("seconds", ipReasmTimeout.getUnits());
    }


    public void testIfMib() {
        SmiMib mib = getMib();
        SmiModule ifModule = mib.findModule("IF-MIB");
        assertNotNull(ifModule);

        SmiType interfaceIndex = ifModule.findType("InterfaceIndex");
        assertNotNull(interfaceIndex);
        Collection<SmiType> interfaceIndexes = mib.getTypes().findAll("InterfaceIndex");
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
        assertEquals("1.3.6.1.2.1.2.2", ifTable.getOidStr());
        Collection<SmiTable> ifTables = mib.getTables().findAll("ifTable");
        assertEquals(2, ifTables.size());
        assertTrue(ifTables.contains(ifTable));

        SmiRow ifEntry = ifModule.findRow("ifEntry");
        assertNotNull(ifEntry);
        assertEquals("1.3.6.1.2.1.2.2.1", ifEntry.getOidStr());
        assertSame(ifEntry.getTable(), ifTable);

        SmiVariable ifAdminStatus = ifModule.findVariable("ifAdminStatus");
        assertNotNull(ifAdminStatus);
        assertEquals("1.3.6.1.2.1.2.2.1.7", ifAdminStatus.getOidStr());
    }

    public void testJobMonitoringMib() {
        SmiOidValue jobmonMIB = getMib().getOidValues().find("jobmonMIB");
        assertNotNull(jobmonMIB);
        assertEquals("1.3.6.1.4.1.2699.1.1", jobmonMIB.getOidStr());
    }

    public void testDlswMib() {
        SmiOidValue nullSymbol = getMib().getOidValues().find("null");
        assertNotNull(nullSymbol);
        assertEquals("0.0", nullSymbol.getOidStr());
    }

    public void testDismanPingMib() {
        SmiMib mib = getMib();
        Collection<SmiSymbol> pingMIBs = mib.getSymbols().findAll("pingMIB");
        assertEquals(2, pingMIBs.size());

        SmiModule dismanPingModule = mib.findModule("DISMAN-PING-MIB");
        SmiOidValue dismanPingMIB = dismanPingModule.findOidValue("pingMIB");
        assertNotNull(dismanPingMIB);
        assertNotNull(dismanPingMIB.getNode().getParent());
        assertEquals(mib.getRootNode(), dismanPingMIB.getNode().getRootNode());
        assertEquals(4, dismanPingMIB.getNode().getChildren().size());
        assertTrue(pingMIBs.contains(dismanPingMIB));

        //tubsPingMIB.dumpTree(System.out, "");

        SmiModule tubsPingModule = mib.findModule("TUBS-IBR-PING-MIB");
        SmiOidValue tubsPingMIB = tubsPingModule.findOidValue("pingMIB");
        assertNotNull(tubsPingMIB);
        assertNotNull(tubsPingMIB.getNode().getParent());
        assertEquals(mib.getRootNode(), tubsPingMIB.getNode().getRootNode());
        assertEquals(2, tubsPingMIB.getNode().getChildren().size());
        assertTrue(pingMIBs.contains(tubsPingMIB));

    }

    public void testComplexIndex() {
        SmiMib mib = getMib();
        for (SmiRow row : mib.getRows()) {
            if (row.getAugments() == null) {
                assertNotNull(row.getIndexes());
                assertTrue(row.getId(), !row.getIndexes().isEmpty());
            }
        }

        SmiRow row = mib.getRows().find("mallocScopeNameEntry");
        assertNotNull(row);
        assertEquals(3, row.getIndexes().size());
        SmiIndex index1 = row.getIndexes().get(0);
        SmiIndex index2 = row.getIndexes().get(1);
        SmiIndex index3 = row.getIndexes().get(2);


        assertEquals("mallocScopeAddressType", index1.getColumn().getId());
        assertFalse(index1.isImplied());
        assertNotNull(index1.getColumn().getRow());
        assertNotSame(row, index1.getColumn().getRow());

        assertEquals("mallocScopeFirstAddress", index2.getColumn().getId());
        assertFalse(index2.isImplied());
        assertNotNull(index2.getColumn().getRow());
        assertNotSame(row, index2.getColumn().getRow());

        assertEquals("mallocScopeNameLangName", index3.getColumn().getId());
        assertTrue(index3.isImplied());
        assertNotNull(index3.getColumn().getRow());
        assertSame(row, index3.getColumn().getRow());

    }

    private List<URL> initFileParserOptions(File mibsDir, String... subDirNames) throws Exception {
        List<URL> result = new ArrayList<URL>();

        for (String subDirName : subDirNames) {
            File dir = new File(mibsDir, subDirName);
            assertTrue(dir.toString(), dir.exists());
            assertTrue(dir.toString(), dir.isDirectory());

            FileURLListFactory urlListFactory = new FileURLListFactory(dir);
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
                    urlListFactory.add(file.getName());
                }
            }
            result.addAll(urlListFactory.create());
        }
        return result;
    }


}
