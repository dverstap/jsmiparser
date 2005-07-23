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
package org.jsmiparser.phase;

import org.jsmiparser.util.problem.ProblemReporterFactory;

import java.util.ArrayList;
import java.util.List;

public class CompositePhase extends AbstractPhase {

    private List<Phase> m_phases = new ArrayList<Phase>();
    private Object m_options;

    protected CompositePhase(ProblemReporterFactory problemReporterFactory) {
        super(problemReporterFactory);
    }

    public List<Phase> getPhases() {
        return m_phases;
    }

    public void setPhases(List<Phase> phases) {
        m_phases = phases;
    }

    public void addPhase(Phase phase) {
        m_phases.add(phase);
    }

    public Object getOptions() {
        return m_options;
    }

    public void setOptions(Object options) {
        m_options = options;
    }

    public Object process(Object input) throws PhaseException {
        for (Phase phase : m_phases) {
            input = phase.process(input);
        }
        return input;
    }
}
