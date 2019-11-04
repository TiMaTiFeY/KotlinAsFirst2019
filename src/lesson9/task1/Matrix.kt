@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.StringBuilder

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private var dataMatrix = mutableListOf<MutableList<E>>()
    private var maxValueLength: Int

    init {
        require(height > 0 && width > 0)
        for (i in 0 until height) {
            val currentRow = mutableListOf<E>()
            for (j in 0 until width) currentRow.add(e)
            dataMatrix.add(currentRow)
        }
        maxValueLength = e.toString().length
    }

    override fun get(row: Int, column: Int): E = dataMatrix[row][column] ?: throw IllegalArgumentException()

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        require(row in 0 until height && column in 0 until width)
        dataMatrix[row][column] = value
        val len = value.toString().length
        if (maxValueLength < len) maxValueLength = len
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) = other is MatrixImpl<*> &&
            height == other.height &&
            width == other.width &&
            dataMatrix == other.dataMatrix

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (row in 0 until height) {
            if (row == 0) sb.append("[") else sb.append(" [")
            for (column in 0 until width)
                if (row == height - 1)
                    if (column == width - 1) sb.append(String.format("%${maxValueLength}s]", this[row, width - 1]))
                    else sb.append(String.format("%${maxValueLength}s, ", this[row, column]))
                else
                    if (column == width - 1) sb.append(String.format("%${maxValueLength}s],\n", this[row, width - 1]))
                    else sb.append(String.format("%${maxValueLength}s, ", this[row, column]))
        }
        sb.append("]")
        return "$sb"
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        return result
    }
}