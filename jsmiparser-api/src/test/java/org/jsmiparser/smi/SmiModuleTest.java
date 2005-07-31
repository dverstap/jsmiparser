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
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.location.Location;

public class SmiModuleTest extends TestCase {
    private SmiMib mib_ = new SmiMib(new SmiJavaCodeNamingStrategy("test"));
    private SmiModule module_ = new SmiModule(mib_, new IdToken(new Location("IF-MIBsource", 1, 0), "IF-MIB"));

    public void testGetJavaId() {
        assertEquals("IfMib", module_.getCodeId());
    }

    public void testMakeClassName() {
        assertEquals("IfMibScalars", module_.getScalarsClass().getId());
    }


}
