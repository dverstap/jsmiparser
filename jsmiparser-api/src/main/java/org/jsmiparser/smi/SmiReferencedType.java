/*
 * Copyright 2006 Davy Verstappen.
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
package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;

import java.util.List;

/**
 * This class is only used during parsing, to temporarily referenced type that are defined
 * somewhere else, or that have not even been defined. If the mib is correct, there will
 * never be instances of this class in the final SmiMib datastructure.
 * <p/>
 * TODO move class out of public API
 */
public class SmiReferencedType extends SmiType {

    private IdToken m_referencedModuleToken;
    private List<SmiNamedNumber> m_namedNumbers; // don't know yet whether this is enums or bitfields

    public SmiReferencedType(IdToken idToken, SmiModule module) {
        super(idToken, module);

        // note that the idToken in this is case is the token where the reference
        // is made, not the token where the Type is defined.
    }

    // TODO put resolve algorithm here

    public IdToken getReferencedModuleToken() {
        return m_referencedModuleToken;
    }

    public void setReferencedModuleToken(IdToken referencedModuleToken) {
        m_referencedModuleToken = referencedModuleToken;
    }

    public List<SmiNamedNumber> getNamedNumbers() {
        return m_namedNumbers;
    }

    public void setNamedNumbers(List<SmiNamedNumber> namedNumbers) {
        m_namedNumbers = namedNumbers;
    }

    @Override
    public SmiType resolveThis() {
        SmiType result = this;

        SmiType type = findReferencedType(getModule().getMib());
        if (type != null) {
            // TODO check compatibility
            // TODO check module token; look in different places than module token
            // TODO verify
            if (getEnumValues() != null || getBitFields() != null || getRangeConstraints() != null || getSizeConstraints() != null)
            {
                result = new SmiType(null, getModule());
                result.setEnumValues(getEnumValues());
                result.setBitFields(getBitFields());
                result.setRangeConstraints(getRangeConstraints());
                result.setSizeConstraints(getSizeConstraints());
                result.setBaseType(type.getBaseType());
                result.setPrimitiveType(type.getPrimitiveType());
            } else {
                result = type;
            }
        } else {
            // TODO
            System.err.println("Couldn't find type during XRef phase: " + getIdToken());
        }

        if (result == null) {
            throw new IllegalStateException();
        }
        return result;
    }

    // TODO change algorithm: use the specified module first, then look in the current module, then use the imports, then use the global mib
    private SmiType findReferencedType(SmiMib mib) {
        return mib.findType(getId());
    }

    @Override
    public String toString() {
        return "WARNING REFERENCED TYPE: " + super.toString();
    }

}

