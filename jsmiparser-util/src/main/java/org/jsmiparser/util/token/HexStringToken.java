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

package org.jsmiparser.util.token;

import org.jsmiparser.util.location.Location;

import java.math.BigInteger;

public class HexStringToken extends StringToken {

    private final char m_radixChar;

    public HexStringToken(Location location, String value) {
        super(location, value.substring(1, value.length() - 2));
        m_radixChar = value.charAt(value.length() - 1);
    }

    public BigInteger getIntegerValue() {
        return new BigInteger(getValue(), 16);
    }

    public String toString() {
        return "'" + getValue() + "'" + m_radixChar;
    }

    public char getRadixChar() {
        return m_radixChar;
    }
}
