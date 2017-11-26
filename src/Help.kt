import java.util.*
import kotlin.collections.ArrayList

/**
 * Здесь все функции для бота
 */

//все возможные ходы (от 2 до 4)
fun allMoves(board: List<Int>): ArrayList<List<Int>> {
    val result = ArrayList<List<Int>>()
    if (board.indexOf(16) != 3 && board.indexOf(16) != 7 &&
            board.indexOf(16) != 11 && board.indexOf(16) < 15) {
        result.add(move(board.toMutableList(), board.indexOf(16) + 1))
    }
    if (board.indexOf(16) > 0 && board.indexOf(16) != 4 &&
            board.indexOf(16) != 8 && board.indexOf(16) != 12) {
        result.add(move(board.toMutableList(), board.indexOf(16) - 1))
    }
    if (board.indexOf(16) - 4 >= 0) {
        result.add(move(board.toMutableList(), board.indexOf(16) - 4))
    }
    if (board.indexOf(16) + 4 <= 15) {
        result.add(move(board.toMutableList(), board.indexOf(16) + 4))
    }
    return result
}

//поменять местами элементы на поле, на вход индекс
fun move(list: MutableList<Int>, indexOfElement: Int): List<Int> {
    val a = list[indexOfElement]
    val indexOld16 = list.indexOf(16)
    list[indexOfElement] = 16
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
fun aStar(startBoard: List<Int>): List<List<Int>>? {

    val cameFrom = HashMap<List<Int>, List<Int>>() //откуда мы пришли (родитель)
    val costSoFar = HashMap<List<Int>, Int>() //стоимость хода, показывает, сколько клеток в данной позиции не на своём месте
    val comparator = Comparator.comparingInt<List<Int>>({ costSoFar[it]!! }) //сравнивает значения score в очереди
    val priorityQueue = PriorityQueue<List<Int>>(1000, comparator) //главная очередь с приоритетом, сюда добавляются варианты

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
            println("Рассмотрено вариантов: " + count)
            val result = LinkedList<List<Int>>() //список верных вариантов
            var board = current
            while (board != helpList) {
                result.addFirst(board)
                board = cameFrom[board]
            }
            return result
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
    return null
}

//функция вывод списка нужных ходов для решения. Функция нужна для того, чтобы передать этот список
//в метод, который наглядно всё соберёт. Также можно самому вручную собрать, используя полученную инструкцию
//метод "solutionToScreen" в классе Fifteen
fun roadToFinal(list: List<List<Int>>): List<Int> {
    val result = ArrayList<Int>()
    for (i in 0 until list.size - 1) {
        for (k in 0 until 15) {
            if (list[i][k] != list[i + 1][k]) {
                if (list[i][k] != 16)
                    result.add(list[i][k]) else result.add(list[i + 1][k])
                break
            }
        }
    }
    return result
}



