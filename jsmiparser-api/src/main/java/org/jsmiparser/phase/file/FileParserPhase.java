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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.apache.log4j.Logger;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.phase.file.antlr.SMILexer;
import org.jsmiparser.phase.file.antlr.SMIParser;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiVersion;
import org.jsmiparser.util.location.Location;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

// TODO allow any URL's

public class FileParserPhase implements Phase {

    private static final Logger m_log = Logger.getLogger(FileParserPhase.class);

    private FileParserProblemReporter m_reporter;
    private FileParserOptions m_options = new FileParserOptions();

    public FileParserPhase(FileParserProblemReporter reporter) {
        m_reporter = reporter;
    }

    public FileParserProblemReporter getFileParserProblemReporter() {
        return m_reporter;
    }

    public SmiMib process(SmiMib mib) throws SmiException {
        for (File file : m_options.getInputFileSet()) {
            parse(mib, file);
        }

        if (m_log.isDebugEnabled()) {
            logParseResults(mib);
        }

        return mib;
    }


    public void parse(SmiMib mib, File inputFile) {
        InputStream is = null;
        try {
            m_log.debug("Parsing :" + inputFile);
            is = new BufferedInputStream(new FileInputStream(inputFile));
            SMILexer lexer = new SMILexer(is);

            SMIParser parser = new SMIParser(lexer);
            parser.init(mib, inputFile.getPath());

            // TODO should define this in the grammar
            SmiModule module = parser.module_definition();
            while (module != null) {
                module = parser.module_definition();
            }
        } catch (TokenStreamException e) {
            m_log.debug(e.getMessage(), e);
            m_reporter.reportTokenStreamError(inputFile);
        } catch (RecognitionException e) {
            m_log.debug(e.getMessage(), e);
            m_reporter.reportParseError(new Location(inputFile.getPath(), e.getLine(), e.getColumn()));
        } catch (FileNotFoundException e) {
            m_reporter.reportFileNotFound(inputFile);
        } finally {
            m_log.debug("Finished parsing :" + inputFile);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    m_log.warn(e.getMessage(), e);
                }
            }
        }
    }
    private void logParseResults(SmiMib mib) {
        Set<SmiModule> v1modules = mib.findModules(SmiVersion.V1);
        Set<SmiModule> v2modules = mib.findModules(SmiVersion.V2);
        m_log.debug("#SMIv1 modules=" + v1modules.size() + " #SMIv2 modules=" + v2modules.size());
        if (v1modules.size() > v2modules.size()) {
            m_log.debug("V2 modules:");
            logMibs(v2modules);
        } else if (v1modules.size() < v2modules.size()) {
            m_log.debug("V1 modules:");
            logMibs(v1modules);
        }
    }

    private void logMibs(Set<SmiModule> modules) {
        for (SmiModule module : modules) {
            m_log.debug(module + ": #V1 features=" + module.getV1Features() + " #V2 features=" + module.getV2Features());
        }
    }

    public FileParserOptions getOptions() {
        return m_options;
    }

}
