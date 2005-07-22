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
package org.jsmiparser.util.problem;

import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;

public class DefaultProblemReporterFactory implements ProblemReporterFactory {
    private static final Logger m_log = Logger.getLogger(DefaultProblemReporterFactory.class);

    private ProblemHandler m_problemHandler;

    public DefaultProblemReporterFactory(ProblemHandler ph) {
        m_problemHandler = ph;
    }

    public <T> T create(ClassLoader classLoader, Class<T> cl) {
        Class[] classArray = { cl };
        return (T) Proxy.newProxyInstance(classLoader, classArray, new ProblemInvocationHandler(cl, m_problemHandler));
    }

}
