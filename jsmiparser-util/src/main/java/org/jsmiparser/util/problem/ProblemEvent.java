/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.util.problem;

import org.apache.log4j.Logger;
import org.jsmiparser.util.problem.annotations.ProblemSeverity;
import org.jsmiparser.util.location.Location;

import java.util.Formatter;

public class ProblemEvent {
    private Location m_location;
    private ProblemSeverity m_severity;
    private String m_messageKey;
    private String m_defaultMessage;
    private Object[] m_arguments;

    public ProblemEvent(Location location, ProblemSeverity severity, String messageKey, String defaultMessage, Object[] arguments) {
        m_location = location;
        m_severity = severity;
        m_messageKey = messageKey;
        m_defaultMessage = defaultMessage;
        m_arguments = arguments;
    }

    public Location getLocation() {
        return m_location;
    }

    public ProblemSeverity getSeverity() {
        return m_severity;
    }

    public String getMessageKey() {
        return m_messageKey;
    }

    public Object[] getArguments() {
        return m_arguments;
    }

    /**
     * @todo actually implement localization.
     * @return The localized message
     */
    public String getLocalizedMessage() {
  		Formatter f = new Formatter();
		f.format(m_defaultMessage, m_arguments);
        return f.toString();
    }
}
