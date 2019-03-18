package connect4.console

import connect4.domain.*
import connect4.domain.ColumnIndex.*
import java.lang.Exception
import java.util.*


class Console  {

    companion object {

        fun showGrid(grid: Grid, alignment: Alignment? = null) {

            println("   -----------------------------")
            for (row in RowIndex.values().reversed()) {
                print("   |")
                for (column in values()) {
                    print(" ")
                    var diskColor = grid.getDiskColorAt(column, row)
                    when (diskColor) {
                        Color.RED -> print("R")
                        Color.YELLOW -> print("Y")
                        else -> print(" ")
                    }
                    print(" |")
                }
                println()
            }
            println("   =============================")
            println("     1   2   3   4   5   6   7")
        }

        fun askColumn(freeColumns: List<ColumnIndex>): ColumnIndex {

            println()
            while (true) {
                println("Choose your column ("+ toString(freeColumns) +") : ")
                try {
                    val input = Scanner(System.`in`)
                    val a = input.nextInt() - 1
                    val choseColumn = ColumnIndex.values()[a]
                    if (choseColumn in freeColumns) {
                        return choseColumn
                    }
                    println("\n\tNot a free column")
                } catch (e: Exception) {
                    println("\n\tBad input")
                }
            }

        }

        private fun toString(freeColumns: List<ColumnIndex>) =
                freeColumns.map { it.ordinal + 1 }.joinToString(",")
    }


}