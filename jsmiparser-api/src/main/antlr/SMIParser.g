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
import org.jsmiparser.util.location.Location;

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

// TODO remove: ?
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
    private SmiMib m_mib;
    private String m_locationPath;
	private ModuleParser m_mp;

    public void init(SmiMib mib, String locationPath) {
        m_mib = mib;
        m_locationPath = locationPath;
    }

    SmiModule beginModule(Token idToken) {
        if (m_mp != null) {
            throw new IllegalStateException("Module " + m_mp.getModule().getIdToken() + " is still being parsed when trying to create new module " + idToken);
        }
        SmiModule module = m_mib.createModule(idt(idToken));
        m_mp = new ModuleParser(module);
        return module;
    }

    private void endModule() {
        if (m_mp == null) {
            throw new IllegalStateException("No module is being parsed");
        }
        m_mp = null;
    }

    private Location makeLocation(Token token) {
        return new Location(m_locationPath, token.getLine(), token.getColumn());
    }

    private IdToken idt(Token idToken) {
        return new IdToken(makeLocation(idToken), idToken.getText());
    }

}



module_definition returns [SmiModule result = null]
:
	EOF |
	result=module_identifier DEFINITIONS_KW ASSIGN_OP
	BEGIN_KW
		module_body
	END_KW { endModule(); }
;

