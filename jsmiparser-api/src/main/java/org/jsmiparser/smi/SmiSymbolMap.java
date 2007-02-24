package org.jsmiparser.smi;

import java.util.List;
import java.util.Collection;

/*
* Copyright 2007 Davy Verstappen.
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
public interface SmiSymbolMap<T extends SmiSymbol> extends Iterable<T> {


    /**
     * @param symbolId The required id of the unique symbol.
     * @return The unique symbol with the required id, or null if it is not found.
     * @throws IllegalArgumentException if there is more than one symbol with the required id.
     */
    T find(String symbolId) throws IllegalArgumentException;

    /**
     * @param moduleId The module where you want to look for the symbol, or null if you want to look through the whole mib.
     * @param symbolId The required id of the unique symbol.
     * @return The unique symbol with the required id, or null if it is not found.
     * @throws IllegalArgumentException if the module is not found or if there is more than one symbol with the required id.
     */
    T find(String moduleId, String symbolId) throws IllegalArgumentException;

    /**
     * @param symbolId The required id of the symbols.
     * @return All the SmiSymbol with the required id, or an empty list if none are found.
     */
    List<T> findAll(String symbolId);

    Collection<T> getAll();

    int size();

    boolean isEmpty();
    
}
