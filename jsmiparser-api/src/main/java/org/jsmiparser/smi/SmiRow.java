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
import org.jsmiparser.phase.xref.XRefProblemReporter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmiRow extends SmiObjectType {

    // TODO remove?
    private List<SmiRow> m_parentRows = new ArrayList<SmiRow>();
    private List<SmiRow> m_childRows = new ArrayList<SmiRow>();

    private List<SmiIndex> m_indexes;
    private ScopedId m_augmentsId;

    public SmiRow(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public SmiTable getTable() {
        return getNode().getParent().getSingleValue(SmiTable.class, getModule());
    }

    public List<SmiVariable> getColumns() {

        List<SmiVariable> result = new ArrayList<SmiVariable>();
        for (SmiOidNode child : getNode().getChildren()) {
            result.add(child.getSingleValue(SmiVariable.class));
        }
        return result;
    }

    public SmiRow getAugments() {
        if (m_augmentsId != null) {
            // TODO type safety check when resolving
            return (SmiRow) m_augmentsId.getSymbol();
        } else {
            return null;
        }
    }

    public void setAugmentsId(ScopedId augmentsId) {
        m_augmentsId = augmentsId;
    }

    public List<SmiIndex> getIndexes() {
        return m_indexes;
    }

    public List<SmiRow> getChildRows() {
        return m_childRows;
    }

    public List<SmiRow> getParentRows() {
        return m_parentRows;
    }

    public SmiVariable findColumn(String id) {
        for (SmiVariable c : getColumns()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public SmiIndex addIndex(ScopedId scopedId, boolean isImplied) {
        if (m_indexes == null) {
            m_indexes = new ArrayList<SmiIndex>();
        }
        SmiIndex index = new SmiIndex(this, scopedId, isImplied);
        m_indexes.add(index);
        return index;
    }

    public boolean hasSameIndexes(SmiRow other) {
        boolean result = false;
        if (m_indexes.size() == other.m_indexes.size()) {
            boolean tmpResult = true;
            Iterator<SmiIndex> i = m_indexes.iterator();
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
        m_parentRows.add(row);
        row.m_childRows.add(this);
    }


    @Override
    public void resolveReferences(XRefProblemReporter reporter) {
        super.resolveReferences(reporter);
        if (m_indexes != null) {
            for (SmiIndex index : m_indexes) {
                index.resolveReferences(reporter);
            }
        } else {
            m_augmentsId.resolveReferences(reporter);
            SmiRow augmentedRow = getAugments();
            if (augmentedRow != null) {
                augmentedRow.m_childRows.add(this);
                m_parentRows.add(augmentedRow);
            }
        }
    }
}
