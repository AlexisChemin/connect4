package connect4.domain

import assertk.assertThat
import assertk.assertions.*
import org.junit.Test

class GridAlignmentsTest {




    @Test
    fun should_compute_alignemt_on_empty_grid() {
        // GIVEN
        val grid = Grid()

        // WHEN
        var alignments = GridAlignments(grid)

        // THEN
        assertThat(alignments).isNotNull()
    }



    @Test
    fun should_compute_alignemt_on_grid() {
        // GIVEN
        val grid = Grid()
        givenRow(grid,  " ", "R", "R", "Y", "Y", "Y", " "  )
//        givenRow(grid,  " ", "Y", "R", "Y", " ", " ", " "  )

        // WHEN
        var alignments = GridAlignments(grid)

        // THEN
        assertThat(alignments).isNotNull()
    }



    private fun givenRow(grid: Grid, s0: String, s1: String, s2: String, s3: String, s4: String, s5: String, s6: String) {
        stringToDisk(s0)?.let {  grid.insertDisk(ColumnIndex.COLUMN_0, it) }
        stringToDisk(s1)?.let {  grid.insertDisk(ColumnIndex.COLUMN_1, it) }
        stringToDisk(s2)?.let {  grid.insertDisk(ColumnIndex.COLUMN_2, it) }
        stringToDisk(s3)?.let {  grid.insertDisk(ColumnIndex.COLUMN_3, it) }
        stringToDisk(s4)?.let {  grid.insertDisk(ColumnIndex.COLUMN_4, it) }
        stringToDisk(s5)?.let {  grid.insertDisk(ColumnIndex.COLUMN_5, it) }
        stringToDisk(s6)?.let {  grid.insertDisk(ColumnIndex.COLUMN_6, it) }
    }

    private fun stringToDisk(s: String): Disk? {
        return when(s) {
            "R" -> Disk.RED
            "Y" -> Disk.YELLOW
            else -> null
        }
    }


}