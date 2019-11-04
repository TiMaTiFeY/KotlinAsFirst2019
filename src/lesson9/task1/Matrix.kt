@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

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
    private val dataMatrix = List(height) { MutableList(width) { e } }
    private var maxValueLength = e.toString().length

    init {
        require(height > 0 && width > 0)
    }

    private fun checkIn(row: Int, column: Int): Boolean = row in 0 until height && column in 0 until width
    override fun get(row: Int, column: Int): E =
        if (checkIn(row, column)) dataMatrix[row][column] else throw IllegalArgumentException()

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        require(checkIn(row, column))
        dataMatrix[row][column] = value
        val len = value.toString().length
        if (maxValueLength < len) maxValueLength = len
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) = other is MatrixImpl<*> &&
            height == other.height &&
            width == other.width &&
            dataMatrix == other.dataMatrix

    override fun toString(): String =
        dataMatrix.joinToString(
            prefix = "{Matrix h=${height}, w=${width}:\n",
            separator = ",\n",
            postfix = "}"
        ) {
            it.joinToString(
                prefix = " [",
                separator = ", ",
                postfix = "]"
            ) { it2 -> String.format("%${maxValueLength}s", it2) }
        }

    override fun hashCode() = dataMatrix.hashCode()
}