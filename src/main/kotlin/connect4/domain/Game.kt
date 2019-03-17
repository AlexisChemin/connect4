package connect4.domain


/**
 *
 */




interface GameStatus {
    fun isTerminated() : Boolean
}

interface Player {
    fun play(color : Disk, grid : Grid) : ColumnIndex
}

class Game (redPlayer : Player, yellowPlayer : Player, val grid : Grid = Grid()) {

    // private constructor gives the 2 players colors (RED / YELLOW)
//    private constructor(val player1Disk : Disk, val player2Disk : Disk, val grid : Grid)
//    // a game implements both players
//    : Player1, Player2 {

/**
 * Public Construct a game from the color used by player 1
 * player 2 's color has the opposite color : RED => YELLOW, YELLOW => RED
 */
//    constructor(player1Disk : Disk, grid : Grid = Grid()) : this(player1Disk ,
//            when(player1Disk == Disk.RED) {
//                true -> Disk.YELLOW
//                else -> Disk.RED
//            },
//            grid
//    )

    private val playersAndColors = mapOf( Disk.RED to redPlayer, Disk.YELLOW to yellowPlayer )

    var playingColor : Disk = Disk.RED

    var status : GameStatus = gameStatus()
        private set



    fun startGameWithRedPlayer() : Game {
        this.playingColor = Disk.RED
        return play()
    }

    fun startGameWithYellowPlayer() : Game {
        this.playingColor = Disk.YELLOW
        return play()
    }

    fun play() : Game {
        val playedColumnIndex = playersAndColors.getValue(playingColor).play(playingColor, grid)
        play(playingColor, playedColumnIndex)
        playingColor = nextPlayerColor()
        return this
    }


    private fun nextPlayerColor(): Disk {
        return when(playingColor) {
            Disk.RED -> Disk.YELLOW
            else -> Disk.RED
        }
    }




    /**
     * internal play : test status to check the game is already terminated or not.
     * makes the move
     * recompute game status
     */
    private fun play(disk: Disk, position: ColumnIndex) {
        if (status.isTerminated()) {
            throw GameTerminatedException("Game is terminated !")
        }

        grid.insertDisk(position, disk)

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


    private fun findWinner(grid: Grid): Winner? {
        // fetch alignments, find one with a length of at least 4
        val alignment = ComputeAlignments(grid).result.find {  it.size >= 4 }

        alignment?.let {
            // alignment starts with the winner color
            // ==> just get the disk at the 'start' position
            val disk = grid.getDiskAt(it.start.first, it.start.second)

            return disk?.let { Winner(disk, alignment) }
        }
        return null
    }



    class GameRunning : GameStatus {
        override fun isTerminated(): Boolean = false
    }

    class GameTerminated(val winner : Winner?) : GameStatus {
        override fun isTerminated(): Boolean = true
    }


    class Winner(val disk : Disk, val alignment: Alignment)


}

class GameTerminatedException(message: String) : RuntimeException(message)
