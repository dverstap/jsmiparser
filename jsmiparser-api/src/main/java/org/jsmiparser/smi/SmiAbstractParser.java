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
package org.jsmiparser.smi;

import org.jsmiparser.util.xmlreflect.ReflectContentHandler;
import org.apache.log4j.Logger;
import org.jsmiparser.util.problem.ErrorHandlerImpl;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SmiAbstractParser implements SmiParser {

    private static final Logger m_log = Logger.getLogger(SmiAbstractParser.class);

	protected ErrorHandlerImpl eh_;
	protected SmiCodeNamingStrategy codeNamingStrategy_;
	private List<File> classDefinitionFiles_ = new ArrayList<File>();

	public SmiAbstractParser(ErrorHandlerImpl eh, SmiCodeNamingStrategy strategy) {
		super();
		eh_ = eh;
		codeNamingStrategy_ = strategy;
	}

	public SmiAbstractParser() {
		super();
	}

	public final SmiMib parse() throws IOException, SAXException {
		SmiMib mib = parseBasics();
		
		if (eh_.isOk()) {
			mib.determineInheritanceRelations();
		}
		
		if (eh_.isOk()) {
			parseClassDefinitions(mib);
		}
		
		return mib;
	}


	private void parseClassDefinitions(SmiMib mib) throws SAXException, IOException {
        m_log.debug("parsing class definitions:");
		for (File f : classDefinitionFiles_ ) {
            m_log.debug("parsing " + f);
			SmiModule module = mib.createModule(null);
			ReflectContentHandler rch = new ReflectContentHandler(eh_, module);
            rch.parse(f);
            if (rch.getSkippedPathSet().size() > 0) {
                for (String skippedPath : rch.getSkippedPathSet()) {
                    eh_.error("Could not process: ", skippedPath);
                }
            }
		}
	}

	protected abstract SmiMib parseBasics() throws IOException;

	public ErrorHandlerImpl getErrorHandler() {
		return eh_;
	}

	public void addClassDefinitionFile(File f) {
		classDefinitionFiles_.add(f);
	}


}
