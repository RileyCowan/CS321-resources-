import java.util.LinkedList;

public class Cache<K, V extends KeyInterface<K>>
        implements CacheInterface<K, V> {

    private LinkedList<V> cache;
    private int maxSize;

    public Cache(int size) {
        cache = new LinkedList<>();
        maxSize = size;
        references = 0;
        hits = 0;

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Cache with " + size + " entries has been created");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public V get(K key) {
        references++;

        for (V value : cache) {
            if (value.getKey().equals(key)) {
                cache.remove(value);
                cache.addFirst(value);
                hits++;
                return value;
            }
        }

        return null;
    }

    @Override
    public V add(V value) {
         V removed = null;

        if (cache.size() >= maxSize) {
            removed = cache.removeLast();
        }

        cache.addFirst(value);

        return removed;
    }

    @Override
    public V remove(K key) {
        for (V value : cache) {
            if (value.getKey().equals(key)) {
                cache.remove(value);
                return value;
            }
        }

        return null;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public String toString() {
        double hitPercent = 0.0;

        if (references > 0) {
            hitPercent = ((double) hits / references) * 100.0;
        }

        return "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "Cache with " + maxSize + " entries has been created\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "Total number of references:        " + references + "\n"
                + "Total number of cache hits:        " + hits + "\n"
                + String.format("Cache hit percent:                 %.2f%%%n", hitPercent);
    }
}
