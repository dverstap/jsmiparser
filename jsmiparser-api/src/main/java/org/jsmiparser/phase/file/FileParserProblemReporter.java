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

import java.io.File;

import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.problem.annotations.ProblemMethod;

public interface FileParserProblemReporter {

    @ProblemMethod(message="Lex error at: %s : %s")
    void reportTokenStreamError(String resourceLocation, String msg);

    @ProblemMethod(message="Parse error: %s")
    void reportParseError(Location location, String msg);

    @ProblemMethod(message = "File not found: %s")
    void reportFileNotFound(File file);

    @ProblemMethod(message="IO error: %s")
    void reportIoException(Location location, String message);
}

