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

import org.jsmiparser.phase.xref.XRefProblemReporter;

/**
 * Indexes belong to a row and refer to a column.
 * Note that it is possible that the column belongs to another table!
 */
public class SmiIndex {

    private final ScopedId m_scopedId;
    private final SmiRow m_row;
    private final boolean m_implied;
	
	public SmiIndex(SmiRow row, ScopedId scopedId, boolean implied) {
        m_row = row;
        m_scopedId = scopedId;
        m_implied = implied;
	}

	public boolean isImplied() {
		return m_implied;
	}

	public SmiVariable getColumn() {
		return (SmiVariable) m_scopedId.getSymbol();
	}

	public SmiRow getRow() {
		return m_row;
	}

    public boolean isColumnFromOtherTable() {
        return m_row.getTable() != getColumn().getTable();
    }

    public void resolveReferences(XRefProblemReporter reporter) {
        m_scopedId.resolveReferences(reporter);
    }

}
