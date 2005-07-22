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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


// TODO rename to SmiModuleClass
public class SmiScalarsClass extends SmiClass {

	private static final Logger log = Logger.getLogger(SmiScalarsClass.class);
	
	private List<SmiScalar> scalars_ = new ArrayList<SmiScalar>();
	private List<SmiScalarsClass> parentScalarsClasses_ = new ArrayList<SmiScalarsClass>();
	private List<SmiScalarsClass> childScalarsClasses_ = new ArrayList<SmiScalarsClass>();
	
	public SmiScalarsClass(String id, SmiModule module) {
		super(id, module);
		log.debug("Creating SmiScalarsClass: " + id + " in module " + module.getId());
	}

	public List<SmiScalar> getScalars()
	{
		return scalars_;
	}

	@Override
	public List<? extends SmiClass> getParentClasses() {
		return parentScalarsClasses_;
	}

	@Override
	public List<? extends SmiClass> getChildClasses() {
		return childScalarsClasses_;
	}

	@Override
	public List<? extends SmiAttribute> getAttributes() {
		return scalars_;
	}

	@Override
	public Set<? extends SmiAttribute> getAllAttributes() {
		return getAllScalars();
	}

	public Set<SmiScalar> getAllScalars() {
		// The LinkedHashSet is used to guarantee uniqueness (necessary
		// because of multiple inheritance), but still provides a reasonable
		// decent order.
		Set<SmiScalar> result = new LinkedHashSet<SmiScalar>();
		addScalars(result);
		return result;
	}

	private void addScalars(Set<SmiScalar> allScalars) {
		for (SmiScalarsClass sc : parentScalarsClasses_) {
			sc.addScalars(allScalars);
		}
		allScalars.addAll(scalars_);
	}

	public List<SmiScalarsClass> getChildScalarsClasses() {
		return childScalarsClasses_;
	}

	public List<SmiScalarsClass> getParentScalarsClasses() {
		return parentScalarsClasses_;
	}

	@Override
	public boolean isRowClass() {
		return false;
	}

	@Override
	public boolean isScalarsClass() {
		return true;
	}
	
	public void addSnmpGroup(String id) {
		SmiModule module = getModule().getMib().findModule(id);
		if (module == null) {
			// TODO error handling
			System.out.println("Couldn't find module " + id);
		} else {
			addParentScalarsClass(module.getScalarsClass());
		}
	}

	private void addParentScalarsClass(SmiScalarsClass parent) {
		this.parentScalarsClasses_.add(parent);
		parent.childScalarsClasses_.add(this);
	}
}
