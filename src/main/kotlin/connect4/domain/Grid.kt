package connect4.domain

import java.lang.RuntimeException


class Grid {



    // a grid is made of an array of Columns
    private val columns = List(GridWidth) { Column() }


    /**
     * Returns true if all grid's columns are empty
     */
    fun isEmpty(): Boolean {
        return columns.all{ it.isEmpty() }
    }

    fun insertDisk(columnIndex: ColumnIndex, disk: Disk) {
        columns[columnIndex.ordinal].insertDisk(disk)
    }

    fun getDiskAt(columnIndex: ColumnIndex, rowIndex: RowIndex): Disk? {
        return columns[columnIndex.ordinal].getDiskAt(rowIndex)
    }

    fun size(): Int {
        return columns.sumBy { it.height() }
    }

    fun isFull(): Boolean {
        return columns.all { it.isFull() }
    }

}






/**
 * DSL for GridBuilder. define a row with the 'r' function.
 * Usage :
 *      val grid = gridOf {
 *
 *           r("R", " ", " ", " ", " ", "Y", " ")
 *           r("R", " ", " ", "Y", " ", "Y", " ")
 *           r("R", " ", " ", "Y", " ", "Y", " ")
 *
 *      }
 *
 */
fun gridOf(block : GridBuilder.() -> Unit) : Grid {
    val gridBuilder = GridBuilder()
    gridBuilder.apply(block)
    return gridBuilder.build()
}


/**
 * Stores grid rows definition (through 'r' function) and build a grid in reverse order
 */
class GridBuilder {
    val rows = arrayListOf<Array<Disk?>>()

    /**
     * Defines a grid row from a piped string . set "R" for a 'RED' disk, "Y" for a 'YELLOW' dist
     * string can be like : "   | R | R | Y | R | Y | Y |   |   "
     */
    fun r(rowString : String) : GridBuilder {
        val diskList = rowString.trim().split("|")
        if (diskList.size != 9) {
            throw RuntimeException("please provide a row line like \"   | R |   |   |   |   |   | Y |   \"")
        }
        val row : Array<Disk?> = diskList.subList(1,8).map{ stringToDisk(it) }.toTypedArray()
        rows.add(row)
        return this
    }



    fun build() : Grid {
        val grid = Grid()
        rows.reversed().forEach {
            for ((index, value) in it.withIndex()) {
                if (value == null) {
                    continue
                }
                val columnIndex = ColumnIndex.values()[index]
                grid.insertDisk(columnIndex, value)
            }
        }
        return grid
    }

    private fun stringToDisk(s: String): Disk? {
        return when(s.trim()) {
            "R" -> Disk.RED
            "Y" -> Disk.YELLOW
            else -> null
        }
    }
}



