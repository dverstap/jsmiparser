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
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.predicates.SourceVertexPredicate;

import java.util.*;

public abstract class TopologicalSort {

    /**
     * Warning: the graph will be modified during this operation
     * <p/>
     * Algorithm is based on http://en.wikipedia.org/wiki/Topological_sorting
     *
     * @param graph that must be topologically sorted
     * @return the list of vertices in topically sorted order
     * @throws DirectedCycleException when the graph has cycles
     */
    public static List<Vertex> sort(DirectedGraph graph) throws DirectedCycleException {
        List<Vertex> result = new ArrayList<Vertex>();
        List<Vertex> sources = findSources(graph);
        while (!sources.isEmpty()) {
            Vertex source = sources.remove(0);
            //System.out.println("processing: " + source);
            result.add(source);
            for (Object o : source.getOutEdges()) {
                Edge outEdge = (Edge) o;
                Vertex sink = outEdge.getOpposite(source);
                graph.removeEdge(outEdge);
                //System.out.println("removed edge: " + outEdge);
                if (sink.inDegree() == 0) {
                    sources.add(sink);
                    //System.out.println("adding new source: " + sink);
                }
            }
        }
        Set<DirectedEdge> remainingEdges = graph.getEdges();
        if (!remainingEdges.isEmpty()) {
            remainingEdges = filterSources(remainingEdges);
            throw new DirectedCycleException(remainingEdges);
        }
        return result;
    }

    private static Set<DirectedEdge> filterSources(Set<DirectedEdge> remainingEdges) {
        Set<DirectedEdge> result = new HashSet<DirectedEdge>();
        for (DirectedEdge edge : remainingEdges) {
            if (edge.getSource().inDegree() > 0 && edge.getDest().outDegree() > 0) {
                result.add(edge);
            }
        }
        return result;
    }

    private static List<Vertex> findSources(DirectedGraph graph) {
        LinkedList<Vertex> result = new LinkedList<Vertex>();
        for (Object o : graph.getVertices()) {
            Vertex vertex = (Vertex) o;
            if (SourceVertexPredicate.getInstance().evaluateVertex(vertex)) {
                result.add(vertex);
            }
        }
        return result;
    }

}
