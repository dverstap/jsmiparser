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
package org.jsmiparser.util.xmlreflect;

import org.xml.sax.Attributes;

import java.util.AbstractMap;
import java.util.Set;
import java.util.AbstractSet;
import java.util.Iterator;

public class SAXAttributesMap extends AbstractMap<String, String> {

    private final Attributes m_atts;
    private boolean m_includeId;

    public SAXAttributesMap(Attributes atts) {
        m_atts = atts;
    }

    public SAXAttributesMap(Attributes atts, boolean includeId) {
        m_atts = atts;
        m_includeId = includeId;
    }

    public Set<Entry<String, String>> entrySet() {
        return new AbstractSet<Entry<String, String>>() {

            public Iterator<Entry<String, String>> iterator() {
                return new Iterator<Entry<String, String>>() {

                    private int m_index = 0;

                    public boolean hasNext() {
                        return m_index < m_atts.getLength();
                    }

                    public Entry<String, String> next() {
                        SAXEntry result = new SAXEntry(m_atts.getLocalName(m_index), m_atts.getValue(m_index));
                        m_index++;
                        return result;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            public int size() {
                return m_atts.getLength();
            }
        };
    }
}
