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

import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import org.jsmiparser.phase.file.antlr.SMILexer;

import java.util.List;

public class TokenArrayStream implements TokenStream {

    private List<Token> m_tokens;
    private int m_index = 0;

    public TokenArrayStream(List<Token> tokens) {
        m_tokens = tokens;
    }

    public Token nextToken() throws TokenStreamException {
        Token result = null;
        if (m_index < m_tokens.size()) {
            result = m_tokens.get(m_index);
            m_index++;
        }
        if (result == null) {
            result = new Token(SMILexer.EOF);
        }
        return result;
    }
}
