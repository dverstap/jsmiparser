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

public class SmiScalar extends SmiAttribute {
	
	private SmiScalarsClass scalarsClass_;

	public SmiScalar(String id, SmiScalarsClass scalarsClass) {
		super(id, scalarsClass.getModule());
		
		scalarsClass_ = scalarsClass;
		scalarsClass_.getScalars().add(this);
	}

	@Override
	public SmiClass getSmiClass() {
		return scalarsClass_;
	}

	@Override
	public String getCodeOid() {
		String result = super.getCodeOid();
		if (!result.endsWith(".0")) {
			result += ".0";
		}
		return result;
	}

	
}
