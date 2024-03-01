package main.kotlin

class LinkedList<T>() : MutableList<T> {
    var head: Node<T>? = null
    var tail: Node<T>? = null

    data class Node<T>(val value: T, var previous: Node<T>?, var next: Node<T>?)

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun clear() {
        head = null
        tail = null
    }

    override fun addAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(index: Int, element: T) {
        TODO("Not yet implemented")
    }

    override fun add(element: T): Boolean {
        TODO("Not yet implemented")
    }

    private fun getNode(index: Int): Node<T> {
        var currentNode = head
        repeat(index) {
            currentNode = currentNode?.next
        }
        currentNode ?: throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    override operator fun get(index: Int): T {

    }

    override fun indexOf(element: T): Int {
        for (current in iterator().withIndex()) {
            if (current == element) {
                return current.index
            }
        }
        return -1
    }

    override fun lastIndexOf(element: T): Int {

    }

    override fun isEmpty(): Boolean = head == null || tail == null

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        val from = getNode(fromIndex)
        val to = getNode(toIndex)
        return LinkedList<T>().apply {
            head = from
            tail = to
        }
    }

    override fun containsAll(elements: Collection<T>): Boolean = elements.all { contains(it) }

    override fun contains(element: T): Boolean = indexOf(element) != -1

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        var current = head

        override fun hasNext(): Boolean = current?.next != null

        override fun next(): T {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            current = current!!.next
            return current!!.value
        }
    }

    override fun listIterator(): ListIterator<T> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<T> = object : ListIterator<T> {
        var current = getNode(index)
        var currentIndex = index

        override fun hasNext(): Boolean = current.next != null

        override fun hasPrevious(): Boolean = current.previous != null

        override fun next(): T {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            current = current.next!!
            currentIndex++
            return current.value
        }

        override fun previous(): T {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            current = current.next!!
            currentIndex--
            return current.value
        }

        override fun nextIndex(): Int = currentIndex + 1

        override fun previousIndex(): Int = currentIndex - 1
    }

    override fun removeAt(index: Int): T {
        TODO("Not yet implemented")
    }

    override fun set(index: Int, element: T): T {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(element: T): Boolean {
        TODO("Not yet implemented")
    }
}

class HashTable() {

}