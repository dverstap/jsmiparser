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
package org.jsmiparser.util.location;

/**
 * Line and column numbers are 1-based (not 0-based).
 */
public class Location {

    public static final char SEPARATOR = ':';
    public static final int INVALID_LINE = -1;
    public static final int INVALID_COLUMN = -1;

    private String m_source;
    private int m_line = INVALID_LINE;
    private int m_column = INVALID_COLUMN;

    public Location(String file, int line, int column) {
        m_source = file;
        m_line = line;
        m_column = column;
    }

    public Location(String source, int line) {
        m_source = source;
        m_line = line;
    }

    public Location(String source) {
        m_source = source;
    }

    public String getSource() {
        return m_source;
    }

    public void setSource(String source) {
        m_source = source;
    }

    public int getLine() {
        return m_line;
    }

    public void setLine(int line) {
        m_line = line;
    }

    public int getColumn() {
        return m_column;
    }

    public void setColumn(int column) {
        m_column = column;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (m_source != null) {
            result.append(m_source);
        }
        result.append(SEPARATOR);
        if (m_line > INVALID_LINE) {
            result.append(m_line);
        }
        result.append(SEPARATOR);
        if (m_column > INVALID_LINE) {
            result.append(m_column);
        }
        return result.toString();
    }
}
