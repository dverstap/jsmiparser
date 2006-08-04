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

import org.apache.log4j.Logger;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.phase.oid.OidMgr;

import java.util.HashSet;
import java.util.Set;

// TODO remove this
public class SkipStandardException extends RuntimeException {

    private static final Logger m_log = Logger.getLogger(SkipStandardException.class);

    public static final Set<String> m_skippedStandardModules = new HashSet<String>();

    static {
//        m_skippedStandardModules.add("RFC1065-SMI");
//        m_skippedStandardModules.add("RFC-1215");
//        m_skippedStandardModules.add("SNMPv2-TC");
//        m_skippedStandardModules.add("SNMPv2-CONF");
//        m_skippedStandardModules.add("SNMPv2-SMI");
//        m_skippedStandardModules.add("RFC1155-SMI");
//        m_skippedStandardModules.add("RFC-1212");
    }

    public SkipStandardException() {
        super();
    }

    public SkipStandardException(String message) {
        super(message);
    }

    public SkipStandardException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkipStandardException(Throwable cause) {
        super(cause);
    }

    public static void skip(String module) {
        if (m_skippedStandardModules.contains(module)) {
            m_log.debug("Skipping " + module);
            throw new SkipStandardException(module);
        }
    }
}
