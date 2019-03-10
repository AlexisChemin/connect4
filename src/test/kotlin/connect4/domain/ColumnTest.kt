package connect4.domain

import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Test

class ColumnTest {


    @Test
    fun should_be_able_to_insert_disk() {
        // GIVEN
        val column = Column()
        assertThat(column.height(), IsEqual(0))

        // WHEN
        column.insertDisk(Disk.YELLOW)

        // THEN
        assertThat(column.height(), IsEqual(1))
    }

    @Test(expected = FullColumnException::class)
    fun could_not_insert_more_than_ColumnHeight_disk() {
        // GIVEN

        val column = Column()
        // fill up column
        for (x in 1..ColumnHeight) {
            column.insertDisk(Disk.YELLOW)
        }

        // WHEN
        // add one more disk
        column.insertDisk(Disk.YELLOW)

        // THEN
        // exception expected
    }
}