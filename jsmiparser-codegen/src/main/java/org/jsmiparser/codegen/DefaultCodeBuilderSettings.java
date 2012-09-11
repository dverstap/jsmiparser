package org.jsmiparser.codegen;/*
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

import java.io.File;

public class DefaultCodeBuilderSettings implements CodeBuilderSettings {

    private String packageName;
    private File outputDir;
    private String bitsEnumTypeNameSuffix = "Bit";

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public String getBitsEnumTypeNameSuffix() {
        return bitsEnumTypeNameSuffix;
    }

    public void setBitsEnumTypeNameSuffix(String bitsEnumTypeNameSuffix) {
        this.bitsEnumTypeNameSuffix = bitsEnumTypeNameSuffix;
    }
}
