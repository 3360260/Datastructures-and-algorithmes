import DataStructures.*;

public class DatasetProcessor<K extends Comparable<K>, V>
{
    private final TreeMapDS<K, V> treeMapTable;
    private final ArrayListDS<K, V> arrayListTable;

    public DatasetProcessor(TreeMapDS<K, V> treeMapTable, ArrayListDS<K, V> arrayListTable)
    {
        this.treeMapTable = treeMapTable;
        this.arrayListTable = arrayListTable;
    }
}
