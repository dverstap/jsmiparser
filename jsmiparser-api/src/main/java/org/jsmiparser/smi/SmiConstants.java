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

import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.location.Location;

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

public class SmiConstants {

    public static final Set<String> SMI_DEFINITION_MODULE_NAMES = initSmiDefinitionModuleNames();

    public static final SmiMib JSMIPARSER_HARDCODED_MIB = new SmiMib(new SmiOptions(), null);
    public static final SmiModule JSMIPARSER_HARDCODED_MODULE = new SmiModule(JSMIPARSER_HARDCODED_MIB, new IdToken(null, "JSMIPARSER_HARDCODED_MIB"));

    public static final SmiType OBJECT_IDENTIFIER_TYPE = newType("OBJECT IDENTIFIER", SmiPrimitiveType.OBJECT_IDENTIFIER);
    public static final SmiType OCTET_STRING_TYPE = newType("OCTET STRING", SmiPrimitiveType.OCTET_STRING);
    public static final SmiType BITS_TYPE = newType("BITS", SmiPrimitiveType.BITS);
    public static final SmiType INTEGER_TYPE = newType("INTEGER", SmiPrimitiveType.INTEGER);

//    public static final SmiType INTEGER_32_TYPE = newType("Integer32");
//    public static final SmiType COUNTER_TYPE = newType("Counter");
//    public static final SmiType COUNTER_32_TYPE = newType("Counter32");
//    public static final SmiType GAUGE_TYPE = newType("Gauge");
//    public static final SmiType GAUGE_32_TYPE = newType("Gauge32");
//    public static final SmiType COUNTER_64_TYPE = newType("Counter64");
//    public static final SmiType TIME_TICKS_TYPE = newType("TimeTicks");

    public static final String[] MACRO_NAMES = {
            "AGENT-CAPABILITIES",
            "MODULE-COMPLIANCE",
            "MODULE-IDENTITY",
            "NOTIFICATION-GROUP",
            "NOTIFICATION-TYPE",
            "OBJECT-GROUP",
            "OBJECT-IDENTITY",
            "OBJECT-TYPE",
            "TEXTUAL-CONVENTION",
            "TRAP-TYPE"
//            ""
    };
    public  static final Location LOCATION = new Location("JSMIPARSER_HARDCODED_MIB");

    private static SmiType newType(String id, SmiPrimitiveType primitiveType) {
        return new SmiType(new IdToken(LOCATION, id), JSMIPARSER_HARDCODED_MODULE, primitiveType);
    }

    private static Set<String> initSmiDefinitionModuleNames() {
        String[] names = new String[] { "RFC1065-SMI", "SNMPv2-SMI" };
        return Collections.unmodifiableSet(new HashSet<String>());
    }
    
    
}
