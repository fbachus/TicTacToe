import java.util.*
import java.lang.Math.*

var fieldSize = 3

const val MAX_RANGE = 20
const val MIN_RANGE = 1

fun main(args: Array<String>)
{
    // Get valid field size
    fieldSize = getInt("Input Field Size: ", MIN_RANGE..MAX_RANGE)!!
    val field = array2d<String?>(fieldSize, fieldSize) { "#" }

    // print the field to ensure it's correctly created
    printField(field)

    // Start the game and print the winner
    //println("The winner is: " + play())
}

// Pretty-Printing the field
//TODO: Implement actual interactive interface
fun printField(field: Array<Array<String?>>)
{
    for (i in 0..(fieldSize * 2))
    {
        for (j in 0..((fieldSize + 1) * 3 + fieldSize - 2))
        {
            if (i == 0)
            {
                if (j % 4 == 0) print(ceil((j / 4).toDouble()).toInt())
                else if (j % 4 == 2) print("|")
                else print(" ")
            }
            else if (i % 2 == 1) print("-")
            else
            {
                if (j == 0) print(ceil((i / 2).toDouble()).toInt())
                else if (j % 2 == 1) print(" ")
                else if (j % 4 == 2) print("|")
                else
                {
                    print(field[ceil((i / 2).toDouble()).toInt() - 1][ceil((j / 4).toDouble()).toInt() - 1])
                }
            }
        }
        print("\n")
    }
}

//TODO: FIX THE GODDAMN INPUT VALIDATION
fun getInt(msg: String, range: IntRange): Int?
{
    var tmp: Int
    print(msg)
    do {
        tmp = readLine()?.toInt()!!
    } while (!(tmp in range))
}

//Method to start a new "play" instance
fun play(field: Array<Array<String?>>): String
{
    var symbols = arrayOf ("X", "O")
    var player = 0
    var x: Int
    var y: Int

    for (turn in 0..pow(fieldSize.toDouble(), 2.0).toInt())
    {
        // Starting Phase: Resetting vars, getting new field from new player
        println("Player " + (player + 1))
        x = getInt("Row:    ", MIN_RANGE..MAX_RANGE)!!
        y = getInt("Column: ", MIN_RANGE..MAX_RANGE)!!

        // Playing Phase: Update map and check for wins
        field[x][y] = symbols[player]
        if (check(field, symbols[player], x, y)) return symbols[player]

        // End Phase: Changing player
        player = player % 2 + 1
    }
    return "Noone!"
}

// Checks for a win; X/O if successful, # otherwise
fun check(field: Array<Array<String?>>, player: String, x: Int, y: Int): Boolean
{
    for (dx in -1..1)
    {
        for (dy in -1..1)
        {
            if (fieldExtension(field,  player, x, y, dx, dy)) return true
        }
    }
    return false
}

// checks recursively every lane from the x-y-coordinate
fun fieldExtension(field: Array<Array<String?>>, player: String, x: Int, y: Int, dx: Int, dy: Int): Boolean
{
    if ((x in MIN_RANGE..MAX_RANGE) && (y in MIN_RANGE..MAX_RANGE))
    {
        if (player != field[x][y]) return false
        else return adjFields(field, player,x + dx, y + dy, dx, dy)
    }
    else return true
}

// Functions to create a 2D Array (copied straight from StackOverflow): https://stackoverflow.com/questions/27512636/two-dimensional-int-array-in-kotlin
public inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }
public fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
        = Array(sizeOuter) { IntArray(sizeInner) }
public fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray>
        = Array(sizeOuter) { LongArray(sizeInner) }
public fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray>
        = Array(sizeOuter) { ByteArray(sizeInner) }
public fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray>
        = Array(sizeOuter) { CharArray(sizeInner) }
public fun array2dOfBoolean(sizeOuter: Int, sizeInner: Int): Array<BooleanArray>
        = Array(sizeOuter) { BooleanArray(sizeInner) }