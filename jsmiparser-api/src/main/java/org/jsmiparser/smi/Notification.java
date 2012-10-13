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
package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;

import java.util.List;

/**
 * Provides a uniform interface for trap and notification types, using:
 * <ul>
 *     <li>the rules from Section 2.2 of RFC 1908, "Coexistence between Version 1 and Version 2 of the
 *         Internet-standard Network Management Framework"</li>
 *     <li>the terminology of SMIv2.</li>
 * </ul>
 *
 * @see http://tools.ietf.org/html/rfc1908#section-2.2
 */
public interface Notification {

    SmiModule getModule();

    String getId();

    IdToken getIdToken();

    List<SmiVariable> getObjects();

    List<IdToken> getObjectTokens();

    String getDescription();

    String getReference();

    String getOidStr();

}
