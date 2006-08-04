/*
 * Copyright 2004 Davy Verstappen
 * Portions Copyright (C) 2003 Vivek Gupta
 * Portions Copyright (C) 2005 Nigel Sheridan-Smith
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

header {
package org.jsmiparser.phase.file.antlr;

import org.jsmiparser.util.token.*;
import org.jsmiparser.smi.*;
import org.jsmiparser.phase.file.*;
import org.jsmiparser.phase.oid.*;

import antlr.*;
import java.lang.* ;
import java.math.*;
import java.util.*;
}

//	Creation of ASN.1 grammar for ANTLR	V2.7.1
// ===================================================
//		  TOKENS FOR ASN.1 LEXER DEFINITIONS
// ===================================================

class SMILexer extends Lexer;
options	{
	k =	3;
	exportVocab=SMI;
	charVocabulary = '\3'..'\377';
	caseSensitive=true;
	testLiterals = true;
	codeGenMakeSwitchThreshold = 2;  // Some optimizations
	codeGenBitsetTestThreshold = 3;
}

//	ASN1 Tokens 

tokens {
	
ABSENT_KW = "ABSENT" ;
ABSTRACT_SYNTAX_KW = "ABSTRACT-SYNTAX" ;
ALL_KW= "ALL" ;
ANY_KW = "ANY" ;
ARGUMENT_KW = "ARGUMENT" ;
APPLICATION_KW = "APPLICATION" ;
AUTOMATIC_KW = "AUTOMATIC" ;
BASED_NUM_KW = "BASEDNUM" ;
BEGIN_KW = "BEGIN" ;
BIT_KW = "BIT" ;
BMP_STRING_KW = "BMPString" ;
BOOLEAN_KW = "BOOLEAN" ;
BY_KW = "BY" ;
CHARACTER_KW = "CHARACTER" ;
CHOICE_KW = "CHOICE" ;
CLASS_KW = "CLASS" ;
COMPONENTS_KW = "COMPONENTS" ;
COMPONENT_KW = "COMPONENT" ;
CONSTRAINED_KW = "CONSTRAINED" ;
DEFAULT_KW = "DEFAULT" ;
DEFINED_KW = "DEFINED" ;
DEFINITIONS_KW = "DEFINITIONS" ;
EMBEDDED_KW = "EMBEDDED" ;
END_KW = "END" ;
ENUMERATED_KW = "ENUMERATED" ;
ERROR_KW = "ERROR" ;
ERRORS_KW = "ERRORS" ;
EXCEPT_KW = "EXCEPT" ;
EXPLICIT_KW = "EXPLICIT" ;
EXPORTS_KW = "EXPORTS" ;
EXTENSIBILITY_KW = "EXTENSIBILITY" ;
EXTERNAL_KW = "EXTERNAL" ;
FALSE_KW = "FALSE" ;
FROM_KW = "FROM" ;
GENERALIZED_TIME_KW = "GeneralizedTime" ;
GENERAL_STR_KW = "GeneralString" ;
GRAPHIC_STR_KW = "GraphicString" ;
IA5_STRING_KW = "IA5String" ;
IDENTIFIER_KW = "IDENTIFIER" ;
IMPLICIT_KW = "IMPLICIT" ;
IMPLIED_KW = "IMPLIED" ;
IMPORTS_KW = "IMPORTS" ;
INCLUDES_KW = "INCLUDES" ;
INSTANCE_KW = "INSTANCE" ;
INTEGER_KW = "INTEGER" ;
INTERSECTION_KW = "INTERSECTION" ;
ISO646STRING_KW = "ISO646String" ;
LINKED_KW = "LINKED" ;
MAX_KW = "MAX" ;
MINUS_INFINITY_KW = "MINUSINFINITY" ;
MIN_KW = "MIN" ;
NULL_KW = "NULL" ;
NUMERIC_STR_KW = "NumericString" ;
OBJECT_DESCRIPTOR_KW = "ObjectDescriptor" ;
OBJECT_KW = "OBJECT" ;
OCTET_KW = "OCTET" ;
OPERATION_KW = "OPERATION" ;
OF_KW = "OF" ;
OID_KW = "OID" ;
OPTIONAL_KW = "OPTIONAL" ;
PARAMETER_KW = "PARAMETER" ;
PDV_KW = "PDV" ;
PLUS_INFINITY_KW = "PLUSINFINITY" ;
PRESENT_KW = "PRESENT" ;
PRINTABLE_STR_KW = "PrintableString" ;
PRIVATE_KW = "PRIVATE" ;
REAL_KW = "REAL" ;
RELATIVE_KW = "RELATIVE" ;
RESULT_KW = "RESULT" ;
SEQUENCE_KW = "SEQUENCE" ;
SET_KW = "SET" ;
SIZE_KW = "SIZE" ;
STRING_KW = "STRING" ;
TAGS_KW = "TAGS" ;
TELETEX_STR_KW = "TeletexString" ;
TRUE_KW = "TRUE" ;
TYPE_IDENTIFIER_KW = "TYPE-IDENTIFIER" ;
UNION_KW = "UNION" ;
UNIQUE_KW = "UNIQUE" ;
UNIVERSAL_KW = "UNIVERSAL" ;
UNIVERSAL_STR_KW = "UniversalString" ;
UTC_TIME_KW = "UTCTime" ;
UTF8STRING_KW = "UTF8String" ;
VIDEOTEX_STR_KW = "VideotexString" ;
VISIBLE_STR_KW = "VisibleString" ;
WITH_KW = "WITH" ;
}

// Operators

ASSIGN_OP:	"::=";
BAR:		'|';
COLON:		':';
COMMA:		',';
COMMENT:	"--";
DOT:		'.';
DOTDOT:		"..";
ELLIPSIS:	"...";
EXCLAMATION:	'!';
INTERSECTION:	'^';
LESS:		'<';
L_BRACE:	'{';
L_BRACKET:	'[';
L_PAREN:	'(';
MINUS:		'-';
PLUS:		'+';
R_BRACE:	'}';
R_BRACKET:	']';
R_PAREN:	')';
SEMI:		';';
SINGLE_QUOTE:	"'";
CHARB:		"'B";
CHARH:		"'H";

// Whitespace -- ignored

WS			
:
	( ' '
	| '\t'
	| '\f'
	|	( options {generateAmbigWarnings=false;}
		:
			"\r\n"	{ newline(); }// DOS
			| '\r'  { newline(); }// Macintosh
			| '\n'	{ newline(); }// Unix 
		)
	)+
{
	$setType(Token.SKIP);
}
;

SMIC_DIRECTIVE
:
(
	"SMI" WS ("OBJECT-TYPE"|"TRAP-TYPE")
)
{
	$setType(Token.SKIP);
}
;

INCLUDE
:
	"#include"
	(~('-'|'\n'|'\r'))*
	(('\r')? '\n') { newline(); }
{
	$setType(Token.SKIP);
}
;

/*
// Single-line comments
SL_COMMENT
:
(options {warnWhenFollowAmbig=false;} 
:
	COMMENT
	(  { LA(2)!='-' }? '-'		| ~('-'|'\n'|'\r'))*
	( (('\r')? '\n') { newline(); }	| COMMENT)
)
{
	$setType(Token.SKIP);
}
;
*/

