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

import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.phase.xref.XRefProblemReporter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class SmiSymbol implements Serializable, Comparable {

    private IdToken m_idToken;
    private SmiModule m_module;
    private List<IdToken> m_users = new ArrayList<IdToken>();


    public SmiSymbol(IdToken idToken, SmiModule module) {
        super();

        if (module == null) {
            throw new IllegalArgumentException();
        }

        m_idToken = idToken;
        m_module = module;
    }

    public SmiSymbol(SmiModule module) {
        super();

        if (module == null) {
            throw new IllegalArgumentException();
        }

        m_module = module;
    }

    public String getId() {
        return m_idToken != null ? m_idToken.getId() : null;
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public void setIdToken(IdToken idToken) {
        m_idToken = idToken;
    }

    //  TODO should be abstract
    public String getCodeId() {
        return null;
    }

    public String getFullCodeId() {
        return m_module.getMib().getCodeNamingStrategy().getFullCodeId(this);
    }

    public SmiModule getModule() {
        return m_module;
    }

    public Location getLocation() {
        return m_idToken != null ? m_idToken.getLocation() : null;
    }

    public String getUcId() {
        return SmiUtil.ucFirst(getId());
    }

    public List<IdToken> getUsers() {
        return m_users;
    }

    public void addUser(IdToken idToken) {
        m_users.add(idToken);
    }

    @Override
    public String toString() {
        return m_module.getId() + ": " + getId();
    }

    @Override
    public int hashCode() {
        if (m_idToken != null) {
            return m_idToken.getId().hashCode();
        }
        return super.hashCode();
    }

    /**
     * @param obj
     * @return equality by SmiSymbol identifier and SmiModule
     */
    @Override
    public boolean equals(Object obj) {
        if (m_idToken != null) {
            if (obj instanceof SmiSymbol) {
                SmiSymbol other = (SmiSymbol) obj;
                return this.m_module.equals(other.m_module) && other.getId().equals(this.getId());
            }
        }
        return super.equals(obj);
    }

    public int compareTo(Object o) {
        return compareTo((SmiSymbol) o);
    }

    public int compareTo(SmiSymbol other) {
        int result = getModule().getId().compareTo(other.getModule().getId());
        if (result == 0) {
            result = getId().compareTo(other.getId());
        }
        return result;
    }

    public void resolveReferences(XRefProblemReporter reporter) {
        // do nothing
    }

}
