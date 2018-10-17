import java.util.*


var field = Array(9) {_ -> "#"}
var symbols = arrayOf ("X", "O")

fun main(args: Array<String>) {
    println("The winner is: " + play())
    printField()
}

fun printField(){
    for (i in 0..8)
    {
        print(field[i])
        if (i % 3 == 2) print("\n")
    }
}

fun play(): String
{
    var player = 0
    var index = 0
    for (i in 0..8){
        printField()
        println("player: " + symbols[player] + "\n")
        do {
            val input = Scanner(System.`in`)
            index = fieldcon(input.nextInt())
        } while (index < 0 || index > 8 || field[index] != "#")
        println("Field:  " + index.toString())
        field[index] = symbols[player]
        var checked = check(player, index)
        if (checked == "X" || checked == "O") return checked
        player = (player + 1) % 2
    }
    return "Noone!"
}

fun fieldcon(index: Int): Int
{
    return when (index)
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

fun check(player: Int, index: Int): String
{
    if ((field[index % 3] == symbols[player] && field[index % 3 + 3] == symbols[player] && field[index % 3 + 6] == symbols[player]) ||
            (field[index % 3] == symbols[player] && field[index % 3 + 1] == symbols[player] && field[index % 3 + 2] == symbols[player]) ||
            (index % 2 == 0 && ((field[0] == symbols[player] && field[4] == symbols[player] && field[8] == symbols[player]) ||
            (field[2] == symbols[player] && field[4] == symbols[player] && field[6] == symbols[player])))) return symbols[player]
    else return "#"
}