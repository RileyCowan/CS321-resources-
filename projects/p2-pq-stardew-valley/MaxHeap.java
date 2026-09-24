/**
 * Array-based max heap of Task objects.
 *
 * @author Riley Cowan
 */
public class MaxHeap {
    protected Task[] heap;
    protected int heapSize;

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Creates an empty heap.
     */
    public MaxHeap() {
        heap = new Task[DEFAULT_CAPACITY];
        heapSize = 0;
    }

    /**
     * Creates a heap from an array of tasks.
     *
     * @param tasks tasks to place in the heap
     */
    public MaxHeap(Task[] tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Tasks cannot be null.");
        }
        heap = new Task[Math.max(DEFAULT_CAPACITY, tasks.length)];
        heapSize = tasks.length;
        System.arraycopy(tasks, 0, heap, 0, tasks.length);
        buildMaxHeap();
    }

    private void heapify(int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heapSize && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }
        if (right < heapSize && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }

        if (largest != index) {
            swap(index, largest);
            heapify(largest);
        }
    }

    private void buildMaxHeap() {
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    private void grow() {
        Task[] larger = new Task[heap.length * 2];
        System.arraycopy(heap, 0, larger, 0, heapSize);
        heap = larger;
    }

    /**
     * Returns the maximum task without removing it.
     *
     * @return maximum task
     */
    public Task max() throws HeapException {
        if (isEmpty()) {
            throw new HeapException("Heap is empty.");
        }
        return heap[0];
    }

    /**
     * Removes and returns the maximum task.
     *
     * @return maximum task
     */
    public Task extractMax() throws HeapException {
        if (isEmpty()) {
            throw new HeapException("Heap is empty.");
        }

        Task maximum = heap[0];
        heap[0] = heap[heapSize - 1];
        heap[heapSize - 1] = null;
        heapSize--;

        if (heapSize > 0) {
            heapify(0);
        }
        return maximum;
    }

    /**
     * Inserts a task into the heap.
     *
     * @param task task to insert
     */
    public void insert(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        if (heapSize == heap.length) {
            grow();
        }

        int index = heapSize;
        heap[heapSize++] = task;

        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) <= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    /**
     * Increases the key at an index.
     *
     * @param index heap index
     * @param task replacement task with a greater or equal key
     */
    public void increaseKey(int index, Task task) {
        if (index < 0 || index >= heapSize) {
            throw new IndexOutOfBoundsException("Invalid heap index.");
        }
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        if (task.compareTo(heap[index]) < 0) {
            throw new IllegalArgumentException("New task has a smaller key.");
        }

        heap[index] = task;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) <= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    /**
     * Returns whether the heap has no elements.
     *
     * @return true when empty
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * Returns the number of tasks in the heap.
     *
     * @return heap size
     */
    public int size() {
        return heapSize;
    }

    private void swap(int first, int second) {
        Task temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < heapSize; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(heap[i]);
        }
        result.append("]");
        return result.toString();
    }
}
