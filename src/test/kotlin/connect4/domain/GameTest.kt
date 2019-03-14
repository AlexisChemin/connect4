package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import org.junit.Test

class GameTest {




    @Test
    fun players_should_plays_their_colors() {
        // GIVEN
        val game = Game(Disk.RED, Disk.YELLOW)

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)).isEqualTo(Disk.RED)
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_5, RowIndex.ROW_0)).isEqualTo(Disk.YELLOW)

    }




    @Test(expected = Exception::class)
    fun players_cannot_be_both_RED() {
        // GIVEN

        // WHEN
        val game = Game(Disk.RED, Disk.RED)

        // THEN
        // exception is thrown
    }



    @Test(expected = Exception::class)
    fun players_cannot_be_both_YELLOW() {
        // GIVEN

        // WHEN
        val game = Game(Disk.YELLOW, Disk.YELLOW)

        // THEN
        // exception is thrown
    }

}