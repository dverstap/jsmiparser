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

import antlr.Token;
import org.apache.log4j.Logger;
import org.jsmiparser.smi.*;
import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleParser {

    private static final Logger m_log = Logger.getLogger(ModuleParser.class);

    private final SmiModule m_module;

    public ModuleParser(SmiModule module) {
        m_module = module;
    }

    public SmiModule getModule() {
        return m_module;
    }

    private Location makeLocation(Token token) {
        String source = m_module.getIdToken().getLocation().getSource();
        return new Location(source, token.getLine(), token.getColumn());
    }

    public IdToken idt(Token idToken) {
        return new IdToken(makeLocation(idToken), idToken.getText());
    }

    public IdToken idt(Token... tokens) {
        for (Token token : tokens) {
            if (token != null) {
                return idt(token);
            }
        }
        return null;
    }

    public IntKeywordToken intkt(Token idToken, SmiPrimitiveType primitiveType, SmiVersion version) {
        if (version != null) {
            if (version == SmiVersion.V1) {
                m_module.incV1Features();
            } else {
                m_module.incV2Features();
            }
        }
        return new IntKeywordToken(makeLocation(idToken), idToken.getText(), primitiveType);
    }

    public String getCStr(Token t) {
        String text = t.getText();
        if (!(text.startsWith("\"") && text.endsWith("\""))) {
            throw new IllegalArgumentException(t.getText());
        }
        return text.substring(1, text.length() - 1);
    }

    public String getOptCStr(Token t) {
        if (t != null) {
            return getCStr(t);
        }
        return null;
    }

    public IntegerToken intt(Token t) {
        int value = Integer.parseInt(t.getText());
        return new IntegerToken(makeLocation(t), value);
    }

    public BigIntegerToken bintt(Token t) {
        return new BigIntegerToken(makeLocation(t), false, t.getText());
    }

    public BigIntegerToken bintt(Token minusToken, Token t) {
        return new BigIntegerToken(makeLocation(t), minusToken != null, t.getText());
    }

    public List<IdToken> makeIdTokenList() {
        return new ArrayList<IdToken>();
    }

    public void addImports(IdToken moduleToken, List<IdToken> importedTokenList) {
        SmiImports result = new SmiImports(m_module, moduleToken, importedTokenList);
        m_module.getImports().add(result);
    }

    public void addOidComponent(List<OidComponent> ocs, Token id, Token value) {
        IdToken idToken = id != null ? idt(id) : null;
        IntegerToken valueToken = value != null ? intt(value) : null;
        ocs.add(new OidComponent(idToken, valueToken));
    }

    public SmiOidValue createOidValue(IdToken idToken, List<OidComponent> oidComponents) {
        SmiOidValue result = new SmiOidValue(idToken, m_module);
        result.setOidComponents(oidComponents);
        return result;
    }

    public SmiMacro createMacro(IdToken idToken) {
        return new SmiMacro(idToken, m_module);
    }

    public SmiOidMacro createOidMacro(IdToken idToken) {
        return new SmiOidMacro(idToken, m_module);
    }

    public SmiVariable createVariable(IdToken idToken, SmiType t, Token units, SmiDefaultValue defaultValue) {
        final String methodWithParams = "createVariable(" + idToken.getId() + ")";
        m_log.debug(methodWithParams);

        QuotedStringToken unitsToken = null;
        if (units != null) {
            unitsToken = new QuotedStringToken(makeLocation(units), units.getText(), '\"');
        }
        return new SmiVariable(idToken, m_module, t, unitsToken, defaultValue);
    }

    public SmiRow createRow(IdToken idToken, SmiType t) {
        final String methodWithParams = "createRow(" + idToken.getId() + ")";
        m_log.debug(methodWithParams);

        SmiRow result = new SmiRow(idToken, m_module);
        result.setType(t);
        return result;
    }

    public SmiTable createTable(IdToken idToken, SmiType t) {
        final String methodWithParams = "createTable(" + idToken.getId() + ")";
        m_log.debug(methodWithParams);

        SmiTable result = new SmiTable(idToken, m_module);
        result.setType(t);
        return result;
    }

    public SmiTextualConvention createTextualConvention(IdToken idToken, Token displayHint, StatusV2 status, Token description, Token reference, SmiType type) {
        SmiTextualConvention result = new SmiTextualConvention(idToken, m_module, getOptCStr(displayHint), status, getCStr(description), getOptCStr(reference));

        if (type.getBaseType() == null) {
            result.setBaseType(type);
        } else {
            result.setBaseType(type.getBaseType());
        }
        result.setPrimitiveType(type.getPrimitiveType());
        result.setEnumValues(type.getEnumValues());
        result.setBitFields(type.getBitFields());
        result.setRangeConstraints(type.getRangeConstraints());
        result.setSizeConstraints(type.getSizeConstraints());
        result.setPrimitiveType(type.getPrimitiveType());

        return result;
    }

    public SmiType createSequenceType(IdToken idToken) {
        return new SmiType(idToken, m_module);
    }

    public SmiType createType(IdToken idToken, SmiType baseType) {
        SmiType result;
        if (baseType == null) {
            throw new IllegalArgumentException();
        }
        if (idToken == null) {
            result = baseType;
        } else {
            result = new SmiType(idToken, m_module);
            result.setBaseType(baseType);
        }
        return result;
    }

    // TODO investigate idea: instead of using the hardcoded SmiConstants here, use SmiReferencedType for everything,
    // and resolve references to INTEGER, BITS, ... during the XRef phase
    public SmiType createIntegerType(IdToken idToken, IntKeywordToken intToken, List<SmiNamedNumber> namedNumbers, List<SmiRange> rangeConstraints) {
        if (idToken == null && intToken.getPrimitiveType() == SmiPrimitiveType.INTEGER && namedNumbers == null && rangeConstraints == null) {
            return SmiConstants.INTEGER_TYPE;
        } else if (idToken != null || namedNumbers != null || rangeConstraints != null) {
            SmiType type = new SmiType(idToken, m_module);
            if (idToken instanceof IntKeywordToken) {
                IntKeywordToken intKeywordToken = (IntKeywordToken) idToken;
                type.setPrimitiveType(intKeywordToken.getPrimitiveType());
            } else {
                type.setPrimitiveType(intToken.getPrimitiveType());
            }
            if (intToken.getPrimitiveType() == SmiPrimitiveType.INTEGER) {
                type.setBaseType(SmiConstants.INTEGER_TYPE);
            } else {
                type.setBaseType(new SmiReferencedType(intToken, m_module));
            }
            type.setEnumValues(namedNumbers);
            type.setRangeConstraints(rangeConstraints);
            return type;
        }
        return new SmiReferencedType(intToken, m_module);
    }

    public SmiType createBitsType(IdToken idToken, List<SmiNamedNumber> namedNumbers) {
        m_module.incV2Features();
        if (idToken != null || namedNumbers != null) {
            SmiType type = new SmiType(idToken, m_module);
            type.setPrimitiveType(SmiPrimitiveType.BITS);
            type.setBaseType(SmiConstants.BITS_TYPE);
            type.setBitFields(namedNumbers);
            return type;
        }
        return SmiConstants.BITS_TYPE;
    }

    public SmiType createOctetStringType(IdToken idToken, List<SmiRange> sizeConstraints) {
        if (idToken != null || sizeConstraints != null) {
            SmiType type = new SmiType(idToken, m_module);
            type.setPrimitiveType(SmiPrimitiveType.OCTET_STRING);
            type.setBaseType(SmiConstants.OCTET_STRING_TYPE);
            type.setSizeConstraints(sizeConstraints);
            return type;
        }
        return SmiConstants.OCTET_STRING_TYPE;

    }

    public SmiType createDefinedType(IdToken idToken, Token moduleToken, Token referencedIdToken,
                                     List<SmiNamedNumber> namedNumbers,
                                     List<SmiRange> sizeConstraints,
                                     List<SmiRange> rangeConstraints) {
        SmiReferencedType referencedType = new SmiReferencedType(idt(referencedIdToken), m_module);
        if (moduleToken != null) {
            referencedType.setReferencedModuleToken(idt(moduleToken));
        }
        referencedType.setNamedNumbers(namedNumbers);
        referencedType.setSizeConstraints(sizeConstraints);
        referencedType.setRangeConstraints(rangeConstraints);

        SmiType result;
        if (idToken != null) {
            result = new SmiType(idToken, m_module);
            result.setBaseType(referencedType);
        } else {
            result = referencedType;
        }

        return result;
    }


    public SmiType createProtocolType(IdToken idToken) {
        if (idToken.getId().equals("NetworkAddress")) {
            SmiType result = new SmiType(idToken, m_module);
            result.setBaseType(new SmiReferencedType(new IdToken(idToken.getLocation(), "IpAddress"), m_module));
            return result;
        }
        return new SmiProtocolType(idToken, m_module);
    }

    public void addField(SmiType sequenceType, Token col, SmiType fieldType) {
        sequenceType.addField(idt(col), fieldType);
    }

    public SmiType createSequenceOfType(Token elementTypeNameToken) {
        SmiType sequenceOfType = new SmiType(null, m_module);
        sequenceOfType.setElementTypeToken(idt(elementTypeNameToken));
        return sequenceOfType;
    }

    public void addRange(List<SmiRange> rc, org.jsmiparser.util.token.Token rv1, org.jsmiparser.util.token.Token rv2) {
        SmiRange range;
        if (rv2 != null) {
            range = new SmiRange(rv1, rv2);
        } else {
            range = new SmiRange(rv1);
        }
        rc.add(range);
    }

    public BinaryStringToken bst(Token t) {
        return new BinaryStringToken(makeLocation(t), t.getText());
    }

    public HexStringToken hst(Token t) {
        return new HexStringToken(makeLocation(t), t.getText());
    }

    public QuotedStringToken dqst(Token t) {
        return new QuotedStringToken(makeLocation(t), t.getText(), '"');
    }

    public void addSymbol(SmiSymbol symbol) {
        if (symbol != null) {
            m_module.addSymbol(symbol);
        }
    }

    public StatusV2 findStatusV2(String text) {
        m_module.incV2Features();
        return StatusV2.find(text, true);
    }

    public ScopedId makeScopedId(Token moduleToken, Token symbolToken) {
        return new ScopedId(m_module, moduleToken != null ? idt(moduleToken) : null, idt(symbolToken));
    }

    public void setPrimitiveType(SmiType t, Token applicationTagToken) {
        int appTag = Integer.parseInt(applicationTagToken.getText());
        switch (appTag) {
            case 0:
                t.setPrimitiveType(SmiPrimitiveType.IP_ADDRESS);
                break;
            case 4:
                t.setPrimitiveType(SmiPrimitiveType.OPAQUE);
                break;
            default:
                throw new IllegalArgumentException("cannot handle application tag " + appTag);
        }
    }
}