SL_COMMENT
:
(options {warnWhenFollowAmbig=false;} 
:
	COMMENT
	( ~('\n'|'\r'))*
	( (('\r')? '\n') { newline(); })
)
{
	$setType(Token.SKIP);
}
;


NUMBER	:	('0'..'9')+ ;

// TODO in principle, underscores are not allowed in UPPER/LOWER identifiers
UPPER	
options {testLiterals = false;}
	:   ('A'..'Z') 
		(options {warnWhenFollowAmbig = false;}
	:	( 'a'..'z' | 'A'..'Z' | '-' | '_' | '0'..'9' ))*
;

LOWER
options {testLiterals = false;}
	:	('a'..'z') 
		(options {warnWhenFollowAmbig = false;}
	:	( 'a'..'z' | 'A'..'Z' | '-' | '_' | '0'..'9' ))*
;


protected
BDIG		: ('0'|'1') ;
protected
HDIG		:	(options {warnWhenFollowAmbig = false;} :('0'..'9') )
			|	('A'..'F')
			|	('a'..'f')
			;

// Unable to resolve a string like 010101 followed by 'H
//B_STRING 	: 	SINGLE_QUOTE ({LA(3)!='B'}? BDIG)+  BDIG SINGLE_QUOTE 'B';
//H_STRING 	: 	SINGLE_QUOTE ({LA(3)!='H'}? HDIG)+  HDIG SINGLE_QUOTE 'H';

