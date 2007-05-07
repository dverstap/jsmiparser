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

public abstract class AbstractSMIv1SymbolDefiner extends AbstractSymbolDefiner {
    protected AbstractSMIv1SymbolDefiner(String moduleId) {
        super(moduleId);
    }

    @Override
    public void defineSymbols() {
        super.defineSymbols();

        addInternetOid();
        addDirectoryOid();
        addMgmtOid();
        addExperimentalOid();
        addPrivateOid();
        addEnterprisesOid();

        addObjectTypeMacro();

        addObjectNameType();

        addObjectSyntaxType();
        addSimpleSyntaxType();
        addApplicationSyntaxType();

        addNetworkAddressType();

        addIpAddressType();
        addCounterType();
        addGaugeType();
        addTimeTicksType();
        addOpaqueType();
    }
}
