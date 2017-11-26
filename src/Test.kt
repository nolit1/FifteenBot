import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun moveT() {
        val start = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 12, 13, 14, 15, 11)
        /* начальное положение (start), тестовая сборка, просто чтобы показать работоспособность метода move
    *  1. 2. 3. 4
    *  5. 6. 7. 8
    *  9.10.16.12
    * 13.14.15.11
    * */
        val moveLeft = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 10, 12, 13, 14, 15, 11)
        val moveUp = intArrayOf(1, 2, 3, 4, 5, 6, 16, 8, 9, 10, 7, 12, 13, 14, 15, 11)
        val moveRight = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 16, 13, 14, 15, 11)
        val moveDown = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 12, 13, 14, 16, 11) //доделать
        assertEquals(moveLeft.toList(), move(start.toMutableList(), 9))
        assertEquals(moveUp.toList(), move(start.toMutableList(), 6))
        assertEquals(moveRight.toList(), move(start.toMutableList(), 11))
        assertEquals(moveDown.toList(), move(start.toMutableList(), 14))
    }

    /* клетки
*  1. 2. 3. 4
*  5. 6. 7. 8
*  9.10.11.12
* 13.14.15.16
* */

    /* индексы
*   0.  1.  2.  3
*   4.  5.  6.  7
*   8.  9. 10. 11
*  12. 13. 14. 15
* */

    @Test
    fun howManyFalseT() {
        val a = intArrayOf(1, 2, 3, 4, 6, 5, 7, 9, 8, 11, 16, 12, 13, 14, 15, 10)
        val b = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        val c = intArrayOf(1, 2, 3, 13, 5, 6, 7, 8, 9, 10, 11, 4, 12, 14, 15, 16)
        assertEquals(7, howManyFalse(a.toList()))
        assertEquals(0, howManyFalse(b.toList()))
        assertEquals(3, howManyFalse(c.toList()))
    }

    @Test
    fun allMovesT()  {
    /* клетки
    *  1. 2. 3. 4
    *  5. 6. 7. 8
    *  9.16.11.12
    * 13.14.15.10
    * */

        val start = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 11, 12, 13, 14, 15, 10)
        val b = intArrayOf(1, 2, 3, 4, 5, 16, 7, 8, 9, 6, 11, 12, 13, 14, 15, 10)
        val c = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 14, 11, 12, 13, 16, 15, 10)
        val d = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 16, 12, 13, 14, 15, 10)
        val e = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 16, 9, 11, 12, 13, 14, 15, 10)
        val res = ArrayList<List<Int>>()
        res.add(d.toList())
        res.add(e.toList())
        res.add(b.toList())
        res.add(c.toList())
        //Добавление именно в таком порядке, так проверяет ходы бот (право, лево, верх, низ)
        assertEquals(res, allMoves(start.toList()))
    }

    @Test
    fun isBoardFinalT() {
        val a = intArrayOf(1, 3, 2, 4, 9, 6, 7, 8, 5, 16, 11, 12, 13, 14, 15, 10)
        val b = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16) //правильно собран
        val c = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 11, 12, 13, 14, 15, 10)
        val d = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 14, 15, 12, 13, 16, 11, 10)

        assertEquals(true, isBoardFinal(b.toList()))
        assertEquals(false, isBoardFinal(a.toList()))
        assertEquals(false, isBoardFinal(c.toList()))
        assertEquals(false, isBoardFinal(d.toList()))
    }

    //Тест проверяет сразу 2 функции, ибо по отдельности я не знаю, как их ещё можно проверить.
    //В данном случае, если хоть одна работает неправильно - рухнет всё. Но оно работает правильно, я старался :)
    @Test
    fun aStarAndRoadToFinal(){
        val start = intArrayOf(6, 5, 2, 3, 13, 10, 7, 4, 9, 1, 16, 8, 14, 15, 11, 12) //начальное положение
        val check = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16) //с ним сравниваем (решённый)
        val b = aStar(start.toList())
        val c = roadToFinal(b!!)
        var helpMass = start.toList()
        c.asSequence().map { helpMass.indexOf(it) }.forEach { helpMass = move(helpMass.toMutableList(), it) }
        assertEquals(check.toList(), helpMass)

        val start2 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 11, 13, 16, 14, 12) //начальное положение
        val check2 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16) //с ним сравниваем (решённый)
        val a2 = aStar(start2.toList())
        val c2 = roadToFinal(a2!!)
        var helpMass2 = start2.toList()
        c2.asSequence().map { helpMass2.indexOf(it) }.forEach { helpMass2 = move(helpMass2.toMutableList(), it) }
        assertEquals(check2.toList(), helpMass2)
    }
}