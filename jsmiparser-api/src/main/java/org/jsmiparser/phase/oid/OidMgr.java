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
package org.jsmiparser.phase.oid;

import org.jsmiparser.util.problem.ProblemReporterFactory;

import java.util.*;
import java.math.BigInteger;

public class OidMgr {

    OidProblemReporter m_pr;
    Set ignorableIds_ = new HashSet();
    Map<String, OidNode> idNodeMap_ = new HashMap<String, OidNode>();
    Map<BigInteger, OidNode> standardSubIdMap_ = new HashMap<BigInteger, OidNode>();

    OidNode root_;

    public OidMgr(ProblemReporterFactory prf) {
        m_pr = prf.create(OidMgr.class.getClassLoader(), OidProblemReporter.class);

        root_ = new OidNode(m_pr, null, "rootNode", new BigInteger("0"));

        // see http://asn1.elibel.tm.fr

        OidNode node0 = new OidNode(m_pr, root_, "itu-t", new BigInteger("0"));
        standardSubIdMap_.put(node0.subId_, node0);
        idNodeMap_.put("itu-t", node0);
        idNodeMap_.put("ccitt", node0);
        idNodeMap_.put("itu-r", node0);
        idNodeMap_.put("itu", node0);

        OidNode node1 = new OidNode(m_pr, root_, "iso", new BigInteger("1"));
        standardSubIdMap_.put(node1.subId_, node1);
        idNodeMap_.put("iso", node1);

        OidNode node2 = new OidNode(m_pr, root_, "joint-iso-itu-t", new BigInteger("2"));
        standardSubIdMap_.put(node2.subId_, node2);
        idNodeMap_.put("joint-iso-itu-t", node2);
        idNodeMap_.put("joint-iso-ccitt", node2);
    }

    public OidNode getNode(OidNode parent, String id, BigInteger subId) {
        OidNode result = null;
        if (parent == null) {
            if (id != null) {
                result = findNode(id);
                if (result == null) {
                    // this is resolved later
                    //m_pr.error("ASN OID that starts with a name must have a first name that is already declared.", id);
                }
            } else {
                result = standardSubIdMap_.get(subId);
                if (result == null) {
                    m_pr.reportInvalidOidStart(subId);
                }
            }
        }

        // we always make one, to avoid crashes
        if (result == null) {
            result = new OidNode(m_pr, parent, id, subId);
        }
        return result;
    }


    public OidNode registerNode(String id, OidNode newNode) {
        if (newNode.id_ == null) {
            newNode.id_ = id;
        }
        if (newNode == null) {
            m_pr.reportNewNullNode(id);
        }
        OidNode result = null;
        OidNode oldNode = findNode(id);
        if (oldNode != null) {
            if (newNode.equals(oldNode)) {
                result = oldNode;
            } else {
                m_pr.reportOidAlreadyRegistered(id);
            }
        } else {
            idNodeMap_.put(id, newNode);
        }

        return result;
    }


    public void resolveAll() {
        for (Iterator<Map.Entry<String, OidNode>> i = idNodeMap_.entrySet().iterator(); i.hasNext();) {
            Map.Entry<String, OidNode> entry = i.next();
            String id = entry.getKey();
            resolve(id);
//            OidNode node = (OidNode) entry.getValue();
//            OidNode nodeRoot = node.getRoot();
//            if (nodeRoot != root_)
//            {
//                resolve(id, node, nodeRoot);
//            }
        }
    }

    private OidNode resolve(String id) {
        OidNode node = findNode(id);
        if (node == null) {
            m_pr.reportCannotFindOidNode(id);
        } else {
            OidNode nodeRoot = node.getRoot();
            if (nodeRoot != root_) {
                if (nodeRoot.subId_ == null) {
                    OidNode n = resolve(nodeRoot.id_);
                    if (n != null) {
                        if (isResolved(n)) {
                            node.fixStart(n);
                        } else {
                            // error message here will only be confusing
                        }
                    } else {
                        // error message already printed by call to resolve
                    }
                } else {
                    m_pr.reportCannotResolveUnresolvedOid(id);
                }
            }
        }
        return node;
    }


    public void printUnresolved() {
        for (Iterator<Map.Entry<String, OidNode>> i = idNodeMap_.entrySet().iterator(); i.hasNext();) {
            Map.Entry<String, OidNode> entry = i.next();
            String id = entry.getKey();
            OidNode node = entry.getValue();
            if (!isResolved(node)) {
                m_pr.reportUnresolvedOid(id);
            } else {
                //System.out.println(id + ": " + node);
            }
        }
    }


    public OidNode findNode(String id) {
        return idNodeMap_.get(id);
    }


    private boolean isResolved(OidNode node) {
        return node.getRoot() == root_;
    }


}
