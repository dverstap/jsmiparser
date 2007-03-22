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

import org.jsmiparser.smi.*;

import java.math.BigInteger;
import java.util.List;

public class TypesMibTest extends AbstractMibTestCase {

    public TypesMibTest() {
        super(null, "types.txt");
    }

    // TODO need to add lots of new tests here

    public void testMyINTEGER() {
        SmiType type = getMib().getTypes().find("MyINTEGER");
        checkMyINTEGER(type);
    }

    public void testTCMyINTEGER() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyINTEGER");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyINTEGER(tc);
    }

    public void testOTMyINTEGER() {
        SmiObjectType ot = getMib().getVariables().find("myINTEGER");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        // this one is an exception, because it refers to the INTEGER type directly:
        // checkMyInteger(ot.getType())

        SmiType type = ot.getType();
        assertSame(SmiConstants.INTEGER_TYPE, type);
        assertSame(SmiPrimitiveType.INTEGER, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    private void checkMyINTEGER(SmiType type) {
        assertSame(SmiConstants.INTEGER_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.INTEGER, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    public void testMyINTEGERFromMinus3To4() {
        SmiType type = getMib().getTypes().find("MyINTEGERFromMinus3To4");
        checkMyINTEGERFromMinus3To4(type);
    }

    public void testTCMyINTEGERFromMinus3To4() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyINTEGERFromMinus3To4");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyINTEGERFromMinus3To4(tc);
    }

    public void testOTMyINTEGERFromMinus3To4() {
        SmiObjectType ot = getMib().getVariables().find("myINTEGERFromMinus3To4");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        checkMyINTEGERFromMinus3To4(ot.getType());
    }

    private void checkMyINTEGERFromMinus3To4(SmiType type) {
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
        SmiType type = getMib().getTypes().find("MyInteger32");
        checkMyInteger32(type);
    }

    public void testTCMyInteger32() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyInteger32");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyInteger32(tc);
    }

    public void testOTMyInteger32() {
        SmiObjectType ot = getMib().getVariables().find("myInteger32");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        SmiType type = ot.getType();
        assertSame(getInteger32(), type);
        assertSame(SmiPrimitiveType.INTEGER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertEquals(1, type.getRangeConstraints().size());
        assertNull(type.getSizeConstraints());
    }

    private void checkMyInteger32(SmiType type) {
        assertSame(getInteger32(), type.getBaseType());
        assertSame(SmiPrimitiveType.INTEGER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    public void testMyInteger32FromMinus5To6() {
        SmiType type = getMib().getTypes().find("MyInteger32FromMinus5To6");
        checkMyInteger32FromMinus5To6(type);
    }

    public void testTCMyInteger32FromMinus5To6() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyInteger32FromMinus5To6");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyInteger32FromMinus5To6(tc);
    }

    public void testOTMyInteger32FromMinus5To6() {
        SmiObjectType ot = getMib().getVariables().find("myInteger32FromMinus5To6");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        checkMyInteger32FromMinus5To6(ot.getType());
    }

    private void checkMyInteger32FromMinus5To6(SmiType type) {
        assertSame(getInteger32(), type.getBaseType());
        assertSame(SmiPrimitiveType.INTEGER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getSizeConstraints());

        assertEquals(1, type.getRangeConstraints().size());
        assertEquals(-5, type.getRangeConstraints().get(0).getMinValue().intValue());
        assertEquals(6, type.getRangeConstraints().get(0).getMaxValue().intValue());
    }

    public void testMyEnum() {
        SmiType type = getMib().getTypes().find("MyEnum");
        checkMyEnum(type);
    }

    public void testTCMyEnum() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyEnum");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyEnum(tc);
    }

    public void testOTMyEnum() {
        SmiObjectType ot = getMib().getVariables().find("myEnum");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        checkMyEnum(ot.getType());
    }

    private void checkMyEnum(SmiType type) {
        assertSame(SmiConstants.INTEGER_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.ENUM, type.getPrimitiveType());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());

        List<SmiNamedNumber> values = type.getEnumValues();
        assertEquals(3, values.size());
        assertEquals(0, values.get(0).getValue().intValue());
        assertEquals("zero", values.get(0).getId());
        assertEquals(1, values.get(1).getValue().intValue());
        assertEquals("one", values.get(1).getId());
        assertEquals(57, values.get(2).getValue().intValue());
        assertEquals("fiftySeven", values.get(2).getId());
    }

    public void testMyBits() {
        SmiType type = getMib().getTypes().find("MyBits");
        checkMyBits(type);
    }

    public void testTCMyBits() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyBits");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyBits(tc);
    }

    public void testOTMyBits() {
        SmiObjectType ot = getMib().getVariables().find("myBits");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        checkMyBits(ot.getType());
    }

    private void checkMyBits(SmiType type) {
        assertSame(SmiConstants.BITS_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.BITS, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());

        List<SmiNamedNumber> values = type.getBitFields();
        assertEquals(3, values.size());
        assertEquals(0, values.get(0).getValue().intValue());
        assertEquals("zero", values.get(0).getId());
        assertEquals(1, values.get(1).getValue().intValue());
        assertEquals("one", values.get(1).getId());
        assertEquals(57, values.get(2).getValue().intValue());
        assertEquals("fiftySeven", values.get(2).getId()); // TODO should generate out-of-range here
    }

    public void testMyOctetString() {
        SmiType type = getMib().getTypes().find("MyOctetString");
        checkMyOctetString(type);
    }

    public void testMyDerivedOctetString() {
        SmiType type = getMib().getTypes().find("MyDerivedOctetString");
        assertNotNull(type);

        SmiType myOctetStringType = getMib().getTypes().find("MyOctetString");
        assertNotNull(myOctetStringType);

        assertSame(myOctetStringType, type.getBaseType());
        assertSame(SmiConstants.OCTET_STRING_TYPE, type.getBaseType().getBaseType());
        assertSame(SmiPrimitiveType.OCTET_STRING, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    public void testMyDerivedOctetStringBetween3And5() {
        SmiType type = getMib().getTypes().find("MyDerivedOctetStringBetween3And5");
        assertNotNull(type);

        SmiType myOctetStringType = getMib().getTypes().find("MyOctetString");
        assertNotNull(myOctetStringType);

        assertSame(myOctetStringType, type.getBaseType());
        assertSame(SmiConstants.OCTET_STRING_TYPE, type.getBaseType().getBaseType());
        assertSame(SmiPrimitiveType.OCTET_STRING, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());

        List<SmiRange> constraints = type.getSizeConstraints();
        assertNotNull(constraints);
        assertEquals(1, constraints.size());
        assertEquals(new BigInteger("3"), constraints.get(0).getMinValue());
        assertEquals(new BigInteger("5"), constraints.get(0).getMaxValue());
    }

    public void testTCMyOctetString() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyOctetString");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyOctetString(tc);
    }

    public void testOTMyOctetString() {
        SmiObjectType ot = getMib().getVariables().find("myOctetString");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        SmiType type = ot.getType();
        assertSame(SmiConstants.OCTET_STRING_TYPE, type);
        assertSame(SmiPrimitiveType.OCTET_STRING, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    private void checkMyOctetString(SmiType type) {
        assertSame(SmiConstants.OCTET_STRING_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.OCTET_STRING, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    public void testMyOctetStringBetweenSize9And5() {
        // TODO should check for error message

        SmiType type = getMib().getTypes().find("MyOctetStringBetweenSize9And5");
        checkMyOctetStringBetweenSize9And5(type);
    }

    public void testTCMyOctetStringBetweenSize9And5() {
        // TODO should check for error message

        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyOctetStringBetweenSize9And5");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyOctetStringBetweenSize9And5(tc);
    }

    public void testOTMyOctetStringBetweenSize9And5() {
        SmiObjectType ot = getMib().getVariables().find("myOctetStringBetweenSize9And5");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        checkMyOctetStringBetweenSize9And5(ot.getType());
    }

    private void checkMyOctetStringBetweenSize9And5(SmiType type) {
        assertSame(SmiConstants.OCTET_STRING_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.OCTET_STRING, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());

        List<SmiRange> constraints = type.getSizeConstraints();
        assertEquals(1, constraints.size());
        assertEquals(9, constraints.get(0).getMinValue().intValue());
        assertEquals(5, constraints.get(0).getMaxValue().intValue());
    }

    public void testOTMyOctetStringOfSize11() {
        SmiVariable ot = getMib().getVariables().find("myOctetStringOfSize11");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());
        assertNotNull(ot.getSizeConstraints());

        SmiType type = ot.getType();
        assertSame(SmiConstants.OCTET_STRING_TYPE, type.getBaseType());
        assertSame(SmiPrimitiveType.OCTET_STRING, type.getPrimitiveType());

        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());

        List<SmiRange> constraints = type.getSizeConstraints();
        assertEquals(1, constraints.size());
        assertTrue(constraints.get(0).isSingle());
        assertEquals(11, constraints.get(0).getMinValue().intValue());
        assertEquals(11, constraints.get(0).getMaxValue().intValue());
    }

    public void testMyCounter() {
        SmiType type = getMib().getTypes().find("MyCounter");
        checkMyCounter(type);
    }

    public void testTCMyCounter() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyCounter");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyCounter(tc);
    }

    public void testOTMyCounter() {
        SmiObjectType ot = getMib().getVariables().find("myCounter");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        SmiType type = ot.getType();
        assertSame(getCounter(), type);
        assertSame(SmiPrimitiveType.COUNTER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertEquals(1, type.getRangeConstraints().size());
        assertNull(type.getSizeConstraints());
    }

    private void checkMyCounter(SmiType type) {
        assertSame(getCounter(), type.getBaseType());
        assertSame(SmiPrimitiveType.COUNTER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getRangeConstraints());
        assertNull(type.getSizeConstraints());
    }

    public void testMyCounterFromMinus100To100() {
        SmiType type = getMib().getTypes().find("MyCounterFromMinus100To100");
        checkMyCounterFromMinus100To100(type);
    }

    public void testTCMyCounterFromMinus5To6() {
        SmiTextualConvention tc = getMib().getTextualConventions().find("TCMyCounterFromMinus100To100");
        assertEquals(tc.getId() + " Desc", tc.getDescription());
        assertEquals("255a", tc.getDisplayHint());
        assertEquals(StatusV2.DEPRECATED, tc.getStatusV2());
        checkMyCounterFromMinus100To100(tc);
    }

    public void testOTMyCounterFromMinus100To100() {
        SmiObjectType ot = getMib().getVariables().find("myCounterFromMinus100To100");
        assertEquals(ot.getId() + " Desc", ot.getDescription());
        assertEquals(ObjectTypeAccessV2.NOT_ACCESSIBLE, ot.getMaxAccess());
        assertEquals(StatusV2.CURRENT, ot.getStatusV2());

        checkMyCounterFromMinus100To100(ot.getType());
    }

    private void checkMyCounterFromMinus100To100(SmiType type) {
        assertSame(getCounter(), type.getBaseType());
        assertSame(SmiPrimitiveType.COUNTER_32, type.getPrimitiveType());
        assertNull(type.getEnumValues());
        assertNull(type.getBitFields());
        assertNull(type.getFields());
        assertNull(type.getSizeConstraints());

        assertEquals(1, type.getRangeConstraints().size());
        assertEquals(-100, type.getRangeConstraints().get(0).getMinValue().intValue()); // TODO test error message:
        assertEquals(100, type.getRangeConstraints().get(0).getMaxValue().intValue());
    }

    public void testMyUnsigned32() {
       SmiObjectType ot = getMib().getVariables().find("myUnsigned32");

       assertNotNull(ot);
       assertNotNull(ot.getType());

       assertEquals(SmiPrimitiveType.UNSIGNED_32, ot.getType().getPrimitiveType());
   }

    public void testMyTimeTicks() {
       SmiObjectType ot = getMib().getVariables().find("myTimeTicks");

       assertNotNull(ot);
       assertNotNull(ot.getType());

       assertEquals(SmiPrimitiveType.TIME_TICKS, ot.getType().getPrimitiveType());
   }

}
