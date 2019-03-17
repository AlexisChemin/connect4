package connect4.domain


/**
 *
 */




interface GameStatus {
    fun isTerminated() : Boolean
}

interface Player {
    fun play(color : Color, grid : Grid) : ColumnIndex
}

/**
 * A Game is composed of 2 players and a grid
 *
 */
class Game (redPlayer : Player, yellowPlayer : Player, val grid : Grid = Grid()) {

    private val playerByColor = mapOf( Color.RED to redPlayer, Color.YELLOW to yellowPlayer )

    // next playing color
    var playingColor : Color = Color.RED

    var status : GameStatus = GameInitialized()
        private set



    fun startGameWithRedPlayer() : Game {
        return startGame(Color.RED)
    }

    fun startGameWithYellowPlayer() : Game {
        return startGame(Color.YELLOW)
    }

    fun play() : Game {
        val playedColumnIndex = playerByColor.getValue(playingColor).play(playingColor, grid)
        play(playingColor, playedColumnIndex)
        playingColor = nextPlayerColor()
        return this
    }

    private fun startGame(color: Color): Game {
        if (status !is GameInitialized) {
            throw GameAlreadyStartedException("Game is already started !")
        }
        this.playingColor = color
        return play()
    }

    private fun nextPlayerColor(): Color {
        return when(playingColor) {
            Color.RED -> Color.YELLOW
            else -> Color.RED
        }
    }




    /**
     * internal play : test status to check the game is already terminated or not.
     * makes the move
     * recompute game status
     */
    private fun play(color: Color, position: ColumnIndex) {
        if (status.isTerminated()) {
            throw GameTerminatedException("Game is terminated !")
        }

        grid.insertDisk(position, color)

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
            // ==> just get the color at the 'start' position
            val disk = grid.getDiskAt(it.start.first, it.start.second)

            return disk?.let { Winner(disk, alignment) }
        }
        return null
    }


    class GameInitialized : GameStatus {
        override fun isTerminated(): Boolean = false
    }

    class GameRunning : GameStatus {
        override fun isTerminated(): Boolean = false
    }

    class GameTerminated(val winner : Winner?) : GameStatus {
        override fun isTerminated(): Boolean = true
    }


    class Winner(val color : Color, val alignment: Alignment)


}

class GameTerminatedException(message: String) : RuntimeException(message)
class GameAlreadyStartedException(message: String) : RuntimeException(message)
