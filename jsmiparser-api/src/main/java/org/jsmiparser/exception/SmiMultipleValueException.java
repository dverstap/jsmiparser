package org.jsmiparser.exception;

import org.jsmiparser.smi.SmiOidValue ;

public class SmiMultipleValueException extends IllegalArgumentException {
	private final SmiOidValue m_value1  ;
	private final SmiOidValue m_value2  ;
	
	public SmiMultipleValueException(SmiOidValue value1, SmiOidValue value2) {
		super("more than one found (" + value1 + " and " + value2 + ")") ;
		this.m_value1 = value1 ;
		this.m_value2 = value2 ;
	}
	
	public SmiOidValue getValue1() {
		return m_value1 ;
	}
	
	public SmiOidValue getValue2() {
		return m_value2 ;
	}
}
