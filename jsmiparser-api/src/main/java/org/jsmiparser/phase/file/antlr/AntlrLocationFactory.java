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

import org.apache.log4j.Logger;
import org.jsmiparser.util.location.LocationFactory;
import org.jsmiparser.util.location.Location;
import antlr.Parser;
import antlr.Token;
import antlr.TokenStreamException;

public class AntlrLocationFactory implements LocationFactory {
    private static final Logger m_log = Logger.getLogger(AntlrLocationFactory.class);

    private Parser m_parser;
    private String m_source;

    public AntlrLocationFactory(Parser parser, String source) {
        m_parser = parser;
        m_source = source;
    }

    public Location create() {
        Location result = null;
        try {
            Token t = m_parser.LT(0);
            if (t != null)
            {
                result = new Location(m_source, t.getLine(), t.getColumn());
            }
            else
            {
                // TODO use AntlrErrorReporter
                m_log.warn("Can't trace line info in " + m_source);
            }
        } catch (TokenStreamException e) {
            // TODO use AntlrErrorReporter
            m_log.warn("TokenStreamException while trying to trace line info in " + m_source, e);
        }
        if (result == null) {
            result = new Location(m_source);
        }
        return result;
    }
}
