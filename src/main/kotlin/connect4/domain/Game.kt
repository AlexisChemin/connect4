package connect4.domain

import sun.audio.AudioPlayer.player


/**
 *
 */

interface Player1 {

    fun player1(playsAt : ColumnIndex) : Player2

}


interface Player2 {

    fun player2(playsAt : ColumnIndex) : Player1

}


interface GameStatus {
    fun isTerminated() : Boolean
}



class Game
    // private constructor gives the 2 players colors (RED / YELLOW)
    private constructor(val player1Disk : Disk, val player2Disk : Disk, val grid : Grid)
    // a game implements both players
    : Player1, Player2 {

    /**
     * Public Construct a game from the color used by player 1
     * player 2 's color has the opposite color : RED => YELLOW, YELLOW => RED
     */
    constructor(player1Disk : Disk, grid : Grid = Grid()) : this(player1Disk ,
            when(player1Disk == Disk.RED) {
                true -> Disk.YELLOW
                else -> Disk.RED
            },
            grid
    )


    var status : GameStatus = gameStatus()


    /**
     * player 1 makes its move
     */
    override fun player1(playsAt: ColumnIndex): Player2 {
        play(player1Disk, playsAt)
        return this
    }


    /**
     * player 2 makes its move
     */
    override fun player2(playsAt: ColumnIndex): Player1 {
        play(player2Disk, playsAt)
        return this
    }



    /**
     * internal play : test status to check the game is already terminated or not.
     * makes the move
     * recompute game status
     */
    private fun play(color: Disk, playsAt: ColumnIndex) {
        if (status.isTerminated()) {
            throw GameTerminatedException("Game is terminated !")
        }
        grid.insertDisk(playsAt, color)

        // any winner ?
        status = gameStatus()
    }


    /**
     * Computes the game status : whether a player has won or the grid is full
     */
    private fun gameStatus() : GameStatus {
        val winner = findWinner(grid)

        if (winner != null || grid.isFull()) {
            return GameTerminated(winner)
        }

        return GameRunning()
    }


    private fun findWinner(grid: Grid): Disk? {
        // fetch alignments, find one with a length of at least 4
        val alignment = ComputeAlignments(grid).result.find {  it.size >= 4 }

        alignment?.let {
            // alignment starts with the winner color
            // ==> just get the disk at the 'start' position
            return grid.getDiskAt( it.start.first, it.start.second )
        }
        return null
    }



    class GameRunning : GameStatus {
        override fun isTerminated(): Boolean = false
    }

    class GameTerminated(val winner : Disk?) : GameStatus {
        override fun isTerminated(): Boolean = true
    }


}

class GameTerminatedException(message: String) : RuntimeException(message)
