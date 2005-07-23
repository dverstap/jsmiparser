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

import java.io.PrintStream;

public class DefaultProblemEventHandler extends AbstractProblemEventHandler {
    private PrintStream out_;
    private PrintStream err_;


    public DefaultProblemEventHandler() {
        super();
        out_ = System.out;
        err_ = System.err;
    }


    public void handle(ProblemEvent event) {
        super.handle(event);
        switch (event.getSeverity()) {
            case ERROR:
                error(event.getLocation(), event.getLocalizedMessage());
                break;
            case FATAL:
                error(event.getLocation(), event.getLocalizedMessage());
                break;
            case WARNING:
                warning(event.getLocation(), event.getLocalizedMessage());
                break;
        }
    }

    private void error(ProblemLocation location, String localizedMessage) {
        print(err_, "Error", location, localizedMessage);
    }


    private void warning(ProblemLocation location, String localizedMessage) {
        print(out_, "Warning", location, localizedMessage);
    }

    private void print(PrintStream stream, String sev, ProblemLocation location, String localizedMessage) {
        stream.println(sev + ":" + location + ":" + localizedMessage);
    }
}
