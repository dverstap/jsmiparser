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

	private SmiRow row_;
	private SmiColumn column_;
	private boolean isImplied_;
	
	public SmiIndex(SmiRow row, SmiColumn column, boolean implied) {
		super();
		assert(column != null);
		
		row_ = row;
		column_ = column;
		column_.getIndexes().add(this);
		isImplied_ = implied;
	}

	public boolean isImplied() {
		return isImplied_;
	}

	public void setImplied(boolean isImplied) {
		isImplied_ = isImplied;
	}

	public SmiColumn getColumn() {
		return column_;
	}

	public SmiRow getRow() {
		return row_;
	}
	
	

}
