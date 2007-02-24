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

import edu.uci.ics.jung.graph.DirectedEdge;
import edu.uci.ics.jung.graph.Vertex;
import org.apache.log4j.Logger;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.util.jung.DirectedCycleException;
import org.jsmiparser.util.jung.TopologicalSort;

import java.util.ArrayList;
import java.util.List;

public class XRefPhase implements Phase {

    private static final Logger m_log = Logger.getLogger(XRefPhase.class);

    private XRefProblemReporter m_reporter;

    public XRefPhase(XRefProblemReporter reporter) {
        m_reporter = reporter;
    }

    public Object getOptions() {
        return null;
    }

    public SmiMib process(SmiMib mib) throws SmiException {
        mib.fillTables();
        mib.defineMissingStandardOids();

        for (SmiModule module : mib.getModules()) {
            module.resolveImports(m_reporter);
        }

        List<SmiModule> modules = sortModules(mib);
        resolveReferences(modules);
        resolveOids(modules);
        mib.fillExtraTables();

        return mib;
    }

    private void resolveReferences(List<SmiModule> modules) {
        for (SmiModule module : modules) {
            for (SmiSymbol symbol : module.getSymbols()) {
                symbol.resolveReferences(m_reporter);
            }
        }
    }

    private void resolveOids(List<SmiModule> modules) {
        for (SmiModule module : modules) {
            m_log.debug("Resolving oids in module: " + module.getId() + " hash=" + module.getId().hashCode());
            for (SmiOidValue oidValue : module.getOidValues()) {
                oidValue.resolveOid(m_reporter);
            }
        }
        for (SmiModule module : modules) {
            for (SmiOidValue oidValue : module.getOidValues()) {
                oidValue.determineFullOid();
            }
        }
    }


    private List<SmiModule> sortModules(SmiMib mib) {
        try {
            List<Vertex> sortedVertexes = TopologicalSort.sort(mib.createGraph());

            List<SmiModule> result = new ArrayList<SmiModule>(sortedVertexes.size());
            for (Vertex sortedVertex : sortedVertexes) {
                SmiModule module = (SmiModule) sortedVertex.getUserDatum(SmiModule.class);
                assert (module != null);
                result.add(module);
            }
            //Collections.reverse(result);
            if (result.size() != mib.getModules().size()) {
                throw new AssertionError("Topological sort failure");
            }

            if (m_log.isDebugEnabled()) {
                StringBuilder msg = new StringBuilder("Topologically sorted modules according to dependencies: ");
                for (SmiModule module : result) {
                    msg.append(module.toString()).append(": ").append(module.getVersion());
                }
                m_log.debug(msg);
            }

            return result;
        } catch (DirectedCycleException e) {
            if (m_log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Cycle consists of: \n");
                for (DirectedEdge edge : e.getCycleEdges()) {
                    SmiModule src = (SmiModule) edge.getSource().getUserDatum(SmiModule.class);
                    SmiModule dest = (SmiModule) edge.getDest().getUserDatum(SmiModule.class);
                    sb.append(src.getId()).append(" -> ").append(dest.getId()).append("\n");
                }
                m_log.debug(sb);
            }
            throw new SmiException(e);
        }
    }

}
