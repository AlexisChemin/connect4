package connect4.domain

import assertk.assertThat
import assertk.assertions.*
import org.junit.Test

class ComputeAlignmentsTest {




    @Test
    fun should_compute_alignemt_on_empty_grid() {
        // GIVEN
        val grid = Grid()

        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).isNotNull()
    }




    @Test
    fun should_compute_horizontal_alignments() {

        // GIVEN
        val grid = gridOf {
            r(" ", " ", " ", " ", " ", " ", " ")
            r(" ", " ", " ", " ", " ", " ", " ")
            r("R", "R", " ", "Y", "Y", "Y", "Y")
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
    fun should_compute_vertical_alignments() {
        // GIVEN
        val grid = gridOf {
            r("R", " ", " ", " ", " ", "Y", " ")
            r("R", " ", " ", "Y", " ", "Y", " ")
            r("R", " ", " ", "Y", " ", "Y", " ")
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
    fun should_compute_diagonal_alignemts() {
        // GIVEN
        val grid = gridOf {
            r(" ", " ", "R", " ", "R", " ", " ")
            r(" ", " ", "Y", "R", "Y", " ", " ")
            r(" ", " ", "R", "Y", "R", " ", " ")
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