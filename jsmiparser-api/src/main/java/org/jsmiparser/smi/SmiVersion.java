/*
 * Copyright 2006 Davy Verstappen.
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

import java.util.Set;
import java.util.Collections;
import java.util.EnumSet;

public enum SmiVersion {

    V1,
    V2;

    public static final  Set<SmiVersion> V1_SET = Collections.singleton(V1);
    public static final  Set<SmiVersion> V2_SET = Collections.singleton(V2);
    public static final  Set<SmiVersion> ALL_SET = Collections.unmodifiableSet(EnumSet.allOf(SmiVersion.class));

}
