/*
 * Copyright 2006 Davy Verstappen.
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

public enum AccessV1 {
    READ_ONLY(AccessAll.READ_ONLY),
    READ_WRITE(AccessAll.READ_WRITE),
    WRITE_ONLY(AccessAll.WRITE_ONLY),
    NOT_ACCESSIBLE(AccessAll.NOT_ACCESSIBLE);

    private AccessAll m_accessAll;

    AccessV1(AccessAll accessAll) {
       m_accessAll = accessAll;
   }

    public String toString() {
        return m_accessAll.toString();
    }

    public AccessV1 find(String keyword) {
        return Util.find(AccessV1.class, keyword);
    }

}
