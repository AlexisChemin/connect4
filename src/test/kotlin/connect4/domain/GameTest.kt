package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import org.junit.Ignore
import org.junit.Test

class GameTest {




    @Test
    fun a_game_RED_vs_YELLOW_plays_RED_first() {
        // GIVEN
        val game = Game(Disk.RED)

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)).isEqualTo(Disk.RED)
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_5, RowIndex.ROW_0)).isEqualTo(Disk.YELLOW)
    }



    @Test
    fun a_game_YELLOW_RED_plays_YELLOW_first() {
        // GIVEN
        val game = Game(Disk.YELLOW)

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)).isEqualTo(Disk.YELLOW)
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_5, RowIndex.ROW_0)).isEqualTo(Disk.RED)
    }




    @Test
    fun a_game_terminated_when_a_player_has_4_align_disk() {
        // GIVEN
        val game = Game(Disk.YELLOW)

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_1)
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_2)
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_3)
                .player1(ColumnIndex.COLUMN_0)

        // THEN
        assertThat(game.status.isTerminated()).isTrue()
        assertThat((game.status as Game.GameTerminated).winner).isEqualTo(Disk.YELLOW)
    }

}