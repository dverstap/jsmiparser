/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.smi;

public enum StatusAll {
    MANDATORY,
    OPTIONAL,
    OBSOLETE,
    DEPRECATED,
    CURRENT;

    private String m_keyword;

    private StatusAll() {
        m_keyword = name().toLowerCase();
    }

    public String toString() {
        return m_keyword;
    }

    public static StatusAll find(String keyword) {
        return Util.find(StatusAll.class, keyword);
    }
}