module_identifier returns [SmiModule result = null]
:
	( u:UPPER | l:LOWER )
{
    // TODO error msg: only upper is correct
	result = beginModule(u != null ? u : l);
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
	| result=lower
	| result=macroName
;



macroName returns [IdToken result = null]
:
(
	ot:"OBJECT-TYPE"
	| mi:"MODULE-IDENTITY"    { m_mp.getModule().incV2Features(); }
	| oi:"OBJECT-IDENTITY"    { m_mp.getModule().incV2Features(); }
	| nt:"NOTIFICATION-TYPE"  { m_mp.getModule().incV2Features(); }
    | tc:"TEXTUAL-CONVENTION" { m_mp.getModule().incV2Features(); }
	| og:"OBJECT-GROUP"       { m_mp.getModule().incV2Features(); }
	| ng:"NOTIFICATION-GROUP" { m_mp.getModule().incV2Features(); }
	| mc:"MODULE-COMPLIANCE"  { m_mp.getModule().incV2Features(); }
	| ac:"AGENT-CAPABILITIES" { m_mp.getModule().incV2Features(); }
	| tt:"TRAP-TYPE"{ m_mp.getModule().incV1Features(); }
)
{
	result = m_mp.idt(ot, mi, oi, nt, tc, og, ng, mc, ac, tt);
}
;


assignment returns [SmiSymbol s = null]
{
    IdToken intToken = null;
    SmiType type = null;
    IdToken mn = null;
}
:
    (
	u:UPPER ASSIGN_OP s=type_assignment[m_mp.idt(u)]
	| l:LOWER s=value_assignment[m_mp.idt(l)]
	| mn=macroName "MACRO" ASSIGN_OP BEGIN_KW ( ~(END_KW) )* END_KW    { s = m_mp.createMacro(mn); }
	)
	{
	    m_mp.addSymbol(s);
	}
;

type_assignment[IdToken idToken] returns [SmiType t = null]
:
	t=textualconvention_macro[idToken]
	| t=leaf_type[idToken]
	| t=sequence_type[idToken]
	| t=choice_type[idToken]
;

// valid type for a leaf node (scalar or column)
leaf_type[IdToken idToken] returns [SmiType t = null]
:
    ( L_BRACKET APPLICATION_KW n:NUMBER R_BRACKET IMPLICIT_KW )? // only used for ApplicationSyntax types
    (
	t=integer_type[idToken, n]
	| t=oid_type[idToken]
	| t=octet_string_type[idToken, n]
	| t=bits_type[idToken]
	| t=defined_type[idToken]
	)
;

integer_type[IdToken idToken, Token applicationTagToken] returns [SmiType t = null]
{
    IntKeywordToken intToken;
    List<SmiNamedNumber> namedNumbers = null;
    List<SmiRange> rangeConstraints = null;
}
:
	intToken=integer_type_kw[idToken]
	(namedNumbers=named_number_list | rangeConstraints=range_constraint)?
	{
	    t = m_mp.createIntegerType(idToken, intToken, applicationTagToken, namedNumbers, rangeConstraints);
	}
;

// TODO should get these SmiType objects from the imports
// TODO should return SmiPrimitiveTypeIdToken here
integer_type_kw[IdToken idToken] returns [IntKeywordToken t = null]
:
	i:INTEGER_KW	{ t = m_mp.intkt(i, SmiPrimitiveType.INTEGER, null); }
;


oid_type[IdToken idToken] returns [SmiType t = null]
:
	OBJECT_KW IDENTIFIER_KW
{
	t = m_mp.createType(idToken, SmiConstants.OBJECT_IDENTIFIER_TYPE);
}
;

octet_string_type[IdToken idToken, Token applicationTagToken] returns [SmiType type = null]
{
    List<SmiRange> sizeConstraints = null;
}
:
	OCTET_KW STRING_KW (sizeConstraints=size_constraint)?
	{
	    type = m_mp.createOctetStringType(idToken, applicationTagToken, sizeConstraints);
	}
;


bits_type[IdToken idToken] returns [SmiType type = null]
{
    List<SmiNamedNumber> namedNumbers = null;
}
:
	"BITS" (namedNumbers=named_number_list)?
	{
        type = m_mp.createBitsType(idToken, namedNumbers);
	}
;

// only used for NetworkAddress, ObjectSyntax, SimpleSyntax and ApplicationSyntax
choice_type[IdToken idToken] returns [SmiType t = null]
:
    "CHOICE" L_BRACE ( ~(R_BRACE) )* R_BRACE
    {
        return m_mp.createChoiceType(idToken);
    }
;

defined_type[IdToken idToken] returns [SmiType type = null]
{
    List<SmiNamedNumber> namedNumbers = null;
    List<SmiRange> sizeConstraints = null;
    List<SmiRange> rangeConstraints = null;
}
:
	(mt:UPPER DOT)? tt:UPPER
	(namedNumbers=named_number_list | sizeConstraints=size_constraint | rangeConstraints=range_constraint)?
	{
	    type = m_mp.createDefinedType(idToken, mt, tt, namedNumbers, sizeConstraints, rangeConstraints);
	}
;

sequence_type[IdToken idToken] returns [SmiType t = null]
:
	SEQUENCE_KW	{ t = m_mp.createSequenceType(idToken); }
	L_BRACE
		sequence_field[t]
		(COMMA sequence_field[t])*
	R_BRACE
;

sequence_field[SmiType sequenceType]
{
	SmiType fieldType = null;
}
:
	l: LOWER
	fieldType=leaf_type[null]
{
	m_mp.addField(sequenceType, l, fieldType);
}
;

sequenceof_type returns [SmiType t = null]
:
	SEQUENCE_KW OF_KW u:UPPER
{
	t = m_mp.createSequenceOfType(u);
}
;

// for strings
// TODO rename to size_constraints
size_constraint returns [List<SmiRange> rc = null]
:
	L_PAREN "SIZE"
		rc=range_constraint
	R_PAREN
;

// for integers
// TODO rename to range_constraints
range_constraint returns [List<SmiRange> rc = null]
:
	L_PAREN		{ rc = new ArrayList<SmiRange>(); }
		range[rc]
		(BAR range[rc])*
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
	t=big_integer_token
	| t=binary_string_token
	| t=hex_string_token
;


// VALUES

value_assignment[IdToken idToken] returns [SmiValue v = null]
:
	v=macro_value_assignment[idToken]
	| v=oid_value_assignment[idToken]
;

oid_value_assignment[IdToken idToken] returns [SmiOidValue v = null]
{
	OidComponent last = null;
}
:
	OBJECT_KW IDENTIFIER_KW ASSIGN_OP last=oid_sequence[idToken]
{
	v = m_mp.createOidValue(idToken, last);
}
;

macro_value_assignment[IdToken idToken] returns [SmiValue v = null]
:
	v=oid_macro_value_assignment[idToken]
	| v=int_macro_value_assignment[idToken]
;

oid_macro_value_assignment[IdToken idToken] returns [SmiOidMacro v = null]
{
	OidComponent lastOidComponent = null;
}
:
	(v=objecttype_macro[idToken]
	| moduleidentity_macro
	| objectidentity_macro
	| v=notificationtype_macro[idToken]
	| objectgroup_macro
	| notificationgroup_macro
	| modulecompliance_macro
	| agentcapabilities_macro)
	ASSIGN_OP lastOidComponent=oid_sequence[idToken]
	// TODO it's probably better to move the oid stuff into the macro def
{
	if (v == null) { // TODO temporary
		v = m_mp.createOidMacro(idToken);
	}
	v.setLastOidComponent(lastOidComponent);
}
;

int_macro_value_assignment[IdToken idToken] returns [SmiTrapType v = null]
:
	(v=traptype_macro[idToken] ASSIGN_OP specificType:NUMBER)
{
	v.setSpecificTypeToken(m_mp.intt(specificType));
}
;


leaf_value returns [SmiDefaultValue result = null]
{
	BigIntegerToken bit = null;
	List<IdToken> bitsIdTokenList = null;
    OidComponent lastOidComponent = null;
    BinaryStringToken bst = null;
	HexStringToken hst = null;
	QuotedStringToken qst = null;
	ScopedId scopedId = null;
	boolean isNullValue = false;
}
:
	(bit=big_integer_token
	| (bits_value) => bitsIdTokenList=bits_value
	| lastOidComponent=oid_sequence[null]
	| bst=binary_string_token
	| hst=hex_string_token
	| qst=double_quoted_string_token
	| scopedId=defined_value
	| NULL_KW { isNullValue = true; }
	)
{
    result = new SmiDefaultValue(m_mp.getModule(), bit, bitsIdTokenList, lastOidComponent, bst, hst, qst, scopedId, isNullValue);
}
;


oid_sequence [IdToken idToken] returns [OidComponent last = null]
:
	L_BRACE
		(last = oid_component[last])+
	R_BRACE
;

oid_component[OidComponent parent] returns [OidComponent oc = null]
:
	nt1:NUMBER { oc = m_mp.createOidComponent(parent, null, nt1); }
	| (lt:LOWER (L_PAREN nt2:NUMBER R_PAREN)?) { oc = m_mp.createOidComponent(parent, lt, nt2); }
;

bits_value returns [List<IdToken> result = new ArrayList<IdToken>()]
:
	L_BRACE (l1:LOWER { result.add(idt(l1)); } (COMMA l2:LOWER { result.add(idt(l2)); })*)? R_BRACE
;

defined_value returns [ScopedId id=null]
:
	(u:UPPER DOT)? l:LOWER { id = m_mp.makeScopedId(u, l); }
;

// TODO: it might be possible to split this up into several
// definitions, thereby syntactically guaranteeing that INDEX/AUGMENTS
// belong only to rows, UNITS and DEFVAL only for variables. And
// REFERENCE?
objecttype_macro[IdToken idToken] returns [SmiObjectType ot = null]
{
    SmiType sequenceOfType = null;
	SmiType type = null;
	SmiVariable var = null;
	SmiRow row = null;
	SmiTable table = null;
	StatusAll status = null;
	SmiDefaultValue defaultValue = null;
}
:
	"OBJECT-TYPE" "SYNTAX"
		( type=leaf_type[null]
		  | sequenceOfType = sequenceof_type )
	("UNITS" units:C_STRING)? // TODO only on SmiVariable
	( ("ACCESS" access:LOWER)
		| ("MAX-ACCESS"  maxAccess:LOWER) )?
	"STATUS" status=status_all
	( "DESCRIPTION" desc:C_STRING )? /* TODO optional only for SMIv1 */
	( "REFERENCE" C_STRING )? 	  
	( ("INDEX" row=objecttype_macro_index[idToken, type]
          | "AUGMENTS" row=objecttype_macro_augments[idToken, type]) )?
	( "DEFVAL" L_BRACE defaultValue=leaf_value R_BRACE )?

	{
	    if (sequenceOfType != null) {
	        ot = table = m_mp.createTable(idToken, sequenceOfType);
	    } else if (row != null) {
	        ot = row;
	    } else {
	        ot = var = m_mp.createVariable(idToken, type, units, defaultValue);
	    }
	    if (access != null) {
	        ot.setAccessToken(m_mp.idt(access));
	    } else {
    	    ot.setMaxAccessToken(m_mp.idt(maxAccess));
    	}
	    ot.setStatus(status);
	    ot.setDescription(m_mp.getOptCStr(desc));
	}
;

status_all returns [StatusAll status = null]
:
	l:LOWER
{
	status = StatusAll.find(l.getText(), true);
}
;

status_v2 returns [StatusV2 status = null]
:
	l:LOWER
{
	status = m_mp.findStatusV2(l.getText());
}
;


objecttype_macro_index[IdToken idToken, SmiType type] returns [SmiRow row = m_mp.createRow(idToken, type)]
:
	L_BRACE
		objecttype_macro_indextype[row]
		(COMMA objecttype_macro_indextype[row])*
	R_BRACE
;    
   
objecttype_macro_indextype[SmiRow row]
{
    boolean implied=false;
    ScopedId id;
}
:
	("IMPLIED" { implied=true; } )?
	id=defined_value
	{ row.addIndex(id, implied); }
;

objecttype_macro_augments[IdToken idToken, SmiType type] returns [SmiRow row = m_mp.createRow(idToken, type)]
{
    ScopedId id;
}
:
	L_BRACE id=defined_value R_BRACE
	{
	    row.setAugmentsId(id);
	}
;




moduleidentity_macro
{
    List<SmiModuleRevision> revisions = new ArrayList<SmiModuleRevision>();
}
:
	"MODULE-IDENTITY" 
	"LAST-UPDATED" lastUpdated:C_STRING
	"ORGANIZATION" organization:C_STRING
	"CONTACT-INFO" contactInfo:C_STRING
	"DESCRIPTION"  description:C_STRING
	(moduleidentity_macro_revision[revisions])*
	{
	    m_mp.setModuleIdentity(lastUpdated, organization, contactInfo, description, revisions);
	}
;

moduleidentity_macro_revision[List<SmiModuleRevision> revisions]
:
	"REVISION"    revision:C_STRING
	"DESCRIPTION" description:C_STRING
	{
        revisions.add(m_mp.createModuleRevision(revision, description));
	}
;


objectidentity_macro
:
	"OBJECT-IDENTITY"
	"STATUS" status_v2
	"DESCRIPTION" C_STRING ("REFERENCE" C_STRING)?
;

notificationtype_macro[IdToken idToken] returns [SmiNotificationType nt = null]
{
	List<IdToken> objectTokens = null;
	StatusV2 status = null;
}
:
	"NOTIFICATION-TYPE"
	("OBJECTS" L_BRACE objectTokens=symbol_list R_BRACE)?
	"STATUS" status=status_v2
	"DESCRIPTION" description:C_STRING
	("REFERENCE" reference:C_STRING)?
	{
		nt = m_mp.createNotification(idToken, objectTokens, status, m_mp.getCStr(description), m_mp.getOptCStr(reference));
	}
;


textualconvention_macro[IdToken idToken] returns [SmiTextualConvention tc=null]
{
    SmiType type;
    StatusV2 status;
}
:
	"TEXTUAL-CONVENTION"
	("DISPLAY-HINT" displayHint:C_STRING)?
	"STATUS" status=status_v2
	"DESCRIPTION" description:C_STRING
	("REFERENCE" reference:C_STRING)?
	"SYNTAX" type=leaf_type[null]
	{
	    tc = m_mp.createTextualConvention(idToken, displayHint, status, description, reference, type);
	}
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
	("SYNTAX" leaf_type[null])?
	("WRITE-SYNTAX" leaf_type[null])?
	("MIN-ACCESS" modulecompliance_access)? 
	"DESCRIPTION" C_STRING
;

modulecompliance_access
:
	l:LOWER
{
	ModuleComplianceAccess.find(l.getText(), true);
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
	AgentCapabilitiesStatus.find(l.getText(), true);
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
	("SYNTAX" leaf_type[null])?
	("WRITE-SYNTAX" leaf_type[null])?
	("ACCESS" agentcapabilities_access)? 
	("CREATION-REQUIRES" L_BRACE LOWER (COMMA LOWER)* R_BRACE)? 
	("DEFVAL" L_BRACE leaf_value R_BRACE)?
	"DESCRIPTION" C_STRING
;


agentcapabilities_access
:
	l:LOWER
{
	AgentCapabilitiesAccess.find(l.getText(), true);
}
;


traptype_macro[IdToken idToken] returns [SmiTrapType tt = null]
{
	List<IdToken> variables = null;
}
:
	"TRAP-TYPE"
	"ENTERPRISE" enterprise:LOWER
	("VARIABLES" L_BRACE variables=symbol_list R_BRACE)? 
	("DESCRIPTION" description:C_STRING)?
	("REFERENCE" reference:C_STRING)?
	{
		tt = m_mp.createTrap(idToken, m_mp.idt(enterprise), variables, m_mp.getOptCStr(description), m_mp.getOptCStr(reference));
	}
;

named_number_list returns [List<SmiNamedNumber> l = new ArrayList<SmiNamedNumber>()]
:
	L_BRACE
		named_number[l]
		(COMMA named_number[l])*
	R_BRACE
;

named_number[List<SmiNamedNumber> l]
{
	IdToken it = null;
	BigIntegerToken bit = null;
}
:
	it=lower L_PAREN bit=big_integer_token R_PAREN
{
	l.add(new SmiNamedNumber(it, bit));
}
;

big_integer_token returns [BigIntegerToken bit = null]
:
	(mt:MINUS)? nt:NUMBER { bit = m_mp.bintt(mt, nt); }
;

binary_string_token returns [BinaryStringToken t = null]
:
	bt:B_STRING		{ t = m_mp.bst(bt); }
;

hex_string_token returns [HexStringToken t = null]
:
    ht:H_STRING		{ t = m_mp.hst(ht); }
;

double_quoted_string_token returns [QuotedStringToken t = null]
:
    ct:C_STRING { t = m_mp.dqst(ct); }
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
