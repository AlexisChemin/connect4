package connect4.domain

import java.util.*


/**
 * Created by vagrant on 3/12/19.
 */


enum class AlignmentDirection {
    Horizontal,
    Vertical,
    UpLeftDownRight,
    DownLeftUpRight
}


//typealias GridPosition = Pair<Int, Int>

typealias GridPosition = Pair<ColumnIndex, RowIndex>

open class Alignment(val start: GridPosition, val direction: AlignmentDirection, var size: Int) {

    fun increase(): Alignment {
        size += 1
        return this
    }
}


class Alignments() {

    val alignmentByDirection: MutableMap<AlignmentDirection, Alignment> = HashMap()

//    constructor(start: GridPosition, size : Int) :  this() {
//        set( Alignment(start, AlignmentDirection.Horizontal, size) )
//        set( Alignment(start, AlignmentDirection.Vertical, size) )
//        set( Alignment(start, AlignmentDirection.UpLeftDownRight, size) )
//        set( Alignment(start, AlignmentDirection.DownLeftUpRight, size) )
//    }

    fun get(direction: AlignmentDirection): Alignment? {
        return alignmentByDirection[direction]
    }

    fun set(alignment: Alignment) {
        alignmentByDirection[alignment.direction] = alignment
    }

}


//fun emptyAlignments(start : GridPosition) : Alignments {
//    return Alignments(start, 0)
//}

typealias AlignementsByDisk = Map<Disk, Alignments>

class GridAlignments(grid: Grid) {

    private var alignmentsGrid: Map<ColumnIndex, Map<RowIndex, AlignementsByDisk>> = initAlignmentMap()

    val result: Set<Alignment>

    init {
//        alignmentsGrid = initAlignmentMap()
        result = compute(grid)
    }

    // what we're about to compute : 2-dimensions array of mapping (Disk -> Alignment)


    private fun initAlignmentMap(): Map<ColumnIndex, Map<RowIndex, AlignementsByDisk>> {
        val result = HashMap<ColumnIndex, Map<RowIndex, Map<Disk, Alignments>>>()
        for (columnIndex in ColumnIndex.values()) {
            val hashMap = HashMap<RowIndex, AlignementsByDisk>()
            result[columnIndex] = hashMap
            for (rowIndex in RowIndex.values()) {
                val position = GridPosition(columnIndex, rowIndex)
                val hashMapRow: AlignementsByDisk = mapOf(Disk.RED to Alignments(), Disk.YELLOW to Alignments())
                hashMap[rowIndex] = hashMapRow
            }
        }
        return result

    }
//    Array(GridWidth){ columnIndex ->


//    private val alignmentsGrid : Array<Array<Map<Disk,Alignments>>> = Array(GridWidth){ columnIndex ->
//            Array(ColumnHeight) {
//                rowIndex ->
//                val start = GridPosition(columnIndex, rowIndex)
////                HashMap() }
//                mapOf(Disk.YELLOW to emptyAlignments(start), Disk.RED to emptyAlignments(start)) }
//        }
////
//    private val alignmentsGrid : Array<Array<Map<Disk,Alignments>>> = Array(GridWidth){
//        Array(ColumnHeight) {}



    fun compute(grid: Grid): Set<Alignment> {

        var all = HashSet<Alignment>()


        for (myColumnIndex in ColumnIndex.values()) {
            for (myRowIndex in RowIndex.values()) {

                val myColor = grid.getDiskAt(myColumnIndex, myRowIndex) ?: continue

                // compute result, from left column and other up/down rows
                val upLeftDownRightAlignment = getAlignment(myColor, myColumnIndex, myRowIndex, AlignmentDirection.UpLeftDownRight).increase()
                val horizontalAlignment = getAlignment(myColor, myColumnIndex, myRowIndex, AlignmentDirection.Horizontal).increase()
                val downRightAlignmentAlignment = getAlignment(myColor, myColumnIndex, myRowIndex, AlignmentDirection.DownLeftUpRight).increase()
                val verticalAlignment = getAlignment(myColor, myColumnIndex, myRowIndex, AlignmentDirection.Vertical).increase()


                // set my result
                val alignments = getAlignments(myColor, myColumnIndex, myRowIndex)
                alignments.set(upLeftDownRightAlignment)
                alignments.set(horizontalAlignment)
                alignments.set(downRightAlignmentAlignment)
                alignments.set(verticalAlignment)

                all.add(upLeftDownRightAlignment)
                all.add(horizontalAlignment)
                all.add(downRightAlignmentAlignment)
                all.add(verticalAlignment)
            }
        }

        return all.filter { it.size > 1 }.toSet()
    }


    /**
     * Retrieve :
     * - the existing alignment relative to this position (columnIndex, rowIndex) corresponding to the direction
     * - a newly alignment for this position+direction
     */
    fun getAlignment(disk: Disk, columnIndex: ColumnIndex, rowIndex: RowIndex, direction: AlignmentDirection): Alignment {
        var otherColumnIndex: ColumnIndex? = columnIndex
        var otherRowIndex: RowIndex? = rowIndex
        when (direction) {
            AlignmentDirection.UpLeftDownRight -> {
                otherColumnIndex = columnIndex.leftward()
                otherRowIndex = rowIndex.uppward()
            }
            AlignmentDirection.Horizontal -> {
                otherColumnIndex = columnIndex.leftward()
                otherRowIndex = rowIndex
            }
            AlignmentDirection.DownLeftUpRight -> {
                otherColumnIndex = columnIndex.leftward()
                otherRowIndex = rowIndex.downward()
            }
            AlignmentDirection.Vertical -> {
                otherColumnIndex = columnIndex
                otherRowIndex = rowIndex.downward()
            }
        }
        if (otherColumnIndex == null || otherRowIndex == null) {
            return emptyAlignment(columnIndex, rowIndex, direction)
        }

        val alignments = getAlignments(disk, otherColumnIndex, otherRowIndex)
        return alignments.get(direction) ?: emptyAlignment(columnIndex, rowIndex, direction)
    }

    private fun getAlignments(disk: Disk, columnIndex: ColumnIndex, rowIndex: RowIndex) =
            alignmentsGrid[columnIndex]!![rowIndex]!![disk]!!
//
//
//        fun getAlignment(disk : Disk, columnIndex : Int, rowIndex : Int, direction : AlignmentDirection) : Alignment {
//        if (columnIndex < 0 || columnIndex >= GridWidth) {
//            return emptyAlignment(columnIndex, rowIndex, direction)
//        }
//        if (rowIndex < 0 || rowIndex >= ColumnHeight) {
//            return emptyAlignment(columnIndex, rowIndex, direction)
//        }
//        val result = alignmentsGrid[columnIndex][rowIndex][disk]
//        return result?.let { it.get(direction) } ?: emptyAlignment(columnIndex, rowIndex, direction)
//    }


    private fun emptyAlignment(columnIndex: ColumnIndex, rowIndex: RowIndex, direction: AlignmentDirection) =
            Alignment(GridPosition(columnIndex, rowIndex), direction, 0)

}