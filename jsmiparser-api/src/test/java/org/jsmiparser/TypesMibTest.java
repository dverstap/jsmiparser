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

import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiConstants;

public class TypesMibTest extends MibTestCase {

    public String[] getResources() {
        return new String[] { "types.txt" };
    }

    // TODO need to add lots of new tests here

    public void testMyINTEGER() {
        SmiType type = getMib().findType("MyINTEGER");
        assertSame(SmiConstants.INTEGER_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.INTEGER, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    public void testMyINTEGERFromMinus3To4() {
        SmiType type = getMib().findType("MyINTEGERFromMinus3To4");
        assertSame(SmiConstants.INTEGER_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.INTEGER, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getSizeConstraints());

        assertEquals(1, type.getRangeConstraints().size());
        assertEquals(-3, type.getRangeConstraints().get(0).getMinValue().intValue());
        assertEquals(4, type.getRangeConstraints().get(0).getMaxValue().intValue());
    }

    public void testMyInteger32() {
        SmiType type = getMib().findType("MyInteger32");
        assertSame(getInteger32(), type.getBaseType());
        assertSame(SmiPrimitiveType.INTEGER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }


}
