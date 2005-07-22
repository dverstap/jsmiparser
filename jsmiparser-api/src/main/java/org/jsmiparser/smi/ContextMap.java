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

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContextMap<K, V> implements Map<K, V> {
	private static final Logger log = Logger.getLogger(ContextMap.class);
	
	Map<K, V> impl_ = new HashMap<K, V>();
	Map<K, V> parentMap_;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ContextMap(Map<K, V> parentMap)
	{
		//log.debug("ContextMap constructor");
		parentMap_ = parentMap;
	}

	public void clear() {
		impl_.clear();
	}

	public boolean containsKey(Object key) {
		return impl_.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return impl_.containsValue(value);
	}

	public Set<Entry<K, V>> entrySet() {
		return impl_.entrySet();
	}

	public boolean equals(Object o) {
		return impl_.equals(o);
	}

	public V get(Object key) {
		return impl_.get(key);
	}

	public int hashCode() {
		return impl_.hashCode();
	}

	public boolean isEmpty() {
		return impl_.isEmpty();
	}

	public Set<K> keySet() {
		return impl_.keySet();
	}

	public V put(K key, V value) {
		//log.debug("insert");
		parentMap_.put(key, value);
		return impl_.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		impl_.putAll(t);
	}

	public V remove(Object key) {
		parentMap_.remove(key);
		return impl_.remove(key);
	}

	public int size() {
		return impl_.size();
	}

	public Collection<V> values() {
		return impl_.values();
	}


}
