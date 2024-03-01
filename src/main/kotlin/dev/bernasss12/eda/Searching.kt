@file:OptIn(ExperimentalStdlibApi::class)

package dev.bernasss12.eda

import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Performs a linear search on the list to find the first occurrence of the specified element.
 *
 * @param element The element to search for.
 * @param comparator The comparator used to compare elements. By default, it uses natural ordering.
 * @return The index of the element if found, otherwise -1.
 */
fun <Element : Comparable<Element>> List<Element>.linearSearch(
    element: Element,
    comparator: Comparator<Element> = compareBy { it }
): Int {
    indices.forEach { index ->
        val current = get(index)
        if (comparator.compare(current, element) == 0) {
            return index
        }
    }
    return -1
}

/**
 * Performs jump search algorithm on the given list to find the index of the specified element.
 *
 * @param element The element to search for.
 * @param comparator The comparator used to compare the elements in the list. By default, it uses the natural order of the elements.
 * @return The index of the element if found, otherwise -1.
 */
fun <Element : Comparable<Element>> List<Element>.jumpSearch(
    element: Element,
    comparator: Comparator<Element> = compareBy { it }
): Int {
    // Check if the element is smaller than the first element.
    // If so, it is guaranteed to not be on the list.
    if (comparator.compare(first(), element) > 0) return -1
    val stepSize = floor(sqrt(size.toDouble())).toInt()
    val indexedBlocks = indices.chunked(stepSize)
    for (block in indexedBlocks) {
        // Check if the element is smaller than the last element in the block,
        // otherwise it could only be on one of the following blocks.
        if (comparator.compare(get(block.last()), element) >= 0) {
            for (index in block.last() downTo block.first()) {
                if (comparator.compare(get(index), element) == 0) {
                    return index
                }
            }
            // If it is smaller than the last element on the block and bigger
            // than the first element on the block it either was found here or
            // it isn't on the list at all.
            return -1
        }
    }
    // If the element is not smaller than any last block element,
    // it can only mean it is bigger than the last list element,
    // in that case it cannot be in the list.
    return -1
}

fun <Element : Comparable<Element>> List<Element>.binarySearch(
    element: Element,
    comparator: Comparator<Element> = compareBy { it }
): Int {
    // Define the range of indices to check. We start with all of them.
    var range = indices

    // Loop until the range only has 1 element
    while (range.first <= range.last) {
        val middle = get(range.middle)
        // Check if the middle element in the range matches the target.
        if (comparator.compare(middle, element) == 0) {
            return range.middle
        }

        range = if (comparator.compare(middle, element) > 0) {
            // If the target is smaller than the middle element:
            // Reassign the range to correspond to the lower half of the original range, excluding the middle.
            range.first ..< range.middle
        } else {
            // If the target is bigger than the middle element:
            // Reassign the range to correspond to the upper half of the original range, excluding the middle.
            range.middle + 1 .. range.last
        }
    }

    // If nothing matched until now, return -1
    return -1
}

val IntRange.middle: Int
    get() = start + (endInclusive - start) / 2