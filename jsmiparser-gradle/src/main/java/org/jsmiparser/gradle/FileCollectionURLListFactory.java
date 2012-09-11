package org.jsmiparser.gradle;/*
 * Copyright 2012 Davy Verstappen.
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

import org.gradle.api.file.FileCollection;
import org.jsmiparser.util.url.URLListFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileCollectionURLListFactory implements URLListFactory {

    private final FileCollection fileCollection;

    public FileCollectionURLListFactory(FileCollection fileCollection) {
        this.fileCollection = fileCollection;
    }

    public List<URL> create() throws Exception {
        List<URL> result = new ArrayList<URL>();
        for (File f : fileCollection) {
            result.add(f.toURI().toURL());
        }
        return result;
    }
}
