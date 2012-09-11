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

import com.sun.codemodel.JCodeModel;
import org.jsmiparser.smi.SmiCodeNamingStrategy;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiNamedNumber;
import org.jsmiparser.smi.SmiType;

import java.util.List;

public class BuilderFactory {

    private final CodeBuilderSettings settings;

    private JCodeModel codeModel;

    public BuilderFactory(CodeBuilderSettings settings) {
        this.settings = settings;
    }

    public CodeBuilder createCodeBuilder(SmiMib mib) {
        mib.setCodeNamingStrategy(createNamingStrategy());
        this.codeModel = new JCodeModel();
        return new CodeBuilder(mib, settings, this, codeModel);
    }

    public SmiCodeNamingStrategy createNamingStrategy() {
        return new StraightNamingStrategy(settings.getPackageName());
    }

    public EnumBuilder createEnumBuilder(SmiType type, String typeName) {
        return new EnumBuilder(settings, type, typeName, codeModel);
    }

    public ModuleBuilder createModuleBuilder(SmiModule module) {
        return new ModuleBuilder(settings, module, codeModel);
    }

}
