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
package org.jsmiparser.phase.file;

import org.jsmiparser.parsetree.asn1.ASNMib;
import org.jsmiparser.parsetree.asn1.Context;
import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.util.token.IdToken;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

// TODO allow any URL's

public class FileParserPhase implements Phase {

    private FileParserProblemReporter m_pr;
    private Constructor<? extends FileParser> m_fileParserConstructor;

    private Context m_context; // TODO delete

    private FileParserOptions m_options = new FileParserOptions();
    private Map<File, FileParser> m_fileParserMap = new LinkedHashMap<File, FileParser>();

    private ASNMib m_mib;

    public FileParserPhase(ProblemReporterFactory prf, Class<? extends FileParser> fileParserClass) {
        super();
        m_pr = prf.create(FileParser.class.getClassLoader(), FileParserProblemReporter.class);
        try {
            m_fileParserConstructor = fileParserClass.getConstructor(FileParserPhase.class, File.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    public FileParserProblemReporter getFileParserProblemReporter() {
        return m_pr;
    }

    public Object process(Object input) throws PhaseException {
        initFileParserMap();
        m_mib = new ASNMib();

        for (FileParser fileParser : m_fileParserMap.values()) {
            if (fileParser.getState() == FileParser.State.UNPARSED) {
                fileParser.parse();
            }
        }

        m_mib.processModules();

        return m_mib;
    }

    private void initFileParserMap() throws PhaseException {
        for (File file : m_options.getInputFileList()) {
            createFileParser(file);
        }
    }

    private FileParser createFileParser(File file) {
        try {
            FileParser fileParser = m_fileParserConstructor.newInstance(this, file);
            m_fileParserMap.put(file, fileParser);
            return fileParser;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Context getContext() {
        return m_context;
    }

    public void setContext(Context context) {
        m_context = context;
    }

    public FileParser use(IdToken idToken) {
        FileParser fileParser;
        File file = m_options.findFile(idToken.getId());
        if (file != null) {
             fileParser = m_fileParserMap.get(file);
            if (fileParser == null) {
                fileParser = createFileParser(file);
            }
            if (fileParser.getState() == FileParser.State.PARSING) {
                System.err.println("Warning: using " + idToken.getId() + " from " + idToken.getLocation() + " while " + file + " is being parsed. Import cycle?");
            }
            if (fileParser.getState() == FileParser.State.UNPARSED) {
                fileParser.parse();
            }
        } else {
            m_pr.reportCannotFindModuleFile(idToken);
            fileParser = createFileParser(file);
        }
        fileParser.useModule(idToken);
        return fileParser;
    }

    public FileParserOptions getOptions() {
        return m_options;
    }

    public ASNMib getMib() {
        return m_mib;
    }
}
