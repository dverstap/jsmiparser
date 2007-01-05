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

public interface SmiCodeNamingStrategy {

	String getModuleId(SmiModule module);

	String getFullModuleId(SmiModule module);

	String getCodeConstantId(SmiVariable variable);
	String getFullCodeConstantId(SmiVariable variable);
	
	String getTypeId(SmiType type);

	String getSingleVariableEnumId(SmiVariable attr);

	String getVariableId(SmiVariable variable);

	String getEnumValueId(SmiNamedNumber value);

	String getRequestMethodId(SmiVariable attr);
	String getGetterMethodId(SmiVariable attr);
	String getSetterMethodId(SmiVariable attr);

	String getFullCodeId(SmiSymbol symbol);

	String getTableId(SmiTable table);

	String getFullVariableOidClassId(SmiModule module);

	String getVariableOidClassId(SmiModule module);





}
