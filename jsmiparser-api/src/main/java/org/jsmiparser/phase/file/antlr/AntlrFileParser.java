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
package org.jsmiparser.phase.file.antlr;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.jsmiparser.parsetree.asn1.ASNModule;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.phase.file.AbstractFileParser;
import org.jsmiparser.phase.file.FileParserPhase;

import java.io.*;

public class AntlrFileParser extends AbstractFileParser {

    public AntlrFileParser(FileParserPhase fileParserPhase, File file) {
        super(fileParserPhase, file);
    }

    public ASNModule parse() {
        assert(m_state == State.UNPARSED);
        try {
            m_state = State.PARSING;
            InputStream is = new BufferedInputStream(new FileInputStream(m_file));
            SMILexer lexer = new SMILexer(is);
            SMIParser parser = new SMIParser(lexer);
            parser.init(m_file.getPath(), this);
            
            m_module = parser.module_definition();
            is.close();

            return m_module;
        } catch (FileNotFoundException e) {
            throw new PhaseException(e);
        } catch (RecognitionException e) {
            throw new PhaseException(e);
        } catch (TokenStreamException e) {
            throw new PhaseException(e);
        } catch (IOException e) {
            throw new PhaseException(e);
        } finally {
            m_state = State.PARSED;
        }
    }



}
