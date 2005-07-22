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

import java.lang.reflect.InvocationTargetException;

/**
 * One ReflectElementHandler object is created for each unique path in the XML document.
 * Each type of ReflectElementHandler object should cache as much of the reflected information
 * as possible to minimize further need for reflection.
 *
 * The created ReflectElementHandler object is cached in a map, for fast lookups.
 *
 * The fact that no ReflectElementHandler object could be created for a particular path is also cached.
 * This eliminates the overhead of any further attempts to use reflection to determine what should be done.
 * It also allows to only write one single trace that no ReflectElementHandler could be created.
 *
 */
public interface ReflectElementHandler {

    public Object handleStart(Object current, String localName, Attributes atts) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;

    public void handleEnd(Object current, String text) throws IllegalAccessException, InvocationTargetException;

    /** This method has to return true if you wish to process contained text in the handleEnd() method.
    */
    public boolean isHandlingText();
}
