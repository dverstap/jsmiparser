/*
 * Copyright 2012 Davy Verstappen.
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
package org.jsmiparser.test.libsmi;

import org.jsmiparser.test.libsmi.adsl2_line_tc_mib.Adsl2TransmissionModeTypeBit;
import org.jsmiparser.test.libsmi.adsl_line_mib.AdslAtucCurrStatusBit;
import org.jsmiparser.test.libsmi.iana_itu_alarm_tc_mib.IANAItuEventType;
import org.jsmiparser.test.libsmi.if_mib.IF_MIB;
import org.jsmiparser.test.libsmi.if_mib.IfAdminStatus;
import org.jsmiparser.test.libsmi.snmp_framework_mib.SnmpSecurityLevel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompiledLibSmiTest {

    @Test
    public void testSnmpSecurityLevelNoAuthNoPrivEnum() {
        SnmpSecurityLevel level = SnmpSecurityLevel.noAuthNoPriv;
        assertEquals("noAuthNoPriv", level.name());
        assertEquals(0, level.ordinal());
        assertEquals("noAuthNoPriv", level.getName());
        assertEquals(1, level.getValue());
    }

    @Test
    public void testIfAdminStatusColumn() {
        assertEquals("ifAdminStatus", IF_MIB.ifAdminStatus.getName());
        assertEquals("1.3.6.1.2.1.2.2.1.7", IF_MIB.ifAdminStatus.getOidStr());
    }

    @Test
    public void testNamedEnumType() {
        assertEquals("IANAItuEventType", IANAItuEventType.class.getSimpleName());
        assertEquals(11, IANAItuEventType.class.getEnumConstants().length);
    }

    @Test
    public void testAnonymousEnumType() {
        assertEquals("IfAdminStatus", IfAdminStatus.class.getSimpleName());
        assertEquals(3, IfAdminStatus.class.getEnumConstants().length);
    }

    @Test
    public void testNamedBitsType() {
        assertEquals("AdslAtucCurrStatusBit", AdslAtucCurrStatusBit.class.getSimpleName());
        assertEquals(10, AdslAtucCurrStatusBit.class.getEnumConstants().length);
    }

    @Test
    public void testAnonymousBitsType() {
        assertEquals("Adsl2TransmissionModeTypeBit", Adsl2TransmissionModeTypeBit.class.getSimpleName());
        assertEquals(56, Adsl2TransmissionModeTypeBit.class.getEnumConstants().length);
    }

}
