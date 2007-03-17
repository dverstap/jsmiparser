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


// TODO add an interface with isWritable, isReadable etc methods, implemented by all three AccessX classes
public enum AccessAll implements AccessPermissions {
    READ_ONLY,
    READ_WRITE,
    WRITE_ONLY,
    NOT_ACCESSIBLE,
    ACCESSIBLE_FOR_NOTIFY,
    READ_CREATE,
    NOT_IMPLEMENTED;

    private String m_keyword;

    private AccessAll() {
        m_keyword = name().toLowerCase().replace('_', '-');
    }

    public String toString() {
        return m_keyword;
    }

    public static AccessAll find(String keyword, boolean mandatory) {
        return Util.find(AccessAll.class, keyword, mandatory);
    }

    public boolean isCreateWritable() {        
        return isWritable() || this == READ_CREATE;
    }

    public boolean isReadable() {
        return this == READ_ONLY || this == READ_WRITE || this == READ_CREATE;
    }

    public boolean isWritable() {
        return this == READ_WRITE || this == WRITE_ONLY;
    }

/*
    public AccessAll find(String keyword, MacroType macroType) {
        AccessAll result = Util.find(AccessAll.class, keyword);
        if (result.isSupportedBy(macroType)) {
            return result;
        }
        throw new IllegalArgumentException("Access " + result + " is not supported by macro " + macroType);
    }

    public boolean isSupportedBy(MacroType macroType) {
        return m_supportedMacroTypes.contains(macroType);
    }
*/

}
