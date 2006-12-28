/*
 * Copyright 2006 Davy Verstappen.
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
package org.jsmiparser.util.jung;

import edu.uci.ics.jung.graph.DirectedEdge;

import java.util.Set;


public class DirectedCycleException extends Throwable {
    private final Set<DirectedEdge> m_cycleEdges;

    public DirectedCycleException(Set<DirectedEdge> cycleEdges) {
        m_cycleEdges = cycleEdges;
    }

    public Set<DirectedEdge> getCycleEdges() {
        return m_cycleEdges;
    }
}
