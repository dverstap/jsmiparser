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
package org.jsmiparser.smi;

import org.apache.log4j.Logger;
import org.jsmiparser.util.token.IdToken;

public class SmiModule extends SmiSymbolContainer {

    private static final Logger log = Logger.getLogger(SmiModule.class);

    private SmiMib mib_;
    private IdToken m_idToken;

    private SmiScalarsClass scalarsClass_;

    public SmiModule(SmiMib mib, IdToken idToken) {
        super(mib);
        mib_ = mib;

        typeMap_ = new ContextMap<String, SmiType>(mib.typeMap_);
        symbolMap_ = new ContextMap<String, SmiSymbol>(mib.symbolMap_);
        classMap_ = new ContextMap<String, SmiClass>(mib.classMap_);
        attributeMap_ = new ContextMap<String, SmiAttribute>(mib.attributeMap_);
        scalarMap_ = new ContextMap<String, SmiScalar>(mib.scalarMap_);
        tableMap_ = new ContextMap<String, SmiTable>(mib.tableMap_);
        rowMap_ = new ContextMap<String, SmiRow>(mib.rowMap_);
        columnMap_ = new ContextMap<String, SmiColumn>(mib.columnMap_);

        if (idToken != null) {
            setIdToken(idToken);

            String classId = mib_.getCodeNamingStrategy().getModuleId(this) + "Scalars"; //TextUtil.makeTypeName(id);
            IdToken classIdToken = new IdToken(idToken.getLocation(), classId);
            // TODO move to ConceptualModelBuilderPhase
            scalarsClass_ = new SmiScalarsClass(classIdToken, this);
            classMap_.put(classId, scalarsClass_);
        }
    }

    public void setIdToken(IdToken id) {
        assert(m_idToken == null);
        m_idToken = id;
        mib_.addModule(id.getId(), this);
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public String getId() {
        return m_idToken.getId();
    }

    public SmiMib getMib() {
        return mib_;
    }

    public SmiScalarsClass getScalarsClass() {
        return scalarsClass_;
    }

    public SmiType createType(IdToken idToken) {
        SmiType type = new SmiType(idToken, this);
        typeMap_.put(idToken.getId(), type);
        return type;
    }

    public SmiTextualConvention createTextualConvention(IdToken idToken) {
        SmiTextualConvention tc = new SmiTextualConvention(idToken, this);
        typeMap_.put(idToken.getId(), tc);
        return tc;
    }

    public SmiSingleAttributeEnum createSingleAttributeEnum(SmiAttribute attribute) {
        SmiSingleAttributeEnum result = new SmiSingleAttributeEnum(attribute);
        typeMap_.put(result.getId(), result);
        return result;
    }

    public SmiScalar createScalar(IdToken idToken) {
        SmiScalar scalar = new SmiScalar(idToken, scalarsClass_);
        scalarMap_.put(idToken.getId(), scalar);
        attributeMap_.put(idToken.getId(), scalar);
        return scalar;
    }

    public String getCodeId() {
        return getMib().getCodeNamingStrategy().getModuleId(this);
    }

    public String getFullCodeId() {
        return getMib().getCodeNamingStrategy().getFullModuleId(this);
    }

    public SmiTable createTable(IdToken idToken) {
        SmiTable table = new SmiTable(idToken, this);
        tableMap_.put(idToken.getId(), table);
        return table;
    }

    public SmiRow createRow(IdToken idToken) {
        SmiRow row = new SmiRow(idToken, this);
        rowMap_.put(idToken.getId(), row);
        classMap_.put(idToken.getId(), row);
        return row;
    }

    public SmiColumn createColumn(IdToken idToken) {
        SmiColumn col = new SmiColumn(idToken, this);
        columnMap_.put(idToken.getId(), col);
        attributeMap_.put(idToken.getId(), col);
        return col;
    }

    public String getFullAttributeOidClassId() {
        return getMib().getCodeNamingStrategy().getFullAttributeOidClassId(this);
    }

    public String getAttributeOidClassId() {
        return getMib().getCodeNamingStrategy().getAttributeOidClassId(this);
    }

    public void setMocMib(IdToken idToken) {
        setIdToken(idToken);
    }

    public void setMocRootOid(String oid) {
        // ignore
    }

    public SmiMultiRowClass createSmiMultiRowClass(IdToken idToken) {
        SmiMultiRowClass cl = new SmiMultiRowClass(this, idToken);
        classMap_.put(idToken.getId(), cl); // TODO unique check
        return cl;
    }

    public SmiScalarsClass createSnmpGroupObject(IdToken idToken) {
        SmiScalarsClass cl = new SmiScalarsClass(idToken, this);
        classMap_.put(idToken.getId(), cl); // TODO unique check
        return cl;
    }


}
