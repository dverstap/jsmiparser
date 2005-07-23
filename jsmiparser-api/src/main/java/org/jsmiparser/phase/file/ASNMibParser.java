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
package org.jsmiparser.phase.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsmiparser.parsetree.asn1.ASNModule;
import org.jsmiparser.parsetree.asn1.ASNMib;
import org.jsmiparser.phase.file.ASNFileFinder;
import org.jsmiparser.phase.file.parser.SMILexer;
import org.jsmiparser.phase.file.parser.SMIParser;

import antlr.RecognitionException;
import antlr.TokenStreamException;


public class ASNMibParser {

	private ASNFileFinder fileFinder_;
	private List<File> files_ = new ArrayList<File>();
	
	public ASNMibParser() {
		super();
	}

	public ASNFileFinder getFileFinder() {
		return fileFinder_;
	}

	public void setFileFinder(ASNFileFinder fileFinder) {
		fileFinder_ = fileFinder;
	}
	
	public void addFile(File file) {
		files_.add(file);
	}

	public ASNMib parse() throws FileNotFoundException, RecognitionException, TokenStreamException {
		ASNMib mib = new ASNMib();
		
		for (File file : files_) {
			ASNModule module = parseFile(file);
			mib.addModule(module);
		}
		
		mib.processModules();
		
		return mib;
	}
	
	private ASNModule parseFile(File f) throws FileNotFoundException, RecognitionException, TokenStreamException {
		InputStream is = new BufferedInputStream(new FileInputStream(f));
		SMILexer lexer = new SMILexer(is);
		SMIParser parser = new SMIParser(lexer);
		
		return parser.module_definition();
	}
}
