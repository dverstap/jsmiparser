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



public class SmiJavaCodeNamingStrategy implements SmiCodeNamingStrategy {

	public static final String ATTR_OIDS = "AttrOids";
	
	private String packagePrefix_;

	public SmiJavaCodeNamingStrategy(String packagePrefix) {
		super();
		packagePrefix_ = packagePrefix;
	}

	public String getModuleId(SmiModule module) {
		StringBuilder result = new StringBuilder();
		String[] parts = module.getId().split("[^_0-9A-Za-z]");
		for (String str : parts) {
			result.append(SmiUtil.ucFirst(str.toLowerCase()));
		}
		return result.toString();
	}

	public String getFullModuleId(SmiModule module) {
		return packagePrefix_ + "." + getModuleId(module).toLowerCase();
	}

	public String getCodeConstantId(SmiVariable variable) {
		return makeConstant(variable.getId());
	}

	public String getFullCodeConstantId(SmiVariable variable) {
		return variable.getModule().getFullVariableOidClassId() + "." + variable.getCodeConstantId();
	}
	
	private enum Category { UPPER, LOWER, DIGIT, OTHER }
	
	public String makeConstant(String str) {
		StringBuilder result = new StringBuilder();
		int i = 0;
		String prev = null;
		while (i < str.length()) {
			int j = i;
			final Category cat = getCategory(str.charAt(j));
			j++;
			while (j < str.length() && getCategory(str.charAt(j)) == cat) {
				j++;
			}
			String current = str.substring(i, j);
			i = j;
			prev = processPrevious(result, prev, cat, current);
		}
		prev = append(result, prev, null);
		
		return result.toString();
	}

	private String processPrevious(StringBuilder result, String prev, final Category cat, String current) {
		String newPrev = null;
		if (prev == null) {
			newPrev = current;
		}
		else {
			switch (getCategory(prev.charAt(0))) {
			case DIGIT:
				newPrev = append(result, prev, current);
				break;
			case UPPER:
				if (cat == Category.LOWER) {
					if (prev.length() == 1) {
						newPrev = prev + current;
					}
					else {
						current = prev.charAt(prev.length()-1) + current;
						prev = prev.substring(0, prev.length()-1);
						newPrev = append(result, prev, current);
					}
				}
				else {
					newPrev = append(result, prev, current);
				}
				break;
			case LOWER:
				newPrev = append(result, prev, current);
				break;
			case OTHER:
				newPrev = current;
			default:
				// do nothing
			}
		}
		return newPrev;
	}

	private String append(StringBuilder result, String prev, String current) {
		if (result.length() != 0) {
			result.append('_');
		}
		result.append(prev.toUpperCase());
		return current;
	}
	
	private Category getCategory(char ch) {
		if (Character.isUpperCase(ch)) {
			return Category.UPPER;
		}
		else if (Character.isLowerCase(ch)) {
			return Category.LOWER;
		}
		else if (Character.isDigit(ch)) {
			return Category.DIGIT;
		}
		else {
			return Category.OTHER;
		}
	}

	public String getTypeId(SmiType type) {
		return type.getId();
	}

	public String getSingleVariableEnumId(SmiVariable attr) {
		return SmiUtil.ucFirst(attr.getCodeId());
	}

	public String getVariableId(SmiVariable variable) {
		return variable.getId();
	}

	public String getEnumValueId(SmiNamedNumber ev) {
		return makeConstant(ev.getId());
	}

	public String getRequestMethodId(SmiVariable attr) {
		return "req" + SmiUtil.ucFirst(attr.getCodeId());
	}
	
	public String getGetterMethodId(SmiVariable attr) {
		return "get" + SmiUtil.ucFirst(attr.getCodeId());
	}

	public String getSetterMethodId(SmiVariable attr) {
		return "set" + SmiUtil.ucFirst(attr.getCodeId());
	}

	public String getFullCodeId(SmiSymbol symbol) {
		return symbol.getModule().getFullCodeId() + "." + symbol.getCodeId();
	}

	public String getTableId(SmiTable table) {
		return table.getId();
	}

	public String getFullVariableOidClassId(SmiModule module) {
		return module.getFullCodeId() + "." + module.getVariableOidClassId();
	}

	public String getVariableOidClassId(SmiModule module) {
		return module.getCodeId().toUpperCase() + ATTR_OIDS;
	}

}
