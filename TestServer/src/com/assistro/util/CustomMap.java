package com.assistro.util;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomMap<K, V> {
  private int size;
  private int DEFAULT_CAPACITY = 16;
  @SuppressWarnings("unchecked")
  private MapEntry<K, V>[] valuestore = new MapEntry[DEFAULT_CAPACITY];


  public V get(K key) {
    for (int i = 0; i < size; i++) {
      if (valuestore[i] != null) {
        if (valuestore[i].getKey().equals(key)) {
          return valuestore[i].getValue();
        }
      }
    }
    return null;
  }

  public void put(K key, V value) {
    boolean insert = true;
    for (int i = 0; i < size; i++) {
      if (valuestore[i].getKey().equals(key)) {
        valuestore[i].setValue(value);
        insert = false;
      }
    }
    if (insert) {
      ensureCapacity();
      valuestore[size++] = new MapEntry<K, V>(key, value);
    }
  }

  private void ensureCapacity() {
    if (size == valuestore.length) {
      int newSize = valuestore.length * 2;
      valuestore = Arrays.copyOf(valuestore, newSize);
    }
  }

  public int size() {
    return size;
  }

  public void remove(K key) {
    for (int i = 0; i < size; i++) {
      if (valuestore[i].getKey().equals(key)) {
        valuestore[i] = null;
        size--;
        condenseArray(i);
      }
    }
  }

  private void condenseArray(int start) {
    for (int i = start; i < size; i++) {
      valuestore[i] = valuestore[i + 1];
    }
  }
  
  public Set<MapEntry<K,V>> entrySet() {
	    Set<MapEntry<K,V>> set = new HashSet<MapEntry<K,V>>();
	    for (int i = 0; i < size; i++) {
	      set.add(valuestore[i]);
	    }
	    return set;
  }

  public Set<K> keySet() {
    Set<K> set = new HashSet<K>();
    for (int i = 0; i < size; i++) {
      set.add(valuestore[i].getKey());
    }
    return set;
  }
  
  public Set<V> values(){
	  Set<V> set = new HashSet<V>();
	  for (int i = 0; i < size; i++) {
	      set.add(valuestore[i].getValue());
	   }    
	  return set;
  }
} 