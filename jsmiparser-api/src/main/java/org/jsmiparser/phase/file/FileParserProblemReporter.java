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

import org.jsmiparser.smi.SmiSymbol;
import org.jsmiparser.util.problem.annotations.ProblemMethod;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.location.Location;

public interface FileParserProblemReporter {

    @ProblemMethod(message = "Cannot find file for module %s")
    void reportCannotFindModuleFile(IdToken moduleToken);

    @ProblemMethod(message = "Duplicate assignment %s originally defined at %s")
    void reportDuplicateAssignment(SmiSymbol duplicate, SmiSymbol original);

    @ProblemMethod(message = "Found %s %s but expected a % instance.")
    void reportIncompatibleType(Location l, String foundClassName, String id, String expectedClassName);
}
