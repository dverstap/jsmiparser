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

import org.jsmiparser.util.token.IdToken;

public class SmiField {

    private SmiType m_parentType;
    private IdToken m_columnIdToken;
    private SmiAttribute m_column;
    private SmiType m_type;

    public SmiField(SmiType parentType, IdToken columnIdToken, SmiType type) {
        m_parentType = parentType;
        m_columnIdToken = columnIdToken;
        m_type = type;
    }

    public SmiType getParentType() {
        return m_parentType;
    }

    public IdToken getColumnIdToken() {
        return m_columnIdToken;
    }

    public SmiAttribute getColumn() {
        return m_column;
    }

    public SmiType getType() {
        return m_type;
    }

    public void resolveReferences() {
        m_column = m_parentType.getModule().resolveReference(m_columnIdToken); // TODO error msg?
    }

    // TODO resolve type?
}
