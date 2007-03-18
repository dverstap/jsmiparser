package org.jsmiparser.smi;

import org.apache.log4j.Logger;
import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.BinaryStringToken;
import org.jsmiparser.util.token.HexStringToken;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.QuotedStringToken;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
public class SmiDefaultValue {

    private static final Logger m_log = Logger.getLogger(SmiDefaultValue.class);

    private final SmiModule m_module;
    private final BigIntegerToken m_bigIntegerToken;
    private final List<IdToken> m_bitsIdTokenList;
    private final OidComponent m_lastOidComponent;
    private final BinaryStringToken m_binaryStringToken;
    private final HexStringToken m_hexStringToken;
    private final QuotedStringToken m_quotedStringToken;
    private final ScopedId m_scopedId;
    private final boolean m_isNullValue;

    SmiVariable m_variable;

    private ArrayList<SmiNamedNumber> m_bitsValue;
    private SmiNamedNumber m_enumValue;
    private SmiOidValue m_oidValue;
    private SmiOidNode m_oidNode;
    private SmiSymbol m_symbolValue;

    public SmiDefaultValue(SmiModule module, BigIntegerToken bigIntegerToken, List<IdToken> bitsIdTokenList, OidComponent lastOidComponent, BinaryStringToken binaryStringToken, HexStringToken hexStringToken, QuotedStringToken quotedStringToken, ScopedId scopedId, boolean nullValue) {
        m_module = module;
        m_bigIntegerToken = bigIntegerToken;
        m_bitsIdTokenList = bitsIdTokenList;
        m_lastOidComponent = lastOidComponent;
        m_binaryStringToken = binaryStringToken;
        m_hexStringToken = hexStringToken;
        m_quotedStringToken = quotedStringToken;
        m_scopedId = scopedId;
        m_isNullValue = nullValue;
    }

    public SmiVariable getVariable() {
        return m_variable;
    }

    public BigInteger getIntegerValue() {
        if (m_bigIntegerToken != null) {
            return m_bigIntegerToken.getValue();
        }
        return null;
    }

    public List<SmiNamedNumber> getBitsValue() {
        return m_bitsValue;
    }

    public SmiNamedNumber getEnumValue() {
        return m_enumValue;
    }

    public SmiOidValue getOidValue() {
        return m_oidValue;
    }

    public SmiOidNode getOidNode() {
        if (m_oidNode != null) {
            return m_oidNode;
        } else if (m_oidValue != null) {
            return m_oidValue.getNode();
        }
        return null;
    }

    public String getCStringValue() {
        if (m_quotedStringToken != null) {
            return m_quotedStringToken.getValue();
        }
        return null;
    }

    public String getBinaryStringValue() {
        if (m_binaryStringToken != null) {
            return m_binaryStringToken.getValue();
        }
        return null;
    }

    public String getHexStringValue() {
        if (m_hexStringToken != null) {
            return m_hexStringToken.getValue();
        }
        return null;
    }

    public SmiSymbol getSymbolValue() {
        return m_symbolValue;
    }

    public BigIntegerToken getBigIntegerToken() {
        return m_bigIntegerToken;
    }

    public List<IdToken> getBitsIdTokenList() {
        return m_bitsIdTokenList;
    }

    public OidComponent getLastOidComponents() {
        return m_lastOidComponent;
    }

    public BinaryStringToken getBinaryStringToken() {
        return m_binaryStringToken;
    }

    public HexStringToken getHexStringToken() {
        return m_hexStringToken;
    }

    public QuotedStringToken getQuotedStringToken() {
        return m_quotedStringToken;
    }

    public ScopedId getScopedId() {
        return m_scopedId;
    }

    public boolean isNullValue() {
        return m_isNullValue;
    }

    public void resolveReferences(XRefProblemReporter reporter) {
        if (m_bitsIdTokenList != null) {
            resolveBits(reporter);
        } else if (m_lastOidComponent != null) {
            m_oidNode = resolveOids(reporter);
        } else if (m_scopedId != null) {
            if (m_scopedId.getModuleToken() != null) {
                m_log.debug("Not yet implemented: " + m_scopedId.getModuleToken());
            } else {
                if (m_variable.getEnumValues() != null) {
                    m_enumValue = m_variable.resolveEnumConstant(m_scopedId.getSymbolToken(), reporter);
                } else {
                    SmiSymbol symbol = m_variable.getModule().resolveReference(m_scopedId.getSymbolToken(), reporter);
                    if (symbol != null) {
                        if (symbol instanceof SmiOidValue && m_variable.getPrimitiveType() == SmiPrimitiveType.OBJECT_IDENTIFIER) {
                            m_oidValue = (SmiOidValue) symbol;
                            m_oidNode = m_oidValue.getNode();
                        } else {
                            // some proprietary mibs define the default value for an integer as a reference
                            // to some other integer variable; 
                            m_symbolValue = symbol;
                            reporter.reportInvalidDefaultValue(m_scopedId.getSymbolToken());
                        }
                    }
                }
            }
        }
    }

    private SmiOidNode resolveOids(XRefProblemReporter reporter) {
        // TODO
        // reporter.reportOidDefaultValueMustBeSingleIdentifier(t);
        return m_lastOidComponent.resolveNode(m_module, reporter);
    }

    private void resolveBits(XRefProblemReporter reporter) {
        if (m_variable.getBitFields() != null) {
            m_bitsValue = new ArrayList<SmiNamedNumber>();
            for (IdToken idToken : m_bitsIdTokenList) {
                SmiNamedNumber nn = m_variable.resolveBitField(idToken, reporter);
                m_bitsValue.add(nn);
            }
        } else {
            reporter.reportBitsValueWithoutBitsType(m_bitsIdTokenList.get(0).getLocation());
        }
    }
}
