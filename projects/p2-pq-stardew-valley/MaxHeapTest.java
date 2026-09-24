import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for MaxHeap.
 *
 * @author Riley Cowan
 */
public class MaxHeapTest {

    private Task task(int priority, int hour) {
        return new Task(priority, hour, Task.TaskType.FISHING, "test");
    }

    @Test
    public void defaultConstructorCreatesEmptyHeap() {
        MaxHeap heap = new MaxHeap();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test
    public void insertIntoEmptyHeap() {
        MaxHeap heap = new MaxHeap();
        Task task = task(3, 2);
        heap.insert(task);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(task, heap.max());
    }

    @Test
    public void maxReturnsHighestPriority() {
        MaxHeap heap = new MaxHeap();
        heap.insert(task(1, 0));
        heap.insert(task(5, 1));
        heap.insert(task(3, 2));
        assertEquals(5, heap.max().getPriority());
    }

    @Test
    public void earlierTaskWinsPriorityTie() {
        MaxHeap heap = new MaxHeap();
        Task newer = task(5, 10);
        Task older = task(5, 2);
        heap.insert(newer);
        heap.insert(older);
        assertEquals(older, heap.max());
    }

    @Test
    public void extractMaxRemovesHighestPriority() throws HeapException {
        MaxHeap heap = new MaxHeap();
        Task low = task(1, 0);
        Task high = task(9, 1);
        heap.insert(low);
        heap.insert(high);

        assertEquals(high, heap.extractMax());
        assertEquals(low, heap.max());
        assertEquals(1, heap.size());
    }

    @Test
    public void extractionEventuallyEmptiesHeap() throws HeapException {
        MaxHeap heap = new MaxHeap();
        heap.insert(task(1, 0));
        heap.insert(task(2, 1));
        heap.insert(task(3, 2));

        heap.extractMax();
        heap.extractMax();
        heap.extractMax();

        assertTrue(heap.isEmpty());
    }

    @Test
    public void emptyMaxThrowsHeapException() {
        MaxHeap heap = new MaxHeap();
        assertThrows(HeapException.class, heap::max);
    }

    @Test
    public void emptyExtractThrowsHeapException() {
        MaxHeap heap = new MaxHeap();
        assertThrows(HeapException.class, heap::extractMax);
    }

    @Test
    public void heapGrowsWhenCapacityIsReached() {
        MaxHeap heap = new MaxHeap();
        for (int i = 0; i < 25; i++) {
            heap.insert(task(i, i));
        }
        assertEquals(25, heap.size());
        assertEquals(24, heap.max().getPriority());
    }

    @Test
    public void arrayConstructorBuildsHeap() throws HeapException {
        Task[] tasks = {
            task(2, 0),
            task(8, 1),
            task(4, 2),
            task(6, 3),
            task(1, 4)
        };

        MaxHeap heap = new MaxHeap(tasks);

        assertEquals(8, heap.max().getPriority());
        assertEquals(8, heap.extractMax().getPriority());
        assertEquals(6, heap.extractMax().getPriority());
        assertEquals(4, heap.extractMax().getPriority());
    }

    @Test
    public void insertMaintainsHeapAfterManyOperations() throws HeapException {
        MaxHeap heap = new MaxHeap();
        int[] priorities = {4, 1, 9, 2, 7, 3, 8, 5, 6};
        for (int i = 0; i < priorities.length; i++) {
            heap.insert(task(priorities[i], i));
        }

        for (int expected = 9; expected >= 1; expected--) {
            assertEquals(expected, heap.extractMax().getPriority());
        }
    }

    @Test
    public void increaseKeyMovesTaskUp() {
        MaxHeap heap = new MaxHeap();
        Task low = task(1, 0);
        Task high = task(5, 1);
        heap.insert(low);
        heap.insert(high);

        Task increased = task(10, 0);
        heap.increaseKey(1, increased);

        assertEquals(increased, heap.max());
    }

    @Test
    public void increaseKeyRejectsSmallerTask() {
        MaxHeap heap = new MaxHeap();
        heap.insert(task(5, 0));

        assertThrows(IllegalArgumentException.class,
                () -> heap.increaseKey(0, task(2, 1)));
    }

    @Test
    public void insertRejectsNull() {
        MaxHeap heap = new MaxHeap();
        assertThrows(IllegalArgumentException.class, () -> heap.insert(null));
    }

    @Test
    public void compareUsesPriorityBeforeHour() {
        Task higher = task(4, 20);
        Task lower = task(3, 0);
        assertTrue(higher.compareTo(lower) > 0);
        assertTrue(lower.compareTo(higher) < 0);
    }

    @Test
    public void taskWaitingTimeCanBeUpdated() {
        Task task = task(0, 0);
        assertEquals(0, task.getWaitingTime());
        task.incrementWaitingTime();
        task.incrementWaitingTime();
        assertEquals(2, task.getWaitingTime());
        task.resetWaitingTime();
        assertEquals(0, task.getWaitingTime());
    }
}
