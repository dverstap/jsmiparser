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
import org.jsmiparser.util.location.Location;

import java.math.BigInteger;

public interface OidProblemReporter {

    @ProblemMethod(message = "fixStart couldn't find root child: %s")
    void reportMissingRootChild(String id);

    @ProblemMethod(message = "Object identifier that starts without a name, must start with 0, 1 or 2: %d")
    void reportInvalidOidStart(BigInteger subId);

    @ProblemMethod(message = "Null new node for %s")
    void reportNewNullNode(Location location, String id);

    @ProblemMethod(message = "Object identifier already registered for %s at %s")
    void reportOidAlreadyRegistered(Location location, String id, Location location1);

    @ProblemMethod(message = "Can't find oid node %s")
    void reportCannotFindOidNode(Location location, String id);

    @ProblemMethod(message = "Cannot use unresolved oid %s that has a subId")
    void reportCannotResolveUnresolvedOid(String id);

    @ProblemMethod(message = "Unresolved oid %s")
    void reportUnresolvedOid(String id);

    @ProblemMethod(message = "Not a valid start node %s")
    void reportNotAValidStartNode(Location location, String id);

    @ProblemMethod(message = "Not a valid start node %d")
    void reportNotAValidStartNode(Location location, BigInteger number);

    @ProblemMethod(message = "%s is not the expected identifier %s (%s) %s")
    void reportIdMismatch(Location location, String id, String origId, Location origLocation, String s);

    @ProblemMethod(message = "%d is not the expected number %d")
    void reportNumberMismatch(Location location, BigInteger number, BigInteger expectedNumber);

    @ProblemMethod(message = "Oid component value is not resolved for %s")
    void reportValueNotResolved(Location location, String id);

    @ProblemMethod(message = "Parent oid component is not resolved for %s")
    void reportParentNotResolved(Location location, String id);

    @ProblemMethod(message = "%s has the same oid component value %d under the common parent %s as %s (%s)")
    void reportChildWithDuplicateValue(Location newLocation, String newId, BigInteger value, String parentId, String oldId, Location oldLocation);

    @ProblemMethod(message = "While resolving %s under %s (%s) detected an oid node with the same name under %s (%s) ")
    void reportDifferentParent(Location childLocation, String childId, String parentId, Location parentLocation, String origParentId, Location origParentLocation);
}
