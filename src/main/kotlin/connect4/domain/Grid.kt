package connect4.domain

import java.lang.RuntimeException


interface Grid {
    /**
     * Returns true if all grid's columns are empty
     */
    fun isEmpty(): Boolean

    fun getDiskAt(columnIndex: ColumnIndex, rowIndex: RowIndex): Color?
    fun size(): Int
    fun isFull(): Boolean
}

class GridImpl : Grid {



    // a grid is made of an array of Columns
    private val columns = List(GridWidth) { Column() }


    /**
     * Returns true if all grid's columns are empty
     */
    override fun isEmpty(): Boolean {
        return columns.all{ it.isEmpty() }
    }

    fun insertDisk(columnIndex: ColumnIndex, color: Color) {
        columns[columnIndex.ordinal].insertDisk(color)
    }

    override fun getDiskAt(columnIndex: ColumnIndex, rowIndex: RowIndex): Color? {
        return columns[columnIndex.ordinal].getDiskAt(rowIndex)
    }

    override fun size(): Int {
        return columns.sumBy { it.height() }
    }

    override fun isFull(): Boolean {
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
fun gridOf(block : GridBuilder.() -> Unit) : GridImpl {
    val gridBuilder = GridBuilder()
    gridBuilder.apply(block)
    return gridBuilder.build()
}


/**
 * Stores grid rows definition (through 'r' function) and build a grid in reverse order
 */
class GridBuilder {
    val rows = arrayListOf<Array<Color?>>()

    /**
     * Defines a grid row from a piped string . set "R" for a 'RED' color, "Y" for a 'YELLOW' dist
     * string can be like : "   | R | R | Y | R | Y | Y |   |   "
     */
    fun r(rowString : String) : GridBuilder {
        val diskList = rowString.trim().split("|")
        if (diskList.size != 9) {
            throw RuntimeException("please provide a row line like \"   | R |   |   |   |   |   | Y |   \"")
        }
        val row : Array<Color?> = diskList.subList(1,8).map{ stringToDisk(it) }.toTypedArray()
        rows.add(row)
        return this
    }



    fun build() : GridImpl {
        val grid = GridImpl()
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

    private fun stringToDisk(s: String): Color? {
        return when(s.trim()) {
            "R" -> Color.RED
            "Y" -> Color.YELLOW
            else -> null
        }
    }
}