B_OR_H_STRING
	:	(options {warnWhenFollowAmbig = false;} 
		:(B_STRING)=>B_STRING {$setType(B_STRING);}
		| H_STRING {$setType(H_STRING);})
	;

/* Changed by NSS 13/1/05 - upper case *or* lower case 'B' and 'H'; zero or more digits */
protected
B_STRING 	: 	SINGLE_QUOTE (BDIG)* SINGLE_QUOTE ('B' | 'b') ;
protected
H_STRING 	: 	SINGLE_QUOTE (HDIG)* SINGLE_QUOTE ('H' | 'h') ;

			
C_STRING 	: 	'"' (options {greedy=false;}
                             : "\r\n"		{ newline(); }// DOS
                             | '\r'   		{ newline(); }// Macintosh
                             | '\n'		{ newline(); }// Unix 
                             | ~('\r' | '\n')
                            )* 
                        '"' ;



//*************************************************************************
//**********		PARSER DEFINITIONS
//*************************************************************************


class SMIParser	extends	Parser;
options	{
	exportVocab=SMI;
	k=3;
        defaultErrorHandler=false;
}

{
	private ModuleParser m_mp;

	public void init(ModuleParser mp) {
		m_mp = mp;
	}

	public SmiModule getCurrentModule() {
		return m_mp.getModule();
	}
}


module_definition returns [SmiModule result = null]
:
	EOF |
	result=module_identifier DEFINITIONS_KW ASSIGN_OP
	BEGIN_KW
		module_body
	END_KW
;

module_identifier returns [SmiModule result = null]
:
	u:UPPER
{
	SkipStandardException.skip(u.getText());
	result = m_mp.makeModule(u);
}
;

module_body
:
	(exports)?
	(imports)?
	(assignment)*
;

exports
:
	EXPORTS_KW (symbol_list) SEMI
;

imports
:
	IMPORTS_KW (symbols_from_module)* SEMI
;

symbols_from_module
{
	List<IdToken> idTokenList = null;
	IdToken m = null;
}
:
	idTokenList=symbol_list FROM_KW m=upper
{
	m_mp.addImports(m, idTokenList);
} 
;

symbol_list returns [List<IdToken> result = m_mp.makeIdTokenList()]
{
	IdToken s1 = null, s2 = null;
}
:
	s1=symbol		{ result.add(s1); }
	(COMMA s2=symbol	{ result.add(s2); } )*
;

symbol returns [IdToken result = null]
:
	result=upper
	| result=integer_type_id
	| result=lower
	| result=macroName
;



macroName returns [IdToken result = null]
:
(
	ot:"OBJECT-TYPE"
	| mi:"MODULE-IDENTITY"
	| oi:"OBJECT-IDENTITY"
	| nt:"NOTIFICATION-TYPE" 
        | tc:"TEXTUAL-CONVENTION"
	| og:"OBJECT-GROUP"
	| ng:"NOTIFICATION-GROUP"
	| mc:"MODULE-COMPLIANCE" 
	| ac:"AGENT-CAPABILITIES"
	| tt:"TRAP-TYPE"
)
{
	result = m_mp.idt(ot, mi, oi, nt, tc, og, ng, mc, ac, tt);
}
;


assignment returns [SmiSymbol s = null]
:
    (
	u:UPPER ASSIGN_OP s=type_assignment[m_mp.idt(u)]
	| l:LOWER s=value_assignment[m_mp.idt(l)]
	| macroName "MACRO" ASSIGN_OP BEGIN_KW ( ~(END_KW) )* END_KW
	| s=defined_integer_type_kw ASSIGN_OP leaf_type
	)
	{
	    if (s != null) {
	        m_mp.addSymbol(s);
	    }
	}
