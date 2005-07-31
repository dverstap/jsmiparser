/*
 * Copyright 2005 Nigel Sheridan-Smith, Davy Verstappen.
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

package org.jsmiparser.parsetree.asn1;

import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.location.Location;

import java.util.*;


/**
 * Represents all the symbols that are imported from a module.
 *
 * @author Nigel Sheridan-Smith
 */
public class ASNImports extends AbstractNamedSymbol {

    private Map<ASNAssignment, IdToken> m_assigmentMap = new LinkedHashMap<ASNAssignment, IdToken>();
    private ASNOidComponentList assignedIdentifierOid;
    private ASNDefinedValue assignedIdentifierDefined;
    private ASNModule m_importedModule;

    public ASNImports(Context context, IdToken idToken, ASNModule module) {
        super(context, idToken);
        m_importedModule = module;
    }

    public ASNModule getImportedModule() {
        return m_importedModule;
    }

    public Collection<ASNAssignment> getSymbols() {
        return m_assigmentMap.keySet();
    }

    public Location getImportLocation(ASNAssignment assignment) {
        return m_assigmentMap.get(assignment).getLocation();
    }

    public void setAssignedIdentifierOid(ASNOidComponentList o) {
        assignedIdentifierOid = o;
    }

    public ASNOidComponentList getAssignedIdentifierOid() {
        return assignedIdentifierOid;
    }

    public void setAssignedIdentifierDefined(ASNDefinedValue d) {
        assignedIdentifierDefined = d;
    }

    public ASNDefinedValue getAssignedIdentifierDefined() {
        return assignedIdentifierDefined;
    }

    public void addAssigment(IdToken idToken, ASNAssignment assignment) {
        m_assigmentMap.put(assignment, idToken);
    }
}
