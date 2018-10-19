import java.util.*
import java.lang.Math.*

var symbols = arrayOf ("X", "O")
var fieldSize = 3

fun main(args: Array<String>)
{
    // Get valid field size
    do {
        fieldSize = getInt("Input Field Size: ")!!
    } while (fieldSize <= 0)
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
    for (i in 0..((fieldSize + 1) * 3 + fieldSize - 2))
    {
        for (j in 0..((fieldSize + 1) * 3 + fieldSize - 2))
        {
            if (i == 0)
            {
                if (j % 4 == 0) print(ceil((j / 4).toDouble()).toInt())
                else if (j % 4 == 2) print("|")
                else print(" ")
            }
            else if (i % 2 == 1)
            {
                if (j % 4 == 2) print("|")
                else print(" ")
            }
            else if (i % 4 == 2) print("-")
            else
            {
                if (j == 0) print(ceil((i / 4).toDouble()).toInt())
                else if (j % 2 == 1) print(" ")
                else if (j % 4 == 2) print("|")
                else
                {
                    print(field[ceil((i / 4).toDouble()).toInt() - 1][ceil((j / 4).toDouble()).toInt() - 1])
                }
            }
        }
        print("\n")
    }
}

//TODO: FIX THE GODDAMN INPUT VALIDATION
fun getInt(msg: String): Int?
{
    print(msg)
    return readLine()?.toInt()
}

//Method to start a new "play" instance
fun play(): String
{
    var player = 0
    var x: Int
    var y: Int

    //for ()

    return "Noone!"
}

//fun check(player: Int, x: Int, y: Int): String
//{

//}

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