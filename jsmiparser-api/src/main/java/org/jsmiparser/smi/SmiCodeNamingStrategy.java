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

	String getCodeConstantId(SmiAttribute attribute);
	String getFullCodeConstantId(SmiAttribute attribute);
	
	String getTypeId(SmiType type);

	String getSingleAttributeEnumId(SmiAttribute attr);

	String getAttributeId(SmiAttribute attribute);

	String getEnumValueId(SmiNamedNumber value);

	String getClassId(SmiClass cl);

	String getRequestMethodId(SmiAttribute attr);
	String getGetterMethodId(SmiAttribute attr);
	String getSetterMethodId(SmiAttribute attr);

	String getFullCodeId(SmiSymbol symbol);

	String getTableId(SmiTable table);

	String getFullAttributeOidClassId(SmiModule module);

	String getAttributeOidClassId(SmiModule module);





}
