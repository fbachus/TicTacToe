import java.lang.Math.*

const val MIN_RANGE = 1     // Must stay positive; limitting size of game with 1 being the smallest
const val MAX_RANGE = 20    // Maximum field size; up to 32767 (though not recommended)

var fieldSize: Int

fun main(args: Array<String>)
{
    // Get valid field size + create field (2D-String-Array)
    var fieldSize = getInt("Input Field Size: ", MIN_RANGE..MAX_RANGE)!!
    val field = array2d<String?>(fieldSize, fieldSize) { "#" }

    // Start the game and print the winner
    println("The winner is: " + play(field))
}

// Pretty-Printing the field
// TODO: Implement actual interactive interface
// TODO: Changing 'if' construct to 'when' construct for aesthetic reasons
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

// TODO: Fix input validation; >> currently not working
fun getInt(msg: String, range: IntRange): Int?
{
    var tmp: Int
    print(msg)
    do {
        tmp = readLine()?.toInt()!!
    } while (!(tmp in range))
    return tmp
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
        // Setup Phase: Print map and current player
        printField(field)
        println("Player " + (player + 1))

        // Playing Phase: Getting coordinates of field
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
    // Starting recursive search; in either kardinal direction, and, if on the middle diagonal line, in those
    if (fieldExtension(field,  player, x, y, ran, ran - 1) &&
            fieldExtension(field,  player, x, y, -ran, -ran + 1)) return true
    if ((fieldSize % 2 == 1) && abs(x) == abs(y)) return ((fieldExtension(field,  player, x, y, 1, 1) &&
                                                            fieldExtension(field,  player, x, y, -1, -1)) ||
                                                            (fieldExtension(field,  player, x, y, 1, -1) &&
                                                            fieldExtension(field,  player, x, y, -1, 1)))
    return false
}

// Checks recursively every lane from the x-y-coordinate
fun fieldExtension(field: Array<Array<String?>>, player: String, x: Int, y: Int, dx: Int, dy: Int): Boolean
{
    if ((x in 0 until fieldSize - 1) && (y in 0 until fieldSize - 1))
    {
        if (player != field[x][y]) return false
        else return fieldExtension(field, player,x + dx, y + dy, dx, dy)
    }
    else return true
}

// Function to create a 2D Array (copied from StackOverflow and removed redundant modifiers)
// Source: https://stackoverflow.com/questions/27512636/two-dimensional-int-array-in-kotlin
inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }