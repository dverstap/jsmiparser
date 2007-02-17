package org.jsmiparser.util.token;

import junit.framework.TestCase;
import org.jsmiparser.util.location.Location;

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
public class QuotedStringTokenTest extends TestCase {

    public void testDoubleQuote() {
        QuotedStringToken token = new QuotedStringToken(new Location("file"), "\"bla\"", '\"');
        assertEquals("bla", token.getValue());
    }

    public void testSingleQuote() {
        QuotedStringToken token = new QuotedStringToken(new Location("file"), "\'bla\'", '\'');
        assertEquals("bla", token.getValue());
    }

    public void testNoQuote() {
        try {
            new QuotedStringToken(new Location("file"), "bla", '\"');
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }


}
