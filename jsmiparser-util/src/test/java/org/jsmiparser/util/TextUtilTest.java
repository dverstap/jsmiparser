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
package org.jsmiparser.util;

import junit.framework.TestCase;

public class TextUtilTest extends TestCase {

    public void testKeyWordMap() {
        assertEquals(53, TextUtil.keyWordMap_.size());
    }

    public void testDeleteChar() {

        assertEquals("", TextUtil.deleteChar("aa", 'a'));
        assertEquals("aa", TextUtil.deleteChar("aa", 'b'));
        assertEquals("IFMIB", TextUtil.deleteChar("IF-MIB", '-'));

    }
}
