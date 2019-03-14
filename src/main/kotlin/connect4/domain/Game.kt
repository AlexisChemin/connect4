package connect4.domain

import java.util.*


/**
 *
 */

interface Player1 {

    fun player1(playsAt : ColumnIndex) : Player2

}


interface Player2 {

    fun player2(playsAt : ColumnIndex) : Player1

}


class Game(val player1Disk : Disk, val player2Disk : Disk) : Player1, Player2 {

    val grid = Grid()


    override fun player1(playsAt: ColumnIndex): Player2 {
        grid.insertDisk(playsAt, player1Disk)
        return this
    }

    override fun player2(playsAt: ColumnIndex): Player1 {
        grid.insertDisk(playsAt, player2Disk)
        return this
    }


}
