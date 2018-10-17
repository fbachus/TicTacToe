import java.util.*

fun main(args: Array<String>) {
    var field = Array<String>(9) {_ -> "#"}
    play(field)
    printField(field)
}

fun printField(field: Array<String>){
    for (i in 0..8)
    {
        print(field[i])
        if (i % 3 == 2) print("\n")
    }
}

fun play(field: Array<String>)
{
    var curSindex = 0
    var symbols = arrayOf ("X", "O")
    var bla = 0
    for (i in 0..8){

        do {
            val input = Scanner(System.`in`)
            bla = fieldcon(input.nextInt())
        } while (bla < 0 || bla > 8 || field[bla] != "#")


        field[bla] = symbols[curSindex]
        curSindex = (curSindex + 1) % 2
        printField(field)
        if (check(field) == "X" || check(field) == "O")
            return check(field)
    }
}

fun fieldcon(bla:Int): Int
{
    return when (bla)
    {
        1 -> 6
        2 -> 7
        3 -> 8
        4 -> 3
        5 -> 4
        6 -> 5
        7 -> 0
        8 -> 1
        9 -> 2
        else -> 42
    }
}

fun check(field: Array<Int>): String
{
    
}