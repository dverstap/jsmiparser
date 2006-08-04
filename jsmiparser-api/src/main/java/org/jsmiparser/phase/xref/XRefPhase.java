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
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiTable;
import org.jsmiparser.smi.SmiModule;

public class XRefPhase implements Phase {

    private ProblemReporterFactory m_problemReporterFactory;

    public XRefPhase(ProblemReporterFactory problemReporterFactory) {
        m_problemReporterFactory = problemReporterFactory;
    }

    public Object getOptions() {
        return null;
    }

    public Object process(Object input) throws PhaseException {
        SmiMib mib = (SmiMib) input;

        for (SmiModule module : mib.getModules()) {
            module.fillTables();
        }

        mib.fillTables();

        // TODO
        return mib;
    }
}
