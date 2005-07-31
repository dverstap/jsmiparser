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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author davy
 */
public abstract class AbstractNamedSymbol extends AbstractSymbol implements NamedSymbol {
    private IdToken m_idToken;
    private List<IdToken> m_users;

    public AbstractNamedSymbol(Context context, IdToken idToken) {
        super(context);
        m_idToken = idToken;
    }

    protected AbstractNamedSymbol(ASNModule module, IdToken idToken) {
        super(module, idToken.getLocation());
        m_idToken = idToken;
    }

    public String getName() {
        return m_idToken.getId();
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    protected void doSetIdToken(IdToken idToken) {
        // TODO put some check here?
        m_idToken = idToken;
    }

    public void addUser(IdToken idToken) {
        if (m_users == null) {
            m_users = new ArrayList<IdToken>();
        }
        m_users.add(idToken);
    }

    public List<IdToken> getUsers() {
        if (m_users == null) {
            return Collections.emptyList();
        }
        return m_users;
    }

    public String toString() {
        return m_idToken.toString();
    }
}
