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
package org.jsmiparser.gradle;


import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.tooling.BuildException;
import org.jsmiparser.codegen.BuilderFactory;
import org.jsmiparser.codegen.CodeBuilder;
import org.jsmiparser.codegen.DefaultCodeBuilderSettings;
import org.jsmiparser.parser.SmiDefaultParser;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;

import java.io.File;
import java.io.PrintStream;


public class CodegenTask extends DefaultTask {

    private final DefaultCodeBuilderSettings settings = new DefaultCodeBuilderSettings();
    private SmiMib mib;
    private FileCollection inputFiles;
    private File statusFile;
    private boolean failOnMibError = true;
    private BuilderFactory builderFactory;

    // TODO: this is ugly, because it forces you to write "settings.xyz = ..." in the build file,
    // but I'm keeping this for now to avoid too much code duplication/delegation. With a plugin that
    // declares extensions, this should not be too bad.
    public DefaultCodeBuilderSettings getSettings() {
        return settings;
    }

    public SmiMib getMib() {
        return mib;
    }

    public void setMib(SmiMib mib) {
        this.mib = mib;
    }

    @InputFiles
    public FileCollection getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(FileCollection inputFiles) {
        this.inputFiles = inputFiles;
    }

    public File getOutputDir() {
        if (settings.getOutputDir() == null) {
            settings.setOutputDir(new File(new File(getProject().getBuildDir(), "generated-src"), "java"));
        }
        return settings.getOutputDir();
    }

    public void setOutputDir(File outputDir) {
        settings.setOutputDir(outputDir);
    }

    @OutputFile
    public File getStatusFile() {
        if (statusFile != null) {
        return statusFile;
        }
        return new File(getOutputDir(), "." + CodegenTask.class.getName() + "." + getName());
    }

    public void setStatusFile(File statusFile) {
        this.statusFile = statusFile;
    }

    public boolean isFailOnMibError() {
        return failOnMibError;
    }

    public void setFailOnMibError(boolean failOnMibError) {
        this.failOnMibError = failOnMibError;
    }

    public BuilderFactory getBuilderFactory() {
        return builderFactory;
    }

    public void setBuilderFactory(BuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }

    @TaskAction
    public void build() throws Exception {
        SmiMib mib = determineMib();
        getLogger().debug("Compiling {} mibs to package {} in {}", mib.getModules().size(), settings.getPackageName(), settings.getOutputDir());

        for (SmiModule module : mib.getModules()) {
            getLogger().debug(module.getId());
        }
        PrintStream status = new PrintStream(getStatusFile());
        try {
            CodeBuilder codeBuilder = determineBuilderFactory().createCodeBuilder(mib);
            codeBuilder.write(status);
        } finally {
            status.close();
        }
    }

    protected BuilderFactory determineBuilderFactory() {
        if (builderFactory != null) {
            return builderFactory;
        }
        return new BuilderFactory(getSettings());
    }

    protected SmiMib determineMib() throws Exception {
        if (mib != null) {
            return mib;
        } else if (inputFiles != null) {
            return parseMib();
        }
        throw new BuildException("Either mib or inputFiles should be set.", null);
    }

    protected SmiMib parseMib() throws Exception {
        SmiDefaultParser parser = createParser();
        SmiMib result = parser.parse();
        if (parser.getProblemEventHandler().isNotOk()) {
            int problemCount = parser.getProblemEventHandler().getTotalCount();
            if (failOnMibError) {
                throw new BuildException("Found " + problemCount + " problems.", null);
            } else {
                getLogger().quiet("Found {} problems, but continuing anyway, because failOnMibError is set to false.", problemCount);
            }
        }
        return result;
    }

    protected SmiDefaultParser createParser() throws Exception {
        SmiDefaultParser parser = new SmiDefaultParser();
        parser.getFileParserPhase().setInputUrls(new FileCollectionURLListFactory(inputFiles).create());
        return parser;
    }

}
