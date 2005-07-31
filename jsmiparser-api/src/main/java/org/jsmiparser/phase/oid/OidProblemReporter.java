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
package org.jsmiparser.phase.oid;

import org.jsmiparser.util.problem.annotations.ProblemMethod;

import java.math.BigInteger;

public interface OidProblemReporter {

    @ProblemMethod(message = "fixStart couldn't find root child: %s")
    void reportMissingRootChild(String id);

    @ProblemMethod(message = "ASN OID that starts without a name, must start with 0, 1 or 2: %d")
    void reportInvalidOidStart(BigInteger subId);

    @ProblemMethod(message = "Null new node for %s")
    void reportNewNullNode(String id);

    @ProblemMethod(message = "ASN oid already registered for %s")
    void reportOidAlreadyRegistered(String id);

    @ProblemMethod(message = "Can't find oid node %s")
    void reportCannotFindOidNode(String id);

    @ProblemMethod(message = "Cannot use unresolved oid %s that has a subId")
    void reportCannotResolveUnresolvedOid(String id);

    @ProblemMethod(message = "Unresolved oid %s")
    void reportUnresolvedOid(String id);
}
