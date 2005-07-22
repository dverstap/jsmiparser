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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.jsmiparser.util.TextUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectBeanUtils {
    private static final Logger m_log = Logger.getLogger(ReflectBeanUtils.class);

    public static void populate(Object object, Attributes atts) throws IllegalAccessException, InvocationTargetException {
        populate(object, atts, null);
    }

    public static void populate(Object object, Attributes atts, String ignoreLocalName) throws IllegalAccessException, InvocationTargetException {
        BeanUtils.populate(object, makeMap(atts, ignoreLocalName));
        // TODO handle boolean attributes with wrong names
    }

    public static void setProperty(Object object, String propertyName, String value) throws IllegalAccessException, InvocationTargetException {
        Method m = findMethod(object, propertyName);
        if (m != null) {
            m.invoke(object, value);
        } else {
            BeanUtils.setProperty(object, propertyName, value);
        }
    }

    private static Method findMethod(Object object, String propertyName) {
        try {
            return object.getClass().getMethod("set" + TextUtil.ucFirst(propertyName), String.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Map makeMap(Attributes atts) {
        return makeMap(atts, null);
    }

    public static Map makeMap(Attributes atts, String ignoreLocalName) {
        Map result = new HashMap();
        for (int i = 0; i < atts.getLength(); i++) {
            if (ignoreLocalName == null) {
                result.put(atts.getLocalName(i), atts.getValue(i));
            } else if (!ignoreLocalName.equals(atts.getLocalName(i))) {
                result.put(atts.getLocalName(i), atts.getValue(i));
            }
        }
        return result;
    }
}
