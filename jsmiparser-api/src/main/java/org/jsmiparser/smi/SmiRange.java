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

import org.jsmiparser.util.token.Token;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.location.Location;

import java.math.BigInteger;

/**
 * TODO the whole range specification stuff is pretty bad.
 *
 */
public class SmiRange {

    private Token m_beginToken;
    private Token m_endToken;

    private Token m_singleToken;

    public SmiRange(Token beginToken, Token endToken) {
        m_beginToken = beginToken;
        m_endToken = endToken;
    }

    public SmiRange(Token singleToken) {
        m_singleToken = singleToken;
    }

    public Token getBeginToken() {
        return m_beginToken;
    }

    public Token getEndToken() {
        return m_endToken;
    }

    public Token getSingleToken() {
        return m_singleToken;
    }

    public boolean isSingle() {
        return m_singleToken != null;
    }

    public Location getLocation() {
        if (isSingle()) {
            return m_singleToken.getLocation();
        } else {
            return m_beginToken.getLocation();
        }
    }

    public BigInteger getMinValue() {
        return ((BigIntegerToken) m_beginToken).getValue();
    }

    public BigInteger getMaxValue() {
        return ((BigIntegerToken) m_endToken).getValue();
    }

    public BigInteger getSingleValue() {
        return ((BigIntegerToken) m_singleToken).getValue();
    }

}
