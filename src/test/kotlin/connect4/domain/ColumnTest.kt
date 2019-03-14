package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import org.junit.Test

class ColumnTest {




    @Test
    fun `a new Column is empty and its size is 0`() {
        // GIVEN

        // WHEN
        val column = Column()

        // THEN
        assertThat(column.isEmpty()).isTrue()
        assertThat(column.height()).isEqualTo(0)
    }



    @Test
    fun `should be able to insert disks within column`() {
        // GIVEN
        val column = Column()

        // WHEN
        column.insertDisk(Disk.YELLOW)
        column.insertDisk(Disk.RED)
        column.insertDisk(Disk.RED)

        // THEN
        assertThat(column.height()).isEqualTo(3)
    }




    @Test(expected = IndexOutOfBoundsException::class)
    fun `could not insert more than -ColumnHeight- disks`() {
        // GIVEN

        //              a filled up column
        val column = Column()
        for (x in 1..ColumnHeight) {
            column.insertDisk(Disk.YELLOW)
        }
        assertThat(column.height()).isEqualTo(ColumnHeight)

        // WHEN
        //              add one more disk
        column.insertDisk(Disk.YELLOW)

        // THEN
        //              exception thrown
    }



    @Test
    fun `getting a disk below -column's height- returns a non null disk`() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(Disk.YELLOW)
        column.insertDisk(Disk.RED)
        column.insertDisk(Disk.YELLOW)


        // WHEN
        //              get disks
        val disk0 = column.getDiskAt(RowIndex.ROW_0)
        val disk1 = column.getDiskAt(RowIndex.ROW_1)
        val disk2 = column.getDiskAt(RowIndex.ROW_2)

        // THEN
        assertThat(disk0).isEqualTo(Disk.YELLOW)
        assertThat(disk1).isEqualTo(Disk.RED)
        assertThat(disk2).isEqualTo(Disk.YELLOW)
    }



    @Test
    fun `getting a disk over -column's height- returns null`() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(Disk.YELLOW)
        column.insertDisk(Disk.RED)
        column.insertDisk(Disk.YELLOW)


        // WHEN
        //              get disks from position 3
        val disk3 = column.getDiskAt(RowIndex.ROW_3)
        val disk4 = column.getDiskAt(RowIndex.ROW_4)

        // THEN
        assertThat(disk3).isNull()
        assertThat(disk4).isNull()
    }





    @Test
    fun `-clear- column should remove all disks of that column`() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(Disk.YELLOW)
        column.insertDisk(Disk.RED)
        column.insertDisk(Disk.YELLOW)

        // WHEN
        column.clear()

        // THEN
        assertThat(column.isEmpty())
        assertThat(column.height()).isEqualTo(0)
    }



}