/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.smi;

import junit.framework.TestCase;
import org.jsmiparser.smi.SmiJavaCodeNamingStrategy;

public class SmiJavaCodeNamingStrategyTest extends TestCase {


//	public void testSplitAroundLettersAndDigits() {
//		SmiJavaCodeNamingStrategy ns = new SmiJavaCodeNamingStrategy("test");
//		
//		List<String> parts = ns.splitAroundLettersAndDigits("");
//		assertEquals(0, parts.size());
//		
//		parts = ns.splitAroundLettersAndDigits("a");
//		assertEquals(1, parts.size());
//		assertEquals()
//
//	}

    public void testMakeConstant() {

        SmiJavaCodeNamingStrategy ns = new SmiJavaCodeNamingStrategy("test");
        assertEquals("IF_LAST_CHANGE", ns.makeConstant("ifLastChange"));
        assertEquals("IF_LAST_CHANGE", ns.makeConstant("if_last_change"));
        assertEquals("IF_LAST_CHANGE", ns.makeConstant("if_Last_Change"));
        assertEquals("IF_LAST_CHANGE", ns.makeConstant("if_last_Change"));

        assertEquals("IF_LAST_CHANGE", ns.makeConstant("if#last@change"));


        // all variation on the real "ifHCPacketGroup":
        assertEquals("IF_H_PACKET_GROUP", ns.makeConstant("ifHPacketGroup"));
        assertEquals("IF_HC_PACKET_GROUP", ns.makeConstant("ifHCPacketGroup"));
        assertEquals("IF_HCB_PACKET_GROUP", ns.makeConstant("ifHCBPacketGroup"));

        assertEquals("IF_H_1_PACKET_GROUP", ns.makeConstant("ifH1PacketGroup"));
        assertEquals("IF_HC_2_PACKET_GROUP", ns.makeConstant("ifHC2PacketGroup"));
        assertEquals("IF_HC_3_B_PACKET_GROUP", ns.makeConstant("ifHC3BPacketGroup"));

        assertEquals("IF_H_16_PACKET_GROUP", ns.makeConstant("ifH16PacketGroup"));
        assertEquals("IF_HC_27_PACKET_GROUP", ns.makeConstant("ifHC27PacketGroup"));
        assertEquals("IF_HC_38_B_PACKET_GROUP", ns.makeConstant("ifHC38BPacketGroup"));


        assertEquals("IF_LAST_CHANGE_1", ns.makeConstant("if_last_change1"));
        assertEquals("IF_LAST_CHANGE_12", ns.makeConstant("if_last_change12"));

        assertEquals("IF_LAST_CHANGE_1", ns.makeConstant("if_last_change_1"));
        assertEquals("IF_LAST_CHANGE_12", ns.makeConstant("if_last_change_12"));

        assertEquals("IF_LAST_1_CHANGE", ns.makeConstant("if_last1change"));
        assertEquals("IF_LAST_12_CHANGE", ns.makeConstant("if_last12change"));

        assertEquals("IF_LAST_1_CHANGE", ns.makeConstant("if_last1change"));
        assertEquals("IF_LAST_12_CHANGE", ns.makeConstant("if_last12change"));
    }

}
