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
package org.jsmiparser.util.pair;

public class Pair<T1, T2> {

    private T1 m_first;
    private T2 m_second;


    public Pair(T1 first, T2 second) {
        m_first = first;
        m_second = second;
    }

    public T1 getFirst() {
        return m_first;
    }

    public void setFirst(T1 first) {
        m_first = first;
    }

    public T2 getSecond() {
        return m_second;
    }

    public void setSecond(T2 second) {
        m_second = second;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (m_first != null ? !m_first.equals(pair.m_first) : pair.m_first != null) return false;
        if (m_second != null ? !m_second.equals(pair.m_second) : pair.m_second != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (m_first != null ? m_first.hashCode() : 0);
        result = 31 * result + (m_second != null ? m_second.hashCode() : 0);
        return result;
    }
}
