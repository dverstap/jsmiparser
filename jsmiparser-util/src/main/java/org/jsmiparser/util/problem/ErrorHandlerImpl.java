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

import org.jsmiparser.util.FileLocator;

import java.io.PrintStream;
import java.util.Formatter;


public class ErrorHandlerImpl {
    private String name_;
    private PrintStream out_;
    private PrintStream err_;
    private int errorCount_ = 0;
    private int warningCount_ = 0;
    private FileLocator locator_ = null;


    public ErrorHandlerImpl(String name, PrintStream err) {
        super();
        name_ = name;
        out_ = System.out;
        err_ = err;
    }

    public ErrorHandlerImpl(String name) {
        super();
        name_ = name;
        out_ = System.out;
        err_ = System.err;
    }

    public ErrorHandlerImpl() {
        super();
        out_ = System.out;
        err_ = System.err;
    }

    private void print(PrintStream s, String msg) {
        if (name_ != null) {
            s.print(name_);
            s.print(": ");
        }
        if (locator_ != null) {
            locator_.printLocation(s);
        }
        s.println(msg);
    }

    public void error(String msg) {
        print(err_, "Error: " + msg);
        errorCount_++;
    }

    public void error(String msg, String what) {
        print(err_, "Error: " + msg + " : " + what);
        errorCount_++;
    }

    public void warning(String msg) {
        print(out_, "Warning: " + msg);
        warningCount_++;
    }

    public void warning(String msg, String what) {
        print(out_, "Warning: " + msg + " : " + what);
        warningCount_++;
    }

    public void info(String msg) {
        print(out_, msg);
    }

    public void info(String msg, String what) {
        print(out_, msg + " : " + what);
    }

    public int getErrorCount() {
        return errorCount_;
    }

    public boolean isOk() {
        return errorCount_ == 0;
    }

    public boolean isNotOk() {
        return errorCount_ != 0;
    }

    public void printStat() {
        err_.print("Total error count: ");
        err_.println(errorCount_);
    }

    public void notFound(String msg, String name, Object obj) {
        if (obj == null) {
            error("Couldn't create " + msg, name);
        }
    }

    public void notFound(String msg, String what, String inWhat, Object obj) {
        if (obj == null) {
            String errStr = "Couldn't create " + msg + " " + what + " " + "in";
            error(errStr, inWhat);
        }
    }


    public void setLocator(FileLocator locator) {
        locator_ = locator;
    }


    public FileLocator getLocator() {
        return locator_;
    }

    public void printLocation(PrintStream s) {
        if (locator_ != null) {
            locator_.printLocation(s);
        }
    }

    public void warningf(String formatStr, Object... args) {
        Formatter f = new Formatter();
        f.format(formatStr, args);
        warning(f.out().toString());
    }
}
