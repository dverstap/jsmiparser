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
package org.jsmiparser.phase.file;

import org.apache.log4j.Logger;
import org.jsmiparser.smi.SmiImports;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.util.token.IdToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmiSymbolMap<Symbol extends SmiSymbol> {
    private static final Logger m_log = Logger.getLogger(SmiSymbolMap.class);

    private SmiModule m_module;

    // TODO use a multimap that also stores the duplicates?
    // unresolved elements are in here as well, with an empty value
    private Map<String, Symbol> m_map = new HashMap<String, Symbol>();

    private Constructor<Symbol> m_constructor;
    private Class<Symbol> m_symbolClass;
    private FileParserProblemReporter m_pr;

    public SmiSymbolMap(FileParserProblemReporter fileParserProblemReporter, SmiModule module, Class<Symbol> assignmentClass) {
        m_module = module;
        m_pr = fileParserProblemReporter;
        m_symbolClass = assignmentClass;
        try {
            m_constructor = assignmentClass.getConstructor(IdToken.class, SmiModule.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    // TODO should this return unresolved symbols as well?
    public Symbol find(String id) {
        return m_map.get(id);
    }

    /**
     * This is to be used when resolving symbols within this module.
     */
    public Symbol resolve(IdToken id) {
        // TODO: this doesn't work because the Context always assigns the module that is currently parsed.
        // assert(id.getLocation().getSource() == m_module.getLocation().getSource());
        // TODO

        Symbol result = m_map.get(id.getId());

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

    private Symbol findImport(IdToken idToken) {
        for (SmiImports imports : m_module.getImports()) {
            for (SmiSymbol symbol : imports.getSymbols()) {
                if (symbol.getId().equals(idToken.getId()) && m_symbolClass.isInstance(symbol)) {
                    return m_symbolClass.cast(symbol);
                }
            }
        }
        return null;
    }

    /**
     * Other module use a symbol (supposedly) defined in this module.
     */
    public Symbol use(IdToken idToken) {
        //assert(!idToken.getLocation().getSource().equals(m_module.getLocation().getSource()));

        Symbol result = m_map.get(idToken.getId());
        if (result == null) {
            result = newInstance(idToken);
            m_map.put(idToken.getId(), result);
        }

        result.addUser(idToken);

        return result;
    }

    public <T extends Symbol> T use(IdToken idToken, Class<T> cl) {
        T result = null;
        Symbol symbol = m_map.get(idToken.getId());
        if (symbol != null) {
            if (cl.isInstance(symbol)) {
                result = cl.cast(symbol);
            } else {
                m_pr.reportIncompatibleType(idToken.getLocation(), symbol.getClass().getSimpleName(),
                        idToken.getId(), cl.getSimpleName());
                result = newInstance(idToken, cl);
            }
        } else {
            result = newInstance(idToken, cl);
            m_map.put(result.getId(), result);
        }

        return result;
    }


    private Symbol newInstance(IdToken idToken) {
        try {
            return m_constructor.newInstance(idToken, m_module);
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

    private <T> T newInstance(IdToken idToken, Class<T> cl) {
        try {
            Constructor<T> constructor = cl.getConstructor(IdToken.class, SmiModule.class);
            return constructor.newInstance(idToken, m_module);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Symbol create(IdToken idToken) {
        Symbol result = m_map.get(idToken.getId());
        if (result == null) {
            // plain and simple case: the symbol wasn't there, and was never referenced either.
            result = newInstance(idToken);
            m_map.put(idToken.getId(), result);
        } else {
            if (result.getModule() != m_module) {
                m_log.warn("created symbol " + idToken.getId() + " in " + m_module.getId() + " is part of " + result.getModule().getId());
            }
            // TODO URGENT if (result.getRightHandSide() != null) { // It is effectively resolved
            if (false) {
                Symbol n = newInstance(idToken); // return dummy new instance
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

    public Collection<Symbol> getAll() {
        return m_map.values();
    }

    public int size() {
        return m_map.size();
    }

}
