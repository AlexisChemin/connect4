package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import connect4.domain.AlignmentDirection.*
import connect4.domain.ColumnIndex.*
import connect4.domain.Disk.*
import connect4.domain.RowIndex.*
import org.junit.Test

class GameTest {




    @Test
    fun `player1 should play RED in a REDvsYELLOW game`() {
        // GIVEN
        val game = Game(player1Disk = RED)

        // WHEN
        game
                .player1(COLUMN_0)
                .player2(COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(COLUMN_0, ROW_0)).isEqualTo(RED)
        assertThat(game.grid.getDiskAt(COLUMN_5, ROW_0)).isEqualTo(YELLOW)
    }



    @Test
    fun `player1 should play YELLOW in a YELLOWvsRED game`() {
        // GIVEN
        val game = Game(player1Disk = YELLOW)

        // WHEN
        game
                .player1(COLUMN_0)
                .player2(COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(COLUMN_0, ROW_0)).isEqualTo(YELLOW)
        assertThat(game.grid.getDiskAt(COLUMN_5, ROW_0)).isEqualTo(RED)
    }




    @Test
    fun `should terminate when a player has 4 aligned disks`() {
        // GIVEN
        val game = Game(YELLOW)

        // WHEN
        game
                .player1(COLUMN_0)
                .player2(COLUMN_1)
                .player1(COLUMN_0)
                .player2(COLUMN_2)
                .player1(COLUMN_0)
                .player2(COLUMN_3)
                .player1(COLUMN_0)

        // THEN
        assertThat(game.status.isTerminated()).isTrue()
        val winner = (game.status as Game.GameTerminated).winner
        assertThat(winner?.disk).isEqualTo(YELLOW)
        assertThat(winner?.alignment).isEqualTo( Alignment(GridPosition(COLUMN_0,ROW_0), Vertical, 4) )
    }



    @Test
    fun `should let player RED play and win`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   |   |   |   | R | R |   |   |   ")
            r("   |   |   | R | Y | R |   |   |   ")
            r("   | R | R | Y | R | Y | Y |   |   ")
        }
        val game = Game(RED , initialGrid)
        assertThat(game.status.isTerminated()).isFalse()

        // WHEN
        game
                .player1(COLUMN_4)


        // THEN
        assertThat(game.status.isTerminated()).isTrue()
        val winner = (game.status as Game.GameTerminated).winner
        assertThat(winner?.disk).isEqualTo(RED)
        assertThat(winner?.alignment).isEqualTo( Alignment(GridPosition(COLUMN_1,ROW_0), DownLeftUpRight, 4) )
    }





    @Test(expected = GameTerminatedException::class)
    fun `should not be able to play a terminated game`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   |   |   |   |   |   |   |   |   ")
            r("   |   |   | Y |   |   | Y |   |   ")
            r("   | R | R | R | R | Y | Y |   |   ")
        }
        val game = Game(RED , initialGrid)
        assertThat(game.status.isTerminated()).isTrue()

        // WHEN
        game
                .player1(COLUMN_4)

        // THEN
        // exception thrown
    }


    @Test
    fun `should terminate when the grid is full without any winner`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   | R |   | R | R | Y | Y | R |   ")
            r("   | R | R | Y | Y | R | Y | R |   ")
            r("   | R | Y | R | R | Y | R | R |   ")
            r("   | Y | R | R | Y | R | Y | Y |   ")
            r("   | Y | Y | R | Y | Y | R | R |   ")
            r("   | Y | R | Y | Y | R | Y | Y |   ")
        }
        val game = Game(YELLOW , initialGrid)
        assertThat(game.status.isTerminated()).isFalse()

        // WHEN
        game
                .player1(COLUMN_1)

        // THEN

        assertThat(game.status.isTerminated()).isTrue()
        val winner = (game.status as Game.GameTerminated).winner
        assertThat(winner).isNull()
    }

}