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
package org.jsmiparser.phase.xref;

/**
 * This SymbolDefiner doesn't define the entire SNMPv2-TC module:
 * only the TEXTUAL-CONVENTION macro.
 */
public class SNMPv2_TCSymbolDefiner extends AbstractSymbolDefiner {

    public SNMPv2_TCSymbolDefiner(String moduleId) {
        super(moduleId);
    }


    @Override
    protected void defineSymbols() {
        super.defineSymbols();

        defineTextualConventionMacro();

    }

    private void defineTextualConventionMacro() {
        addMacro("TEXTUAL-CONVENTION");
    }
}
