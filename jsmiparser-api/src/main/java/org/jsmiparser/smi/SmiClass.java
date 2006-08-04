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

import java.util.List;
import java.util.Set;

public interface SmiClass {

    String getId();

    String getCodeId();

    List<? extends SmiClass> getParentClasses();

    List<? extends SmiClass> getChildClasses();

    SmiAttribute findAttribute(String id);

    List<? extends SmiAttribute> getAttributes();

    /**
     * @return All attributes, including the ones from the (indirect) parent classes.
     */
    Set<? extends SmiAttribute> getAllAttributes();

    boolean isRowClass();

    boolean isScalarsClass();

}
