package connect4.domain

import java.util.*


/**
 * A Column of the Grid. Up to 'ColumnHeight' Disks can be inserted into a Column
 */

class Column {

    private var disks = Stack<Disk>()


    fun insertDisk(disk: Disk) {
        if (isFull()) {
            throw IndexOutOfBoundsException()
        }
        disks.push(disk)
    }



    fun getDiskAt(rowIndex : RowIndex) : Disk? {
        val stackIndex = rowIndex.ordinal
        if (stackIndex >= height()) {
            return null
        }
        return disks.elementAt(stackIndex)
    }



    fun height(): Int {
        return disks.size
    }



    fun isEmpty() : Boolean {
        return disks.isEmpty()
    }

    fun clear() {
        disks.clear()
    }

    inline fun isFull(): Boolean {
        return height() >= ColumnHeight
    }

}
