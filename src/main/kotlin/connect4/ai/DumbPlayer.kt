package connect4.ai

import connect4.domain.*
import connect4.domain.ColumnIndex.*
import java.lang.Exception
import java.util.*


class DumbPlayer : Player {


    val random = Random()

    override fun play(color: Color, grid: Grid): ColumnIndex {

        val freeColumns = grid.freeColumns()
        val nbOfFreeColumn = freeColumns.size
        val columnOrdinal = rand(0, nbOfFreeColumn)
        var columnIndex : ColumnIndex = freeColumns[columnOrdinal]

        println(color.name + " played column " + (columnIndex.ordinal+1) )

        return columnIndex
    }


    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

}