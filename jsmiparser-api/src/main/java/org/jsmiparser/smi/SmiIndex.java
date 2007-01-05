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

public class SmiIndex {

	private SmiRow m_row;
	private SmiAttribute m_column;
	private boolean m_implied;
	
	public SmiIndex(SmiRow row, SmiAttribute column, boolean implied) {
		super();
		assert(column != null);
		
		m_row = row;
		m_column = column;
		// TODO m_column.getIndexes().add(this);
		m_implied = implied;
	}

	public boolean isImplied() {
		return m_implied;
	}

	public void setImplied(boolean isImplied) {
		m_implied = isImplied;
	}

	public SmiAttribute getColumn() {
		return m_column;
	}

	public SmiRow getRow() {
		return m_row;
	}
	
	

}
