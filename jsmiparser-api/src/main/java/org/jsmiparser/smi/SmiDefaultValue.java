package org.jsmiparser.smi;

import org.jsmiparser.util.token.QuotedStringToken;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.HexStringToken;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.BinaryStringToken;

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

    private final BigIntegerToken m_bigIntegerToken;
    private final List<IdToken> m_bitsIdTokenList;
    private final List<OidComponent> m_oidComponents;
    private final BinaryStringToken m_binaryStringToken;
    private final HexStringToken m_hexStringToken;
    private final QuotedStringToken m_quotedStringToken;
    private final ScopedId m_scopedId;
    private final boolean m_isNullValue;

    public SmiDefaultValue(BigIntegerToken bigIntegerToken, List<IdToken> bitsIdTokenList, List<OidComponent> oidComponents, BinaryStringToken binaryStringToken, HexStringToken hexStringToken, QuotedStringToken quotedStringToken, ScopedId scopedId, boolean nullValue) {
        m_bigIntegerToken = bigIntegerToken;
        m_bitsIdTokenList = bitsIdTokenList;
        m_oidComponents = oidComponents;
        m_binaryStringToken = binaryStringToken;
        m_hexStringToken = hexStringToken;
        m_quotedStringToken = quotedStringToken;
        m_scopedId = scopedId;
        m_isNullValue = nullValue;
    }


    public BigIntegerToken getBigIntegerToken() {
        return m_bigIntegerToken;
    }

    public List<IdToken> getBitsIdTokenList() {
        return m_bitsIdTokenList;
    }

    public List<OidComponent> getOidComponents() {
        return m_oidComponents;
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
}