;

type_assignment[IdToken idToken] returns [SmiType t = null]
:
	t=textualconvention_macro[idToken]
	| t=leaf_type		{ if (t != null) { t.setIdToken(idToken); } }
	| t=sequence_type	{ t.setIdToken(idToken); }
;

// valid type for a leaf node (scalar or column)
leaf_type returns [SmiType t = null]
:
    ( L_BRACKET APPLICATION_KW NUMBER R_BRACKET IMPLICIT_KW )? // only used for ApplicationSyntax types
    (
	t=integer_type
	| t=oid_type
	| t=octet_string_type
	| t=bits_type
	| t=choice_type
	| t=defined_type
	)
;

integer_type returns [SmiType t = null]
:
	t=integer_type_kw
	(t=named_number_list[t] | t=range_constrained_type[t])?
;

range_constrained_type[SmiType baseType] returns [SmiType t = null]
{
	List<SmiRange> rc = null;
}
:
	rc=range_constraint
{
	t = m_mp.createType(t);
	t.setRangeConstraint(rc);
}
;

// TODO should get these SmiType objects from the imports
integer_type_kw returns [SmiType t = null]
:
	INTEGER_KW	{ t = SmiConstants.INTEGER_TYPE; }
	| t=defined_integer_type_kw
;

defined_integer_type_kw returns [SmiType t = null]
:
	"Integer32"	{ t = SmiConstants.INTEGER_32_TYPE; }
	| "Counter"	{ t = SmiConstants.COUNTER_TYPE; }
	| "Counter32"	{ t = SmiConstants.COUNTER_32_TYPE; }
	| "Gauge"	{ t = SmiConstants.GAUGE_TYPE; }
	| "Gauge32"	{ t = SmiConstants.GAUGE_32_TYPE; }
	| "Counter64"	{ t = SmiConstants.COUNTER_64_TYPE; }
	| "TimeTicks"	{ t = SmiConstants.TIME_TICKS_TYPE; }
;

integer_type_id returns [IdToken result = null]
:
(
	  i32 : "Integer32"
	| c   : "Counter"
	| c32 : "Counter32"
	| g   : "Gauge"
	| g32 : "Gauge32"
	| c64 : "Counter64"
	| tt  : "TimeTicks"
)
{
	result = m_mp.idt(i32, c, c32, g, g32, c64, tt);
}
;

oid_type returns [SmiType t = null]
:
	OBJECT_KW IDENTIFIER_KW
{
	t = SmiConstants.OBJECT_IDENTIFIER_TYPE;
}
;

octet_string_type returns [SmiType t = null]
:
	OCTET_KW STRING_KW { t = SmiConstants.OCTET_STRING_TYPE; }
	(t=size_constrained_type[t])?
;

size_constrained_type[SmiType baseType] returns [SmiType t = null]
{
	List<SmiRange> sc = null;
}
:
	sc=size_constraint
{
	t = m_mp.createType(baseType);
	t.setSizeConstraint(sc);
}
;

bits_type returns [SmiType t = null]
:
	"BITS" 			{ t= SmiConstants.BITS_TYPE; }
	(t=named_number_list[t])?
;

// only used for ObjectSyntax, SimpleSyntax and ApplicationSyntax
choice_type returns [SmiType t = null]
:
    "CHOICE" L_BRACE ( ~(R_BRACE) )* R_BRACE
;

defined_type returns [SmiType t = null]
:
	(mt:UPPER DOT)? tt:UPPER 	{ t = m_mp.getDefinedType(mt, tt); }
	(t=named_number_list[t] | t=constrained_type[t])?
;

constrained_type[SmiType baseType] returns [SmiType t = null]
:
	t = size_constrained_type[baseType]
	| t = range_constrained_type[baseType]
;


sequence_type returns [SmiType t = null]
:
	SEQUENCE_KW	{ t = m_mp.createSequenceType(); }
	L_BRACE
		sequence_field[t]
		(COMMA sequence_field[t])*
	R_BRACE
;

