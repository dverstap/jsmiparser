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

package org.jsmiparser.smi;

import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.BinaryStringToken;
import org.jsmiparser.util.token.HexStringToken;
import org.jsmiparser.util.token.Token;

import java.math.BigInteger;

public class SmiRange {

    private Token m_beginToken;
    private Token m_endToken;

    public SmiRange(Token beginToken, Token endToken) {
        m_beginToken = beginToken;
        m_endToken = endToken;
    }

    public SmiRange(Token singleToken) {
        m_beginToken = singleToken;
        m_endToken = singleToken;
    }

    public Token getBeginToken() {
        return m_beginToken;
    }

    public Token getEndToken() {
        return m_endToken;
    }

    public boolean isSingle() {
        return m_beginToken == m_endToken;
    }

    public Location getLocation() {
        return m_beginToken.getLocation();
    }

    public BigInteger getMinValue() {
        return getValue(m_beginToken);
    }

    public BigInteger getMaxValue() {
        return getValue(m_endToken);
    }

    private static BigInteger getValue(Token token) {
        if (token instanceof BigIntegerToken) {
            return ((BigIntegerToken) token).getValue();
        } else if (token instanceof HexStringToken) {
            return ((HexStringToken) token).getIntegerValue();
        } else if (token instanceof BinaryStringToken) {
            return ((BinaryStringToken) token).getIntegerValue();
        }
        return null;
    }


    public String toString() {
        if (m_beginToken == m_endToken) {
            return m_beginToken.getObject().toString();
        } else {
            StringBuilder result = new StringBuilder("(");
            result.append(m_beginToken.getObject());
            result.append("..");
            result.append(m_endToken);
            result.append(")");
            return result.toString();
        }
    }
}
