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

package org.jsmiparser.antlr.asn1;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNTag {
    
	public enum Type
	{
		UNKNOWN,
		EXPLICIT,
		IMPLICIT,
		AUTOMATIC
	}
    
    private String tagType;
    private ASNClassNumber tagNumber;
    
    /** Creates a new instance of ASNTag */
    public ASNTag() {
    }
    
    public void setTagType (String t)
    {
        tagType = t;
    }
    
    public String getTagType ()
    {
        return tagType;
    }
    
    public void setTagNumber (ASNClassNumber c)
    {
        tagNumber = c;
    }
    
    public ASNClassNumber getTagNumber ()
    {
        return tagNumber;
    }
    
}
