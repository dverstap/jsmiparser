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

import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.oid.OidMgr;
import org.jsmiparser.phase.lexer.LexerMib;
import org.jsmiparser.phase.lexer.LexerModule;
import org.jsmiparser.smi.SmiJavaCodeNamingStrategy;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiVersion;
import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.util.symbol.IdSymbolList;
import org.jsmiparser.util.symbol.IdSymbolListImpl;
import org.jsmiparser.util.token.IdToken;
import org.apache.log4j.Logger;

import java.util.Set;

// TODO allow any URL's

public class FileParserPhase implements Phase {

    private static final Logger m_log = Logger.getLogger(FileParserPhase.class);

    private FileParserProblemReporter m_pr;
    private FileParserOptions m_options = new FileParserOptions();
    private SmiMib m_mib;
    private LexerMib m_lexerMib;
    private IdSymbolList<ModuleParser> m_parserModules = new IdSymbolListImpl<ModuleParser>();
    private IdSymbolList<ModuleParser> m_usedParserModules = new IdSymbolListImpl<ModuleParser>();
    private IdSymbolList<ModuleParser> m_unresolvedParserModules = new IdSymbolListImpl<ModuleParser>();
    private OidMgr m_oidMgr;

    public FileParserPhase(ProblemReporterFactory prf) {
        super();
        m_pr = prf.create(FileParserPhase.class.getClassLoader(), FileParserProblemReporter.class);
        m_oidMgr = new OidMgr(prf);
    }

    public FileParserProblemReporter getFileParserProblemReporter() {
        return m_pr;
    }

    public SmiMib process(Object input) throws PhaseException {
        m_lexerMib = (LexerMib) input;

        createParserModules();

        m_mib = new SmiMib(new SmiJavaCodeNamingStrategy("org.jsmiparser.mib")); // TODO
        for (String moduleId : SkipStandardException.m_skippedStandardModules) {
            new SmiModule(m_mib, new IdToken(new Location(moduleId), moduleId));
        }

        for (ModuleParser moduleParser : m_parserModules) {
            if (moduleParser.getState() == ModuleParser.State.UNPARSED) {
                moduleParser.parse();
            }
        }

        if (m_log.isDebugEnabled()) {
            Set<SmiModule> v1modules = m_mib.findModules(SmiVersion.V1);
            Set<SmiModule> v2modules = m_mib.findModules(SmiVersion.V2);
            m_log.debug("#SMIv1 modules=" + v1modules.size() + " #SMIv2 modules=" + v2modules.size());
            if (v1modules.size() > v2modules.size()) {
                m_log.debug("V2 modules:");
                logMibs(v2modules);
            } else if (v1modules.size() < v2modules.size()) {
                m_log.debug("V1 modules:");
                logMibs(v1modules);
            }
        }


        m_oidMgr.check();

        return m_mib;
    }

    private void logMibs(Set<SmiModule> modules) {
        for (SmiModule module : modules) {
            m_log.debug(module + ": #V1 features=" + module.getV1Features() + " #V2 features=" + module.getV2Features());
        }
    }

    private void createParserModules() {
        for (LexerModule lm : m_lexerMib.getModules()) {
            m_parserModules.add(new ModuleParser(this, lm));
        }
    }

    public ModuleParser use(IdToken idToken) {
        ModuleParser pm = m_parserModules.find(idToken.getId());
        if (pm == null) {
            pm = m_usedParserModules.find(idToken.getId());
            if (pm == null) {
                LexerModule lm = null;
                if (m_lexerMib != null) {
                    lm = m_lexerMib.find(idToken.getId());
                }
                if (lm != null) {
                    pm = new ModuleParser(this, lm);
                    m_usedParserModules.add(pm);
                } else {
                    m_pr.reportCannotFindModuleFile(idToken);
                    pm = new ModuleParser(this, new LexerModule(idToken.getId(), null));
                    m_unresolvedParserModules.add(pm);
                }
            }
        }
        if (pm.getState() == ModuleParser.State.UNPARSED) {
            pm.parse();
        }
        return pm;
    }


    public FileParserOptions getOptions() {
        return m_options;
    }

    public SmiMib getMib() {
        if (m_mib == null) {
            m_mib = new SmiMib(new SmiJavaCodeNamingStrategy("org.jsmiparser.mib")); // TODO
        }
        return m_mib;
    }

    public OidMgr getOidMgr() {
        return m_oidMgr;
    }
}
