package dev.bernasss12.eda

import kotlin.test.Test
import kotlin.test.assertEquals

class LinkedListTest {

    @Test
    fun testAdd() {
        val list = LinkedList<Int>()
        assert(list.add(5))
    }

    @Test
    fun `test add remove size clear contains`() {
        val list = LinkedList<Int>()
        repeat(5) { list.add(5) }
        assertEquals(5, list.size)
        repeat(3) { list.remove(5) }
        assertEquals(2, list.size)
        repeat(5) { list.add(it * 5) }
        assertEquals(7, list.size)
        list.add(48)
        assert(list.contains(48))
    }

    @Test
    fun `test addAll containsAll removeAll retainAll`() {
        val list1 = listOf(2, 5, 6, 7, 1, 6, 6, 1, 21, 4, 51, 5)
        val list2 = listOf(123, 5123, 5123, 5124, 5156, 6514)
        val list = LinkedList<Int>()

        list.addAll(list1)
        list.addAll(list2)
        assert(list.containsAll(list1))

        assert(list.containsAll(list2))
        list.removeAll(list2)
        assert(!list.containsAll(list2))

        list.retainAll(list1)
    }

    @Test
    fun get() {
    }

    @Test
    fun indexOf() {
    }

    @Test
    fun lastIndexOf() {
    }

    @Test
    fun isEmpty() {
    }

    @Test
    fun subList() {
    }

    @Test
    fun containsAll() {
    }

    @Test
    fun iterator() {
    }

    @Test
    fun listIterator() {
    }

    @Test
    fun testListIterator() {
    }

    @Test
    fun removeAt() {
    }

    @Test
    fun set() {
    }

    @Test
    fun retainAll() {
    }

    @Test
    fun removeAll() {
        val original = LinkedList(listOf(1, 2, 3, 4, 5, 5, 6, 7, 7, 7, 8, 8, 9, 9, 9, 10))
    }

    @Test
    fun remove() {
    }

}