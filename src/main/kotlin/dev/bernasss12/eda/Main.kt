package main.kotlin

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val directoryFile = Path("directory.txt")
    val findFile = Path("find.txt")

    val directory = directoryFile.readLines().map(PhoneBookEntry.Companion::fromEntry)
    val find = findFile.readLines().map(PhoneBookEntry.Companion::fromName)

    println("Start searching (linear search)...")
    val (linearCount, linearTime) = timed {
        find.count { entry ->
            directory.linearSearch(entry) != -1
        }
    }
    println("Found $linearCount / ${find.size} entries. Time taken: ${linearTime.toDurationString()}\n")

    println("Start searching (bubble sort + jump search)...")
    val (bubbleSort, bubbleTime) = timed {
        directory.quickSort()
    }
    val (jumpCount, jumpTime) = timed {
        find.count { entry ->
            bubbleSort.jumpSearch(entry) != -1
        }
    }
    println("Found $jumpCount / ${find.size} entries. Time taken: ${(bubbleTime + jumpTime).toDurationString()}")
    println("Sorting time - STOPPED, moved to linear search: ${bubbleTime.toDurationString()}")
    println("Searching time: ${jumpTime.toDurationString()}\n")

    println("Start searching (quick sort + binary search)...")
    val (quickSort, sortingTime) = timed {
        directory.quickSort()
    }
    val (binaryCount, binaryTime) = timed {
        find.count { entry ->
            quickSort.binarySearch(entry) != -1
        }
    }
    println("Found $binaryCount / ${find.size} entries. Time taken: ${(sortingTime + binaryTime).toDurationString()}")
    println("Sorting time: ${sortingTime.toDurationString()}")
    println("Searching time: ${binaryTime.toDurationString()}\n")

    println("Start searching (hash table)...")
    val (hashMap, hashTime) = timed {
        directory.associateBy { it.name }
    }
    val (hashCount, findTime) = timed {
        find.count { entry ->
            hashMap[entry.name] != null
        }
    }
    println("Found $hashCount / ${find.size} entries. Time taken: ${(hashTime + findTime).toDurationString()}")
    println("Creating time: ${hashTime.toDurationString()}")
    println("Searching time: ${findTime.toDurationString()}\n")
}

data class PhoneBookEntry(val number: Int, val name: String) : Comparable<PhoneBookEntry> {
    companion object {
        fun fromEntry(string: String): PhoneBookEntry {
            val (number, name) = string.split(" ", limit = 2)
            return PhoneBookEntry(number.toInt(), name)
        }

        fun fromName(name: String): PhoneBookEntry {
            return PhoneBookEntry(-1, name)
        }
    }

    override fun compareTo(other: PhoneBookEntry): Int {
        return name.compareTo(other.name)
    }
}

fun <T> timed(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = block.invoke()
    val end = System.currentTimeMillis()
    val difference = end - start
    return result to difference
}

fun Long.toDurationString(): String = toDuration(DurationUnit.MILLISECONDS).useComponents { days, hours, minutes, seconds, milliseconds ->
    val builder = StringBuilder()
    builder.append("$minutes min. ")
    builder.append("$seconds sec. ")
    builder.append("$milliseconds ms.")
    return@useComponents builder.toString()
}

fun <T> Duration.useComponents(block: (days: Int, hours: Int, minutes: Int, seconds: Int, milliseconds: Int) -> T): T {
    val days = inWholeDays.toInt()
    val hours = (inWholeHours - inWholeDays * 24).toInt()
    val minutes = (inWholeMinutes - inWholeHours * 60).toInt()
    val seconds = (inWholeSeconds - inWholeMinutes * 60).toInt()
    val milliseconds = (inWholeMilliseconds - inWholeSeconds * 1000).toInt()
    return block(days, hours, minutes, seconds, milliseconds)
}