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
package org.jsmiparser.smi.impl.parsesmi;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import org.jsmiparser.parsetree.asn1.*;
import org.jsmiparser.phase.file.parser.ASNMib;
import org.jsmiparser.phase.file.parser.ASNMibParser;
import org.jsmiparser.parsetree.smi.SMINamedBit;
import org.jsmiparser.parsetree.smi.SMIObjectTypeMacro;
import org.jsmiparser.parsetree.smi.SMITextualConventionMacro;
import org.jsmiparser.parsetree.smi.SMIType;
import org.jsmiparser.smi.*;
import org.jsmiparser.util.problem.DefaultProblemHandler;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class APParser extends SmiAbstractParser {
	
	//private List<File> mibFiles_ = new ArrayList<File>();
	//private Map<String, ASNModule> modules_ = new HashMap<String, ASNModule>();

	private ASNMibParser mibParser_ = new ASNMibParser();
	
	
	public APParser(DefaultProblemHandler eh, SmiCodeNamingStrategy strategy) {
		super(eh, strategy);
	}

	public APParser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void addMibFile(File f) {
		mibParser_.addFile(f);
	}

	protected SmiMib parseBasics() throws IOException {
		SmiMib mib = new SmiMib(codeNamingStrategy_);
		
		ASNMib asnMib = null;
		try {
			asnMib = mibParser_.parse();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}
				
		for (ASNModule am : asnMib.getModules()) {
			SmiModule sm = mib.createModule(am.getModuleName());
			processModule(mib, am, sm);
		}
		
		return mib;
	}

	private void processModule(SmiMib mib, ASNModule m, SmiModule sm) {
		for (ASNAssignment a : m.getAssignments()) {
			final String name = a.getName();
			if (a instanceof ASNTypeAssignment) {
				ASNTypeAssignment typeAssignment = (ASNTypeAssignment) a;
				ASNType asnType = typeAssignment.getEntityType();
				
				SmiPrimitiveType pt = convertToPrimitiveType(asnType);
				if (pt != null) {
					SmiType st = sm.createType(name);
					st.setPrimitiveType(pt);
					
				
					convertType(asnType, st);
				} else if (asnType instanceof SMITextualConventionMacro) {
					//System.out.println("TC: " + m.getName() + ": " + name);
					SMITextualConventionMacro atc = (SMITextualConventionMacro) asnType;
					SmiTextualConvention stc = sm.createTextualConvention(name);
					ASNType at = atc.getSyntax();
					//System.out.println(" is " + at.getClass().getName());
					convertType(at, stc);
					if (!atc.getSyntaxNamedBits().isEmpty()) {
						for (SMINamedBit nb : atc.getSyntaxNamedBits()) {
							stc.addEnumValue(nb.getName(), BigInteger.valueOf(nb.getNumber()));
						}
					}
				}
			}
			else if (a instanceof ASNValueAssignment) {
				ASNValueAssignment va = (ASNValueAssignment) a;
				ASNType asnType = va.getEntityType();
				if (asnType instanceof SMIObjectTypeMacro) {
					//System.out.println("ObjectType macro: " + name);
					SMIObjectTypeMacro otm = (SMIObjectTypeMacro) asnType;
					if (otm.getSyntax().getType() != SMIType.Enum.SEQUENCE
							&& otm.getSyntax().getType() != SMIType.Enum.SEQUENCEOF) {
						//System.out.println("  is not SEQ(OF)");
						if (!otm.getNamedBits().isEmpty()) {
							SmiType type = sm.createType(SmiUtil.ucFirst(name));
							//System.out.println("Create attr type " + type.getId());
							for (SMINamedBit nb : otm.getNamedBits()) {
								type.addEnumValue(nb.getName(), BigInteger.valueOf(nb.getNumber()));
							}
						}
					}
				}
			}
		}
	}

	private void convertType(ASNType asnType, SmiType st) {
		if (asnType instanceof ASNNamedNumberType) {
			ASNNamedNumberType nnt = (ASNNamedNumberType) asnType;
			if (!nnt.getNamedNumbers().isEmpty()) {
				for (ASNNamedNumber ann : nnt.getNamedNumbers()) {
					ASNLiteralValue lv = (ASNLiteralValue) ann.getIntValue();
					st.addEnumValue(ann.getName(), lv.getNumberValue());
				}
			}
		}
	}

	private SmiPrimitiveType convertToPrimitiveType(ASNType asnType) {
		SmiPrimitiveType result = null;
		switch (asnType.getType()) {
		case ANY:
			break;
		case BOOLEAN:
			break;
		case BITSTRING:
			break;
		case CHARACTERSTRING:
			break;
		case CHOICE:
			break;
		case DEFINED:
			break;
		case EMBEDDED:
			break;
		case ENUM:
			result = SmiPrimitiveType.ENUM;
			break;
		case ERRORSMACRO:
			break;
		case EXTERNAL:
			break;
		case INTEGER:
			result = SmiPrimitiveType.INTEGER;
			break;
		case NULL:
			break;
		case OBJECTIDENTIFIER:
			result = SmiPrimitiveType.OBJECT_IDENTIFIER;
			break;
		case OCTETSTRING:
			result = SmiPrimitiveType.OCTET_STRING;
			break;
		case OPERATIONMACRO:
			break;
		case REAL:
			break;
		case RELATIVEOID:
			break;
		case SELECTION:
			break;
		case SEQUENCE:
			break;
		case SEQUENCEOF: 
			break;
		case SET:
			break;
		case SETOF:
			break;
		case SMIAGENTCAPABILITIESMACRO:
			break;
		case SMIMODULECOMPLIANCEMACRO:
			break;
		case SMIMODULEIDENTITYMACRO:
			break;
		case SMINOTIFICATIONGROUPMACRO:
			break;
		case SMINOTIFICATIONTYPEMACRO:
			break;
		case SMIOBJECTGROUPMACRO:
			break;
		case SMIOBJECTIDENTITYMACRO:
			break;
		case SMIOBJECTTYPEMACRO:
			break;
		case SMITEXTUALCONVENTIONMACRO:
			break;
		case SMITRAPTYPEMACRO:
			break;
		case SMITYPE:
			SMIType smiType = (SMIType) asnType;
			switch (smiType.getSmiType()) {
			case SMI_BITS:
				result = SmiPrimitiveType.BITS;
				break;
			case SMI_INTEGER:
				result = SmiPrimitiveType.INTEGER_32;
				break;
			case SMI_OCTETSTRING:
				result = SmiPrimitiveType.OCTET_STRING;
				break;
			case SMI_OID:
				result = SmiPrimitiveType.OCTET_STRING;
				break;
			case SMI_SUBTYPE:
				break;
			case SMI_UNKNOWN:
				break;
			}
			break;
		case TAG:
			break;
		case UNKNOWN:
			break;
		}
		return result;
	}

	
	
//	switch (asnType.getType()) {
//	case ANY:
//		break;
//	case BOOLEAN:
//		break;
//	case BITSTRING:
//		break;
//	case CHARACTERSTRING:
//		break;
//	case CHOICE:
//		break;
//	case DEFINED:
//		break;
//	case EMBEDDED:
//		break;
//	case ENUM:
//		break;
//	case ERRORSMACRO:
//		break;
//	case EXTERNAL:
//		break;
//	case INTEGER:
//		break;
//	case NULL:
//		break;
//	case OBJECTIDENTIFIER:
//		break;
//	case OCTETSTRING:
//		break;
//	case OPERATIONMACRO:
//		break;
//	case REAL:
//		break;
//	case RELATIVEOID:
//		break;
//	case SELECTION:
//		break;
//	case SEQUENCE:
//		break;
//	case SEQUENCEOF: 
//		break;
//	case SET:
//		break;
//	case SETOF:
//		break;
//	case SMIAGENTCAPABILITIESMACRO:
//		break;
//	case SMIMODULECOMPLIANCEMACRO:
//		break;
//	case SMIMODULEIDENTITYMACRO:
//		break;
//	case SMINOTIFICATIONGROUPMACRO:
//		break;
//	case SMINOTIFICATIONTYPEMACRO:
//		break;
//	case SMIOBJECTGROUPMACRO:
//		break;
//	case SMIOBJECTIDENTITYMACRO:
//		break;
//	case SMIOBJECTTYPEMACRO:
//		break;
//	case SMITEXTUALCONVENTIONMACRO:
//		break;
//	case SMITRAPTYPEMACRO:
//		break;
//	case SMITYPE:
//		SMIType smiType = (SMIType) asnType;
//		switch (smiType.getSmiType()) {
//		case SMI_BITS:
//			result = SmiPrimitiveType.BITS;
//			break;
//		case SMI_INTEGER:
//			result = SmiPrimitiveType.INTEGER_32;
//			break;
//		case SMI_OCTETSTRING:
//			result = SmiPrimitiveType.OCTET_STRING;
//			break;
//		case SMI_OID:
//			result = SmiPrimitiveType.OCTET_STRING;
//			break;
//		case SMI_SUBTYPE:
//			break;
//		case SMI_UNKNOWN:
//			break;
//		}
//		break;
//	case TAG:
//		break;
//	case UNKNOWN:
//		break;
//	}
	
}
