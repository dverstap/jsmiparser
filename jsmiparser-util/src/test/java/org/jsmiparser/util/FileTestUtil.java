/*
 * Copyright 2009-2012 Davy Verstappen.
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
package org.jsmiparser.util;

import org.junit.Assert;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class FileTestUtil {

    public static File getBuildDir(Class testClass) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource(testClass.getName().replace('.', '/') + ".class");
        if (url == null) {
            throw new IllegalArgumentException("class file not found for: " + testClass);
        }
        File classFile;
        try {
            classFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        if (!classFile.exists()) {
            throw new IllegalStateException(classFile + " does not exist.");
        }
        File dir = classFile.getParentFile();
        while (dir != null) {
            if (dir.getName().equals("build")) {
                return dir;
            }
            dir = dir.getParentFile();
        }
        throw new IllegalStateException("Could not find 'build' dir as a parent of " + classFile);
    }

    public static File makeBuildSubDir(Class testClass, String... subDirs) {
        File result = getBuildDir(testClass);
        for (String subDir: subDirs) {
            result = new File(result, subDir);
        }
        return createDir(result);
    }

    public static File createDir(File dir) {
        if (dir.exists()) {
            Assert.assertTrue(dir + " already exists, but it is not a directory.", dir.isDirectory());
        } else {
            boolean isCreated = dir.mkdirs();
            Assert.assertTrue(dir + " does not exist, but cannot be created.", isCreated);
        }
        return dir;
    }


    public static void assertExistsAndNotEmpty(File file) {
        Assert.assertTrue("File not created:" + file, file.exists());
        Assert.assertTrue(file + " size is " + file.length(), file.length() > 0);
    }

}
