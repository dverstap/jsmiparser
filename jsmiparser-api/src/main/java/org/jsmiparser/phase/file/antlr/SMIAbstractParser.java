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

import java.util.List;

/**
 * @author davy
 */
public abstract class SMIAbstractParser extends LLkParser implements Context {

    private ASNModule m_module;
    private LocationFactory m_locationFactory;

    protected Context context_ = this;

    private String m_source;

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

    public String getSource() {
        return m_source;
    }

    public void setSource(String source) {
        m_source = source;
        m_locationFactory = new AntlrLocationFactory(this, source);
    }

    protected ASNModule makeModule(Token nameToken) {
        m_module = new ASNModule(this, nameToken.getText());
        setPosition(m_module, nameToken);
        return m_module;
    }

    protected ASNImports makeImports(List<String> symbols, Token fromModuleToken) {
        return new ASNImports(context_,
                fromModuleToken.getText(), symbols);
    }

    protected ASNNamedNumber makeNamedNumber(ASNNamedNumberType nnt,
                                             Token nameToken, ASNValue s, ASNValue d) {
        return new ASNNamedNumber(context_, nnt, nameToken.getText(), s, d);
    }

    protected SMINamedBit makeNamedBit(List<SMINamedBit> list, Token nameToken, Token valueToken) {
        SMINamedBit n = new SMINamedBit(context_, nameToken.getText());
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
        return new ASNChoiceValue(context_, nameToken.getText());
    }

    protected ASNNamedValue makeNamedValue(Token nameToken, ASNValue value) {
        return new ASNNamedValue(context_, nameToken.getText(), value);
    }

//	protected ASNValueAssignment makeValueAssignment(Token nameToken)
//	{
//		ASNValueAssignment result = new ASNValueAssignment(context_, nameToken);
//		setPosition(result, nameToken);
//		return result;
//	}

    protected ASNOidComponent makeOidComponent(Token nameToken, Token numberToken) {
        String name = null;
        if (nameToken != null) {
            name = nameToken.getText();
        }
        long number = Long.parseLong(numberToken.getText());
        return new ASNOidComponent(context_, name, number);
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

    private void setPosition(Symbol symbol, Token token) {
        symbol.setLocation(new Location(m_source, token.getLine(), token.getColumn()));
    }

    public LocationFactory getLocationFactory() {
        return m_locationFactory;
    }

    public ASNModule getModule() {
        return m_module;
    }
}
