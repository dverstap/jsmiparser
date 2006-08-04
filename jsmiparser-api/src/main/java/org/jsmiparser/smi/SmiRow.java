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

import java.util.*;

public class SmiRow extends SmiObjectType implements SmiClass {

	private SmiTable table_;
	private List<SmiRow> parentRows_ = new ArrayList<SmiRow>();
	private List<SmiRow> childRows_ = new ArrayList<SmiRow>();
	private List<SmiColumn> columns_ = new ArrayList<SmiColumn>();
	private SmiRow augments_;
	private List<SmiIndex> indexes_ = new ArrayList<SmiIndex>();

	public SmiRow(IdToken idToken, SmiModule module) {
		super(idToken, module);
	}
	
	public SmiTable getTable()
	{
		return table_;
	}
	
	public void setTable(SmiTable table) {
		table_ = table;
		table_.row_ = this;
	}

	public List<SmiColumn> getColumns()
	{
		return columns_;
	}
	
	public SmiRow getAugments()
	{
		return augments_;
	}
	
	public void setAugments(SmiRow augments) {
		augments_ = augments;
		augments_.childRows_.add(this);
	}

	public List<SmiIndex> getIndexes()
	{
		return indexes_;
	}

	public List<? extends SmiClass> getParentClasses() {
		return parentRows_;
	}

	public List<? extends SmiClass> getChildClasses() {
		return childRows_;
	}

    public SmiAttribute findAttribute(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

	public List<SmiColumn> getAttributes() {
		return columns_;
	}

	public Set<SmiColumn> getAllAttributes() {
		return getAllColumns();
	}

    public boolean isRowClass() {
        return true;
    }

    public boolean isScalarsClass() {
        return false;
    }

    private Set<SmiColumn> getAllColumns() {
		Set<SmiColumn> result = new LinkedHashSet<SmiColumn>();
		addAllColumns(result);
		return result;
	}

	private void addAllColumns(Set<SmiColumn> allColumns) {
		for (SmiRow parent : parentRows_) {
			parent.addAllColumns(allColumns);
		}
		allColumns.addAll(columns_);
	}

	public List<SmiRow> getChildRows() {
		return childRows_;
	}

	public List<SmiRow> getParentRows() {
		return parentRows_;
	}

	public SmiColumn findColumn(String id) {
		for (SmiColumn c : columns_) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	public SmiIndex addIndex(SmiColumn col, boolean isImplied) {
		SmiIndex index = new SmiIndex(this, col, isImplied);
		indexes_.add(index);
		return index;
	}

	public boolean hasSameIndexes(SmiRow other) {
		boolean result = false;
		if (indexes_.size() == other.indexes_.size()) {
			boolean tmpResult = true;
			Iterator<SmiIndex> i = indexes_.iterator();
			Iterator<SmiIndex> j = other.getIndexes().iterator();
			while (tmpResult && i.hasNext() && j.hasNext()) {
				SmiIndex i1 = i.next();
				SmiIndex i2 = j.next();
				if (i1.getColumn() != i2.getColumn()) {
					tmpResult = false;
				}
				if (i1.isImplied() != i2.isImplied()) {
					System.out.printf("implied index is not the same for %s and %s", getId(), other.getId())
						.println();
					tmpResult = false;
				}
			}
			result = tmpResult;
		}
		//System.out.printf("IndexCheck for %s and %s : %b%n", getId(), other.getId(), result);
		return result;
	}

	public void addParentRow(SmiRow row) {
		parentRows_.add(row);
		row.childRows_.add(this);
	}
}
