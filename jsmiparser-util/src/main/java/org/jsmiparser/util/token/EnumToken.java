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
package org.jsmiparser.util.token;

import org.jsmiparser.util.location.Location;

public class EnumToken<E extends Enum> extends GenericToken<E> {

    public EnumToken(Location location, Class<E> enumClass, String value) {
        super(location, determineValue(enumClass.getEnumConstants(), value));
    }

    public EnumToken(Location location, E value) {
        super(location, value);
    }

    private static <E> E determineValue(E[] enumConstants, String value) {
        for (E e : enumConstants) {
            if (e.toString().equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum found for " + value);
    }
}
