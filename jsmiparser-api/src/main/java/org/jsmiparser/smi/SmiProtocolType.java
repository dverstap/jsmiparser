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
package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;

/**
 * Types such as ObjectSyntax, SimpleSyntax and ApplicationSyntax, that are only used in SNMP protocol PDU's,
 * but never used as variable, row or table types.
 */
public class SmiProtocolType extends SmiType {

    public SmiProtocolType(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public static SmiType createChoiceType(IdToken idToken, SmiModule module) {
        if (idToken.getId().equals("NetworkAddress")) {
            SmiType result = new SmiType(idToken, module);
            result.setBaseType(new SmiReferencedType(new IdToken(idToken.getLocation(), "IpAddress"), module));
            return result;
        }
        return new SmiProtocolType(idToken, module);
    }

}
