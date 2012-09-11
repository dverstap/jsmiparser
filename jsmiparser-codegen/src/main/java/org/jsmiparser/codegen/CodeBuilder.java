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
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.smi.SmiUtil;
import org.jsmiparser.smi.SmiVariable;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CodeBuilder {

    private final SmiMib mib;
    private final CodeBuilderSettings settings;
    private final BuilderFactory builderFactory;
    private final JCodeModel codeModel;

    public CodeBuilder(SmiMib mib, CodeBuilderSettings settings, BuilderFactory builderFactory, JCodeModel codeModel) {
        this.mib = mib;
        this.settings = settings;
        this.builderFactory = builderFactory;
        this.codeModel = codeModel;
    }

    public void build() {
        buildOids();
        buildEnums();
    }

    protected void buildOids() {
        for (SmiModule m : mib.getModules()) {
            buildModule(m);
        }
    }

    protected void buildModule(SmiModule module) {
        builderFactory.createModuleBuilder(module).build();
    }

    protected void buildEnums() {
        for (SmiType type : mib.getTypes()) {
            if (type.getNamedNumbers() != null) {
                String typeName = type.getCodeId();
                if (type.getBitFields() != null) {
                    typeName += settings.getBitsEnumTypeNameSuffix();
                }
                buildEnum(type, typeName);
            }
        }
        for (SmiVariable variable : mib.getVariables()) {
            SmiType type = variable.getType();
            if (type.getId() == null && type.getNamedNumbers() != null) {
                String typeName = SmiUtil.ucFirst(variable.getCodeId());
                if (type.getBitFields() != null) {
                    typeName += settings.getBitsEnumTypeNameSuffix();
                }
                buildEnum(type, typeName);
            }
        }
    }

    protected void buildEnum(SmiType type, String typeName) {
        builderFactory.createEnumBuilder(type, typeName).build();
    }

    public void write(PrintStream status) throws IOException {
        build();

        File outputDir = settings.getOutputDir();
        if (outputDir.exists()) {
            if (!outputDir.isDirectory()) {
                throw new IllegalStateException("The path of the output directory " + outputDir + " exists, but is not a directory.");
            }
        } else {
            boolean isCreated = outputDir.mkdirs();
            if (!isCreated) {
                throw new IllegalStateException("Output directory " + outputDir + " could not be created.");
            }
        }
        codeModel.build(outputDir, status);
    }

}
