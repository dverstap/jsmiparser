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
package org.jsmiparser.antlr.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ASNDefaultFileFinder implements ASNFileFinder {

	private List<File> directories_ = new ArrayList<File>();
	private List<String> extensions_ = new ArrayList<String>();
	
	public ASNDefaultFileFinder() {
		super();
	}
	
	public void addDirectory(File dir) {
		directories_.add(dir);
	}
	
	public void addExtension(String extension) {
		if (!extension.startsWith(".")) {
			extension = "." + extension;
		}
		extensions_ .add(extension);
	}

	public File findFile(String moduleName) {
		for (File dir : directories_) {
			File file = new File(dir, moduleName);
			if (file.exists()) {
				return file;
			}
		}

		for (File dir : directories_) {
			for (String ext : extensions_) {
				File file = new File(dir, moduleName + ext);
				if (file.exists()) {
					return file;
				}
			}
		}
		return null;
	}

}
