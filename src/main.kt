import java.util.*


var field = Array(9) {_ -> "#"}
var symbols = arrayOf ("X", "O")

fun main(args: Array<String>) {
    printField()
    print("The winner is: " + play())
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
    var bla = 0
    for (i in 0..8){

        do {
            val input = Scanner(System.`in`)
            bla = fieldcon(input.nextInt())
        } while (bla < 0 || bla > 8 || field[bla] != "#")

        field[bla] = symbols[player]
        player = (player + 1) % 2
        printField()
        var checked = check(player, bla)
        if (checked == "X") return symbols[0]
        else if (checked == "O") return symbols[1]
    }
    return "Noone!"
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

fun check(player: Int, index: Int): String
{
    if((field[index % 3] == symbols[player] && field[index % 3 + 3] == symbols[player] && field[index % 3 + 6] == symbols[player])) return symbols[player]
    else if(field[index % 3] == symbols[player] && field[index % 3 + 1] == symbols[player] && field[index % 3 + 2] == symbols[player]) return symbols[player]
    else if(index % 2 == 0 && ((field[0] == symbols[player] && field[4] == symbols[player] && field[8] == symbols[player]) || (field[2] == symbols[player] && field[4] == symbols[player] && field[6] == symbols[player]))) return symbols[player]
    else return "#"
}