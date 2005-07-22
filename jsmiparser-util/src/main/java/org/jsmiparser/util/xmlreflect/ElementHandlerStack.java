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

import java.util.Stack;

public class ElementHandlerStack {
    private static final Logger m_log = Logger.getLogger(ElementHandlerStack.class);

    private Stack<Frame> m_rep = new Stack<Frame>();


    public void push(ReflectElementHandler elementHandler, String localName, Object object) {
        String path = null;
        if (m_rep.isEmpty()) {
            path = "/" + localName;
        } else {
            path = m_rep.peek().m_path + "/" + localName;
        }
        Frame f = new Frame(elementHandler, object, path);
        m_rep.push(f);
    }

    public void pop() {
        m_rep.pop();
    }

    public ReflectElementHandler getCurrentElementHandler() {
        Frame f = m_rep.peek();
        return f.m_elementHandler;
    }

    public Object getCurrentObject() {
        Frame f = m_rep.peek();
        return f.m_object;
    }

    public boolean isEmpty() {
        return m_rep.isEmpty();
    }

    public String getCurrentPath() {
        return m_rep.peek().m_path;
    }

    private class Frame {
        public ReflectElementHandler m_elementHandler;
        public Object m_object;
        public String m_path;

        public Frame(ReflectElementHandler elementHandler, Object object, String path) {
            m_elementHandler = elementHandler;
            m_object = object;
            m_path = path;
        }

    }
}
