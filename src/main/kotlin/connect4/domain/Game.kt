package connect4.domain


/**
 *
 */



interface Player {
    fun play(color : Color, grid : Grid) : ColumnIndex
}




interface GameStatus {

    fun isTerminated() : Boolean

}


interface GameInitialized : GameStatus {

    override fun isTerminated() : Boolean = false

    fun startGameWithRedPlayer() : GameRunning

    fun startGameWithYellowPlayer() : GameRunning
}



interface GameRunning : GameStatus {

    override fun isTerminated() : Boolean = false

    fun play(): GameRunning
}


class Winner(val color : Color, val alignment: Alignment)


class GameTerminated(val winner : Winner?) : GameRunning {

    override fun isTerminated(): Boolean = true

    override fun play(): GameRunning {
        throw GameTerminatedException("Game is terminated !")
    }
}


/**
 * A Game is composed of 2 players and a grid
 *
 */
class Game (redPlayer : Player, yellowPlayer : Player, val grid : Grid = Grid()) : GameInitialized {

    private val playerByColor = mapOf( Color.RED to redPlayer, Color.YELLOW to yellowPlayer )

    // next play color
    private var playingColor : Color = Color.RED

    var status : GameRunning? = null // null means 'not started'
        private set



    override fun startGameWithRedPlayer() : GameRunning {
        return startGame(Color.RED)
    }

    override fun startGameWithYellowPlayer() : GameRunning {
        return startGame(Color.YELLOW)
    }


    override fun isTerminated() : Boolean = status?.isTerminated() ?: false



    private fun startGame(color: Color): GameRunning {
        if (status != null) {
            throw GameAlreadyStartedException("Game is already started !")
        }
        this.playingColor = color
        return play()
    }


    private fun play() : GameRunning {
        val playedColumnIndex = playerByColor.getValue(playingColor).play(playingColor, grid)
        val newStatus = play(playingColor, playedColumnIndex)
        playingColor = nextPlayerColor()
        status = newStatus
        return newStatus
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
    private fun play(color: Color, position: ColumnIndex) : GameRunning {
        if (isTerminated()) {
            throw GameTerminatedException("Game is terminated !")
        }

        grid.insertDisk(position, color)

        // any winner ?
        return gameStatus()
    }


    /**
     * Computes the game status : whether a player has won or the grid is full
     */
    private fun gameStatus() : GameRunning {
        val winner = findWinner(grid)

        if (winner != null || grid.isFull()) {
            return GameTerminated(winner)
        }

        return Running()
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

    private inner class Running : GameRunning {
        override fun play(): GameRunning {
            return this@Game.play()
        }
    }






}

class GameTerminatedException(message: String) : RuntimeException(message)
class GameAlreadyStartedException(message: String) : RuntimeException(message)
