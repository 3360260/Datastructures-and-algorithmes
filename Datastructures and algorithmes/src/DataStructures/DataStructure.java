package DataStructures;

public interface DataStructure<K, V>
{
    void put(K key, V Value);
    V get(K key);
    void remove(K key);
    Iterable<V> values();
}
