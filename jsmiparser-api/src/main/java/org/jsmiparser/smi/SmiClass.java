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

import java.util.List;
import java.util.Set;

public abstract class SmiClass extends SmiSymbol {

	public SmiClass(String id, SmiModule module) {
		super(id, module);
	}
	
	abstract public List<? extends SmiClass> getParentClasses();
	abstract public List<? extends SmiClass> getChildClasses();
	
	public SmiAttribute findAttribute(String id)
	{
		for (SmiAttribute a : getAttributes()) {
			if (a.getId().equals(id)) {
				return a;
			}
		}
		return null;
	}
	public abstract List<? extends SmiAttribute> getAttributes();
	
	/**
	 * @return All attributes, including the ones from the (indirect) parent classes.
	 */
	public abstract Set<? extends SmiAttribute> getAllAttributes();

	public String getCodeId() {
		return getModule().getMib().getCodeNamingStrategy().getClassId(this);
	}

	public abstract boolean isRowClass();
	public abstract boolean isScalarsClass();
	
}
