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

import org.jsmiparser.util.TextUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.URISyntaxException;

public class FileParserOptions {

    private static final Logger m_log = Logger.getLogger(FileParserOptions.class);

    private List<File> m_usedDirList = new ArrayList<File>();
    private List<File> m_inputDirList = new ArrayList<File>();

    private List<File> m_usedFileList = new ArrayList<File>();
    private List<File> m_inputFileList = new ArrayList<File>();

    private List<String> m_extensions = new ArrayList<String>();

    private Map<String, File> m_cache = new HashMap<String, File>();

    public FileParserOptions() {
        m_extensions.add(".mib");
        m_extensions.add(".smi");
        m_extensions.add(".asn");
    }

    public List<File> getUsedDirList() {
        return m_usedDirList;
    }

    public void setUsedDirList(List<File> usedDirList) {
        m_usedDirList = usedDirList;
    }

    public List<File> getInputDirList() {
        return m_inputDirList;
    }

    public void setInputDirList(List<File> inputDirList) {
        m_inputDirList = inputDirList;
    }

    public List<File> getUsedFileList() {
        return m_usedFileList;
    }

    public void setUsedFileList(List<File> usedFileList) {
        m_usedFileList = usedFileList;
    }

    public List<File> getInputFileList() {
        return m_inputFileList;
    }

    public void setInputFileList(List<File> inputFileList) {
        m_inputFileList = inputFileList;
    }

    public void addFile(File file) {
        m_inputFileList.add(file);
    }

    public void addResource(String path) throws URISyntaxException {
        URL pathURL = getClass().getClassLoader().getResource(path);
        File pathFile = new File(pathURL.toURI());
        addFile(pathFile);
    }

    public List<String> getExtensions() {
        return m_extensions;
    }

    public void setExtensions(List<String> extensions) {
        m_extensions = extensions;
    }

    public File findFile(final String moduleName) {
        boolean itf = false;
        if ("ITF-MIB".equals(moduleName)) {
            itf = true;
        }

        File result = null; //m_cache.get(moduleName);
        if (result != null) {
            return result;
        }

        result = findModuleFile(moduleName, null);
        if (result != null) {
            return result;
        }

        for (String ext : m_extensions) {
            result = findModuleFile(moduleName, ext);
            if (result != null) {
                return result;
            }
        }

        if (!moduleName.equals(moduleName.toLowerCase())) {
            result = findFile(moduleName.toLowerCase());
            if (result != null) {
                return result;
            }
        }

        if (moduleName.contains("-")) {
            result = findFile(moduleName.replace('-', '_'));
            if (result != null) {
                return result;
            }

            result = findFile(TextUtil.deleteChar(moduleName, '-'));
            if (result != null) {
                return result;
            }
        }

        if (moduleName.endsWith("-MIB") || moduleName.endsWith("-SMI")) {
            result = findFile(moduleName.substring(0, moduleName.length() - 4));
            if (result != null) {
                return result;
            }
        }

        if (itf) {
            m_log.debug("trying itf");
        }
        Properties props = getModuleFileNameProperties();
        if (props != null) {
            if (itf) {
                m_log.debug("found properties for itf");
            }
            String fileName = (String) props.get(moduleName);
            if (itf) {
                m_log.debug("itf fileName: " + fileName);
            }
            if (fileName != null) {
                result = doFindFile(fileName);
                if (result != null) {
                    return result;
                }
            } else {
                m_log.debug(moduleName + " not found in properties file");
            }
        }

        return result;
    }

    private Properties getModuleFileNameProperties() {
        try {
            String fileName = System.getenv("MIBS_MODULE_FILE");
            if (fileName != null) {
                Properties p = new Properties();
                InputStream is = new BufferedInputStream(new FileInputStream(fileName));
                p.load(is);
                //m_log.debug("properties file size: " + p.size());
                is.close();
                return p;
            } else {
                m_log.debug("MIBS_MODULE_FILE not set");
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File findModuleFile(final String moduleName, final String extension) {
        String fileName = moduleName;
        if (extension != null) {
            fileName += extension;
        }

        File result = doFindFile(fileName);
        if (result != null) {
            m_cache.put(moduleName, result);
        }
        return result;
    }

    private File doFindFile(String fileName) {
        for (File dir : m_usedDirList) {
            File file = new File(dir, fileName);
            if (file.exists()) {
                return file;
            } else {
                //m_log.debug(file.getPath() + " does not exist.");
            }
        }

        return null;
    }
}