sequence_field[SmiType sequenceType]
{
	SmiAttribute col = null;
	SmiType fieldType = null;
}
:
	l: LOWER	{ col = m_mp.useColumn(l); }
	fieldType=leaf_type
{
	m_mp.addField(sequenceType, col, fieldType);
}
;

sequenceof_type returns [SmiType t = null]
:
	SEQUENCE_KW OF_KW u:UPPER
{
	t = m_mp.createSequenceOfType(u);
}
;


/* SMI v2: Sub-typing - defined in RFC 1902 section 7.1 and appendix C and RFC 1904 */
/* not needed anymore
constraint[SmiType baseType] returns [SmiType t = null]
:
	t=range_constraint[baseType]
	| t=size_constraint[baseType]
;
*/


// for integers
range_constraint returns [List<SmiRange> rc = null]
:
	L_PAREN		{ rc = new ArrayList<SmiRange>(); }
		range[rc]
		(BAR range[rc])*
	R_PAREN
;

// for strings
size_constraint returns [List<SmiRange> rc = null]
:
	L_PAREN "SIZE"
		rc=range_constraint
	R_PAREN
;

range[List<SmiRange> rc]
{
	org.jsmiparser.util.token.Token rv1 = null;
	org.jsmiparser.util.token.Token rv2 = null;
}
:
	rv1=range_value
	(DOTDOT rv2=range_value)?
{
	m_mp.addRange(rc, rv1, rv2);
}
;

range_value returns [org.jsmiparser.util.token.Token t = null]
:
	(mt:MINUS)? nt:NUMBER	{ t = m_mp.bintt(mt, nt); }
	| bt:B_STRING		{ t = m_mp.bst(bt); }
	| ht:H_STRING		{ t = m_mp.hst(ht); }
;


// VALUES

value_assignment[IdToken idToken] returns [SmiValue v = null]
:
	v=macro_value_assignment[idToken]
	| v=primitive_value_assignment[idToken]
;

primitive_value_assignment[IdToken idToken] returns [SmiOidValue v = null]
{
	OidNode oc = null;
}
:
	OBJECT_KW IDENTIFIER_KW ASSIGN_OP oc=oid_sequence[idToken]
{
	v = m_mp.createOidValue(idToken, oc);
}
;

macro_value_assignment[IdToken idToken] returns [SmiValue v = null]
:
	v=oid_macro_value_assignment[idToken]
	| int_macro_value_assignment
;

oid_macro_value_assignment[IdToken idToken] returns [SmiOidMacro v = null]
{
	OidNode oc = null;
}
:
	(v=objecttype_macro[idToken]
	| moduleidentity_macro
	| objectidentity_macro
	| notificationtype_macro
	| objectgroup_macro
	| notificationgroup_macro
	| modulecompliance_macro
	| agentcapabilities_macro)
	ASSIGN_OP oc=oid_sequence[idToken]
	// TODO it's probably better to move the oid stuff into the macro def
{
	if (v == null) { // TODO temporary
		v = m_mp.createOidMacro(idToken);
	}
	v.setOidComponent(oc);
}
;

int_macro_value_assignment
:
	traptype_macro ASSIGN_OP NUMBER
;


leaf_value
{
	OidNode oc = null;
}
:
	integer_value
	| (bits_value) => bits_value
	| oc=oid_sequence[null]
	| octet_string_value
	| defined_value
	| NULL_KW
;


oid_sequence [IdToken idToken] returns [OidNode oc = null]
:
	L_BRACE
		(oc=oid_component[oc])+
	R_BRACE
{
	if (idToken != null) {
		oc = m_mp.registerOid(idToken, oc);
	}
}
;

oid_component[OidNode parent] returns [OidNode oc = null]
:
	nt1:NUMBER { oc = m_mp.resolveOidComponent(parent, null, nt1); }
	| (lt:LOWER (L_PAREN nt2:NUMBER R_PAREN)?) { oc = m_mp.resolveOidComponent(parent, lt, nt2); }
;

octet_string_value
:
	B_STRING|H_STRING|C_STRING
;

integer_value
:
	signed_number
;


bits_value
:
	L_BRACE (LOWER (COMMA LOWER)*)? R_BRACE
