package connect4.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Ignore
import org.junit.Test

class GameTest {




    @Test
    fun `player1 should play RED in a REDvsYELLOW game`() {
        // GIVEN
        val game = Game(player1Disk = Disk.RED)

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)).isEqualTo(Disk.RED)
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_5, RowIndex.ROW_0)).isEqualTo(Disk.YELLOW)
    }



    @Test
    fun `player1 should play YELLOW in a YELLOWvsRED game`() {
        // GIVEN
        val game = Game(player1Disk = Disk.YELLOW)

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_0)
                .player2(ColumnIndex.COLUMN_5)

        // THEN
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_0, RowIndex.ROW_0)).isEqualTo(Disk.YELLOW)
        assertThat(game.grid.getDiskAt(ColumnIndex.COLUMN_5, RowIndex.ROW_0)).isEqualTo(Disk.RED)
    }




    @Test
    fun `should terminate when a player has 4 aligned disks`() {
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



    @Test
    fun `should let player RED plays and win`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   |   |   |   | R | R |   |   |   ")
            r("   |   |   | R | Y | R |   |   |   ")
            r("   | R | R | Y | R | Y | Y |   |   ")
        }
        val game = Game(Disk.RED , initialGrid)
        assertThat(game.status.isTerminated()).isFalse()

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_4)

        // THEN
        assertThat(game.status.isTerminated()).isTrue()
        assertThat((game.status as Game.GameTerminated).winner).isEqualTo(Disk.RED)
    }





    @Test(expected = GameTerminatedException::class)
    fun `should not be able to play a terminated game`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   |   |   |   |   |   |   |   |   ")
            r("   |   |   | Y |   |   | Y |   |   ")
            r("   | R | R | R | R | Y | Y |   |   ")
        }
        val game = Game(Disk.RED , initialGrid)
        assertThat(game.status.isTerminated()).isTrue()

        // WHEN
        game
                .player1(ColumnIndex.COLUMN_4)

        // THEN
        // exception thrown
    }


}