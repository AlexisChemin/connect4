package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import org.junit.Test

class GridTest {




    @Test
    fun a_new_Grid_is_empty() {
        // GIVEN

        // WHEN
        val grid = Grid()

        // THEN
        assertThat(grid.isEmpty()).isTrue()
    }



    @Test
    fun should_be_able_to_insert_single_disk() {
        // GIVEN
        val grid = Grid()

        // WHEN
        grid.insertDisk(ColumnIndex.COLUMN_3, Disk.YELLOW)

        // THEN
        assertThat(grid.isEmpty()).isFalse()
        assertThat(grid.size()).isEqualTo(1)
    }


    @Test
    fun should_be_able_to_insert_several_disks() {
        // GIVEN
        val grid = Grid()

        // WHEN
        grid.insertDisk(ColumnIndex.COLUMN_3, Disk.YELLOW)
        grid.insertDisk(ColumnIndex.COLUMN_3, Disk.RED)
        grid.insertDisk(ColumnIndex.COLUMN_0, Disk.RED)

        // THEN
        assertThat(grid.isEmpty()).isFalse()
        assertThat(grid.size()).isEqualTo(3)
    }



    @Test
    fun disk_at_given_position_can_be_retrieve() {
        // GIVEN
        //              a three Disks Grid
        val grid = Grid()
        grid.insertDisk(ColumnIndex.COLUMN_1, Disk.YELLOW)
        grid.insertDisk(ColumnIndex.COLUMN_1, Disk.RED)
        grid.insertDisk(ColumnIndex.COLUMN_0, Disk.RED)


        // WHEN
        //              get disks
        val disk0 = grid.getDiskAt(ColumnIndex.COLUMN_1, RowIndex.ROW_0)
        val disk1 = grid.getDiskAt(ColumnIndex.COLUMN_1, RowIndex.ROW_1)
        val disk2 = grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)


        // THEN
        assertThat(disk0).isEqualTo(Disk.YELLOW)
        assertThat(disk1).isEqualTo(Disk.RED)
        assertThat(disk2).isEqualTo(Disk.RED)
    }



    @Test
    fun disk_at_position_over_or_equal_to_column_height_is_null() {
        // GIVEN
        //              a three Disks Column
        val grid = Grid()
        grid.insertDisk(ColumnIndex.COLUMN_3, Disk.YELLOW)


        // WHEN
        //              get disks from position 3
        val disk3 = grid.getDiskAt(ColumnIndex.COLUMN_3, RowIndex.ROW_1) // row over height
        val disk4 = grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)

        // THEN
        assertThat(disk3).isNull()
        assertThat(disk4).isNull()
    }


}