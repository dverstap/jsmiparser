/*
* Copyright 2006 Davy Verstappen.
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
package org.jsmiparser.phase.check;

import org.jsmiparser.phase.Phase;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.smi.SmiMib;

/**
 * This phase will be responsible for checking that the contents of each symbol is consistent
 * and correct.
 *
 * Many of the checks in this phase should be configurable (e.g. execute them or not)
 */
public class ErrorCheckPhase implements Phase {
    public Object getOptions() {
        return null;
    }

    public SmiMib process(SmiMib mib) throws SmiException {

        // TODO

        return mib;
    }
}
