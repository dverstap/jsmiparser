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

import java.util.Collection;

public class SmiOidValue extends SmiValue {

    private static final Logger m_log = Logger.getLogger(SmiOidValue.class);

    private OidComponent m_lastOidComponent;
//    private SmiOidValue m_parent;
//    private int[] m_oid;
//    private String m_oidStr;
//    private Map<Integer, SmiOidValue> m_childMap = new TreeMap<Integer, SmiOidValue>();

    private SmiOidNode m_node;

    public SmiOidValue(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public SmiOidValue(IdToken idToken, SmiModule internalModule, SmiOidNode node) {
        super(idToken, internalModule);
        m_node = node;
    }

    public OidComponent getLastOidComponent() {
        return m_lastOidComponent;
    }

    public void setLastOidComponent(OidComponent lastOidComponent) {
        m_lastOidComponent = lastOidComponent;
    }

    public int[] getOid() {
        return m_node.getOid();
    }

    /**
     *
     * @return null for the root node; the OID in decimal dotted notation for all other nodes
     */
    public String getOidStr() {
        return m_node.getOidStr();
    }

/*
    public SmiOidValue getParent() {
        //return m_parent;
        return null;
    }
*/

//    void setParent(SmiOidValue parent) {
//        if (parent == null) {
//            throw new IllegalArgumentException(getId());
//        }
//        SmiOidValue oldChild = parent.findChild(getLastOid());
//        if (oldChild != null && oldChild != this) {
//            m_log.warn("there is already a child " + oldChild.getIdToken() + " for last oid " + getLastOid() + " under " + getIdToken());
//        }
//        m_parent = parent;
//        m_parent.m_childMap.put(getLastOid(), this);
//    }

    public Collection<? extends SmiOidValue> getChildren() {
        //return m_childMap.values();
        return null; // TODO
    }
//
//    public boolean hasChildren() {
//        return m_childMap != null && !m_childMap.isEmpty();
//    }

    public SmiOidNode resolveOid(XRefProblemReporter reporter) {
        //System.out.println("resolveOid: " + getIdToken());
        if (m_node == null) {
            m_node = m_lastOidComponent.resolveNode(getModule(), reporter);
            m_node.getValues().add(this);
        }
        return m_node;

/*
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

//            if (parent != null && oidValue != null) {
//                parent.m_childMap.put(oidValue.getLastOid(), oidValue);
//                // add child
//            }

            parent = oidValue;
            prevOidComponent = oidComponent;
        }
*/
    }


/*
    private SmiOidValue createDummyOidValue(OidComponent oidComponent, OidComponent prevOidComponent, SmiOidValue parent) {
        Token token = oidComponent.getIdToken() != null ? oidComponent.getIdToken() : oidComponent.getValueToken();
        System.out.println("warning: creating dummy middle oid for: " + token.getObject() + " at " + token.getLocation()); // TODO
        SmiOidValue oidValue = new SmiOidValue(oidComponent.getIdToken(), getModule());
        List<OidComponent> oidComponents = new ArrayList<OidComponent>();
        OidComponent oc1 = new OidComponent(null, prevOidComponent.getIdToken(), prevOidComponent.getValueToken());
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
*/

/*
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
*/

    public SmiOidValue findChild(int id) {
        throw new UnsupportedOperationException();
        //return m_childMap.get(id);
    }


    public SmiOidNode getNode() {
        return m_node;
    }
}