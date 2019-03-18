package connect4.domain

import assertk.assertThat
import assertk.assertions.*
import connect4.domain.AlignmentDirection.*
import connect4.domain.ColumnIndex.*
import connect4.domain.RowIndex.*
import org.junit.Test

class ComputeAlignmentsTest {




    @Test
    fun `should compute alignments on an empty grid`() {
        // GIVEN
        val grid = GridImpl()

        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).isNotNull()
    }




    @Test
    fun `should detect horizontal alignments`() {

        // GIVEN
        val grid = gridOf {
            r("   |   |   |   |   |   |   |   |   ")
            r("   |   |   |   |   |   |   |   |   ")
            r("   | R | R |   | Y | Y | Y | Y |   ")
        }


        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).containsAll(
                Alignment(GridPosition(COLUMN_0, ROW_0), Horizontal, 2),
                Alignment(GridPosition(COLUMN_3, ROW_0), Horizontal, 4)
        )
    }




    @Test
    fun `should detect vertical alignments`() {
        // GIVEN
        val grid = gridOf {
            r("   | R |   |   |   |   | Y |   |   ")
            r("   | R |   |   | Y |   | Y |   |   ")
            r("   | R |   |   | Y |   | Y |   |   ")
        }


        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).containsAll(
                Alignment(GridPosition(COLUMN_0, ROW_0), Vertical, 3),
                Alignment(GridPosition(COLUMN_3, ROW_0), Vertical, 2),
                Alignment(GridPosition(COLUMN_5, ROW_0), Vertical, 3)
        )
    }





    @Test
    fun `should detect diagonal alignments`() {
        // GIVEN
        val grid = gridOf {
            r("   |   |   | R |   | R |   |   |   ")
            r("   |   |   | Y | R | Y |   |   |   ")
            r("   |   |   | R | Y | R |   |   |   ")
        }

        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).containsAll(
                Alignment(GridPosition(COLUMN_2, ROW_0), DownLeftUpRight, 3),
                Alignment(GridPosition(COLUMN_2, ROW_2), UpLeftDownRight, 3),
                Alignment(GridPosition(COLUMN_2, ROW_1), UpLeftDownRight, 2),
                Alignment(GridPosition(COLUMN_3, ROW_0), DownLeftUpRight, 2)
        )
    }


}