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
package org.jsmiparser.util;

import org.xml.sax.Locator;

public class SAXFileLocator extends FileLocator {
    private Locator m_locator;

    public SAXFileLocator() {
    }

    public SAXFileLocator(Locator locator) {
        super();
        m_locator = locator;
    }

    public Locator getLocator() {
        return m_locator;
    }

    public void setLocator(Locator locator) {
        this.m_locator = locator;
    }

    public String getFileName() {
        if (m_locator.getPublicId() != null)
            return "publicId: " + m_locator.getPublicId();
        if (m_locator.getSystemId() != null)
            return "systemId: " + m_locator.getSystemId();
        return null;
    }

    public int getLine() {
        return m_locator.getLineNumber();
    }

    public int getColumn() {
        return m_locator.getColumnNumber();
    }

}
