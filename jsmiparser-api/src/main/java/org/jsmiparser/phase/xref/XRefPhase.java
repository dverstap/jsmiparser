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

import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.oid.OidProblemReporter;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.util.jung.TopologicalSort;
import org.jsmiparser.util.jung.DirectedCycleException;
import org.jsmiparser.smi.*;
import org.apache.log4j.Logger;

import java.util.*;

import edu.uci.ics.jung.graph.Vertex;

public class XRefPhase implements Phase {

    private static final Logger m_log = Logger.getLogger(XRefPhase.class);

    private ProblemReporterFactory m_problemReporterFactory;

    public XRefPhase(ProblemReporterFactory problemReporterFactory) {
        m_problemReporterFactory = problemReporterFactory;
    }

    public Object getOptions() {
        return null;
    }

    public SmiMib process(Object input) throws PhaseException {
        SmiMib mib = (SmiMib) input;

        for (SmiModule module : mib.getModules()) {
            module.fillTables();
        }

        mib.fillTables();

        for (SmiModule module : mib.getModules()) {
            module.resolveImports();
        }

        List<SmiModule> modules = sortModules(mib);
        if (modules.size() != mib.getModules().size()) {
            throw new AssertionError("Topological sort failure");
        }

        if (m_log.isDebugEnabled()) {
            for (SmiModule module : modules) {
                m_log.debug(module.toString() + ": " + module.getVersion());
            }
        }

        for (SmiModule module : modules) {
            for (SmiType type : module.getTypes()) {
                type.resolveReferences();
            }
        }

        for (SmiModule module : modules) {
            for (SmiAttribute attribute : module.getAttributes()) {
                attribute.resolveReferences();
            }
        }

        OidProblemReporter reporter = m_problemReporterFactory.create(getClass().getClassLoader(), OidProblemReporter.class);
        for (SmiModule module : modules) {
            m_log.debug("Resolving oids in module: " + module.getId() + " hash=" + module.getId().hashCode());
            for (SmiSymbol symbol : module.getSymbols()) {
                if (symbol instanceof SmiOidValue) {
                    SmiOidValue oidValue = (SmiOidValue) symbol;
                    oidValue.resolveOid(reporter);
                }
            }
        }

        // TODO
        return mib;
    }

    private List<SmiModule> sortModules(SmiMib mib) {
        try {
            List<Vertex> sortedVertexes = TopologicalSort.sort(mib.createGraph());

            List<SmiModule> result = new ArrayList<SmiModule>(sortedVertexes.size());
            for (Vertex sortedVertex : sortedVertexes) {
                SmiModule module = (SmiModule) sortedVertex.getUserDatum(SmiModule.class);
                assert(module != null);
                result.add(module);
            }
            //Collections.reverse(result);
            return result;
        } catch (DirectedCycleException e) {
            throw new PhaseException(e);
        }
    }

}
