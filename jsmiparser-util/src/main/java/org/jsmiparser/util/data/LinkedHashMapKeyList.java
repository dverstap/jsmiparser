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
package org.jsmiparser.util.data;

import java.util.*;

public class LinkedHashMapKeyList<Key, Value> implements List<Key> {

    private LinkedHashMap<Key, Value> m_rep;

    public LinkedHashMapKeyList(LinkedHashMap<Key, Value> rep) {
        m_rep = rep;
    }


    public int size() {
        return m_rep.size();
    }

    public boolean isEmpty() {
        return m_rep.isEmpty();
    }

    public boolean contains(Object o) {
        return m_rep.keySet().contains(o);
    }

    public Iterator<Key> iterator() {
        return m_rep.keySet().iterator();
    }

    public Object[] toArray() {
        return m_rep.keySet().toArray();
    }

    public <T>T[] toArray(T[] a) {
        return m_rep.keySet().toArray(a);
    }

    public boolean add(Key o) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        return m_rep.keySet().containsAll(c);
    }

    public boolean addAll(Collection<? extends Key> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends Key> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public Key get(int index) {
        int i = 0;
        for (Key key : m_rep.keySet()) {
            if (i == index) {
                return key;
            }
            i++;
        }
        return null;
    }

    public Key set(int index, Key element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, Key element) {
        throw new UnsupportedOperationException();
    }

    public Key remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        int i = 0;
        for (Key key : m_rep.keySet()) {
            if (key.equals(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int result = -1;
        int i = 0;
        for (Key key : m_rep.keySet()) {
            if (key.equals(o)) {
                result = i;
            }
            i++;
        }
        return result;
    }

    public ListIterator<Key> listIterator() {
        throw new UnsupportedOperationException(); // TODO
    }

    public ListIterator<Key> listIterator(int index) {
        throw new UnsupportedOperationException(); // TODO
    }

    public List<Key> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException(); // TODO
    }
}