;

defined_value
:
	(UPPER DOT)? LOWER
;

// TODO: it might be possible to split this this up into several
// definitions, thereby syntactically guaranteeing that INDEX/AUGMENTS
// belong only to rows, UNITS and DEFVAL only for variables. And
// REFERENCE?
objecttype_macro[IdToken idToken] returns [SmiObjectType ot = null]
{
    SmiType sequenceOfType = null;
	SmiType type = null; // TODO fill in
	SmiAttribute attr = null;
	SmiRow row = null;
	SmiTable table = null;
	boolean isRow = false;
}
:
	"OBJECT-TYPE" "SYNTAX"
		( type=leaf_type {  }
		  | sequenceOfType = sequenceof_type )
	("UNITS" C_STRING)? // TODO only on SmiAttribute
	( ("ACCESS" objecttype_access_v1)
		| ("MAX-ACCESS"  objecttype_access_v2) )? 
	"STATUS" status_all
	( "DESCRIPTION" C_STRING )? /* TODO optional only for SMIv1 */
	( "REFERENCE" C_STRING )? 	  
	( ("INDEX" objecttype_macro_index
          | "AUGMENTS" objecttype_macro_augments) { isRow = true; } )?
	( "DEFVAL" L_BRACE leaf_value R_BRACE )?

	{
	    if (sequenceOfType != null) {
	        ot = table = m_mp.createTable(idToken, type);
	    } else if (isRow) {
	        ot = row = m_mp.createRow(idToken, type);
	    } else {
	        ot = attr = m_mp.createVariable(idToken, type);
	    }
	}
;

objecttype_access_v1
:
	l:LOWER
{
	ObjectTypeAccessV1.find(l.getText());
}
;

objecttype_access_v2
:
	l:LOWER
{
	ObjectTypeAccessV2.find(l.getText());
}
;

status_all
:
	l:LOWER
{
	StatusAll.find(l.getText());
}
;

status_v1
:
	l:LOWER
{
	StatusV1.find(l.getText());
}
;

status_v2
:
	l:LOWER
{
	StatusV2.find(l.getText());
}
;


objecttype_macro_index
:
	L_BRACE
		objecttype_macro_indextype
		(COMMA objecttype_macro_indextype)*
	R_BRACE
;    
   
objecttype_macro_indextype
:
	("IMPLIED")? defined_value
;

objecttype_macro_augments
:
	L_BRACE use_row R_BRACE
;




moduleidentity_macro
:
	"MODULE-IDENTITY" 
	"LAST-UPDATED" C_STRING
	"ORGANIZATION" C_STRING
	"CONTACT-INFO" C_STRING 
	"DESCRIPTION"  C_STRING
	(moduleidentity_macro_revision)*
;

moduleidentity_macro_revision
:
	"REVISION"    C_STRING
	"DESCRIPTION" C_STRING
;


objectidentity_macro
:
	"OBJECT-IDENTITY"
	"STATUS" status_v2
	"DESCRIPTION" C_STRING ("REFERENCE" C_STRING)?
;


notificationtype_macro
:
	"NOTIFICATION-TYPE"
	("OBJECTS" L_BRACE LOWER (COMMA LOWER)* R_BRACE)? 
	"STATUS" status_v2
	"DESCRIPTION" C_STRING ("REFERENCE" C_STRING)?
;

textualconvention_macro[IdToken idToken] returns [SmiTextualConvention tc=null]
:
	"TEXTUAL-CONVENTION"	{ tc = m_mp.createTextualConvention(idToken); }
	("DISPLAY-HINT" C_STRING)?
	"STATUS" status_v2 
	"DESCRIPTION" C_STRING 
	("REFERENCE" C_STRING)? 
	"SYNTAX" leaf_type
;


objectgroup_macro
:
	"OBJECT-GROUP" "OBJECTS"
	L_BRACE
		LOWER (COMMA LOWER)*
	R_BRACE 
	"STATUS" status_v2
	"DESCRIPTION" C_STRING
	("REFERENCE" C_STRING)?
;


