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
    private constructor(val player1Disk : Disk, val player2Disk : Disk)
    // a game implements both players
    : Player1, Player2 {

    /**
     * Construct a game from the color used by player 1
     * player 2 's color has the opposite color : RED => YELLOW, YELLOW => RED
     */
    constructor(player1Disk : Disk) :this(player1Disk,
            when(player1Disk == Disk.RED) {
                true -> Disk.YELLOW
                else -> Disk.RED
            }
    )


    val grid = Grid()

    var status : GameStatus = GameRunning()

    class GameRunning : GameStatus {
        override fun isTerminated(): Boolean = false
    }


    class GameTerminated(val winner : Disk?) : GameStatus {
        override fun isTerminated(): Boolean = true
    }



    override fun player1(playsAt: ColumnIndex): Player2 {
        play(player1Disk, playsAt)
        return this
    }

    override fun player2(playsAt: ColumnIndex): Player1 {
        play(player2Disk, playsAt)
        return this
    }



    private fun play(color: Disk, playsAt: ColumnIndex) {
        if (status.isTerminated()) {
            throw RuntimeException("Game is terminated !")
        }
        grid.insertDisk(playsAt, color)

        // any winner ?
        val winner = findWinner(grid)

        if (winner!=null || grid.isFull() ) {
            status = GameTerminated(winner)
        }
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


}
