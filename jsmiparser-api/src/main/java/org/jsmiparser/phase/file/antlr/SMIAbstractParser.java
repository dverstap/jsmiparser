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
package org.jsmiparser.phase.file.antlr;

import antlr.*;
import org.jsmiparser.parsetree.asn1.*;
import org.jsmiparser.parsetree.smi.PIBAccess;
import org.jsmiparser.parsetree.smi.SMIAccess;
import org.jsmiparser.parsetree.smi.SMINamedBit;
import org.jsmiparser.parsetree.smi.SMIStatus;
import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.location.LocationFactory;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.phase.file.FileParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author davy
 */
public abstract class SMIAbstractParser extends LLkParser implements Context {

    private ASNModule m_module;
    private LocationFactory m_locationFactory;

    protected Context context_ = this;

    private String m_source;
    private AntlrFileParser m_fileParser;

    public SMIAbstractParser(int k) {
        super(k);
    }

    public SMIAbstractParser(ParserSharedInputState inputState, int k) {
        super(inputState, k);
    }

    public SMIAbstractParser(TokenBuffer tokenBuffer, int k) {
        super(tokenBuffer, k);
    }

    public SMIAbstractParser(TokenStream tokenStream, int k) {
        super(tokenStream, k);
    }

    public void init(String source, AntlrFileParser fileParser) {
        m_source = source;
        m_locationFactory = new AntlrLocationFactory(this, source);

        m_fileParser = fileParser;
        m_fileParser.getFileParserPhase().setContext(this);
    }

    public String getSource() {
        return m_source;
    }

    private Location makeLocation(Token token) {
        return new Location(m_source, token.getLine(), token.getColumn());
    }

    protected IdToken idt(Token idToken) {
        return new IdToken(makeLocation(idToken), idToken.getText());
    }

    protected List<IdToken> idt(List<Token> tokens) {
        List<IdToken> result = new ArrayList<IdToken>(tokens.size());
        for (Token token : tokens) {
            result.add(idt(token));
        }
        return result;
    }

    protected ASNModule makeModule(Token nameToken) {
        m_module = m_fileParser.createModule(idt(nameToken));
        return m_module;
    }

    protected void makeExports(List<Token> tokens) {
        for (Token token : tokens) {
            IdToken idToken = idt(token);
            m_fileParser.create(idToken);
        }
    }

    protected ASNImports makeImports(List<Token> importTokens, Token fromModuleToken) {
        IdToken moduleToken = idt(fromModuleToken);
        FileParser importedFileParser = m_fileParser.getFileParserPhase().use(moduleToken);
        ASNImports result = new ASNImports(context_, moduleToken, importedFileParser.getModule());
        for (Token token : importTokens) {
            IdToken idToken = idt(token);
            ASNAssignment assignment = importedFileParser.use(idToken);
            result.addAssigment(idToken, assignment);
        }
        return result;
    }

    protected ASNNamedNumber makeNamedNumber(ASNNamedNumberType nnt,
                                             Token nameToken, ASNValue s, ASNValue d) {
        return new ASNNamedNumber(context_, nnt, idt(nameToken), s, d);
    }

    protected SMINamedBit makeNamedBit(List<SMINamedBit> list, Token nameToken, Token valueToken) {
        SMINamedBit n = new SMINamedBit(context_, idt(nameToken));
        if (valueToken != null) // TODO don't allow null
        {
            n.setNumber(Long.parseLong(valueToken.getText()));
        }
        if (list != null) // TODO don't allow null
        {
            list.add(n);
        }
        return n;
    }

    protected ASNChoiceValue makeChoiceValue(Token nameToken) {
        return new ASNChoiceValue(context_, idt(nameToken));
    }

    protected ASNNamedValue makeNamedValue(Token nameToken, ASNValue value) {
        return new ASNNamedValue(context_, idt(nameToken), value);
    }

//	protected ASNValueAssignment makeValueAssignment(Token nameToken)
//	{
//		ASNValueAssignment result = new ASNValueAssignment(context_, nameToken);
//		setPosition(result, nameToken);
//		return result;
//	}

