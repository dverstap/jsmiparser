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
import org.apache.commons.beanutils.MethodUtils;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractElementHandler implements ReflectElementHandler {
    private static final Logger m_log = Logger.getLogger(AbstractElementHandler.class);

    public void handleEnd(Object current, String text) throws IllegalAccessException, InvocationTargetException {
        // we cannot lookup and cache the checkMethod in the constructor, because we don't know the exact class
        // of the created object there (only the return type of the create method)
        Method checkMethod = MethodUtils.getAccessibleMethod(current.getClass(), "checkElement", (Class[]) null);
        if (checkMethod != null) {
            if (m_log.isDebugEnabled()) {
                m_log.debug("Running checkElement() on " + current.getClass().getName());
            }
            checkMethod.invoke(current);
        }
    }

    public boolean isHandlingText() {
        return false;
    }

    protected static Method findMethod(Logger log, Class cl, String prefix, String localName, String postfix) {
        String name = (prefix + localName).toLowerCase();
        if (postfix != null) {
            name = name + postfix.toLowerCase();
        }
        for (Method method : cl.getMethods()) {
            if (method.getName().toLowerCase().equals(name)) {
                log.debug("Found " + cl.getName() + "." + method.getName() + "() to handle '" + localName + "'");
                return method;
            }
        }
        if (postfix != null) {
            prefix += postfix;
        }
        log.debug("Could not find '" + prefix + "' method in " + cl.getName() + " to handle '" + localName + "'");
        return null;
    }
}
