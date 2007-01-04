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

import org.jsmiparser.phase.CompositePhase;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.xref.XRefPhase;
import org.jsmiparser.phase.lexer.LexerPhase;
import org.jsmiparser.phase.file.FileParserPhase;
import org.jsmiparser.phase.oid.OidResolverPhase;
import org.jsmiparser.phase.quality.MibQualityCheckerPhase;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.util.problem.DefaultProblemEventHandler;
import org.jsmiparser.util.problem.DefaultProblemReporterFactory;
import org.jsmiparser.util.problem.ProblemEventHandler;

public class SmiDefaultParser extends CompositePhase implements SmiParser {

    private Phase m_fileParserPhase;
    private Phase m_oidResolverPhase;
    private Phase m_xrefPhase;
    private Phase m_mibQualityCheckerPhase;
    private Phase m_lexerPhase;

    protected SmiDefaultParser() {
        super(new DefaultProblemReporterFactory(new DefaultProblemEventHandler()));
    }

    public SmiDefaultParser(ProblemEventHandler problemEventHandler) {
        super(new DefaultProblemReporterFactory(problemEventHandler));
    }

    public void init() {

        m_lexerPhase = createLexerPhase();
        addOptionalPhase(m_lexerPhase);

        m_fileParserPhase = createFileParserPhase();
        addOptionalPhase(m_fileParserPhase);

        m_oidResolverPhase = createOidResolverPhase();
        addOptionalPhase(m_oidResolverPhase);

        m_xrefPhase = createXRefPhase();
        addOptionalPhase(m_xrefPhase);

        m_mibQualityCheckerPhase = createMibQualityCheckerPhase();
        addOptionalPhase(m_mibQualityCheckerPhase);
    }

    protected Phase createLexerPhase() {
        return new LexerPhase(m_problemReporterFactory);
    }

    protected Phase createFileParserPhase() {
        return new FileParserPhase(m_problemReporterFactory);
    }

    protected OidResolverPhase createOidResolverPhase() {
        return new OidResolverPhase(m_problemReporterFactory);
    }

    protected Phase createXRefPhase() {
        return new XRefPhase(m_problemReporterFactory);
    }

    protected Phase createMibQualityCheckerPhase() {
        return new MibQualityCheckerPhase(m_problemReporterFactory);
    }

    private void addOptionalPhase(Phase phase) {
        if (phase != null) {
            addPhase(phase);
        }
    }

//    public Phase getLexerPhase() {
//        return m_lexerPhase;
//    }

    public Phase getFileParserPhase() {
        return m_fileParserPhase;
    }

    public Phase getOidResolverPhase() {
        return m_oidResolverPhase;
    }

    public Phase getMibQualityCheckerPhase() {
        return m_mibQualityCheckerPhase;
    }

    public SmiMib parse() throws PhaseException {
        return (SmiMib) process(null);
    }
}
