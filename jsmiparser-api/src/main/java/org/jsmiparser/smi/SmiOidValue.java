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

import org.apache.log4j.Logger;
import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.Token;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SmiOidValue extends SmiValue {

    private static final Logger m_log = Logger.getLogger(SmiOidValue.class);

    private List<OidComponent> m_oidComponents;
    private SmiOidValue m_parent;
    private int[] m_oid;
    private String m_oidStr;
    private Map<Integer, SmiOidValue> m_childMap = new TreeMap<Integer, SmiOidValue>();

    public SmiOidValue(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public List<OidComponent> getOidComponents() {
        return m_oidComponents;
    }

    public void setOidComponents(List<OidComponent> oidComponents) {
        m_oidComponents = oidComponents;
    }

    public int[] getOid() {
        return m_oid;
    }

    /**
     *
     * @return null for the root node; the OID in decimal dotted notation for all other nodes
     */
    public String getOidStr() {
        return m_oidStr;
    }

    public SmiOidValue getParent() {
        return m_parent;
    }

    void setParent(SmiOidValue parent) {
        if (parent == null) {
            throw new IllegalArgumentException(getId());
        }
        SmiOidValue oldChild = parent.findChild(getLastOid());
        if (oldChild != null && oldChild != this) {
            m_log.warn("there is already a child " + oldChild.getIdToken() + " for last oid " + getLastOid() + " under " + getIdToken());
        }
        m_parent = parent;
        m_parent.m_childMap.put(getLastOid(), this);
    }

    public Collection<? extends SmiOidValue> getChildren() {
        return m_childMap.values();
    }

    public boolean hasChildren() {
        return m_childMap != null && !m_childMap.isEmpty();
    }

    public void resolveOid(XRefProblemReporter reporter) {
        SmiOidValue parent = null;
        OidComponent prevOidComponent = null;
        boolean isFirst = true;
        for (Iterator<OidComponent> iterator = m_oidComponents.iterator(); iterator.hasNext();) {
            OidComponent oidComponent = iterator.next();
            SmiOidValue oidValue;
            if (isFirst) {
                if (iterator.hasNext()) {
                    oidValue = resolveOidComponent(oidComponent, parent, isFirst, reporter);
                } else {
                    assert (m_oidComponents.size() == 1);
                    oidValue = this;
                    setParent(getModule().getMib().getRootNode());
                }
                isFirst = false;
            } else if (iterator.hasNext()) {
                oidValue = resolveOidComponent(oidComponent, parent, isFirst, reporter);
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

    public int[] determineFullOid() {
        if (m_oid == null) {
            SmiOidValue parent = getParent();
            if (parent != null) {
                int[] parentOid = parent.determineFullOid();
                if (parentOid != null) {
                    m_oid = new int[parentOid.length + 1];
                    System.arraycopy(parentOid, 0, m_oid, 0, parentOid.length);
                    m_oid[m_oid.length-1] = getLastOid();
                    m_oidStr = parent.getOidStr() + "." + getLastOid();
                } else {
                    m_oid = new int[] {getLastOid()};
                    m_oidStr = String.valueOf(getLastOid());
                }
            }
        }
        return m_oid;
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

    private SmiOidValue resolveOidComponent(OidComponent oidComponent, SmiOidValue parent, boolean isFirst, XRefProblemReporter reporter) {
        SmiOidValue oidValue;
        if (oidComponent.getIdToken() != null) {
            SmiSymbol symbol = getModule().resolveReference(oidComponent.getIdToken(), null);
            if (symbol != null) {
                if (symbol instanceof SmiOidValue) {
                    oidValue = (SmiOidValue) symbol;
                    if (oidComponent.getValueToken() != null) {
                        // TODO compare
                    }
                } else {
                    reporter.reportFoundSymbolButWrongType(oidComponent.getIdToken(), SmiOidValue.class, symbol.getClass());
                    oidValue = null;
                }
            } else if (parent != null && oidComponent.getValueToken() != null) {
                int value = oidComponent.getValueToken().getValue();
                oidValue = parent.m_childMap.get(value);
            } else {
                oidValue = null;
            }
        } else {
            if (isFirst) {
                oidValue = getModule().getMib().getRootNode().m_childMap.get(oidComponent.getValueToken().getValue());
                if (oidValue == null) {
                    throw new IllegalStateException(oidComponent.getValueToken().toString());
                }
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

    public int getLastOid() {
        if (m_oidComponents.isEmpty()) {
            return -1;
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
     * This method as such makes no sense; it is used mainly for unit testing.
     *
     * @return The root of the oid tree.
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

    public SmiOidValue findChild(int id) {
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