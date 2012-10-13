/*
 * Copyright 2012 The OpenNMS Group, Inc.
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
package org.jsmiparser.util.traps;

import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiTrapType;
import org.jsmiparser.util.token.IdToken;

import java.util.List;

public class SmiTrapWrapper implements TrapWrapper {

    private final SmiTrapType trap;

    public SmiTrapWrapper(SmiTrapType trap) {
        this.trap = trap;
    }

    public String getDescription() {
        return trap.getDescription();
    }

    public List<IdToken> getParameterTokens() {
        return trap.getVariableTokens();
    }

    public SmiModule getModule() {
        return trap.getModule();
    }

    public String getId() {
        return trap.getId();
    }

    public String getTrapOid() {
        return trap.getEnterpriseOid().getOidStr() + '.' + trap.getSpecificType();
    }

}
