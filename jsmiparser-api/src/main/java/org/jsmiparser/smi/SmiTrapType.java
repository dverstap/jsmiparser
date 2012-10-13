/*
 * Copyright 2012 The OpenNMS Group, Inc.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmiTrapType extends SmiValue implements Notification {

    private IdToken m_enterpriseIdToken;
    private SmiOidValue m_enterpriseOid;
    private List<IdToken> m_variableTokens;
    private List<SmiVariable> m_variables = new ArrayList<SmiVariable>();
    private String m_description;
    private String m_reference;
    private IntegerToken m_specificTypeToken;
    
    public SmiTrapType(IdToken idToken, SmiModule module,
                       IdToken enterpriseIdToken, List<IdToken> variableTokens,
                       String description, String reference) {
        super(idToken, module);
        m_enterpriseIdToken = enterpriseIdToken;
        m_variableTokens = variableTokens;
        if (m_variableTokens == null) {
            m_variableTokens = Collections.emptyList();
        }
        m_description = description;
        m_reference = reference;
    }

    public String getCodeId() {
	    return getId();
	}

    public void resolveReferences(XRefProblemReporter reporter) {
    	m_enterpriseOid = getModule().resolveReference(m_enterpriseIdToken, SmiOidValue.class, reporter);
        for (IdToken variableToken : m_variableTokens) {
            SmiVariable variable = getModule().resolveReference(variableToken, SmiVariable.class, reporter);
            if (variable != null) {
                m_variables.add(variable);
            }
        }
    }

    public IdToken getEnterpriseIdToken() {
        return m_enterpriseIdToken;
    }

    public SmiOidValue getEnterpriseOid() {
        return m_enterpriseOid;
    }

    public List<IdToken> getVariableTokens() {
        return m_variableTokens;
    }

    public List<SmiVariable> getVariables() {
        return m_variables;
    }

    public List<SmiVariable> getObjects() {
        return m_variables;
    }

    public List<IdToken> getObjectTokens() {
        return m_variableTokens;
    }

    public String getDescription() {
        return m_description;
    }

    /**
     * This method is just here to implement Notification, for v1/v2 interoperability.
     *
     * Ideally we would return an SmiOidValue object here, but those would essentially always
     * cause clashes and "duplicate OID" errors. This is because traps essentially do not
     * define unique OIDs, and concatenating the trap enterprise OID with the trap specific type
     * integer, will essentially always clash with other OBJECT-TYPE definitions in the mib file.
     *
     * For instance:
     * <ul>
     *     <li>bgpVersion OBJECT-TYPE [snip] ::= { bgp 1 }</li>
     *     <li>bgpEstablished TRAP-TYPE ENTERPRISE bgp [snip] ::= 1</li>
     * </ul>
     *
     * @return The OID in decimal dotted notation.
     */

    public String getOidStr() {
        return getEnterpriseOid().getOidStr() + '.' + getSpecificType();
    }

    public String getReference() {
        return m_reference;
    }

    public IntegerToken getSpecificTypeToken() {
    	return m_specificTypeToken;
    }
    
    public void setSpecificTypeToken(IntegerToken specificTypeToken) {
    	m_specificTypeToken = specificTypeToken;
    }

    public int getSpecificType() {
        return m_specificTypeToken.getValue();
    }

}
