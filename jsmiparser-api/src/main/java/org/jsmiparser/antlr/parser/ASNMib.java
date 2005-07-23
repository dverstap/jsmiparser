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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jsmiparser.antlr.asn1.ASNAssignment;
import org.jsmiparser.antlr.asn1.ASNModule;
import org.jsmiparser.antlr.asn1.ASNOidComponentList;
import org.jsmiparser.antlr.asn1.ASNTypeAssignment;
import org.jsmiparser.antlr.asn1.ASNValue;
import org.jsmiparser.antlr.asn1.ASNValueAssignment;

public class ASNMib {

	private Map<String, ASNModule> moduleMap_ = new HashMap<String, ASNModule>();
	
	private Map<String, ASNTypeAssignment> typeMap_ = new HashMap<String, ASNTypeAssignment>();
	private Map<String, ASNValueAssignment> valueMap_ = new HashMap<String, ASNValueAssignment>();
	
	private Map<ASNValueAssignment, String> oidMap_ = new HashMap<ASNValueAssignment, String>();
	
	public ASNMib() {
		
	}

	public Collection<ASNModule> getModules() {
		return moduleMap_.values();
	}
	
	public ASNModule findModule(String id) {
		return moduleMap_.get(id);
	}
	
	public void addModule(ASNModule module) {
		// TODO unique
		moduleMap_.put(module.getName(), module);
	}

	public Collection<ASNTypeAssignment> getTypes() {
		return typeMap_.values();
	}
	
	public ASNTypeAssignment findType(String id) {
		return typeMap_.get(id);
	}
	
	public Collection<ASNValueAssignment> getValues() {
		return valueMap_.values();
	}
	
	public ASNValueAssignment findValue(String id) {
		return valueMap_.get(id);
	}
	
	public void processModules() {
		processTypes();
		processValues();
		processOids();
	}

	private void processTypes() {
		for (ASNModule m : getModules()) {
			for (ASNAssignment a : m.getAssignments()) {
				if (a instanceof ASNTypeAssignment) {
					ASNTypeAssignment ta = (ASNTypeAssignment) a;
					typeMap_.put(a.getName(), ta);
				}
			}
		}
	}

	private void processValues() {
		for (ASNModule m : getModules()) {
			for (ASNAssignment a : m.getAssignments()) {
				if (a instanceof ASNValueAssignment) {
					ASNValueAssignment va = (ASNValueAssignment) a;
					valueMap_.put(a.getName(), va);
				}
			}
		}
	}

	private void processOids() {
		for (ASNValueAssignment va : getValues()) {
			findOid(va);
		}
	}

	private String findOid(ASNValueAssignment va) {
		String oid = oidMap_.get(va);
		if (oid == null) {
			ASNValue value = va.getValue();
			if (value instanceof ASNOidComponentList) {
				ASNOidComponentList oidList = (ASNOidComponentList) value;
				if (oidList.getDefinedValue() == null) {
					System.out.println(va.getName() + " defined value: " + oidList.getDefinedValue());
				}
			}
		}
		return oid;
	}
}
