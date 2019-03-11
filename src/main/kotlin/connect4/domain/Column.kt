package connect4.domain

import java.util.*


val ColumnHeight = 6

/**
 * A Column of the Grid. Up to 'ColumnHeight' Disks can be inserted into a Column
 */

class Column {

    var disks = Stack<Disk>()


    fun insertDisk(disk: Disk) {
        if (height() >= ColumnHeight) {
            throw IndexOutOfBoundsException()
        }
        disks.push(disk);
    }



    fun getDiskAt(position : Int) : Disk? {
        if (position >= ColumnHeight) {
            throw IndexOutOfBoundsException()
        }
        if (position >= height()) {
            return null
        }
        return disks.elementAt(position);
    }



    fun height(): Int {
        return disks.size
    }



    fun isEmpty() : Boolean {
        return disks.isEmpty()
    }



}
