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

import java.lang.reflect.Proxy;

public class DefaultProblemReporterFactory implements ProblemReporterFactory {

    private final ClassLoader m_classLoader;
    private final ProblemEventHandler m_problemEventHandler;

    public DefaultProblemReporterFactory(ProblemEventHandler ph) {
        m_classLoader = Thread.currentThread().getContextClassLoader();
        m_problemEventHandler = ph;
    }

    public DefaultProblemReporterFactory(ClassLoader classLoader, ProblemEventHandler ph) {
        m_classLoader = classLoader;
        m_problemEventHandler = ph;
    }

    public ClassLoader getClassLoader() {
        return m_classLoader;
    }

    public ProblemEventHandler getProblemEventHandler() {
        return m_problemEventHandler;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cl) {
        Class[] classArray = {cl};
        return (T) Proxy.newProxyInstance(m_classLoader, classArray, new ProblemInvocationHandler(cl, m_problemEventHandler));
    }

}
