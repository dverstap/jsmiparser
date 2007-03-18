package org.jsmiparser.util.token;

import junit.framework.TestCase;
import org.jsmiparser.util.location.Location;

import java.math.BigInteger;

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
public class HexStringTokenTest extends TestCase {


    public void testGood() {
        HexStringToken t = new HexStringToken(new Location("testcase"), "'7FFF'h");
        BigInteger value = t.getIntegerValue();
        assertEquals(0x7FFF, value.intValue());
    }

    public void testBad() {
        HexStringToken t = new HexStringToken(new Location("testcase"), "blabla");
        try {
            t.getIntegerValue();
            fail();
        } catch (Exception ignored) {
            // ignore
        }
    }

    public void testToString() {
        String str = "'7FFF'h";
        HexStringToken t = new HexStringToken(new Location("testcase"), str);
        assertEquals(str, t.toString());
    }
    
}
