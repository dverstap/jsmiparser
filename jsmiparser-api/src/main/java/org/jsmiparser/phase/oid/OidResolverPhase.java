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

import org.apache.log4j.Logger;
import org.jsmiparser.parsetree.asn1.ASNMib;
import org.jsmiparser.phase.AbstractPhase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.util.problem.ProblemReporterFactory;

public class OidResolverPhase extends AbstractPhase {
    private static final Logger m_log = Logger.getLogger(OidResolverPhase.class);

    public OidResolverPhase(ProblemReporterFactory problemReporterFactory) {
        super(problemReporterFactory);
    }

    public Object getOptions() {
        return null;
    }

    public ASNMib process(Object input) throws PhaseException {
        ASNMib mib = (ASNMib) input;
        // TODO
        return mib;
    }
}