    protected ASNOidComponent makeOidComponent(Token nameToken, Token numberToken) {
        IdToken idToken = null;
        if (nameToken != null) {
            idToken = idt(nameToken);
        }
        long number = Long.parseLong(numberToken.getText()); // TODO LongToken
        return new ASNOidComponent(context_, idToken, number);
    }

    protected long makeLong(Token minusToken, Token numberToken) {
        long value = Long.parseLong(numberToken.getText());
        if (minusToken != null) {
            value = -value;
        }
        return value;
    }

    public SMIStatus makeStatus(Token l, String where) {
        SMIStatus s = null;

        return s;
    }


    public SMIAccess makeSmiAccess(Token l, String where) throws SemanticException {
        SMIAccess a = null;
        if (l.getText().equals("not-accessible"))
            a = SMIAccess.NOT_ACCESSIBLE;
        else if (l.getText().equals("accessible-for-notify"))
            a = SMIAccess.ACCESSIBLE_FOR_NOTIFY;
        else if (l.getText().equals("read-only"))
            a = SMIAccess.READ_ONLY;
        else if (l.getText().equals("read-write"))
            a = SMIAccess.READ_WRITE;
        else if (l.getText().equals("read-create"))
            a = SMIAccess.READ_CREATE;
        else
            throw new SemanticException("Invalid " + where + " access type for MIB");
        return a;
    }

    public PIBAccess makePibAccess(Token l, String where) throws SemanticException {
        PIBAccess p = null;
        if (l.getText().equals("not-accessible"))
            p = PIBAccess.NOT_ACCESSIBLE;
        else if (l.getText().equals("install"))
            p = PIBAccess.INSTALL;
        else if (l.getText().equals("notify"))
            p = PIBAccess.NOTIFY;
        else if (l.getText().equals("install-notify"))
            p = PIBAccess.INSTALL_NOTIFY;
        else if (l.getText().equals("report-only"))
            p = PIBAccess.REPORT_ONLY;
        else
            throw new SemanticException("Invalid " + where + " access type for PIB");
        return p;
    }

    public LocationFactory getLocationFactory() {
        return m_locationFactory;
    }

    public ASNModule getModule() {
        return m_module;
    }

    protected ASNTypeAssignment makeTypeAssignment(Token idToken, ASNType type) {
        ASNTypeAssignment result = m_fileParser.getTypeMap().create(idt(idToken));
        result.setEntityType(type);
        return result;
    }

    protected ASNValueAssignment makeValueAssignment(Token idToken, ASNType type, ASNValue value) {
        ASNValueAssignment result = m_fileParser.getValueMap().create(idt(idToken));
        result.setEntityType(type);
        result.setValue(value);
        return result;
    }

    protected ASNMacroDefinition makeMacroDefinition(Token idToken) {
        return m_fileParser.getMacroMap().create(idt(idToken));
    }

    protected ASNDefinedType makeDefinedType(Token moduleToken, Token idToken, ASNConstraint c) {
        ASNDefinedType result = new ASNDefinedType(context_);

        if (moduleToken != null) {
            FileParser fileParser = m_fileParser.getFileParserPhase().use(idt(moduleToken));
            ASNTypeAssignment ta = fileParser.getTypeMap().use(idt(idToken));
            result.setTypeAssignment(ta);
        } else {
            result.setTypeAssignment(m_fileParser.getTypeMap().resolve(idt(idToken)));
        }
        result.setConstraint(c);

        return result;
    }

    protected ASNDefinedValue makeDefinedValue(Token moduleToken, Token idToken) {
        ASNDefinedValue result = new ASNDefinedValue(context_);
        if (moduleToken != null) {
            FileParser fileParser = m_fileParser.getFileParserPhase().use(idt(moduleToken));
            ASNValueAssignment va = fileParser.getValueMap().use(idt(idToken));
            result.setValueAssignment(va);
        } else {
            result.setValueAssignment(m_fileParser.getValueMap().resolve(idt(idToken)));
        }
        return result;
    }
}
