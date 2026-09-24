/**
 * Priority queue implemented by extending the MaxHeap.
 *
 * @author Riley Cowan
 */
public class MyPriorityQueue extends MaxHeap implements PriorityQueueInterface {

    /**
     * Creates an empty priority queue.
     */
    public MyPriorityQueue() {
        super();
    }

    /**
     * Adds a task to the priority queue.
     *
     * @param task task to enqueue
     */
    @Override
    public void enqueue(Task task) {
        insert(task);
    }

    /**
     * Removes the highest-priority task.
     *
     * @return highest-priority task, or null if empty
     */
    @Override
    public Task dequeue() {
        if (isEmpty()) {
            return null;
        }
        try {
            return extractMax();
        } catch (HeapException e) {
            return null;
        }
    }

    /**
     * Returns whether the queue is empty.
     *
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Updates waiting times and raises priority when a task has waited
     * long enough.
     *
     * @param timeToIncrementPriority number of hours before increasing priority
     * @param maxPriority maximum allowed priority
     */
    @Override
    public void update(int timeToIncrementPriority, int maxPriority) {
        if (timeToIncrementPriority < 1) {
            throw new IllegalArgumentException("timeToIncrementPriority must be positive.");
        }
        if (maxPriority < 0) {
            throw new IllegalArgumentException("maxPriority cannot be negative.");
        }

        for (int i = 0; i < heapSize; i++) {
            Task task = heap[i];
            task.incrementWaitingTime();

            if (task.getWaitingTime() >= timeToIncrementPriority
                    && task.getPriority() < maxPriority) {
                task.setPriority(task.getPriority() + 1);
                task.resetWaitingTime();
            }
        }
        buildHeapAfterUpdate();
    }

    private void buildHeapAfterUpdate() {
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            heapifyPublic(i);
        }
    }

    private void heapifyPublic(int index) {
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
            Task temp = heap[index];
            heap[index] = heap[largest];
            heap[largest] = temp;
            heapifyPublic(largest);
        }
    }
}
