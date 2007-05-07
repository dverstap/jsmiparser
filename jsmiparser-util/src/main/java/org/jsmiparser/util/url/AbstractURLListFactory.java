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
import java.util.List;


public abstract class AbstractURLListFactory implements URLListFactory {

    protected String m_rootPath;
    protected List<String> m_children;

    public AbstractURLListFactory() {
        this("");
    }

    public AbstractURLListFactory(String rootPath) {
        this(rootPath, new ArrayList<String>());
    }

    public AbstractURLListFactory(String rootPath, List<String> children) {
        m_rootPath = rootPath;
        m_children = children;
    }

    public String getRootPath() {
        return m_rootPath;
    }

    public void setRootPath(String rootPath) {
        m_rootPath = rootPath;
    }

    public List<String> getChildren() {
        return m_children;
    }

    public void setChildren(List<String> children) {
        m_children = children;
    }

    public void add(String child) {
        getChildren().add(child);
    }

}
