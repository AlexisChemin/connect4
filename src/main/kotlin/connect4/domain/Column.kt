package connect4.domain

import java.util.*


val ColumnHeight = 6

class Column {

    var disks = Stack<Disk>()

    fun insertDisk(disk: Disk) {
        disks.push(disk);
    }

    fun height(): Int {
        return disks.size
    }

}
