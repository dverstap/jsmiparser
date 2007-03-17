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

import static org.jsmiparser.smi.MacroType.*;

import java.util.Set;
import java.util.EnumSet;

public enum StatusAll {
    MANDATORY(OBJECT_TYPE_V1),
    OPTIONAL(OBJECT_TYPE_V1),
    OBSOLETE(OBJECT_TYPE_V1, OBJECT_IDENTITY, NOTIFICATION_TYPE, TEXTUAL_CONVENTION, OBJECT_GROUP, NOTIFICATION_GROUP, MODULE_COMPLIANCE, AGENT_CAPABILITIES),
    DEPRECATED(OBJECT_TYPE_V1, OBJECT_IDENTITY, NOTIFICATION_TYPE, TEXTUAL_CONVENTION, OBJECT_GROUP, NOTIFICATION_GROUP, MODULE_COMPLIANCE),
    CURRENT(OBJECT_IDENTITY, NOTIFICATION_TYPE, TEXTUAL_CONVENTION, OBJECT_GROUP, NOTIFICATION_GROUP, MODULE_COMPLIANCE, AGENT_CAPABILITIES);

    private String m_keyword;
    private Set<MacroType> m_supportedMacroTypes = EnumSet.noneOf(MacroType.class);

    private StatusAll(MacroType... macroTypes) {
        m_keyword = name().toLowerCase();

        for (MacroType macroType : macroTypes) {
            m_supportedMacroTypes.add(macroType);
        }
    }

    public String toString() {
        return m_keyword;
    }

    public static StatusAll find(String keyword, boolean mandatory) {
        return Util.find(StatusAll.class, keyword, mandatory);
    }

    public StatusV1 getStatusV1() {
        for (StatusV1 statusV1 : StatusV1.values()) {
            if (statusV1.getStatusAll() == this) {
                return statusV1;
            }
        }
        return null;
    }

    public StatusV2 getStatusV2() {
        for (StatusV2 statusV2 : StatusV2.values()) {
            if (statusV2.getStatusAll() == this) {
                return statusV2;
            }
        }
        return null;
    }

    public static StatusAll findV1(MacroType macroType, String keyword) {
        StatusAll result = find(keyword, true);
        // TODO handle macro's for both V1/V2
        if (result.isSupportedBy(macroType)) {
            return result;
        }
        throw new IllegalArgumentException("Status " + result + " is not supported by " + macroType);
    }

    public boolean isSupportedBy(MacroType macroType) {
        return m_supportedMacroTypes.contains(macroType);
    }

}
