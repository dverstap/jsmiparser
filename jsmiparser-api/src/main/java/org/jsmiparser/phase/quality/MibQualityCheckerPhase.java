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

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.Renderer;
import edu.uci.ics.jung.visualization.SpringLayout;
import edu.uci.ics.jung.visualization.contrib.TreeLayout;
import edu.uci.ics.jung.visualization.contrib.DAGLayout;
import edu.uci.ics.jung.utils.UserDataContainer.CopyAction;
import edu.uci.ics.jung.utils.UserDataContainer.CopyAction.Shared;
import edu.uci.ics.jung.utils.UserDataContainer;
import edu.uci.ics.jung.io.GraphMLFile;
import org.apache.log4j.Logger;
import org.jsmiparser.phase.AbstractPhase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.smi.SmiImports;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.util.problem.ProblemReporterFactory;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;
import java.awt.color.ColorSpace;
import java.awt.*;

import samples.graph.BasicRenderer;


public class MibQualityCheckerPhase extends AbstractPhase {
    private static final Logger m_log = Logger.getLogger(MibQualityCheckerPhase.class);

    public MibQualityCheckerPhase(ProblemReporterFactory problemReporterFactory) {
        super(problemReporterFactory);
    }

    public SmiMib process(Object input) throws PhaseException {
        SmiMib mib = (SmiMib) input;

        // TODO scary: runs out of memory with max heap size 64m
        //checkDependencyCycles(mib);

        return mib;
    }

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
            Vertex vertex = vertexMap.get(module);
            for (SmiImports imports : module.getImports()) {
                SmiModule importedModule = imports.getImportedModule();
                Vertex importedVertex = vertexMap.get(importedModule);
                try {
                    graph.addEdge(new DirectedSparseEdge(vertex, importedVertex));
                } catch (Exception e) {
                    // TODO is this really a dependency problem?
                    System.out.println(e.getMessage() + ": Cyclic dependency problem with: " + module.getId() + " -> " + importedModule.getId());
                }
            }
        }

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

}
