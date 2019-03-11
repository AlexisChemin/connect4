package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import org.junit.Test

class ColumnTest {




    @Test
    fun a_new_Column_is_empty_and_has_a_height_of_zero() {
        // GIVEN

        // WHEN
        val column = Column()

        // THEN
        assertThat(column.isEmpty()).isTrue()
        assertThat(column.height()).isEqualTo(0)
    }



    @Test
    fun should_be_able_to_insert_disks() {
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
    fun could_not_insert_more_than_ColumnHeight_disk() {
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
    fun disk_at_position_below_height_can_be_retrieve() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(Disk.YELLOW)
        column.insertDisk(Disk.RED)
        column.insertDisk(Disk.YELLOW)


        // WHEN
        //              get disks
        val disk0 = column.getDiskAt(0)
        val disk1 = column.getDiskAt(1)
        val disk2 = column.getDiskAt(2)

        // THEN
        assertThat(disk0).isEqualTo(Disk.YELLOW)
        assertThat(disk1).isEqualTo(Disk.RED)
        assertThat(disk2).isEqualTo(Disk.YELLOW)
    }



    @Test
    fun disk_at_position_over_or_equal_to_height_is_null() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(Disk.YELLOW)
        column.insertDisk(Disk.RED)
        column.insertDisk(Disk.YELLOW)


        // WHEN
        //              get disks from position 3
        val disk3 = column.getDiskAt(3)
        val disk4 = column.getDiskAt(4)

        // THEN
        assertThat(disk3).isNull()
        assertThat(disk4).isNull()
    }




    @Test(expected = IndexOutOfBoundsException::class)
    fun could_not_get_disk_at_position_over_or_equals_to_ColumnHeight() {
        // GIVEN

        //              a one Disk Column
        val column = Column()
        column.insertDisk(Disk.YELLOW)


        // WHEN
        column.getDiskAt(ColumnHeight)

        // THEN
        //              exception thrown
    }
}