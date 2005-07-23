/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.phase.file;

import org.jsmiparser.parsetree.asn1.ASNMib;
import org.jsmiparser.parsetree.asn1.ASNModule;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.util.problem.ProblemReporterFactory;

import java.io.File;

// TODO allow any URL's

public class FileParserPhase implements Phase {

    private ProblemReporterFactory m_problemReporterFactory;
    private Class m_fileParserClass;

    private ASNFileFinder fileFinder_;
    private FileParserOptions m_options = new FileParserOptions();

    public FileParserPhase(ProblemReporterFactory prf, Class fileParserClass) {
        super();
        m_problemReporterFactory = prf;
        m_fileParserClass = fileParserClass;
    }

    public ASNFileFinder getFileFinder() {
        return fileFinder_;
    }

    public void setFileFinder(ASNFileFinder fileFinder) {
        fileFinder_ = fileFinder;
    }

    private ASNModule parseFile(File file) throws PhaseException {
        try {
            FileParser fileParser = (FileParser) m_fileParserClass.newInstance();
            return fileParser.parse(file);
        } catch (InstantiationException e) {
            throw new PhaseException(e);
        } catch (IllegalAccessException e) {
            throw new PhaseException(e);
        }
    }

    public Object process(Object input) throws PhaseException {
        ASNMib mib = new ASNMib();

        for (File file : m_options.getInputFileList()) {
            ASNModule module = parseFile(file);
            mib.addModule(module);
        }

        mib.processModules();

        return mib;
    }

    public FileParserOptions getOptions() {
        return m_options;
    }
}
