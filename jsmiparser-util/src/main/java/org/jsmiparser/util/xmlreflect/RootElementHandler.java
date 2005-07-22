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

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

import java.lang.reflect.InvocationTargetException;

public class RootElementHandler extends AbstractElementHandler {
    private static final Logger m_log = Logger.getLogger(RootElementHandler.class);

    private static final String XMLSCHEMA_INSTANCE_URL = "http://www.w3.org/2001/XMLSchema-instance";

    private Object m_rootObject;

    public RootElementHandler(Object rootObject) {
        assert(rootObject != null);
        m_rootObject = rootObject;
    }

    public Object handleStart(Object current, String localName, Attributes atts) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        assert(current == m_rootObject);

        for (int i = 0; i < atts.getLength(); i++) {
            if (!XMLSCHEMA_INSTANCE_URL.equals(atts.getValue(i))) {
                ReflectBeanUtils.setProperty(current, atts.getLocalName(i), atts.getValue(i));
            }
        }

        return m_rootObject;
    }

}
