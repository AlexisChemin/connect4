package connect4.domain

import assertk.assertThat
import assertk.assertions.*
import org.junit.Test

class ComputeAlignmentsTest {




    @Test
    fun `should compute alignments on an empty grid`() {
        // GIVEN
        val grid = Grid()

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
                Alignment(GridPosition(ColumnIndex.COLUMN_0, RowIndex.ROW_0), AlignmentDirection.Horizontal, 2),
                Alignment(GridPosition(ColumnIndex.COLUMN_3, RowIndex.ROW_0), AlignmentDirection.Horizontal, 4)
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
                Alignment(GridPosition(ColumnIndex.COLUMN_0, RowIndex.ROW_0), AlignmentDirection.Vertical, 3),
                Alignment(GridPosition(ColumnIndex.COLUMN_3, RowIndex.ROW_0), AlignmentDirection.Vertical, 2),
                Alignment(GridPosition(ColumnIndex.COLUMN_5, RowIndex.ROW_0), AlignmentDirection.Vertical, 3)
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
                Alignment(GridPosition(ColumnIndex.COLUMN_2, RowIndex.ROW_0), AlignmentDirection.DownLeftUpRight, 3),
                Alignment(GridPosition(ColumnIndex.COLUMN_2, RowIndex.ROW_2), AlignmentDirection.UpLeftDownRight, 3),
                Alignment(GridPosition(ColumnIndex.COLUMN_2, RowIndex.ROW_1), AlignmentDirection.UpLeftDownRight, 2),
                Alignment(GridPosition(ColumnIndex.COLUMN_3, RowIndex.ROW_0), AlignmentDirection.DownLeftUpRight, 2)
        )
    }


}