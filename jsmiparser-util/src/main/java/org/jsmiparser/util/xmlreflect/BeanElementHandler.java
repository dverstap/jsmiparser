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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanElementHandler extends AbstractElementHandler {
    private static final Logger m_log = Logger.getLogger(BeanElementHandler.class);

    public static final ReflectElementHandlerFactory FACTORY = new ReflectElementHandlerFactory() {

        public ReflectElementHandler create(Object parentObject, String localName, Attributes atts) {
            return BeanElementHandler.create(parentObject, localName, atts);
        }
    };

    private Method m_method;
    private Class<?> m_parameterType;
    private static final String ID = "id";

    protected BeanElementHandler(Method method) {
        m_method = method;
        assert(method.getParameterTypes().length <= 1);

        if (method.getParameterTypes().length == 1) {
            m_parameterType = method.getParameterTypes()[0];
        }
    }

    public Object handleStart(Object parent, String localName, Attributes atts) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object result = null;
        if (m_parameterType == null) {
            result = m_method.invoke(parent);
        } else {
            Object key = null;
            Method createKeyMethod = findMethod(m_log, parent.getClass(), "create", localName, "Key");
            if (createKeyMethod != null) {
                key = buildKey(createKeyMethod, parent, atts);
                result = m_method.invoke(parent, key);
            } else {
                key = buildKey(atts);
                result = m_method.invoke(parent, key);
                if (atts.getLength() > 1) {
                    ReflectBeanUtils.populate(key, atts, ID);                    
                }
            }
        }
        return result;
    }

    private Object buildKey(Method createKeyMethod, Object parent, Attributes atts) throws IllegalAccessException, InvocationTargetException {
        Object key = null;
        if (createKeyMethod.getParameterTypes().length == 0) {
            key = createKeyMethod.invoke(parent);
        } else {
            key = createKeyMethod.invoke(parent, atts);
        }
        return key;
    }

    private Object buildKey(Attributes atts) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object key = null;
        if (m_parameterType.equals(String.class)) {
            if (atts.getLength() == 1) {
                key = atts.getValue(0);
            } else {
                key = findId(atts);
            }
        } else {
            key = m_parameterType.newInstance();
            BeanUtils.populate(key, new SAXAttributesMap(atts));
        }
        assert(key != null);
        return key;
    }

    private String findId(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            if (ID.equals(atts.getLocalName(i))) {
                return atts.getValue(i);
            }
        }
        return null;
    }


    private static ReflectElementHandler create(Object parent, String localName, Attributes atts) {
        ReflectElementHandler result = null;
        Method m = findMethod(m_log, parent.getClass(), "create", localName, null);
        if (m != null) {
            result = new BeanElementHandler(m);
        }
        return result;
    }

}
