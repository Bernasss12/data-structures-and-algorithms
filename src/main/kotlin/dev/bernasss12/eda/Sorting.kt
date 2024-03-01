package main.kotlin

fun <Element : Comparable<Element>> List<Element>.bubbleSort(
    comparator: Comparator<Element> = compareBy { it }
): List<Element> {
    val list = this.toMutableList()
    for (iteration in 0 until list.lastIndex) {
        for (index in 0 until list.lastIndex - iteration) {
            if (comparator.compare(list[index], list[index + 1]) > 0) {
                val temp = list[index]
                list[index] = list[index + 1]
                list[index + 1] = temp
            }
        }
    }
    return list.toList()
}

fun <Element : Comparable<Element>> List<Element>.quickSort(
    comparator: Comparator<Element> = compareBy { it }
): List<Element> {
    if (size <= 1) return this
    val pivot = this[size / 2]
    val equal = this.filter { it == pivot }
    val less = this.filter { comparator.compare(it, pivot) < 0 }.quickSort(comparator)
    val greater = this.filter { comparator.compare(it, pivot) > 0 }.quickSort(comparator)
    return less + equal + greater
}