/*
 * Copyright 2005 Nigel Sheridan-Smith, Davy Verstappen.
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

package org.jsmiparser.parsetree.asn1;

import org.jsmiparser.util.token.EnumToken;
import org.jsmiparser.util.token.StringToken;
import org.jsmiparser.util.token.BigIntegerToken;

import java.math.BigInteger;

/**
 * @author Nigel Sheridan-Smith
 */
public class ASNLiteralValue extends ASNValue {

    public enum LType {
        UNKNOWN,
        TRUE,
        FALSE,
        NULL,
        STRING,
        PLUS_INFINITY,
        MINUS_INFINITY,
        NUMBER,
        BSTRING,
        HSTRING
    }

    private EnumToken<LType> m_literalToken;
    private StringToken m_stringToken;
    private LType m_stringTokenLiteralType;
    private BigIntegerToken m_bigIntegerToken;

    public ASNLiteralValue(ASNModule module, EnumToken<LType> literalToken) {
        super(module, literalToken.getLocation(), Type.LITERAL);
        m_literalToken = literalToken;
    }

    public ASNLiteralValue(ASNModule module, StringToken stringToken, LType literalType) {
        super(module, stringToken.getLocation(), Type.LITERAL);
        m_stringToken = stringToken;
        m_stringTokenLiteralType = literalType;
        assert(m_stringTokenLiteralType == LType.STRING || m_stringTokenLiteralType == LType.BSTRING || m_stringTokenLiteralType == LType.HSTRING);
    }

    public ASNLiteralValue(ASNModule module, BigIntegerToken token) {
        super(module, token.getLocation(), Type.LITERAL);
        m_bigIntegerToken = token;
    }

    public LType getLiteralType() {
        if (m_literalToken != null) {
            return m_literalToken.getValue();
        } else if (m_stringToken != null) {
            return m_stringTokenLiteralType;
        } else {
            assert(m_bigIntegerToken != null);
            return LType.NUMBER;
        }
    }

    public String getStringValue() {
        return m_stringToken.getValue();
    }

    public BigInteger getNumberValue() {
        return m_bigIntegerToken.getValue();
    }

}
