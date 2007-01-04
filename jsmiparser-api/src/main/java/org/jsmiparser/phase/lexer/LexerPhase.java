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
package org.jsmiparser.phase.lexer;

import antlr.TokenStream;
import antlr.Token;
import antlr.TokenStreamException;
import org.apache.log4j.Logger;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.file.FileParserOptions;
import org.jsmiparser.phase.file.antlr.SMILexer;
import org.jsmiparser.util.problem.ProblemReporterFactory;

import java.io.*;
import java.util.List;

public class LexerPhase implements Phase {

    private static final Logger m_log = Logger.getLogger(LexerPhase.class);

    private FileParserOptions m_options = new FileParserOptions();
    private LexerMib m_lexerMib;

    public LexerPhase(ProblemReporterFactory problemReporterFactory) {
        // TODO
    }

    public FileParserOptions getOptions() {
        return m_options;
    }

    public LexerMib process(Object input) throws PhaseException {
        m_lexerMib = new LexerMib();

        for (File file : m_options.getInputFileSet()) {
            lexFile(file);
        }

        // TODO lots of work with input dirs, used dirs, used files etc

        return m_lexerMib;
    }

    private void lexFile(File file) {
        m_log.debug("lexFile: " + file);
        
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            SMILexer lexer = new SMILexer(is);
            lex(lexer, file.getPath(), m_lexerMib.getModules());
        } catch (TokenStreamException e) {
            m_log.error(e.getClass().getSimpleName(), e);
            throw new PhaseException(file.getPath(), e);
        } catch (FileNotFoundException e) {
            m_log.error(e.getClass().getSimpleName(), e);
            throw new PhaseException(file.getPath(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new PhaseException(e);
                }
            }
        }
    }

    // TODO this approach can leave various tokens between and before modules fix this
    public static void lex(TokenStream ts, String source, List<LexerModule> modules) throws TokenStreamException {
        LexerModule module = null;
        Token prev = ts.nextToken();
        Token current = ts.nextToken();
        while (current != null && current.getType() != SMILexer.EOF) {
            if (prev.getType() == SMILexer.UPPER && current.getType() == SMILexer.DEFINITIONS_KW) {
                module = new LexerModule(prev.getText(), source);
                modules.add(module);
                module.add(prev);
            }
            if (module != null) {
                module.add(current);
            }

            prev = current;
            current = ts.nextToken();
        }
    }
}
