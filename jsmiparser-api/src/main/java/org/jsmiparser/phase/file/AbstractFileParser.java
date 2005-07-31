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
package org.jsmiparser.phase.file;

import org.jsmiparser.parsetree.asn1.*;
import org.jsmiparser.util.token.IdToken;

import java.io.File;

public abstract class AbstractFileParser implements FileParser {

    protected FileParserPhase m_fileParserPhase;
    protected File m_file;
    protected State m_state = State.UNPARSED;
    protected ASNModule m_module;

    private ASNSymbolMap<ASNTypeAssignment> m_typeMap;
    private ASNSymbolMap<ASNValueAssignment> m_valueMap;
    private ASNSymbolMap<ASNMacroDefinition> m_macroMap;

    protected AbstractFileParser(FileParserPhase fileParserPhase, File file) {
        m_fileParserPhase = fileParserPhase;
        m_file = file;
    }

    private void init() {
        assert(m_module != null);
        m_typeMap = new ASNSymbolMap<ASNTypeAssignment>(m_fileParserPhase.getFileParserProblemReporter(), m_module, ASNTypeAssignment.class);
        m_valueMap = new ASNSymbolMap<ASNValueAssignment>(m_fileParserPhase.getFileParserProblemReporter(), m_module, ASNValueAssignment.class);
        m_macroMap = new ASNSymbolMap<ASNMacroDefinition>(m_fileParserPhase.getFileParserProblemReporter(), m_module, ASNMacroDefinition.class);
    }

    public FileParserPhase getFileParserPhase() {
        return m_fileParserPhase;
    }

    public File getFile() {
        return m_file;
    }

    public State getState() {
        return m_state;
    }

    public ASNModule getModule() {
        return m_module;
    }

    public ASNSymbolMap<ASNTypeAssignment> getTypeMap() {
        return m_typeMap;
    }

    public ASNSymbolMap<ASNValueAssignment> getValueMap() {
        return m_valueMap;
    }

    public ASNSymbolMap<ASNMacroDefinition> getMacroMap() {
        return m_macroMap;
    }

    public ASNAssignment create(IdToken idToken) {
        ASNAssignment result = null;
        switch (ASNAssignment.determineType(idToken)) {
            case MACRODEF:
                result = m_macroMap.create(idToken);
                break;
            case TYPE:
                result = m_typeMap.create(idToken);
                break;
            case VALUE:
                result = m_valueMap.create(idToken);
                break;
        }
        return result;
    }

    public ASNAssignment use(IdToken idToken) {
        ASNAssignment result = null;
        switch (ASNAssignment.determineType(idToken)) {
            case MACRODEF:
                result = m_macroMap.use(idToken);
                break;
            case TYPE:
                result = m_typeMap.use(idToken);
                break;
            case VALUE:
                result = m_valueMap.use(idToken);
                break;
        }
        return result;
    }

    public ASNModule useModule(IdToken idToken) {
        if (m_module == null) {
            m_module = new ASNModule(m_fileParserPhase.getMib(), idToken);
            init();
        }
        return m_module;
    }

    public ASNModule createModule(IdToken idToken) {
        if (m_module == null) {
            m_module = new ASNModule(m_fileParserPhase.getMib(), idToken);
            init();
        }
        return m_module;
    }
}
