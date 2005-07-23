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
package org.jsmiparser.smi.impl.parsesmi;

import org.jsmiparser.smi.SmiAbstractParser;
import org.jsmiparser.smi.SmiCodeNamingStrategy;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.util.problem.DefaultProblemHandler;

import java.io.IOException;

public class APParser extends SmiAbstractParser {
	
	//private List<File> mibFiles_ = new ArrayList<File>();
	//private Map<String, ASNModule> modules_ = new HashMap<String, ASNModule>();

	//private FileParserPhase m_fileParserPhase = new FileParserPhase();
	
	
	public APParser(DefaultProblemHandler eh, SmiCodeNamingStrategy strategy) {
		super(eh, strategy);
	}

	public APParser() {
		super();
		// TODO Auto-generated constructor stub
	}
    /*	
	public void addMibFile(File f) {
		m_fileParserPhase.addFile(f);
	}*/

	protected SmiMib parseBasics() throws IOException {
		SmiMib mib = new SmiMib(codeNamingStrategy_);

        /*
        ASNMib asnMib = null;
		try {
			asnMib = m_fileParserPhase.parse();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}
				
*/
		
		return mib;
	}


	
}
