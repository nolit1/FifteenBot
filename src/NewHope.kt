import java.util.*
import kotlin.collections.ArrayList

val row = 2
val column = 4
var cnt = 0

//заполнение массива числами в правильном порядке
fun testFun(): List<IntArray> {
    var k = 0
    val tiles = List(row) { IntArray(column) }
    for (i in 0 until tiles.size) {
        for (j in 0 until tiles[0].size) {
            cnt++
            tiles[i][j] = cnt
        }
    }
    val a = ArrayList<Int>()
    val b = ArrayList<IntArray>()
    for (i in 0 until tiles.size) {
        for (j in 0 until tiles[0].size) {
            a.add(tiles[i][j])
        }
        b.add(k++, a.toIntArray())
        a.clear()
    }
    return b
}

fun indexOfElementInBoardSec(board: List<IntArray>, elem: Int): ArrayList<Int> {
    val k = ArrayList<Int>()
    for (i in 0 until board.size) {
        for (j in 0 until board[0].size) {
            if (elem == board[i][j]) {
                k.add(i)
                k.add(j)
            }
        }
    }
    return k
}

fun howManyFalseSec(board: List<IntArray>): Int {
    var cnt = 0
    var buf = 0
    for (i in 0 until board.size) {
        for (j in 0 until board[0].size) {
            buf++
            if (buf != board[i][j]) cnt++
        }
    }
    return cnt
}

fun arrayToBoardSec(board: List<IntArray>) {
    for (i in 0 until board.size) {
        for (j in 0 until board[0].size) {
            if (board[i][j] < 10)
                print(" " + board[i][j] + " ")
            else print("" + board[i][j] + " ")
        }
        if (i != board.size) println()
    }
}

fun moveSec(board: MutableList<IntArray>, index: ArrayList<Int>): List<IntArray> {
    val a = board[index[0]][index[1]]
    val indexOldBlank = indexOfElementInBoardSec(board, board.size * board[0].size)
    board[index[0]][index[1]] = board.size * board[0].size
    board[indexOldBlank[0]][indexOldBlank[1]] = a
    return board
}

fun isTrueMoveSec(board: List<IntArray>, elem: Int): Boolean {

    if (indexOfElementInBoardSec(board, elem)[0] < 0 && indexOfElementInBoardSec(board, elem)[0] > board.size)
        return false
    if (indexOfElementInBoardSec(board, elem)[1] < 0 && indexOfElementInBoardSec(board, elem)[1] > board.size)
        return false

    val dx = indexOfElementInBoardSec(board, board.size * board[0].size)[0] - indexOfElementInBoardSec(board, elem)[0]
    val dy = indexOfElementInBoardSec(board, board.size * board[0].size)[1] - indexOfElementInBoardSec(board, elem)[1]
    return Math.abs(dx) + Math.abs(dy) == 1 && dx * dy == 0
}

fun allMovesSec(board: List<IntArray>): ArrayList<List<IntArray>> {
    val result = ArrayList<List<IntArray>>()
    var k = 0

    for (i in 0 until board.size) {
        for (j in 0 until board[0].size) {

            val test = Array(board.size) { IntArray(board[0].size) }
            for (n in 0 until board.size) {
                for (m in 0 until board[0].size) {
                    test[n][m] = board[n][m]
                }
            }
            if (isTrueMoveSec(test.toList(), test[i][j]) && test[i][j] != board.size * board[0].size) {
                val a = test[i][j]
                result.add(moveSec(test.toMutableList(), indexOfElementInBoardSec(test.toList(), a)))
                k++
            }
        }
    }
//    println("----------------")
//    for (i in result) arrayToBoardSec(i)
//    println(k)
    return result
}

fun isBoardFinalSec(board: List<IntArray>): Boolean {
    return howManyFalseSec(board) == 0
}

private fun shuffle(board: List<IntArray>, quantity: Int): List<IntArray> {
    var a = listOf<IntArray>()
    for (i in 0 until quantity) {
        val possible = allMovesSec(board)
        val which = possible[(Math.random() * possible.size).toInt()]
        a = moveSec(board.toMutableList(), indexOfElementInBoardSec(which, board.size * board[0].size))
        //println(arrayToBoardSec(a))
    }
    return a
}

fun aStarSec(startBoard: List<IntArray>): LinkedList<List<IntArray>> {

    val cameFrom = HashMap<List<IntArray>, List<IntArray>>() //откуда мы пришли (родитель)
    val costSoFar = HashMap<List<IntArray>, Int>() //стоимость хода, показывает, сколько клеток в данной позиции не на своём месте
    val comparator = Comparator.comparingInt<List<IntArray>>({ costSoFar[it]!! }) //сравнивает значения score в очереди
    val priorityQueue = PriorityQueue<List<IntArray>>(1000, comparator) //главная очередь с приоритетом, сюда добавляются варианты
    val result = LinkedList<List<IntArray>>() //список верных вариантов
    var finalBoard = listOf<IntArray>()

    val helpList = ArrayList<IntArray>() //вспомогательный лист, просто нужен для уникальности начального положения
    val a = ArrayList<Int>()
    a.add(1)
    helpList.add(0, a.toIntArray())

    cameFrom.put(startBoard, helpList) //добавляем начальное положение
    costSoFar.put(startBoard, howManyFalseSec(startBoard)) //его стоимость
    priorityQueue.add(startBoard) //добавляем в очередь
    var count = 0 //считает количество рассмотренных вариантов

    while (priorityQueue.size > 0) { //пока существует очередь выполняем
        val current = priorityQueue.remove() //удаляем из очереди элемент и сохраняем в переменную, это наше текущее состояние
        count++
        if (isBoardFinalSec(current)) { //если решение найдено
            finalBoard = current //as ArrayList<IntArray>
            break
        }
        val moves = allMovesSec(current)
        for (board in moves) { //для каждого варианта хода рассматриваем
            if (!cameFrom.containsKey(board)) {
                cameFrom.put(board, current)
                costSoFar.put(board, 1 + howManyFalseSec(board))
                priorityQueue.add(board)
            }
        }
    }
    println("Рассмотрено вариантов: " + count)
    var board = finalBoard
    while (board != helpList) {
        result.addFirst(board)
        board = cameFrom[board]!! //as ArrayList<IntArray>
    }
    println("Для решения нужно ходов: " + result.size)

    for (n in result) {
        println(arrayToBoardSec(n))
    }
    return result
}

fun main(args: Array<String>) {
    val test = testFun()
    val a = shuffle(test, 10)
    println("-----Начальное положение-----")
    println(arrayToBoardSec(a))
    println("-----Решение A*-----")
    aStarSec(a)
}


/***/