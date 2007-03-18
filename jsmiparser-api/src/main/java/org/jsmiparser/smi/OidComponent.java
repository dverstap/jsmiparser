/*
 * Copyright 2006 Davy Verstappen.
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

import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.IntegerToken;
import org.jsmiparser.util.token.Token;

public class OidComponent {

    private final OidComponent m_parent;
    private OidComponent m_child;
    private final IdToken m_idToken;
    private final IntegerToken m_valueToken;
    private SmiOidNode m_node;
    private boolean m_isResolved = false;

    public OidComponent(OidComponent parent, IdToken idToken, IntegerToken intToken) {
        m_parent = parent;
        if (m_parent != null) {
            m_parent.m_child = this;
        }
        m_idToken = idToken;
        m_valueToken = intToken;
    }

//    public String getId() {
//        if (m_idToken != null) {
//            return m_idToken.getId();
//        } else if (m_symbol != null) {
//            return m_symbol.getId();
//        }
//        return null;
//    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public IntegerToken getValueToken() {
        return m_valueToken;
    }

    public SmiOidNode getNode() {
        return m_node;
    }

    public SmiOidNode resolveNode(SmiModule module, XRefProblemReporter reporter) {
        assert (m_node == null);

/*
        if (m_parent == null) {
            if (m_child != null) {
                m_node = resolveOidComponent(oidComponent, parent, isFirst, reporter);
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
*/
/*
            if (parent != null && oidValue != null) {
                parent.m_childMap.put(oidValue.getLastOid(), oidValue);
                // add child
            }
*/
        //parent = oidValue;

        if (!m_isResolved) {
            SmiOidNode parent = null;
            if (m_parent != null) {
                parent = m_parent.resolveNode(module, reporter);
                if (parent == null) {
                    System.out.println("couldn't find parent for: " + m_parent.getToken());
                }
            }
            m_node = doResolve(module, parent, reporter);
            if (m_node == null) {
                if (isLast()) {
                    if (parent != null) {
                        if (m_valueToken != null) {
                            m_node = new SmiOidNode(parent, m_valueToken.getValue());
                        } else {
                            System.out.println("valueToken missing for last subid: " + getToken());
                        }
                    } else {
                        System.out.println("parent missing for last subid: " + getToken());
                    }
                } else {
                    System.out.println("couldn't resolve non-last subid " + getToken());
                }
            }
            m_isResolved = true;
        }
        return m_node;
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

    private Token getToken() {
        if (m_idToken != null) {
            return m_idToken;
        }
        return m_valueToken;
    }

    private boolean isFirst() {
        return m_parent == null;
    }

    private boolean isLast() {
        return m_child == null;
    }

    private SmiOidNode doResolve(SmiModule module, SmiOidNode parent, XRefProblemReporter reporter) {
        SmiOidNode node;
        if (m_idToken != null && !isLast()) { // isLast check deals with jobmonMIB situation
            SmiSymbol symbol = module.resolveReference(m_idToken, null);
            if (symbol != null) {
                if (symbol instanceof SmiOidValue) {
                    SmiOidValue oidValue = (SmiOidValue) symbol;
                    node = oidValue.resolveOid(reporter);
                    if (node != null && m_valueToken != null) {
                        // TODO compare
                    }
                } else {
                    reporter.reportFoundSymbolButWrongType(m_idToken, SmiOidValue.class, symbol.getClass());
                    node = null;
                }
            } else if (parent != null && m_valueToken != null) {
                int value = m_valueToken.getValue();
                node = parent.m_childMap.get(value);
                if (node == null) {
                    node = new SmiOidNode(parent, value);
                }
            } else {
                node = null;
            }
        } else {
            if (isFirst()) {
                node = module.getMib().getRootNode().findChild(m_valueToken.getValue());
                if (node == null) {
                    node = new SmiOidNode(module.getMib().getRootNode(), m_valueToken.getValue());
                    //throw new IllegalStateException(m_valueToken.toString());
                }
            } else if (parent != null) {
                node = parent.findChild(m_valueToken.getValue());
                if (node == null) {
                    node = new SmiOidNode(parent, m_valueToken.getValue());
                }
            } else {
                throw new IllegalStateException(m_valueToken.toString());
            }
        }
        return node;
    }

}
