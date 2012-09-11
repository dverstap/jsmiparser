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
package org.jsmiparser.parser;

import org.jsmiparser.util.url.FileURLListFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LibSmiParserFactory {

    private final File mibsDir;

    public LibSmiParserFactory(File mibsDir) {
        this.mibsDir = mibsDir;
    }

    public SmiDefaultParser create() throws Exception {
        System.out.println("Creating a SmiDefaultParser for the libsmi mibs ...");
        SmiDefaultParser parser = new SmiDefaultParser();
        List<URL> inputUrls = initFileParserOptions(mibsDir, "iana", "ietf", "site", "tubs");
        parser.getFileParserPhase().setInputUrls(inputUrls);
        return parser;
    }

    private List<URL> initFileParserOptions(File mibsDir, String... subDirNames) throws Exception {
        List<URL> result = new ArrayList<URL>();

        for (String subDirName : subDirNames) {
            File dir = new File(mibsDir, subDirName);
//            assertTrue(dir.toString(), dir.exists());
//            assertTrue(dir.toString(), dir.isDirectory());

            FileURLListFactory urlListFactory = new FileURLListFactory(dir);
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()
                        && !file.getName().equals("RFC1158-MIB") // obsoleted by RFC-1213
                        && !file.getName().contains("TOTAL")
                        && !file.getName().endsWith("tree")
                        && !file.getName().startsWith("Makefile")
                        && !file.getName().endsWith("~")
                        //&& !v1mibs.contains(file.getName())
                        && !file.getName().endsWith("-orig") // TODO parsing -orig should give more errors!
                        && !(file.getName().equals("IANA-ITU-ALARM-TC-MIB") && file.getParentFile().getName().equals("iana"))
                        ) {
                    urlListFactory.add(file.getName());
                }
            }
            result.addAll(urlListFactory.create());
        }
        return result;
    }

}
