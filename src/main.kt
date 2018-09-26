fun main(args: Array<String>) {
    var field = emptyArray<String>(9)
    printField(field)
}

fun printField(field: Array<String>){
    for (i in 0..8)
    {
        print(field[i])
        if (i % 3 = 2) print("\n")
    }
}