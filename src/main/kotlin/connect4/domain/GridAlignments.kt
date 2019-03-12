package connect4.domain


/**
 * Created by vagrant on 3/12/19.
 */


enum class AlignmentDirection {
    Horizontal,
    Vertical,
    UpLeftDownRight,
    DownLeftUpRight
}

data class Alignment(var start : GridPosition, var direction : AlignmentDirection, var size : Int)
data class Alignments(var upperDiagonal : Int, var leftHorizontal : Int, var lowerDiagnoal : Int, var downVertical : Int)

val NoAlignment = Alignments(0,0,0,0)
val SingleAlignment = Alignments(1,1,1,1)


val outboundAlignments = mapOf(Disk.YELLOW to NoAlignment, Disk.RED to NoAlignment)

class GridAlignments(grid : Grid) {

    // what we're about to compute : 2-dimensions array of mapping (Disk -> Alignment)
    val alignmentsArray = Array(GridWidth){ Array(ColumnHeight, { mutableMapOf(Disk.YELLOW to NoAlignment, Disk.RED to NoAlignment) }) }

    private fun compute() {

        val myColor = Disk.RED
        val myColumnIndex = 3
        val myRowIndex = 4


        // compute alignments, from left column and other up/down rows
        val upperDiagonal =     1 + getAlignmentsByDisk( myColumnIndex - 1, myRowIndex + 1 )[myColor]!!.upperDiagonal
        val leftHorizontal =    1 + getAlignmentsByDisk( myColumnIndex - 1, myRowIndex     )[myColor]!!.leftHorizontal
        val lowerDiagnoal  =    1 + getAlignmentsByDisk( myColumnIndex - 1, myRowIndex - 1 )[myColor]!!.lowerDiagnoal
        val downVertical  =     1 + getAlignmentsByDisk( myColumnIndex    , myRowIndex - 1 )[myColor]!!.downVertical

        val myAligments = Alignments(upperDiagonal, leftHorizontal, lowerDiagnoal, downVertical)

        // set my alignments
        alignmentsArray[myColumnIndex][myRowIndex][myColor] = myAligments
    }



    fun getAlignmentsByDisk(columnIndex:Int, rowIndex : Int) : Map<Disk, Alignments> {
        if (columnIndex < 0 || columnIndex >= GridWidth) {
            return outboundAlignments
        }
        if (rowIndex < 0 || rowIndex >= ColumnHeight) {
            return outboundAlignments
        }
        return alignmentsArray[columnIndex][rowIndex]
    }

}