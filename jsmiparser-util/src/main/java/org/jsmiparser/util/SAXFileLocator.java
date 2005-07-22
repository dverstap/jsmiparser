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

public class SAXFileLocator extends FileLocator
{
    private Locator locator_;

    public SAXFileLocator(Locator locator)
    {
        super();
        locator_ = locator;
    }

    public String getFileName()
    {
        if (locator_.getPublicId() != null)
            return "publicId: " + locator_.getPublicId();
        if (locator_.getSystemId() != null)
            return "systemId: " + locator_.getSystemId();
        return null;
    }


    public int getLine()
    {
        return locator_.getLineNumber();
    }


    public int getColumn()
    {
        return locator_.getColumnNumber();
    }

}
