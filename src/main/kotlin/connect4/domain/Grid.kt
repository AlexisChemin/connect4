package connect4.domain



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


}