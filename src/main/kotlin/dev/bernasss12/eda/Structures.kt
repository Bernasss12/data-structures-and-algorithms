package dev.bernasss12.eda

class LinkedList<T>() : MutableList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    constructor(size: Int, generator: (Int) -> T): this() {
        repeat(size) { index ->
            add(generator(index))
        }
    }

    constructor(collection: Collection<T>): this() {
        addAll(collection)
    }

    data class Node<T>(val value: T, var previous: Node<T>? = null, var next: Node<T>? = null)

    override val size: Int
        get() {
            var current = head
            var count = 0
            while (current != null) {
                count++
                current = current.next
            }
            return count
        }

    override fun clear() {
        // Iterate through every node and clear every reference to allow GC
        var current = head
        while (current != null) {
            val next = current.next
            current.previous = null
            current.next = null
            current = next
        }
        head = null
        tail = null
    }

    override fun addAll(elements: Collection<T>): Boolean = elements.all(this::add)

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        elements.reversed().forEach { add(index, it) }
        return true
    }

    override fun add(index: Int, element: T) {
        // Create new clean node.
        val new = Node(element, null, null)

        // Get the node currently in the desired index.
        val old = getNode(index)

        // Take the old's place.
        new.next = old
        new.previous = old.previous
        new.previous?.next = new

        // If old was the first element replace the head
        if (old == head) {
            head = new
        }
    }

    override fun add(element: T): Boolean {
        // Create new clean node.
        val new = Node(element, null, null)

        // Check if the list is empty
        if (head == null) {
            head = new
            tail = new
            return true
        }

        // Set the current tail's next to the new element
        tail?.next = new
        // Set the new's previous node to the current tail
        new.previous = tail
        // Set the current tail to the new node
        tail = new
        return true
    }

    private fun getNode(index: Int): Node<T> {
        var currentNode = head
        repeat(index) {
            currentNode = currentNode?.next ?: throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        return currentNode ?: throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private fun removeNode(node: Node<T>) {
        node.previous?.next = node.next
        node.next?.previous = node.previous
    }

    override fun get(index: Int): T = getNode(index).value

    override fun indexOf(element: T): Int {
        for (index in indices) {
            if (get(index) == element) {
                return index
            }
        }
        return -1
    }

    override fun lastIndexOf(element: T): Int {
        for (index in indices.reversed()) {
            if (get(index) == element) {
                return index
            }
        }
        return -1
    }

    override fun isEmpty(): Boolean = head == null || tail == null

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        val sublist = LinkedList<T>()
        for (i in fromIndex until toIndex) {
            sublist.add(get(i))
        }
        return sublist
    }

    override fun containsAll(elements: Collection<T>): Boolean = elements.all { contains(it) }

    override fun contains(element: T): Boolean = indexOf(element) != -1

    override fun iterator(): MutableIterator<T> = object : MutableIterator<T> {
        var current: Node<T>? = null
        var next = false

        override fun hasNext(): Boolean = current?.next != null

        override fun next(): T {
            // Check if there is a next otherwise throw NoSuchElementException
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            // Check what the current will need to be depending on what it was
            current = if (current == null) {
                // If current was null it means the iterator was just started, since it shouldn't even be set to null again.
                head
            } else {
                // Otherwise the next node should be set.
                current!!.next
            }
            // Flag that next has been called.
            next = true
            return current!!.value
        }

        override fun remove() {
            // Make sure next has been called before this.
            check(next) { "next() must be called before remove()" }
            val remove = current
            checkNotNull(remove) { "this should never happen" }
            current = remove.previous
            removeNode(remove)
            // Unset flag so next will need to be called before removed is called again.
            next = false
        }
    }

    override fun listIterator(): MutableListIterator<T> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<T> = object : MutableListIterator<T> {
        var current = getNode(index)
        var currentIndex = index
        override fun add(element: T) {
            TODO("Not yet implemented")
        }

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

        override fun remove() {
            TODO("Not yet implemented")
        }

        override fun set(element: T) {
            TODO("Not yet implemented")
        }
    }

    override fun remove(element: T): Boolean {
        // If tail is null list should be empty
        if (tail == null) return false

        // Get the current tail to clean up later
        val old = tail ?: throw IllegalStateException("Tail shouldn't be null at this point.")

        // Set new tail as previous node of old tail and set next as null.
        tail = old.previous
        tail?.next = null

        // Remove references to other nodes on old tail.
        old.previous = null
        old.next = null

        return true
    }

    override fun set(index: Int, element: T): T {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAt(index: Int): T {
        // Get the node to be removed
        val remove = getNode(index)

        // Remove itself from the next and previous nodes
        remove.next?.previous = remove.previous
        remove.previous?.next = remove.next

        return remove.value
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        // Keep track if any elements were removed.
        var removed = false
        for (element in listIterator()) {
            if (element in elements) {
                remove(element)
                removed = true
            }
        }
        return removed
    }
}

class HashTable() {

}