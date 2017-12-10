import java.util.*
import kotlin.collections.ArrayList

/**
 * Здесь все функции для бота
 */

val mMax = Fifteen.getROOTOFBOARDSIZE()
val max = mMax * mMax

fun allMoves(board: List<Int>): ArrayList<List<Int>> {
    val result = ArrayList<List<Int>>()

    var cash = 0
    var k = -1
    while (k < max - 1) {
        k += mMax
        if (board.indexOf(max) != k) cash++
    }
    if (cash == mMax) result.add(move(board.toMutableList(), board.indexOf(max) + 1))

    cash = 0
    k = 0

    while (k < max) {
        k += mMax
        if (board.indexOf(max) != k) cash++
    }
    if (cash == mMax) result.add(move(board.toMutableList(), board.indexOf(max) - 1))

    if (board.indexOf(max) - mMax >= 0) {
        result.add(move(board.toMutableList(), board.indexOf(max) - mMax))
    }
    if (board.indexOf(max) + mMax <= max - 1) {
        result.add(move(board.toMutableList(), board.indexOf(max) + mMax))
    }
    return result
}


//поменять местами элементы на поле, на вход индекс
fun move(list: MutableList<Int>, indexOfElement: Int): List<Int> {
    val a = list[indexOfElement]
    val indexOld16 = list.indexOf(max)
    list[indexOfElement] = max
    list[indexOld16] = a
    return list
}

//функция - количество неправильных клеток
fun howManyFalse(board: List<Int>): Int {
    var count = 1
    var result = 0
    for (n in board) {
        if (board.indexOf(n) != n - 1) {
            result++
        }
        count++
    }

    return result
}


//функция - является ли поле решённым
fun isBoardFinal(board: List<Int>): Boolean {
    return howManyFalse(board) == 0
}

//вызывается для поля
fun aStar(startBoard: List<Int>): List<List<Int>> {

    val cameFrom = HashMap<List<Int>, List<Int>>() //откуда мы пришли (родитель)
    val costSoFar = HashMap<List<Int>, Int>() //стоимость хода, показывает, сколько клеток в данной позиции не на своём месте
    val comparator = Comparator.comparingInt<List<Int>>({ costSoFar[it]!! }) //сравнивает значения score в очереди
    val priorityQueue = PriorityQueue<List<Int>>(1000, comparator) //главная очередь с приоритетом, сюда добавляются варианты
    val result = LinkedList<List<Int>>() //список верных вариантов
    var finalBoard = ArrayList<Int>()

    val helpList = java.util.ArrayList<Int>() //вспомогательный лист, просто нужен для уникальности начального положения
    helpList.add(1)

    cameFrom.put(startBoard, helpList) //добавляем начальное положение
    costSoFar.put(startBoard, howManyFalse(startBoard)) //его стоимость
    priorityQueue.add(startBoard) //добавляем в очередь
    var count = 0 //считает количество рассмотренных вариантов

    while (priorityQueue.size > 0) { //пока существует очередь выполняем
        val current = priorityQueue.remove() //удаляем из очереди элемент и сохраняем в переменную, это наше текущее состояние
        count++

        if (isBoardFinal(current)) { //если решение найдено
            finalBoard = current as ArrayList<Int>
            break
        }

        val moves = allMoves(current)
        for (board in moves) { //для каждого варианта хода рассматриваем
            if (!cameFrom.containsKey(board)) {
                cameFrom.put(board, current)
                costSoFar.put(board, 1 + howManyFalse(board))
                priorityQueue.add(board)
            }
        }
    }
    println("Рассмотрено вариантов: " + count)
    var board = finalBoard
    while (board != helpList) {
        result.addFirst(board)
        board = cameFrom[board] as ArrayList<Int>
    }
    println("Для решения нужно ходов: " + result.size)
    return result
}

//функция вывод списка нужных ходов для решения. Функция нужна для того, чтобы передать этот список
//в метод, который наглядно всё соберёт. Также можно самому вручную собрать, используя полученную инструкцию
//метод "solutionToScreen" в классе Fifteen
fun roadToFinal(list: List<List<Int>>): List<Int> {
    val result = ArrayList<Int>()
    for (i in 0 until list.size - 1) {
        for (k in 0 until max -1) {
            if (list[i][k] != list[i + 1][k]) {
                if (list[i][k] != max)
                    result.add(list[i][k]) else result.add(list[i + 1][k])
                break
            }
        }
    }
    return result
}



