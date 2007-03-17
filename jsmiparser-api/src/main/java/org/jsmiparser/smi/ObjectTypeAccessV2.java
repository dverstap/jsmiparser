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

public enum ObjectTypeAccessV2 implements AccessPermissions {
    NOT_ACCESSIBLE(AccessAll.NOT_ACCESSIBLE),
    ACCESSIBLE_FOR_NOTIFY(AccessAll.ACCESSIBLE_FOR_NOTIFY),
    READ_ONLY(AccessAll.READ_ONLY),
    READ_WRITE(AccessAll.READ_WRITE),
    READ_CREATE(AccessAll.READ_CREATE);

    private AccessAll m_accessAll;

    ObjectTypeAccessV2(AccessAll accessAll) {
        m_accessAll = accessAll;
    }

    public AccessAll getAccessAll() {
        return m_accessAll;
    }

    public String toString() {
        return m_accessAll.toString();
    }

    public static ObjectTypeAccessV2 find(String keyword, boolean mandatory) {
        return Util.find(ObjectTypeAccessV2.class, keyword, mandatory);
    }

    public boolean isCreateWritable() {        
        return isWritable() || this == READ_CREATE;        
    }

    public boolean isReadable() {
        return this == READ_ONLY || this == READ_WRITE || this == READ_CREATE;
    }

    public boolean isWritable() {        
        return this == READ_WRITE;
    }
}
