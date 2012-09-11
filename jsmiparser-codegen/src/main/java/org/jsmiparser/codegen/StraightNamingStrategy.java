/*
 * Copyright 2012 Davy Verstappen.
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
package org.jsmiparser.codegen;


import com.sun.codemodel.JJavaName;
import org.jsmiparser.smi.SmiJavaCodeNamingStrategy;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiNamedNumber;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiUtil;

// TODO implement directly the interface
public class StraightNamingStrategy extends SmiJavaCodeNamingStrategy {

    public StraightNamingStrategy(String packagePrefix) {
        super(packagePrefix);
    }

    @Override
    public String getModuleId(SmiModule module) {
        return fix(module.getId());
    }

    @Override
    public String getTypeId(SmiType type) {
        return fix(SmiUtil.ucFirst(type.getId()));
    }

    @Override
    public String getOidValueId(SmiOidValue oidValue) {
        return fix(oidValue.getId());
    }

    @Override
    public String getEnumValueId(SmiNamedNumber ev) {
        return fix(ev.getId());
    }

    private String fix(String id) {
        id = id.replace('-', '_');
        if (Character.isDigit(id.charAt(0))) {
            id = "_" + id;
        } else if (!JJavaName.isJavaIdentifier(id)) {
            id = "_" + id;
        }
        return id;
    }
}
