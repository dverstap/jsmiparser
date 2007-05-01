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

import org.jsmiparser.smi.OidComponent;
import org.jsmiparser.smi.SmiMacro;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiProtocolType;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiConstants;
import org.jsmiparser.smi.SmiRange;
import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.pair.StringIntPair;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.IntegerToken;
import org.jsmiparser.util.token.BigIntegerToken;

import java.util.Collections;

public abstract class AbstractSymbolDefiner implements SymbolDefiner {

    protected String m_moduleId;
    protected SmiModule m_module;

    protected boolean m_defineItu;
    protected boolean m_defineIso;

    protected AbstractSymbolDefiner(String moduleId) {
        m_moduleId = moduleId;
    }

    public String getModuleId() {
        return m_moduleId;
    }

    public void setModuleId(String moduleId) {
        m_moduleId = moduleId;
    }

    public void defineSymbols(SmiModule module) {
        m_module = module;
        defineSymbols();
        m_module = null;
    }

    protected void defineSymbols() {
        if (m_defineIso) {
            addIsoOid();
        }

    }

    public void addItuOid() {
        addOid("itu", new StringIntPair(0));
    }

    public void addIsoOid() {
        addOid("iso", new StringIntPair(1));
    }

    public void addOrgOid() {
        addOid("org", new StringIntPair("iso", 3));
    }

    public void addDodOid() {
        addOid("dod", new StringIntPair("org", 1));
    }

    public void addInternetOid() {
        addOid("internet", new StringIntPair("iso"), new StringIntPair("org", 3), new StringIntPair("dod", 6), new StringIntPair(1));
    }


    public void addDirectoryOid() {
        addOid("directory", new StringIntPair("internet"), new StringIntPair(1));
    }

    public void addMgmtOid() {
        addOid("mgmt", new StringIntPair("internet"), new StringIntPair(2));
    }

    public void addMib2Oid() {
        addOid("mib-2", new StringIntPair("mgmt"), new StringIntPair(1));
    }

    public void addTransmissionOid() {
        addOid("transmission", new StringIntPair("mib-2"), new StringIntPair(10));
    }

    public void addExperimentalOid() {
        addOid("experimental", new StringIntPair("internet"), new StringIntPair(3));
    }

    public void addPrivateOid() {
        addOid("private", new StringIntPair("internet"), new StringIntPair(4));
    }

    public void addEnterprisesOid() {
        addOid("enterprises", new StringIntPair("private"), new StringIntPair(1));
    }

    public void addOid(String id, StringIntPair... oidComponents) {
        if (isMissing(id)) {
            SmiOidValue oidValue = new SmiOidValue(idt(id), m_module);
            OidComponent oc = null;
            for (StringIntPair oidComponent : oidComponents) {
                IdToken idToken = oidComponent.getString() != null ? idt(oidComponent.getString()) : null;
                IntegerToken intToken = oidComponent.getInt() != null ? intt(oidComponent.getInt()) : null;
                oc = new OidComponent(oc, idToken, intToken);
            }
            oidValue.setLastOidComponent(oc);
            m_module.addSymbol(oidValue);
        }
    }

    public boolean isMissing(String id) {
        for (SmiSymbol symbol : m_module.getSymbols()) {
            if (id.equals(symbol.getId())) {
                return false;
            }
        }
        return true;
    }

    public IdToken idt(String id) {
        return new IdToken(location(), id);
    }

    public IntegerToken intt(int value) {
        return new IntegerToken(location(), value);
    }

    public Location location() {
        //return SmiConstants.LOCATION;
        return null;
    }

    public void addObjectTypeMacro() {
        addMacro("OBJECT-TYPE");
    }

    public void addTrapTypeMacro() {
        addMacro("TRAP-TYPE");
    }

    public void addMacro(String id) {
        if (isMissing(id)) {
            SmiMacro macro = new SmiMacro(idt(id), m_module);
            m_module.addSymbol(macro);
        }
    }

    public void addObjectSyntaxType() {
        addChoiceType("ObjectSyntax");
    }

    public void addSimpleSyntaxType() {
        addChoiceType("SimpleSyntax");
    }

    public void addApplicationSyntaxType() {
        addChoiceType("ApplicationSyntax");
    }

    public void addIndexSyntaxType() {
        addChoiceType("IndexSyntax");
    }

    public void addNetworkAddressType() {
        addChoiceType("NetworkAddress");
    }

    public void addChoiceType(String id) {
        if (isMissing(id)) {
            SmiType type = SmiProtocolType.createChoiceType(idt(id), m_module);
            m_module.addSymbol(type);
        }
    }

    public void addObjectNameType() {
        addObjectIdentifierType("ObjectName");
    }

    public void addNotificationNameType() {
        addObjectIdentifierType("NotificationName");
    }

    public void addObjectIdentifierType(String id) {
        if (isMissing(id)) {
            SmiType type = new SmiType(idt(id), m_module);
            type.setBaseType(SmiConstants.OBJECT_IDENTIFIER_TYPE);
        }
    }

    public void addInteger32Type() {
        if (isMissing("Integer32")) {
            SmiType type = new SmiType(idt("Integer32"), m_module);
            type.setBaseType(SmiConstants.INTEGER_TYPE);
            SmiRange range = new SmiRange(new BigIntegerToken(-2147483648), new BigIntegerToken(2147483647));
            type.setRangeConstraints(Collections.singletonList(range));
            m_module.addSymbol(type);
        }
    }

    public void addIpAddressType() {
        addApplicationType("IpAddress", 0);
    }

    public void addCounterType() {
        addApplicationType("Counter", 1);
    }

    public void addCounter32Type() {
        addApplicationType("Counter32", 1);
    }

    public void addGaugeType() {
        addApplicationType("Gauge", 2);
    }

    public void addGauge32Type() {
        addApplicationType("Gauge32", 2);
    }

    public void addUnsigned32Type() {
        addApplicationType("Unsigned32", 2);
    }

    public void addTimeTicksType() {
        addApplicationType("TimeTicks", 3);
    }

    public void addOpaqueType() {
        addApplicationType("Opaque", 4);
    }

    public void addCounter64Type() {
        addApplicationType("Counter64", 6);
    }

    public void addApplicationType(String id, int tag) {
        if (isMissing(id)) {
            SmiType type = new SmiType(idt(id), m_module, tag);
            m_module.addSymbol(type);
        }
    }

    public boolean isDefineItu() {
        return m_defineItu;
    }

    public void setDefineItu(boolean defineItu) {
        m_defineItu = defineItu;
    }

    public boolean isDefineIso() {
        return m_defineIso;
    }

    public void setDefineIso(boolean defineIso) {
        m_defineIso = defineIso;
    }


}
