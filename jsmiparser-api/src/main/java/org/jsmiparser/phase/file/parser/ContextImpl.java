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
package org.jsmiparser.phase.file.parser;


import org.jsmiparser.antlr.asn1.ASNModule;
import org.jsmiparser.antlr.asn1.Context;
import org.apache.log4j.Logger;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * @author davy
 *
 */
class ContextImpl implements Context {

    private static final Logger m_log = Logger.getLogger(ContextImpl.class);

    SMIAbstractParser parser_;
	ASNModule module_;
	
	/**
	 * @param parser
	 */
	public ContextImpl(SMIAbstractParser parser) {
		super();
		
		parser_ = parser;
	}
	


	/**
	 * @return The current line number.
	 */
	public int getLine() {
		int result = -1;
		try {
			Token t = parser_.LT(0);
			if (t != null)
			{
				result = t.getLine();
			}
			else
			{
				m_log.warn("Can't trace line info in " + module_.getName());
			}
		} catch (TokenStreamException e) {
            m_log.warn("TokenStreamException while trying to trace line info in " + module_.getName(), e);
		}
		return result;
	}



	/**
	 * @return The current column number.
	 */
	public int getColumn() {
		int result = -1;
		try {
			Token t = parser_.LT(0);
			if (t != null)
			{
				result = t.getLine();
			}
		} catch (TokenStreamException e) {
            m_log.warn("TokenStreamException while trying to trace column info in " + module_.getName(), e);						
		}
		return result;
	}
	
	/**
	 * @return Returns the module.
	 */
	public ASNModule getModule() {
		return module_;
	}
	
	/**
	 * @param module The module to set.
	 */
	public void setModule(ASNModule module) {
		module_ = module;
	}
}
