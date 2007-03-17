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

class Util {

    public static <T> T find(Class<T> cl, String keyword, boolean mandatory) {
        for (T enumConstant : cl.getEnumConstants()) {
            if (enumConstant.toString().equals(keyword)) {
                return enumConstant;
            }
        }
        if (mandatory) {
            throw new IllegalArgumentException(keyword + " is not a valid " + cl.getSimpleName());
        } else {
            return null;
        }
    }
}
