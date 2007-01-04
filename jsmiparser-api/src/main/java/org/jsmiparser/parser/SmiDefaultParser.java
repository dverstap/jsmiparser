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
package org.jsmiparser.parser;

import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.check.ErrorCheckPhase;
import org.jsmiparser.phase.file.FileParserPhase;
import org.jsmiparser.phase.file.FileParserProblemReporter;
import org.jsmiparser.phase.xref.XRefPhase;
import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.smi.SmiJavaCodeNamingStrategy;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.util.problem.DefaultProblemEventHandler;
import org.jsmiparser.util.problem.DefaultProblemReporterFactory;
import org.jsmiparser.util.problem.ProblemEventHandler;
import org.jsmiparser.util.problem.ProblemReporterFactory;

public class SmiDefaultParser implements SmiParser {

    protected boolean m_failOnError = false;
    protected ProblemEventHandler m_problemEventHandler;
    protected ProblemReporterFactory m_problemReporterFactory;
    protected Phase m_fileParserPhase;
    protected Phase m_xRefPhase;
    protected Phase m_errorCheckPhase;

    public SmiMib parse() throws PhaseException {
        SmiMib mib = new SmiMib(new SmiJavaCodeNamingStrategy("org.jsmiparser.mib")); // TODO
        
        Phase[] phases = new Phase[]{getFileParserPhase(), getXRefPhase(), getErrorCheckPhase()};
        for (Phase phase : phases) {
            phase.process(mib);
        }

        if (m_failOnError && m_problemEventHandler.isNotOk()) {
            throw new PhaseException();
        }
        return mib;
    }

    protected Phase createFileParserPhase() {
        return new FileParserPhase(getProblemReporterFactory().create(FileParserProblemReporter.class));
    }

    protected Phase createXRefPhase() {
        return new XRefPhase(getProblemReporterFactory().create(XRefProblemReporter.class));
    }

    private Phase createErrorCheckPhase() {
        return new ErrorCheckPhase();
    }

    public ProblemEventHandler getProblemEventHandler() {
        if (m_problemEventHandler == null) {
            m_problemEventHandler = new DefaultProblemEventHandler();
        }
        return m_problemEventHandler;
    }

    public void setProblemEventHandler(ProblemEventHandler problemEventHandler) {
        m_problemEventHandler = problemEventHandler;
    }

    public ProblemReporterFactory getProblemReporterFactory() {
        if (m_problemReporterFactory == null) {
            m_problemReporterFactory = new DefaultProblemReporterFactory(getProblemEventHandler());
        }
        return m_problemReporterFactory;
    }

    public void setProblemReporterFactory(ProblemReporterFactory problemReporterFactory) {
        m_problemReporterFactory = problemReporterFactory;
    }

    public Phase getFileParserPhase() {
        if (m_fileParserPhase == null) {
            m_fileParserPhase = createFileParserPhase();
        }
        return m_fileParserPhase;
    }

    public void setFileParserPhase(Phase fileParserPhase) {
        m_fileParserPhase = fileParserPhase;
    }

    public Phase getXRefPhase() {
        if (m_xRefPhase == null) {
            m_xRefPhase = createXRefPhase();
        }
        return m_xRefPhase;
    }

    public void setXRefPhase(Phase xrefPhase) {
        m_xRefPhase = xrefPhase;
    }

    public Phase getErrorCheckPhase() {
        if (m_errorCheckPhase == null) {
            m_errorCheckPhase = createErrorCheckPhase();
        }
        return m_errorCheckPhase;
    }

    public void setErrorCheckPhase(Phase errorCheckPhase) {
        m_errorCheckPhase = errorCheckPhase;
    }

    public boolean isFailOnError() {
        return m_failOnError;
    }

    public void setFailOnError(boolean failOnError) {
        m_failOnError = failOnError;
    }
}
