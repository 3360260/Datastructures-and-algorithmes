package DataStructures;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ArrayListDS<K extends Comparable<K>, V> implements DataStructure<K, V>
{
    private final ArrayList<Entry<K, V>> table;

    public ArrayListDS()
    {
        table = new ArrayList<>();
    }

    public void put(K key, V value)
    {
        table.add(new Entry<>(key, value));
    }

    public V get(K key)
    {
        for (Entry<K, V> entry : table)
        {
            if (entry.getKey().equals(key))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    public void remove(K key)
    {
        Iterator<Entry<K, V>> iterator = table.iterator();;
        while (iterator.hasNext())
        {
            Entry<K, V> entry = iterator.next();
            if (entry.getKey().equals(key))
            {
                iterator.remove();
                break;
            }
        }
    }

    public List<V> values()
    {
        List<V> values = new ArrayList<>();
        for (Entry<K, V> entry : table)
        {
            values.add(entry.getValue());
        }
        return values;
    }

    public void quickSort(Comparator<V> comparator)
    {
        quickSort(0, table.size() - 1, comparator);
    }

    private void quickSort(int left, int right, Comparator<V> comparator)
    {
        if (left < right)
        {
            int partIndex = partition(left, right, comparator);
            quickSort(left, partIndex - 1, comparator);
            quickSort(partIndex + 1, right, comparator);
        }
    }

    private int partition(int left, int right, Comparator<V> comparator)
    {
        Entry<K, V> pivot = table.get(right);
        int x = left - 1;

        for (int y = left; y < right; y++)
        {
            if (comparator.compare(table.get(y).getValue(), pivot.getValue()) < 0)
            {
                x++;
                swap(x,y);
            }
        }
        swap(x + 1, right);
        return x + 1;
    }

    private void swap(int x, int y)
    {
        Entry<K, V> temp = table.get(x);
        table.set(x, table.get(y));
        table.set(y, temp);
    }


    private static class Entry<K, V>
    {
        private final K key;
        private final V value;

        public Entry(K key, V Value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey()
        {
            return key;
        }

        public V getValue()
        {
            return value;
        }
    }
}
