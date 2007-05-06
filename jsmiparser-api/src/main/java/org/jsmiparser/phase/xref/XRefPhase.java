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
package org.jsmiparser.phase.xref;

import org.apache.log4j.Logger;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.smi.SmiDefaultValue;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiOidNode;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.smi.SmiVariable;
import org.jsmiparser.util.problem.DefaultProblemReporterFactory;
import org.jsmiparser.util.problem.ProblemEventHandler;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.util.token.IdToken;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class XRefPhase implements Phase {

    private static final Logger m_log = Logger.getLogger(XRefPhase.class);

    private XRefProblemReporter m_reporter;
    private Map<String, SymbolDefiner> m_symbolDefinerMap = new LinkedHashMap<String, SymbolDefiner>();

    public XRefPhase(XRefProblemReporter reporter) {
        m_reporter = reporter;
    }

    public XRefPhase(ProblemReporterFactory reporterFactory) {
        m_reporter = reporterFactory.create(XRefProblemReporter.class);
    }

    public XRefPhase(ProblemEventHandler eventHandler) {
        DefaultProblemReporterFactory reporterFactory = new DefaultProblemReporterFactory(eventHandler);
        m_reporter = reporterFactory.create(XRefProblemReporter.class);
    }

    public Object getOptions() {
        return null;
    }

    public Map<String, SymbolDefiner> getSymbolDefinerMap() {
        return m_symbolDefinerMap;
    }

    public void setSymbolDefinerMap(Map<String, SymbolDefiner> symbolDefinerMap) {
        m_symbolDefinerMap = symbolDefinerMap;
    }

    public SmiMib process(SmiMib mib) throws SmiException {

        defineMissingSymbols(mib);

        mib.fillTables();
        mib.defineMissingStandardOids();

        for (SmiModule module : mib.getModules()) {
            module.resolveImports(m_reporter);
        }

        Collection<SmiModule> modules = mib.getModules();
        resolveReferences(modules);
        resolveOids(modules);
        mib.fillExtraTables();
        resolveDefaultValues(mib);

        return mib;
    }

    protected void defineMissingSymbols(SmiMib mib) {
        for (Map.Entry<String, SymbolDefiner> entry : m_symbolDefinerMap.entrySet()) {
            SmiModule module = mib.findModule(entry.getKey());
            if (module == null) {
                module = mib.createModule(new IdToken(null, entry.getKey()));
            }
            entry.getValue().defineSymbols(module);
        }
    }

    protected void resolveReferences(Collection<SmiModule> modules) {
        for (SmiModule module : modules) {
            for (SmiSymbol symbol : module.getSymbols()) {
                symbol.resolveReferences(m_reporter);
            }
        }
    }

    protected void resolveOids(Collection<SmiModule> modules) {
        for (SmiModule module : modules) {
            m_log.debug("Resolving oids in module: " + module.getId() + " hash=" + module.getId().hashCode());
            for (SmiOidValue oidValue : module.getOidValues()) {
                oidValue.resolveOid(m_reporter);
            }
        }
        for (SmiModule module : modules) {
            for (SmiOidValue oidValue : module.getOidValues()) {
                SmiOidNode node = oidValue.getNode();
                if (node != null) {
                    node.determineFullOid();
                }
            }
        }
    }

    protected void resolveDefaultValues(SmiMib mib) {
        for (SmiVariable variable : mib.getVariables()) {
            SmiDefaultValue defaultValue = variable.getDefaultValue();
            if (defaultValue != null) {
                defaultValue.resolveReferences(m_reporter);
            }
        }
    }

}
