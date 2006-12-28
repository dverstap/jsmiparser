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

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.Renderer;
import junit.framework.TestCase;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.util.List;
import java.util.Arrays;


public class TopologicalSortTest extends TestCase {

    public void testACyclicGraph() throws DirectedCycleException {
        DirectedGraph graph = new DirectedSparseGraph();
        Vertex a = graph.addVertex(new DirectedSparseVertex());
        Vertex b = graph.addVertex(new DirectedSparseVertex());
        Vertex c = graph.addVertex(new DirectedSparseVertex());
        Vertex d = graph.addVertex(new DirectedSparseVertex());
        graph.addEdge(new DirectedSparseEdge(a, b));
        graph.addEdge(new DirectedSparseEdge(b, c));
        graph.addEdge(new DirectedSparseEdge(c, d));
        List<Vertex> sortedVertices = TopologicalSort.sort(graph);
        assertEquals(a, sortedVertices.get(0));
        assertEquals(b, sortedVertices.get(1));
        assertEquals(c, sortedVertices.get(2));
    }

    public void testCyclicGraphWithSources() {
        DirectedGraph graph = new DirectedSparseGraph();
        List<Edge> cycleEdges = createCyclicGraph(graph, true);
        try {
            TopologicalSort.sort(graph);
            fail();
        } catch (DirectedCycleException e) {
            assertEquals(cycleEdges.size(), e.getCycleEdges().size());
            assertTrue(e.getCycleEdges().containsAll(cycleEdges));
        }
    }

    public void testCyclicGraphWithoutSources() {
        DirectedGraph graph = new DirectedSparseGraph();
        List<Edge> cycleEdges = createCyclicGraph(graph, false);
        try {
            TopologicalSort.sort(graph);
            fail();
        } catch (DirectedCycleException e) {
            assertEquals(cycleEdges.size(), e.getCycleEdges().size());
            assertTrue(e.getCycleEdges().containsAll(cycleEdges));
        }
    }

    private static List<Edge> createCyclicGraph(DirectedGraph graph, boolean withSources) {
        Vertex a = graph.addVertex(new DirectedSparseVertex());
        Vertex b = graph.addVertex(new DirectedSparseVertex());
        Vertex c = graph.addVertex(new DirectedSparseVertex());
        Edge ab = graph.addEdge(new DirectedSparseEdge(a, b));
        Edge ba = graph.addEdge(new DirectedSparseEdge(b, a));
        if (withSources) {
            // c has no incoming edges
            graph.addEdge(new DirectedSparseEdge(c, b));
        } else {
            graph.addEdge(new DirectedSparseEdge(b, c));
        }
        Edge[] cycleEdges = new Edge[] { ab, ba };
        return Arrays.asList(cycleEdges);
    }


        private static void showGraph(final DirectedGraph graph) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Layout l = new FRLayout(graph);
                    Renderer r = new PluggableRenderer();
                    VisualizationViewer vv = new VisualizationViewer(l, r);
                    final JFrame jf = new JFrame();
                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jf.getContentPane().add(vv);

                    jf.pack();
                    jf.setVisible(true);
                }
            });
        }

        public static void main(String[] args) {
            DirectedGraph graph = new DirectedSparseGraph();
            createCyclicGraph(graph, false);

            showGraph(graph);

        }
}
