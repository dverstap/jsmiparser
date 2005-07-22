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

/**
 * This enum identifies the various fields in SimpleSyntax and ApplicationSyntax (together
 * these two form the ObjectSyntax choice) that can be used as the value of a VarBind.
 * 
 * <pre>
 * -- syntax of objects

-- the "base types" defined here are:
--   3 built-in ASN.1 types: INTEGER, OCTET STRING, OBJECT IDENTIFIER
--   8 application-defined types: Integer32, IpAddress, Counter32,
--              Gauge32, Unsigned32, TimeTicks, Opaque, and Counter64

ObjectSyntax ::=
    CHOICE {
        simple
            SimpleSyntax,

          -- note that SEQUENCEs for conceptual tables and
          -- rows are not mentioned here...

        application-wide
            ApplicationSyntax
    }

-- built-in ASN.1 types

SimpleSyntax ::=
    CHOICE {
        -- INTEGERs with a more restrictive range
        -- may also be used
        integer-value               -- includes Integer32
            INTEGER (-2147483648..2147483647),

        -- OCTET STRINGs with a more restrictive size
        -- may also be used
        string-value
            OCTET STRING (SIZE (0..65535)),

        objectID-value
            OBJECT IDENTIFIER
    }

-- application-wide types

ApplicationSyntax ::=
    CHOICE {
        ipAddress-value
            IpAddress,

        counter-value
            Counter32,

        timeticks-value
            TimeTicks,

        arbitrary-value
            Opaque,

        big-counter-value
            Counter64,

        unsigned-integer-value  -- includes Gauge32
            Unsigned32
    }

-- in network-byte order
-- (this is a tagged type for historical reasons)
IpAddress ::=
    [APPLICATION 0]
        IMPLICIT OCTET STRING (SIZE (4))

-- this wraps
Counter32 ::=
    [APPLICATION 1]
        IMPLICIT INTEGER (0..4294967295)

-- this doesn't wrap
Gauge32 ::=
    [APPLICATION 2]
        IMPLICIT INTEGER (0..4294967295)

-- an unsigned 32-bit quantity
-- indistinguishable from Gauge32
Unsigned32 ::=
    [APPLICATION 2]
        IMPLICIT INTEGER (0..4294967295)

-- hundredths of seconds since an epoch
TimeTicks ::=
    [APPLICATION 3]
        IMPLICIT INTEGER (0..4294967295)

-- for backward-compatibility only
Opaque ::=
    [APPLICATION 4]
        IMPLICIT OCTET STRING

-- for counters that wrap in less than one hour with only 32 bits
Counter64 ::=
    [APPLICATION 6]
        IMPLICIT INTEGER (0..18446744073709551615)

 * </pre>
 * 
 * @author davy
 *
 */
public enum SmiVarBindField {
	
	// SimpleSyntax:
	INTEGER_VALUE,
	STRING_VALUE,
	OBJECTID_VALUE,
	
	// ApplicationSyntax:
	IPADDRESS_VALUE,
	COUNTER_VALUE,
	TIMETICKS_VALUE,
	ARBITRARY_VALUE,
	BIG_COUNTER_VALUE,
	UNSIGNED_INTEGER_VALUE
}
