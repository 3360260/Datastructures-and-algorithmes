package DataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TreeMapDS<K, V> implements DataStructure<K, V>
{
    private final TreeMap<K, V> table;

    public TreeMapDS()
    {
        table = new TreeMap<>();
    }

    public void put(K key, V value)
    {
        table.put(key, value);
    }

    public V get(K key)
    {
        return table.get(key);
    }

    public void remove(K key)
    {
        table.remove(key);
    }

    public List<V> values()
    {
        return new ArrayList<>(table.values());
    }
}