notificationgroup_macro
:
	"NOTIFICATION-GROUP" "NOTIFICATIONS"
	L_BRACE
		LOWER (COMMA LOWER)*
	R_BRACE 
	"STATUS" status_v2
	"DESCRIPTION" C_STRING
	("REFERENCE" C_STRING)?
;


modulecompliance_macro
:
	"MODULE-COMPLIANCE"
	"STATUS" status_v2
	"DESCRIPTION" C_STRING
	("REFERENCE" C_STRING)?
	(modulecompliance_macro_module)+
;

modulecompliance_macro_module
:
	"MODULE" (UPPER)? 
	("MANDATORY-GROUPS" L_BRACE LOWER (COMMA LOWER)* R_BRACE)?
	(modulecompliance_macro_compliance)*
;

modulecompliance_macro_compliance
:
	modulecompliance_macro_compliance_group
	| modulecompliance_macro_compliance_object
;

modulecompliance_macro_compliance_group
:
	"GROUP" LOWER
	"DESCRIPTION" C_STRING
;

modulecompliance_macro_compliance_object
:
	"OBJECT" LOWER
	("SYNTAX" leaf_type)? 
	("WRITE-SYNTAX" leaf_type)?
	("MIN-ACCESS" modulecompliance_access)? 
	"DESCRIPTION" C_STRING
;

modulecompliance_access
:
	l:LOWER
{
	ModuleComplianceAccess.find(l.getText());
}
;


agentcapabilities_macro
:
	"AGENT-CAPABILITIES"
	"PRODUCT-RELEASE" C_STRING
	"STATUS" agentcapabilities_status
	"DESCRIPTION" C_STRING
	("REFERENCE" C_STRING)?
	(agentcapabilities_macro_module)*
;

agentcapabilities_status
:
	l:LOWER
{
	AgentCapabilitiesStatus.find(l.getText());
}
;

agentcapabilities_macro_module
:
	"SUPPORTS" UPPER
	"INCLUDES" L_BRACE LOWER (COMMA LOWER)* R_BRACE 
	(agentcapabilities_macro_variation)*
;

agentcapabilities_macro_variation
:
	"VARIATION" LOWER
	("SYNTAX" leaf_type)?
	("WRITE-SYNTAX" leaf_type)?
	("ACCESS" agentcapabilities_access)? 
	("CREATION-REQUIRES" L_BRACE LOWER (COMMA LOWER)* R_BRACE)? 
	("DEFVAL" L_BRACE leaf_value R_BRACE)?
	"DESCRIPTION" C_STRING
;


agentcapabilities_access
:
	l:LOWER
{
	AgentCapabilitiesAccess.find(l.getText());
}
;


traptype_macro
:
	"TRAP-TYPE"
	"ENTERPRISE" LOWER
	("VARIABLES" L_BRACE LOWER (COMMA LOWER)* R_BRACE)? 
	("DESCRIPTION" C_STRING)?
	("REFERENCE" C_STRING)?
;

named_number_list[SmiType baseType] returns [SmiType t = null]
:
	L_BRACE		{ t = m_mp.createType(baseType); }
		named_number[t]
		(COMMA named_number[t])*
	R_BRACE
;

named_number[SmiType t]
{
	IdToken it = null;
	BigIntegerToken bit = null;
}
:
	it=lower L_PAREN bit=signed_number R_PAREN
{
	t.addEnumValue(it, bit);
}
;

signed_number returns [BigIntegerToken bit = null]
:
	(mt:MINUS)? nt:NUMBER { bit = m_mp.bintt(mt, nt); }
;

upper returns [IdToken result = null]
:
	u:UPPER
{
	result = m_mp.idt(u);
}
;

lower returns [IdToken result = null]
:
	l:LOWER
{
	result = m_mp.idt(l);
}
;

use_row returns [SmiRow row = null]
{
	ModuleParser mp = m_mp;
	IdToken idt = null;
}
:
	(mp=use_module_parser)? idt=lower
{
	row = mp.useRow(idt);
}
;


use_module_parser returns [ModuleParser mp = null]
{
	IdToken mt = null;
}
:
	mt=upper
{
	mp = m_mp.getParserPhase().use(mt);
}
;
