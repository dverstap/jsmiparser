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
import java.lang.reflect.Method;


public class TextElementHandler extends AbstractElementHandler {
    private static final Logger m_log = Logger.getLogger(TextElementHandler.class);

    public static final ReflectElementHandlerFactory FACTORY = new ReflectElementHandlerFactory() {

        public ReflectElementHandler create(Object parentObject, String localName, Attributes atts) {
            return TextElementHandler.create(parentObject, localName, atts);
        }
    };

    private Method m_method;

    protected TextElementHandler(Method method) {
        m_method = method;
    }

    public Object handleStart(Object parent, String localName, Attributes atts) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return parent;
    }

    public void handleEnd(Object current, String text) throws IllegalAccessException, InvocationTargetException {
        m_method.invoke(current, text);
    }

    public boolean isHandlingText() {
        return true;
    }


    private static ReflectElementHandler create(Object parent, String localName, Attributes atts) {
        ReflectElementHandler result = null;
        if (atts.getLength() == 0) {
            Method m = findMethod(m_log, parent.getClass(), "add", localName, null);
            if (m == null) {
                m = findMethod(m_log, parent.getClass(), "set", localName, null);
            }
            if (m != null) {
                Class[] paramTypes = m.getParameterTypes();
                // TODO the type should be allowed to be something else too:
                //  we need to do conversion of the text value then
                if (paramTypes.length == 1 && String.class.equals(paramTypes[0])) {
                    result = new TextElementHandler(m);
                }
            }
        }
        return result;
    }

}
