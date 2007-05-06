/*
 * Copyright 2007 Davy Verstappen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsmiparser.util.url;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeURLListFactory implements URLListFactory {

    private List<URLListFactory> m_children;

    public CompositeURLListFactory() {
        this(new ArrayList<URLListFactory>());
    }

    public CompositeURLListFactory(List<URLListFactory> children) {
        m_children = children;
    }

    public CompositeURLListFactory(URLListFactory... urlListFactories) {
        this(Arrays.asList(urlListFactories));
    }

    public List<URLListFactory> getChildren() {
        return m_children;
    }

    public void setChildren(List<URLListFactory> children) {
        m_children = children;
    }

    public List<URL> create() throws Exception {
        List<URL> result = new ArrayList<URL>();
        for (URLListFactory child : m_children) {
            result.addAll(child.create());
        }
        return result;
    }
}
