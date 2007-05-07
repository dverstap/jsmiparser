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

import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.io.File;

public class FileURLListFactory extends AbstractURLListFactory {

    public FileURLListFactory() {
        super();
    }

    public FileURLListFactory(File rootDir) {
        this(rootDir.getAbsolutePath());
    }

    public FileURLListFactory(String rootPath) {
        super(rootPath);
    }

    public FileURLListFactory(File rootDir, List<String> children) {
        super(rootDir.getAbsolutePath(), children);
    }

    public FileURLListFactory(String rootPath, List<String> children) {
        super(rootPath, children);
    }


    public List<URL> create() throws Exception {
        List<URL> result = new ArrayList<URL>();
        File dir = new File(m_rootPath);
        for (String child : m_children) {
            File file = new File(dir, child);
            if (!file.exists()) {
                throw new IllegalStateException("File doesn't exist: " + file);
            }
            result.add(file.toURL());
        }
        return result;
    }
}
