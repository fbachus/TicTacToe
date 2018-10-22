import java.lang.Math.*

const val MIN_RANGE = 1                             // Must stay positive; minimum selectable field size with 1 being the smallest
const val MAX_RANGE = 9                             // Maximum field size; up to 32767 (though not recommended)
const val DEFAULT_SIZE = 3                          // Default Value

//TODO: let players input their symbol
val SYMBOLS =                                 // Symbols for each player
        arrayOf ("X", "O", "A", "Z", "@", "#")
val MAX_PLAYERS = SYMBOLS.size

const val DEF_CHAR = " "                            // Default Symbol; printed for not yet used fields; cosmetic purpose only

var fieldSize = DEFAULT_SIZE
var players = MAX_PLAYERS

var DEFAULT_RESTART_OPTION = true

fun main(args: Array<String>)
{


    do
    {
        // Get number of players (limitted by symbols avaible)
        players = getInt("Number of players: ", 2..MAX_PLAYERS, 2, true)

        // Get valid field size + create field (2D-String-Array)
        fieldSize = getInt("Input Field Size: ", MIN_RANGE..MAX_RANGE, DEFAULT_SIZE, true)

        // Create empty field
        val field = array2d<String?>(fieldSize, fieldSize) { DEF_CHAR }

        // Start the game and print the winner
        println("The winner is: " + play(field))
        printField(field)
    } while (getYesNo("Play again? (" + getYesNo(DEFAULT_RESTART_OPTION) + ") ", DEFAULT_RESTART_OPTION))
}

// Pretty-Printing the field
// TODO: Implement actual interactive interface
// TODO: Fix off-errors by field coordinates greater than 10
fun printField(field: Array<Array<String?>>)
{
    for (i in 0..(fieldSize * 2))
    {
        for (j in 0..((fieldSize + 1) * 3 + fieldSize - 2))
        {
            if (i == 0)
            {
                when (j%4)
                {
                    0 -> print(ceil((j / 4).toDouble()).toInt())
                    2 -> print("|")
                    else -> print(" ")
                }
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

// Returns an integer: the input if valid, default otherwise
fun getInt(msg: String, range: IntRange, default: Int, canBeNull: Boolean): Int
{
    if (!canBeNull) return getInt(msg, range)
    var tmp: Int?
    do {
        print(msg)
        tmp = readLine()?.toIntOrNull()
    } while (!(tmp in range))
    if (tmp == null) return default
    return tmp
}

// Method overloading to forbid null integer
fun getInt(msg: String, range: IntRange): Int
{
    var tmp: Int?
    do {
        print(msg)
        tmp = readLine()?.toIntOrNull()
    } while (!(tmp in range) || tmp == null)
    return tmp
}

fun getString(msg: String): String?
{
    print(msg)
    return readLine()
}

// Asks for Yes/No input (yes, Yes, y, Y for True; no, No, n, N for False)
fun getYesNo(msg: String, default: Boolean): Boolean
{
    var tmp: String?
    do {
        print(msg)
        tmp = readLine()
        if (tmp.equals(null)) return default
    } while (!(isYes(tmp) || isNo(tmp)))
    return isYes(tmp)
}

fun getYesNo(bool: Boolean):

fun isYes(answer: String?): Boolean
{
    return (answer.equals("yes", true) || answer.equals("y", true))
}

fun isNo(answer: String?): Boolean
{
    return (answer.equals("no", true) || answer.equals("n", true))
}

//Method to start a new "play" instance
fun play(field: Array<Array<String?>>): String
{
    var player = 0      // from 0 to players - 1
    var x: Int
    var y: Int

    for (turn in 1..pow(fieldSize.toDouble(), 2.0).toInt())
    {
        // Setup Phase: Print map and current player
        printField(field)
        println("Player " + (SYMBOLS[player]) + " (" + (player + 1) + "/" + players + ")")

        do
        {
            // Playing Phase: Getting coordinates of field
            x = getInt("Row:    ", 1..(fieldSize)) - 1
            y = getInt("Column: ", 1..(fieldSize)) - 1
        } while (!(field[x][y].equals(DEF_CHAR)))

        // Playing Phase: Update map and check for wins
        field[x][y] = SYMBOLS[player]
        if (check(field, player, x, y)) return SYMBOLS[player]

        // End Phase: Changing player
        player = (player + 1) % players
    }
    return "Noone!"
}

// Checks for a win; X/O if successful, # otherwise
fun check(field: Array<Array<String?>>, player: Int, x: Int, y: Int): Boolean
{
    // Starting recursive search:
    return (checkCardinal(field, player, x, y) || checkDiagonal(field, player, x, y))
}

fun checkCardinal(field: Array<Array<String?>>, player: Int, x: Int, y: Int): Boolean
{
    // Check both cardinal directions:
    return ((fieldExtension(field, player, x, y, -1, 0) && fieldExtension(field, player, x, y, 1, 0)) ||    // Vertical
            (fieldExtension(field, player, x, y, 0, -1) && fieldExtension(field, player, x, y, 0, 1)))      // Horizontal
}

fun checkDiagonal(field: Array<Array<String?>>, player: Int, x: Int, y: Int): Boolean
{
    // check diagonal directions if on middle diagonal:
    if (fieldSize % 2 == 1)
    {
        // Upper left to lower right
        if (x == y) return (fieldExtension(field,  player, x, y, 1, 1) &&
                fieldExtension(field,  player, x, y, -1, -1))
        // Upper right to lower left
        if (x + y == fieldSize - 1) return (fieldExtension(field,  player, x, y, 1, -1) &&
                fieldExtension(field,  player, x, y, -1, 1))
    }
    return false
}

// Checks recursively every lane from the x-y-coordinate
fun fieldExtension(field: Array<Array<String?>>, player: Int, x: Int, y: Int, dx: Int, dy: Int): Boolean
{
    if ((x in 0..(fieldSize - 1)) && (y in 0..(fieldSize - 1)))
    {
        if (!(SYMBOLS[player].equals(field[x][y]))) return false
        else return fieldExtension(field, player,x + dx, y + dy, dx, dy)
    }
    return true
}

// Function to create a 2D Array (copied from StackOverflow and removed redundant modifiers)
// Source: https://stackoverflow.com/questions/27512636/two-dimensional-int-array-in-kotlin
inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }