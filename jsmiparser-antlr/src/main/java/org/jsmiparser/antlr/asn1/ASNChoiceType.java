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

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNChoiceType extends ASNType {
    
    private List<ASNElementType> elementTypeList = new ArrayList<ASNElementType>();
    
    /** Creates a new instance of ASNChoiceType */
    public ASNChoiceType(Context context) {
        super(context, Enum.CHOICE);
    }
    
    
	/**
	 * @return Returns the elementTypeList.
	 */
	public List<ASNElementType> getElementTypeList() {
		return elementTypeList;
	}
	
	/**
	 * @param elementTypeList The elementTypeList to set.
	 */
	public void setElementTypeList(List<ASNElementType> elementTypeList) {
		this.elementTypeList = elementTypeList;
	}
}
