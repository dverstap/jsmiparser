/*
 * Copyright 2005 Nigel Sheridan-Smith, Davy Verstappen.
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

package org.jsmiparser.parsetree.smi;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class SMIRangeValue {
    
    private long number_;
    private String binaryString_;
    private String hexString_;
    
    public SMIRangeValue() {
    }

	/**
	 * @return Returns the binaryString.
	 */
	public String getBinaryString() {
		return binaryString_;
	}
	
	/**
	 * @param binaryString The binaryString to set.
	 */
	public void setBinaryString(String binaryString) {
		binaryString_ = binaryString;
		
        /* Check the length first */
        if (binaryString.length() <= 3)
        {
            throw new NumberFormatException("Binary string value must be at least 3 characters long: " + binaryString);
        }
        /* Delete the first character and the last two */
        StringBuffer buf = new StringBuffer(binaryString);
        buf.deleteCharAt (0);
        buf.deleteCharAt (buf.length()-1);
        buf.deleteCharAt (buf.length()-1);
        
        /* Convert from binary number to integer */
        try 
        {
            number_ = Long.parseLong (buf.toString(), 2);
            
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Binary string value must be in radix 2 format");
        }
	}
	
	/**
	 * @return Returns the hexString.
	 */
	public String getHexString() {
		return hexString_;
	}
	
	/**
	 * @param hexString The hexString to set.
	 */
	public void setHexString(String hexString) {
		hexString_ = hexString;
		
        /* Check the length first */
        if (hexString.length() <= 3)
        {
            throw new NumberFormatException("Hexadecimal string value must be at least 3 characters long: " + hexString);
        }
        
        /* Delete the first character and the last two */
        StringBuffer buf = new StringBuffer(hexString);
        buf.deleteCharAt (0);
        buf.deleteCharAt (buf.length()-1);
        buf.deleteCharAt (buf.length()-1);
        
        /* Convert from hex number to integer */
        try 
        {
            number_ = Long.parseLong (buf.toString(), 16);
            
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Hexadecimal string value must be in radix 16 format");
        }
	}
	
	/**
	 * @return Returns the number.
	 */
	public long getNumber() {
		return number_;
	}
	
	/**
	 * @param number The number to set.
	 */
	public void setNumber(long number) {
		number_ = number;
	}
}
