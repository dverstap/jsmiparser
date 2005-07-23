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
import org.jsmiparser.util.SAXFileLocator;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class ReflectContentHandler extends DefaultHandler {
    private static final Logger m_log = Logger.getLogger(ReflectContentHandler.class);

    public static final ReflectElementHandlerFactory[] FACTORIES = {TextElementHandler.FACTORY, BeanElementHandler.FACTORY};

    private SAXFileLocator m_locator = new SAXFileLocator();
    ;
    private XmlReflectProblemReporter m_ph;
    Object root_;
    ElementHandlerStack stack_ = new ElementHandlerStack();
    boolean isOk_ = true;

    private StringBuffer text_ = null;


    private ReflectElementHandlerFactory[] m_elementHandlerFactories;
    private Map<String, ReflectElementHandler> m_elementHandlerCache = new HashMap<String, ReflectElementHandler>();
    private Set<String> m_skippedPathSet = new LinkedHashSet<String>();

    public ReflectContentHandler(ProblemReporterFactory problemReportFactory, Object object) {
        this(problemReportFactory, FACTORIES, object);
    }


    public ReflectContentHandler(ProblemReporterFactory problemReporterFactory, ReflectElementHandlerFactory[] elementHandlerFactories, Object root) {
        super();
        m_ph = problemReporterFactory.create(ReflectContentHandler.class.getClassLoader(), XmlReflectProblemReporter.class);
        m_elementHandlerFactories = elementHandlerFactories;
        root_ = root;
    }

    private boolean isStartRootElement() {
        return stack_.isEmpty();
    }

    public void endDocument() throws SAXException {
        m_locator.setLocator(null);
    }

    public void setDocumentLocator(Locator locator) {
        m_locator.setLocator(locator);
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        ReflectElementHandler reh = stack_.getCurrentElementHandler();
        if (reh != null && reh.isHandlingText()) {
            if (text_ == null)
                text_ = new StringBuffer(length);
            text_.append(ch, start, length);
        }
    }


    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        ReflectElementHandler reh = stack_.getCurrentElementHandler();
        Object currentObject = stack_.getCurrentObject();
        if (reh != null && currentObject != null) {
            String text = null;
            if (text_ != null) {
                text = text_.toString();
                text_ = null;
            }
            try {
                reh.handleEnd(currentObject, text);
            } catch (IllegalAccessException e) {
                throw new SAXException(e);
            } catch (InvocationTargetException e) {
                throw new SAXException(e);
            }
        }
        stack_.pop();
    }


    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {
        Object storedObject = null;

        ReflectElementHandler reh = null;
        if (isStartRootElement()) {
            reh = new RootElementHandler(root_);
            storedObject = callHandleStart(reh, root_, localName, atts);
        } else {
            Object currentObject = stack_.getCurrentObject();
            String path = stack_.getCurrentPath() + "/" + localName;
            if (currentObject == null) {
                m_skippedPathSet.add(path);
            } else {
                Object newObject = null;
                reh = findElementHandler(localName, currentObject, atts, path);
                newObject = callHandleStart(reh, currentObject, localName, atts);
                storedObject = newObject;
            }
        }

        stack_.push(reh, localName, storedObject);
    }

    private Object callHandleStart(ReflectElementHandler reh, Object currentObject, String localName, Attributes atts) throws SAXException {
        Object newObject = null;
        if (reh != null) {
            try {
                newObject = reh.handleStart(currentObject, localName, atts);
            } catch (IllegalAccessException e) {
                m_log.error(e.getMessage(), e);
                throw new SAXException(e);
            } catch (InvocationTargetException e) {
                m_log.error(e.getMessage(), e);
                throw new SAXException(e);
            } catch (InstantiationException e) {
                m_log.error(e.getMessage(), e);
                throw new SAXException(e);
            } catch (NoSuchMethodException e) {
                m_log.error(e.getMessage(), e);
                throw new SAXException(e);
            }
        }
        return newObject;
    }

    private ReflectElementHandler findElementHandler(String localName, Object currentObject, Attributes atts, String path) {
        ReflectElementHandler reh;
        reh = m_elementHandlerCache.get(path);
        if (reh == null) {
            if (!m_skippedPathSet.contains(path)) {
                reh = createElementHandler(currentObject, localName, atts);
                if (reh == null) {
                    m_skippedPathSet.add(path);
                } else {
                    m_log.debug("Using " + reh.getClass() + " to handle path " + path);
                    m_elementHandlerCache.put(path, reh);
                }
            }
        }
        return reh;
    }


    private ReflectElementHandler createElementHandler(Object parentObject, String localName, Attributes atts) {
        for (ReflectElementHandlerFactory factory : m_elementHandlerFactories) {
            ReflectElementHandler reh = factory.create(parentObject, localName, atts);
            if (reh != null) {
                return reh;
            }
        }
        return null;
    }

    public Set<String> getSkippedPathSet() {
        return m_skippedPathSet;
    }

    public void parse(File file) throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(this);

        InputStream is = new BufferedInputStream(new FileInputStream(file));
        InputSource inputSource = new InputSource(is);
        reader.parse(inputSource);
        is.close();
    }

    public void parse(InputStream is) throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(this);

        InputSource inputSource = new InputSource(is);
        reader.parse(inputSource);
        is.close();
    }

    public void printSkippedPathErrors() {
        for (String skippedPath : getSkippedPathSet()) {
            m_ph.reportSkippedPath(skippedPath);
        }
    }
}
