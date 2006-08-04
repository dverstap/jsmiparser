/*
 * Copyright 2005 Davy Verstappen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;

public class SmiObjectType extends SmiOidMacro {

    protected SmiType type_;
    private SmiPrimitiveType primitiveType_;

    public SmiObjectType(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }


    public SmiPrimitiveType getPrimitiveType() {
		if (type_ != null) {
			return type_.getPrimitiveType();
		}
		else {
			return primitiveType_;
		}
	}

    public void setPrimitiveType(SmiPrimitiveType primitiveType) {
		primitiveType_ = primitiveType;
	}

    public SmiType getType() {
		return type_;
	}

    public void setType(SmiType type) {
		type_ = type;
	}
}
