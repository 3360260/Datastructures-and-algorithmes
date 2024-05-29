import DataStructures.ArrayListDS;
import DataStructures.DataStructure;
import DataStructures.LinkedListDS;
import DataStructures.TreeMapDS;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DatasetProcessor<K extends Comparable<K>, V> {
    private final LinkedListDS<K, V> linkedListTable;
    private final ArrayListDS<K, V> arrayListTable;
    private final TreeMapDS<K, V> treeMapTable;

    public DatasetProcessor(LinkedListDS<K, V> linkedListTable, ArrayListDS<K, V> arrayListTable, TreeMapDS<K, V> treeMapTable) {
        this.linkedListTable = linkedListTable;
        this.arrayListTable = arrayListTable;
        this.treeMapTable = treeMapTable;
    }

    public List<V> sortItemsByNaturalOrder(int dataStructureIndex) {
        Iterable<V> items = getSelectedDataStructure(dataStructureIndex).values();
        return StreamSupport.stream(items.spliterator(), false)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<V> sortItems(Comparator<V> comparator, int dataStructureIndex) {
        Iterable<V> items = getSelectedDataStructure(dataStructureIndex).values();
        return StreamSupport.stream(items.spliterator(), false)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Optional<V> searchItemByCriteria(Predicate<V> criteria, int dataStructureIndex) {
        Iterable<V> items = getSelectedDataStructure(dataStructureIndex).values();
        return StreamSupport.stream(items.spliterator(), false)
                .filter(criteria)
                .findFirst();
    }

    public List<V> searchItemsByFieldValue(Function<V, String> fieldExtractor, String value, int dataStructureIndex) {
        Iterable<V> items = getSelectedDataStructure(dataStructureIndex).values();
        return StreamSupport.stream(items.spliterator(), false)
                .filter(item -> fieldExtractor.apply(item).equals(value))
                .collect(Collectors.toList());
    }

    private DataStructure<K, V> getSelectedDataStructure(int dataStructureIndex) {
        switch (dataStructureIndex) {
            case 0:
                return linkedListTable;
            case 1:
                return arrayListTable;
            case 2:
                return treeMapTable;
            default:
                throw new IllegalArgumentException("Invalid data structure index");
        }
    }
}
