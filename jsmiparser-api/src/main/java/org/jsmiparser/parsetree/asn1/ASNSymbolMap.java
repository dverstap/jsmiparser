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
package org.jsmiparser.parsetree.asn1;

import org.apache.log4j.Logger;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.phase.file.FileParserProblemReporter;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// TODO move to phase.parser package
public class ASNSymbolMap<Assignment extends ASNAssignment> {
    private static final Logger m_log = Logger.getLogger(ASNSymbolMap.class);

    private ASNModule m_module;

    // TODO use a multimap that also stores the duplicates?
    // unresolved elements are in here as well, with an empty value
    private Map<String, Assignment> m_map = new HashMap<String, Assignment>();

    private Constructor<Assignment> m_constructor;
    private Class<Assignment> m_assigmentClass;
    private FileParserProblemReporter m_pr;

    public ASNSymbolMap(FileParserProblemReporter fileParserProblemReporter, ASNModule module, Class<Assignment> assignmentClass) {
        m_module = module;
        m_pr = fileParserProblemReporter;
        m_assigmentClass = assignmentClass;
        try {
            m_constructor = assignmentClass.getConstructor(ASNModule.class, IdToken.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    // TODO should this return unresolved symbols as well?
    public Assignment find(String id) {
        return m_map.get(id);
    }

    /**
     * This is to be used when resolving symbols within this module.
     */
    public Assignment resolve(IdToken id) {
        // TODO: this doesn't work because the Context always assigns the module that is currently parsed.
        // assert(id.getLocation().getSource() == m_module.getLocation().getSource());
        // TODO

        Assignment result = m_map.get(id.getId());

        if (result == null) {
            result = findImport(id);
        }

        if (result == null) {
            result = newInstance(id);
            m_map.put(id.getId(), result);
        }

        result.addUser(id);

        return result;
    }

    private Assignment findImport(IdToken idToken) {
        for (ASNImports imports : m_module.getImports()) {
            for (ASNAssignment assignment : imports.getSymbols()) {
                if (assignment.getName().equals(idToken.getId()) && m_assigmentClass.isInstance(assignment)) {
                    return m_assigmentClass.cast(assignment);
                }
            }
        }
        return null;
    }

    /**
     * Other module use a symbol (supposedly) defined in this module.
     */
    public Assignment use(IdToken idToken) {
        //assert(!idToken.getLocation().getSource().equals(m_module.getLocation().getSource()));

        Assignment result = m_map.get(idToken.getId());
        if (result == null) {
            result = newInstance(idToken);
            m_map.put(idToken.getId(), result);
        }

        result.addUser(idToken);

        return result;
    }


    private Assignment newInstance(IdToken idToken) {
        try {
            return m_constructor.newInstance(m_module, idToken);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Assignment create(IdToken idToken) {
        Assignment result = m_map.get(idToken.getId());
        if (result == null) {
            // plain and simple case: the symbol wasn't there, and was never referenced either.
            result = newInstance(idToken);
            m_map.put(idToken.getId(), result);
        } else {
            if (result.getModule() != m_module) {
                m_log.warn("created symbol " + idToken.getId() + " in " + m_module.getName() + " is part of " + result.getModule().getName());
            }
            if (result.getRightHandSide() != null) { // It is effectively resolved
                Assignment n = newInstance(idToken); // return dummy new instance
                m_pr.reportDuplicateAssignment(n, result);
                result = n;
                // TODO register duplicate in multimap?
            } else {
                // The idToken must be the correct one, to ensure the right location
                result.setIdToken(idToken);
            }
        }
//        if (m_log.isDebugEnabled()) {
//            m_log.debug("Created " + idToken + " " + result.getIdToken());
//        }
        assert(result.getIdToken() == idToken);
        return result;
    }

    public Collection<Assignment> getAll() {
        return m_map.values();
    }

    public int size() {
        return m_map.size();
    }


}
