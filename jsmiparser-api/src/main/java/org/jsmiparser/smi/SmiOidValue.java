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

import org.jsmiparser.phase.oid.OidNode;
import org.jsmiparser.phase.oid.OidProblemReporter;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.Token;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SmiOidValue extends SmiValue {

    //private OidNode m_oidComponent;

    private List<OidComponent> m_oidComponents;
    private SmiOidValue m_parent;
    private String m_oid;
    private Map<BigInteger, SmiOidValue> m_childMap = new TreeMap<BigInteger, SmiOidValue>();

    public SmiOidValue(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    // TODO delete
    public OidNode getOidComponent() {
        return null;
//        return m_oidComponent;
    }

    public void setOidComponent(OidNode oidComponent) {
//        m_oidComponent = oidComponent;
    }

    public List<OidComponent> getOidComponents() {
        return m_oidComponents;
    }

    public void setOidComponents(List<OidComponent> oidComponents) {
        m_oidComponents = oidComponents;
    }

    /**
     * @return null for the root node; the OID in decimal dotted notation for all other nodes
     */
    public String getOid() {
        if (m_oid == null) {
            SmiOidValue parent = getParent();
            if (parent != null) {
                String parentOid = parent.getOid();
                if (parentOid != null) {
                    m_oid = parent.getOid() + "." + getLastOid();
                } else {
                    m_oid = getLastOid().toString();
                }
            }
        }
        return m_oid;
/*
        if (m_oidComponent == null) {
            return null;
            // TODO throw new IllegalStateException("oidComponent not set on " + getIdToken());
        }

        return m_oidComponent.getDecimalDottedStr();
*/
    }

    public SmiOidValue getParent() {
        return m_parent;
//        if (m_oidComponents.size() > 1) {
//            return m_oidComponents.get(m_oidComponents.size() - 2).getSymbol();
//        } else {
//            return null;
//        }
    }

    void setParent(SmiOidValue parent) {
        if (parent == null) {
            throw new IllegalArgumentException(getId());
        }
        SmiOidValue oldChild = parent.findChild(getLastOid());
        if (oldChild != null) {
            System.out.println("there is already a child " + oldChild.getIdToken() + " for last oid " + getLastOid() + " under " + getIdToken());
        }
        m_parent = parent;
        m_parent.m_childMap.put(getLastOid(), this);
    }

    public Collection<? extends SmiOidValue> getChildren() {
        return m_childMap.values();
    }

    public void resolveOid(OidProblemReporter pr) {
        SmiOidValue parent = null;
        OidComponent prevOidComponent = null;
        boolean isFirst = true;
        for (Iterator<OidComponent> iterator = m_oidComponents.iterator(); iterator.hasNext();) {
            OidComponent oidComponent = iterator.next();
            SmiOidValue oidValue = null;
            if (isFirst) {
                if (iterator.hasNext()) {
                    oidValue = resolveOidComponent(oidComponent, parent, prevOidComponent, isFirst);
                } else {
                    assert(m_oidComponents.size() == 1);
                    assert(parent == null);
                    oidValue = this;
                }
                isFirst = false;
            } else if (iterator.hasNext()) {
                oidValue = resolveOidComponent(oidComponent, parent, prevOidComponent, isFirst);
                if (oidValue == null) {
                    oidValue = createDummyOidValue(oidComponent, prevOidComponent, parent);
                }
            } else {
                oidValue = this;
                setParent(parent);
            }
            oidComponent.setSymbol(oidValue);
/*
            if (parent != null && oidValue != null) {
                parent.m_childMap.put(oidValue.getLastOid(), oidValue);
                // add child
            }
*/
            parent = oidValue;
            prevOidComponent = oidComponent;
        }
    }

    private SmiOidValue createDummyOidValue(OidComponent oidComponent, OidComponent prevOidComponent, SmiOidValue parent) {
        Token token = oidComponent.getIdToken() != null ? oidComponent.getIdToken() : oidComponent.getValueToken();
        System.out.println("warning: creating dummy middle oid for: " + token.getObject() + " at " + token.getLocation()); // TODO
        SmiOidValue oidValue = new SmiOidValue(oidComponent.getIdToken(), getModule());
        List<OidComponent> oidComponents = new ArrayList<OidComponent>();
        OidComponent oc1 = new OidComponent(prevOidComponent.getIdToken(), prevOidComponent.getValueToken());
        oc1.setSymbol(parent);
        oidComponents.add(oc1);
        OidComponent oc2 = new OidComponent(null, oidComponent.getValueToken());
        oc2.setSymbol(oidValue);
        oidComponents.add(oc2);
        oidValue.setOidComponents(oidComponents);
        oidValue.setParent(parent);
        getModule().getMib().m_dummyOidNodesCount++;
        return oidValue;
    }

    private SmiOidValue resolveOidComponent(OidComponent oidComponent, SmiOidValue parent, OidComponent prevOidComponent, boolean isFirst) {
        SmiOidValue oidValue = null;
        if (oidComponent.getIdToken() != null) {
            oidValue = getModule().resolveReference(oidComponent.getIdToken());
            if (oidValue != null) {
                if (oidComponent.getValueToken() != null) {
                    // TODO compare
                }
            } else if (parent != null && oidComponent.getValueToken() != null) {
                BigInteger value = oidComponent.getValueToken().getValue();
                oidValue = parent.m_childMap.get(value);
            }
        } else {
            if (isFirst) {
                oidValue = getModule().getMib().getRootNode().m_childMap.get(oidComponent.getValueToken().getValue());
            } else if (parent != null) {
                oidValue = parent.m_childMap.get(oidComponent.getValueToken().getValue());
            } else {
                throw new IllegalStateException(oidComponent.getValueToken().toString());
            }
        }
        return oidValue;
    }

    public int getTotalChildCount() {
        int result = m_childMap.size();
        for (SmiOidValue oidValue : m_childMap.values()) {
            result += oidValue.getTotalChildCount();
        }
        return result;
    }

    public BigInteger getLastOid() {
        if (m_oidComponents.isEmpty()) {
            return null;
        } else {
            return m_oidComponents.get(m_oidComponents.size() - 1).getValueToken().getValue();
        }
    }

    public void dumpTree(PrintStream w, String indent) {
        w.print(indent);
        w.print(getId());
        w.print("(");
        w.print(getLastOid());
        w.println(")");
        for (SmiOidValue child : m_childMap.values()) {
            child.dumpTree(w, indent + " ");
        }
    }

    /**
     * This method makes no sense; it is used exclusively for unit testing.
     */
    public SmiOidValue getRootNode() {
        SmiOidValue parent = this;
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
    }

    public boolean contains(SmiOidValue oidValue) {
        SmiOidValue result = m_childMap.get(oidValue.getLastOid());
        return result != null && result == oidValue;
    }

    public SmiOidValue findChild(BigInteger id) {
        return m_childMap.get(id);
    }

    public void dumpAncestors(PrintStream out) {
        SmiOidValue oidValue = this;
        while (oidValue != null) {
            out.print(oidValue.getId());
            out.print("(");
            out.print(oidValue.getLastOid());
            out.print(")");
            if (oidValue.getParent() != null) {
                out.print(": ");
            }
            oidValue = oidValue.getParent();
        }
        out.println();
    }
}