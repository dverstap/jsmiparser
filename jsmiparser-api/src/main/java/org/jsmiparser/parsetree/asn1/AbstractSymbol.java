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
package org.jsmiparser.parsetree.asn1;

import org.jsmiparser.util.location.Location;


/**
 * @author davy
 */
public abstract class AbstractSymbol implements Symbol {

    protected ASNModule m_module;
    private Location m_location;

    // TODO remove
    protected AbstractSymbol(Context context) {
        super();

        m_module = context.getModule();
        m_location = context.getLocationFactory().create();
    }

    protected AbstractSymbol(ASNModule module, Location location) {
        m_module = module;
        m_location = location;
    }

    public Location getLocation() {
        return m_location;
    }

    public ASNModule getModule() {
        return m_module;
    }

}
