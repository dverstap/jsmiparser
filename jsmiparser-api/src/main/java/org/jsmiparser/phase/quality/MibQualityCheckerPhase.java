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
package org.jsmiparser.phase.quality;

import org.apache.log4j.Logger;
import org.jsmiparser.phase.AbstractPhase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.smi.SmiImports;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.util.problem.ProblemReporterFactory;

import java.util.HashSet;
import java.util.Set;


public class MibQualityCheckerPhase extends AbstractPhase {
    private static final Logger m_log = Logger.getLogger(MibQualityCheckerPhase.class);

    public MibQualityCheckerPhase(ProblemReporterFactory problemReporterFactory) {
        super(problemReporterFactory);
    }

    public SmiMib process(Object input) throws PhaseException {
        SmiMib mib = (SmiMib) input;

        // TODO scary: runs out of memory with max heap size 64m
        for (SmiModule module : mib.getModules()) {
            checkDependencyCycles(module, module, new HashSet<SmiModule>());
        }

        return mib;
    }

    private void checkDependencyCycles(SmiModule root, SmiModule module, Set<SmiModule> visited) {
        //Stack<SmiModule> trail = new Stack<SmiModule>();
        //trail.push(module);

        for (SmiImports imports : module.getImports()) {
            SmiModule importedModule = imports.getModule();
            if (!visited.contains(importedModule)) {
                visited.add(importedModule);
                if (importedModule == root) {
                    String msg = "cyclic dependency with " + root.getId() + " via import in " + module.getId();
                    System.err.println(msg);
                    throw new RuntimeException(msg);
                } else {
                    checkDependencyCycles(root, importedModule, visited);
                }
            }
        }

    }

/*
    private void checkDependencyCycles(SmiMib mib) {

        Map<SmiModule, Vertex> vertexMap = new HashMap<SmiModule, Vertex>();
        DirectedSparseGraph graph = new DirectedSparseGraph();
        for (SmiModule module : mib.getModules()) {
            DirectedSparseVertex vertex = new DirectedSparseVertex();
            //vertex.setUserDatum(SmiModule.class, module, new Shared());
            graph.addVertex(vertex);
            vertexMap.put(module, vertex);
            vertex.setUserDatum("name", module.getId(), new Shared());
        }

        for (SmiModule module : mib.getModules()) {
            Vertex vertex = vertexMap.getOne(module);
            for (SmiImports imports : module.getImports()) {
                SmiModule importedModule = imports.getImportedModule();
                Vertex importedVertex = vertexMap.getOne(importedModule);
                try {
                    graph.addEdge(new DirectedSparseEdge(vertex, importedVertex));
                } catch (Exception e) {
                    // TODO is this really a dependency problem?
                    System.out.println(e.getMessage() + ": Cyclic dependency problem with: " + module.getId() + " -> " + importedModule.getId());
                }
            }
        }
*/

/*
        try {

            GraphMLFile file = new GraphMLFile();
            file.save(graph, new PrintStream(new FileOutputStream(new File("/tmp/t.graphml"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("graph: " + graph);
*/
/*
        //Layout layout = new FRLayout(graph);
        Layout layout = new DAGLayout(graph);
        //PluggableRenderer renderer = new PluggableRenderer();
        Renderer renderer = new BasicRenderer() {
            @Override
            public void paintVertex(Graphics graphics, Vertex vertex, int x, int y) {
                SmiModule module = (SmiModule) vertex.getUserDatum(SmiModule.class);
                graphics.drawString(module.getId(), x, y);
            }
        };
        VisualizationViewer vv = new VisualizationViewer(layout, renderer);

        JFrame jf = new JFrame();
        jf.getContentPane().add(vv);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.show();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
*/

}

