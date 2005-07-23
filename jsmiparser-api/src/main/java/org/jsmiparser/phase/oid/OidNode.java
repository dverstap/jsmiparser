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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class OidNode {

    OidProblemReporter m_pr;
    String id_; // TODO remove?
    BigInteger subId_;
    OidNode parent_;

    Map subIdChildMap_ = new HashMap();

    public OidNode(OidProblemReporter pr, OidNode parent, String id, BigInteger subId) {
        super();
        m_pr = pr;
        parent_ = parent;
        id_ = id;
        subId_ = subId;

        if (parent != null) {

        }
    }

    public OidNode findChild(BigInteger subId) {
        return (OidNode) subIdChildMap_.get(subId);
    }

    public boolean hasChildren() {
        return subIdChildMap_.size() > 0;
    }

    public boolean equals(OidNode on) {
        if (on == this) {
            return true;
        } else {
            OidNode on1 = this;
            OidNode on2 = on;
            while (on1 != null && on2 != null) {
//                if ((on1.id_ == null && on2.id_ == null)
//                        ||(!(on1.id_ != null && on2.id_ != null
//                                && on1.id_.equals(on2.id_))))
//                {
//                    String msg = this.id_ + ".equals(" + on.id_
//                    	+ ") not equal: " + on1.id_ + " " + on2.id_;
//                    System.out.println(msg);
//                    return false;
//                }
                if (!(on1.subId_ != null && on2.subId_ != null
                        && on1.subId_.equals(on2.subId_))) {
                    return false;
                }
                on1 = on1.parent_;
                on2 = on2.parent_;
            }
            if (!(on1 == null && on2 == null))
                return false;
        }
        return true;
    }

    public void toString(StringBuffer buf) {
        if (parent_ != null) {
            parent_.toString(buf);
            buf.append("/");
        }
        if (id_ != null)
            buf.append(id_);
        buf.append("#");
        if (subId_ != null)
            buf.append(subId_);
    }

    public OidNode getRoot() {
        OidNode n = this;
        while (n.parent_ != null) {
            n = n.parent_;
        }
        return n;
    }

    void fixStart(OidNode startNode) {
        OidNode n = this;
        while (n.parent_.parent_ != null) {
            n = n.parent_;
        }

        // n should now be the direct child of the root
        if (n.parent_ != getRoot()) {
            m_pr.reportMissingRootChild(id_);
        }

        n.parent_ = startNode;

    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        toString(buf);
        return buf.toString();
    }

    public String getDecimalDottedStr() {
        StringBuffer buf = new StringBuffer();
        getDecimalDottedStr(buf);
        return buf.toString();
    }

    private void getDecimalDottedStr(StringBuffer buf) {
        if (parent_ != null && parent_.parent_ != null) {
            parent_.getDecimalDottedStr(buf);
            buf.append(".");
        }
        buf.append(subId_);
    }
}
