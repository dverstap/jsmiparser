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
package org.jsmiparser.util.problem;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * @todo figure out and clarify whether line/column numbers are 0 or 1 based
 */
public class ProblemLocation {
    private static final Logger m_log = Logger.getLogger(ProblemLocation.class);

    private File m_file;
    private int m_line;
    private int m_column;

    public ProblemLocation(File file, int line, int column) {
        m_file = file;
        m_line = line;
        m_column = column;
    }

    public File getFile() {
        return m_file;
    }

    public void setFile(File file) {
        m_file = file;
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
}
