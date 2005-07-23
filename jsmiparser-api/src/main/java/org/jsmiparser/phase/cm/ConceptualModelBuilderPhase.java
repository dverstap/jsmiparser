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
package org.jsmiparser.phase.cm;

import org.jsmiparser.phase.AbstractPhase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.util.xmlreflect.ReflectContentHandler;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class ConceptualModelBuilderPhase extends AbstractPhase {

    private static final Logger m_log = Logger.getLogger(ConceptualModelBuilderPhase.class);

    private List<File> m_classDefinitionFiles = new ArrayList<File>();

    public ConceptualModelBuilderPhase(ProblemReporterFactory problemReporterFactory) {
        super(problemReporterFactory);
    }

    @Override
    public List<File> getOptions() {
        return m_classDefinitionFiles;
    }

    public List<File> getClassDefinitionFiles() {
        return m_classDefinitionFiles;
    }

    public void setClassDefinitionFiles(List<File> classDefinitionFiles) {
        m_classDefinitionFiles = classDefinitionFiles;
    }

    public SmiMib process(Object input) throws PhaseException {
        SmiMib mib = (SmiMib) input;

        try {
            mib.determineInheritanceRelations();

            parseClassDefinitions(mib);
        } catch (SAXException e) {
            throw new PhaseException(e);
        } catch (IOException e) {
            throw new PhaseException(e);
        }
        return mib;
    }

        private void parseClassDefinitions(SmiMib mib) throws SAXException, IOException {
        m_log.debug("parsing class definitions:");
        for (File f : m_classDefinitionFiles ) {
            m_log.debug("parsing " + f);
            SmiModule module = mib.createModule(null);
            ReflectContentHandler rch = new ReflectContentHandler(m_problemReporterFactory, module);
            rch.parse(f);
            if (rch.getSkippedPathSet().size() > 0) {
                rch.printSkippedPathErrors();
            }
        }
    }
}
