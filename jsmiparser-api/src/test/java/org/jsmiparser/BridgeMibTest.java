/*
 * Copyright 2006 Davy Verstappen.
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
package org.jsmiparser;

import org.jsmiparser.smi.SmiTextualConvention;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiAttribute;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiRange;

import java.util.List;

public class BridgeMibTest extends MibTestCase {

    public String[] getResources() {
        return new String[]{
                "libsmi-0.4.5/mibs/iana/IANAifType-MIB",
                "libsmi-0.4.5/mibs/ietf/IF-MIB",
                "libsmi-0.4.5/mibs/ietf/BRIDGE-MIB"
        };
    }

    public void testBridgeMib() {
        SmiType integer32 = getInteger32();

        SmiModule bridgeMib = getMib().findModule("BRIDGE-MIB");
        assertEquals("BRIDGE-MIB", bridgeMib.getId());

        SmiTextualConvention timeout = getMib().findTextualConvention("Timeout");
        assertNotNull(timeout);
        assertEquals(integer32, timeout.getBaseType());
        assertEquals(SmiPrimitiveType.INTEGER_32, timeout.getPrimitiveType());
        assertNull(timeout.getRangeConstraints());

        SmiAttribute dot1dStpBridgeMaxAge = getMib().findAttribute("dot1dStpBridgeMaxAge");
        assertNotNull(dot1dStpBridgeMaxAge);
        assertSame(integer32, dot1dStpBridgeMaxAge.getType().getBaseType());
        assertEquals(SmiPrimitiveType.INTEGER_32, dot1dStpBridgeMaxAge.getType().getPrimitiveType());
        List<SmiRange> dot1dStpBridgeMaxAgeRangeConstraints = dot1dStpBridgeMaxAge.getType().getRangeConstraints();
        assertEquals(1, dot1dStpBridgeMaxAgeRangeConstraints.size());
        assertEquals(600, dot1dStpBridgeMaxAgeRangeConstraints.get(0).getMinValue().intValue());
        assertEquals(4000, dot1dStpBridgeMaxAgeRangeConstraints.get(0).getMaxValue().intValue());

    }


}
