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

import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.problem.annotations.ProblemMethod;
import org.jsmiparser.parsetree.asn1.ASNModule;
import org.jsmiparser.parsetree.asn1.ASNAssignment;

public interface FileParserProblemReporter {

    @ProblemMethod(message = "Cannot find file for module %s")
    void reportCannotFindModuleFile(IdToken moduleToken);

    @ProblemMethod(message = "Duplicate module %s originally defined at %s")
    void reportDuplicateModule(ASNModule duplicate, ASNModule original);

    @ProblemMethod(message = "Duplicate assignment %s originally defined at %s")
    void reportDuplicateAssignment(ASNAssignment duplicate, ASNAssignment original);
}
