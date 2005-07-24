/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;

public abstract class SmiSymbol {

    private IdToken m_idToken;
    private SmiModule module_;


    public SmiSymbol(IdToken idToken, SmiModule module) {
        super();
        m_idToken = idToken;
        module_ = module;
    }


    public String getId() {
        return m_idToken.getId();
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public abstract String getCodeId();

    public String getFullCodeId() {
        return module_.getMib().getCodeNamingStrategy().getFullCodeId(this);
    }


    public SmiModule getModule() {
        return module_;
    }

    public String getUcId()
    {
        return SmiUtil.ucFirst(getId());
    }

}
