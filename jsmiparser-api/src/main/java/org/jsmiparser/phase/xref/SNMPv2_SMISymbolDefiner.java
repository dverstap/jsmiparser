/*
 * Copyright 2007 Davy Verstappen.
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
package org.jsmiparser.phase.xref;

import org.jsmiparser.smi.SmiConstants;
import org.jsmiparser.smi.SmiRange;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.util.pair.StringIntPair;
import org.jsmiparser.util.token.BigIntegerToken;

import java.util.ArrayList;
import java.util.List;

public class SNMPv2_SMISymbolDefiner extends AbstractSymbolDefiner {

    protected SNMPv2_SMISymbolDefiner() {
        super("SNMPv2-SMI");
    }


    @Override
    protected void defineSymbols() {
        super.defineSymbols();

        addOrgOid();
        addDodOid();
        addInternetOid();

        addDirectoryOid();

        addMgmtOid();
        addMib2Oid();
        addTransmissionOid();

        addExperimentalOid();

        addPrivateOid();
        addEnterprisesOid();

        addSnmpV2Oid();
        addSnmpDomainsOid();
        addSnmpProxysOid();
        addSnmpModulesOid();

        addExtUTCTimeType();

        addModuleIdentityMacro();
        addObjectIdentityMacro();

        addObjectNameType();
        addNotificationNameType();

        addObjectSyntaxType();
        addSimpleSyntaxType();
        addInteger32Type();
        addApplicationSyntaxType();

        addNetworkAddressType();

        addIpAddressType();
        addCounter32Type();
        addGauge32Type();
        addTimeTicksType();
        addOpaqueType();
        addCounter64Type();

        addObjectTypeMacro();
        addNotificationTypeMacro();

        addZeroDotZeroObjectIdentity();
    }


    private void addSnmpV2Oid() {
        addOid("snmpV2", new StringIntPair("internet"), new StringIntPair(6));
    }

    private void addSnmpDomainsOid() {
        addOid("snmpDomains", new StringIntPair("snmpV2"), new StringIntPair(1));
    }

    private void addSnmpProxysOid() {
        addOid("snmpProxys", new StringIntPair("snmpV2"), new StringIntPair(2));
    }

    private void addSnmpModulesOid() {
        addOid("snmpModules", new StringIntPair("snmpV2"), new StringIntPair(3));
    }

    private void addExtUTCTimeType() {
        if (isMissing("ExtUTCTime")) {
            SmiType type = new SmiType(idt("ExtUTCTime"), m_module);
            type.setBaseType(SmiConstants.OCTET_STRING_TYPE);
            List<SmiRange> constraints = new ArrayList<SmiRange>();
            constraints.add(new SmiRange(new BigIntegerToken(11)));
            constraints.add(new SmiRange(new BigIntegerToken(13)));
            type.setSizeConstraints(constraints);
            m_module.addSymbol(type);
        }
    }

    private void addModuleIdentityMacro() {
        addMacro("MODULE-IDENTITY");
    }

    private void addObjectIdentityMacro() {
        addMacro("OBJECT-IDENTITY");
    }

    private void addNotificationTypeMacro() {
        addMacro("NOTIFICATION-TYPE");
    }

    private void addZeroDotZeroObjectIdentity() {
        // TODO this should be SmiObjectIdentity
        addOid("zeroDotZero", new StringIntPair(0), new StringIntPair(0));
    }

}
