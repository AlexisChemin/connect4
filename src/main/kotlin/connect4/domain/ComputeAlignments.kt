package connect4.domain

import java.util.*


/**
 * Created by vagrant on 3/12/19.
 */


typealias GridPosition = Pair<ColumnIndex, RowIndex>

enum class AlignmentDirection {
    Horizontal,
    Vertical,
    UpLeftDownRight,
    DownLeftUpRight
}

/**
 * Alignment is what we want to compute. It has a 'start'ing position (a disk), a direction and a size (length)
 */
data class Alignment(val start: GridPosition, val direction: AlignmentDirection, var size: Int) {

    fun increase(): Alignment {
        size += 1
        return this
    }
}

/**
 * Map of Alignments indexed by Direction
 */
class Alignments {

    val alignmentByDirection: MutableMap<AlignmentDirection, Alignment> = HashMap()


    fun get(direction: AlignmentDirection): Alignment? {
        return alignmentByDirection[direction]
    }

    fun set(alignment: Alignment) {
        alignmentByDirection[alignment.direction] = alignment
    }

}


/**
 * All alignments for a given disk
 */
typealias AlignmentsByDisk = Map<Disk, Alignments>



class ComputeAlignments(grid: Grid) {

    // what we're about to compute : 2-dimensions array of mapping (Disk -> Alignments)
    private var alignmentsGrid: Map<ColumnIndex, Map<RowIndex, AlignmentsByDisk>> = initAlignmentMap()

    // result goes here
    val result: Set<Alignment>

    init {
        result = compute(grid)
    }



    /**
     * computation function : fills up the alignmentsGrid with alignments
     * and stores alignement with size greater than 1
     */
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


    /**
     * Shortcut to get the set of alignment corresponding to the given position (columnIndex, rowIndex)
     * and given disk
     */
    private fun getAlignments(disk: Disk, columnIndex: ColumnIndex, rowIndex: RowIndex) =
            alignmentsGrid[columnIndex]!![rowIndex]!![disk]!!

    /**
     * Initialisation of 'alignmentsGrid' that will be computed later
     */
    private fun initAlignmentMap(): Map<ColumnIndex, Map<RowIndex, AlignmentsByDisk>> {
        val result = HashMap<ColumnIndex, Map<RowIndex, Map<Disk, Alignments>>>()
        for (columnIndex in ColumnIndex.values()) {
            val hashMap = HashMap<RowIndex, AlignmentsByDisk>()
            result[columnIndex] = hashMap
            for (rowIndex in RowIndex.values()) {
                val hashMapRow: AlignmentsByDisk = mapOf(Disk.RED to Alignments(), Disk.YELLOW to Alignments())
                hashMap[rowIndex] = hashMapRow
            }
        }
        return result

    }



    private fun emptyAlignment(columnIndex: ColumnIndex, rowIndex: RowIndex, direction: AlignmentDirection) =
            Alignment(GridPosition(columnIndex, rowIndex), direction, 0)

}