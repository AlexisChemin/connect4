package connect4.domain

import connect4.domain.AlignmentDirection.*
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


        for (column in ColumnIndex.values()) {
            for (row in RowIndex.values()) {

                // skip 'no-disk' at  (column,row) position
                val color = grid.getDiskAt(column, row) ?: continue

                val position = GridPosition(column, row)

                // compute alignments at this (column,row) position
                // ==> increase existing alignment or increase a new empty alignment
                val upLeftDownRightAlignment    = getAlignment(color, position, UpLeftDownRight).increase()
                val horizontalAlignment         = getAlignment(color, position, Horizontal).increase()
                val downRightAlignmentAlignment = getAlignment(color, position, DownLeftUpRight).increase()
                val verticalAlignment           = getAlignment(color, position, Vertical).increase()


                // set new alignments for this (column,row)
                val alignments = getAlignments(color, position)
                alignments.set(upLeftDownRightAlignment)
                alignments.set(horizontalAlignment)
                alignments.set(downRightAlignmentAlignment)
                alignments.set(verticalAlignment)

                // store any alignment whose size is greater than 1
                all.addIfSizedEnough(upLeftDownRightAlignment)
                all.addIfSizedEnough(horizontalAlignment)
                all.addIfSizedEnough(downRightAlignmentAlignment)
                all.addIfSizedEnough(verticalAlignment)
            }
        }
        return all
    }


    /**
     * Retrieve :
     * - the existing alignment relative to this position (columnIndex, rowIndex) corresponding to the direction
     * - a newly alignment for this position+direction
     */
    fun getAlignment(disk: Disk, position : GridPosition, direction: AlignmentDirection): Alignment {

        // find sibbling
        val sibbling = getSibblingPosition(position, direction) ?:
                // none => return empty alignment
                return emptyAlignment(position, direction)

        // get all sibbling alignments
        val alignments = getAlignments(disk, sibbling)

        // return the one of given direction (or new one)
        return alignments.get(direction) ?: emptyAlignment(position, direction)
    }


    /**
     * get the sibbling position of the given position going in given 'direction'
     */
    private fun getSibblingPosition(position: GridPosition, direction: AlignmentDirection): GridPosition? {
        var otherColumnIndex: ColumnIndex? = position.first
        var otherRowIndex: RowIndex? = position.second
        when (direction) {
            UpLeftDownRight -> {
                otherColumnIndex = position.first.leftward()
                otherRowIndex = position.second.uppward()
            }
            Horizontal -> {
                otherColumnIndex = position.first.leftward()
                otherRowIndex = position.second
            }
            DownLeftUpRight -> {
                otherColumnIndex = position.first.leftward()
                otherRowIndex = position.second.downward()
            }
            Vertical -> {
                otherColumnIndex = position.first
                otherRowIndex = position.second.downward()
            }
        }

        if (otherColumnIndex == null || otherRowIndex == null) {
            return null
        }

        return GridPosition(otherColumnIndex, otherRowIndex)
    }


    /**
     * Shortcut to get the set of alignment corresponding to the given position (columnIndex, rowIndex)
     * and given disk
     */
    private fun getAlignments(disk: Disk, position: GridPosition) =
            alignmentsGrid[position.first]!![position.second]!![disk]!!

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



    private fun emptyAlignment(position: GridPosition, direction: AlignmentDirection) =
            Alignment(position, direction, 0)

}

private fun <E : Alignment> HashSet<E>.addIfSizedEnough(alignment: E) {
    if (alignment.size > 1) {
        this.add(alignment)
    }
}
