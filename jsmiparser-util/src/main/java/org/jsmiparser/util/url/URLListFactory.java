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
import java.util.List;

/**
 * Utility interface to create a List<URL> for input to the FileParserPhase of the jsmiparser.
 */
public interface URLListFactory {

    /**
     * @return The URL List.
     */
    List<URL> create() throws Exception;

}
