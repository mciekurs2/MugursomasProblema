import java.util.*

fun main (args: Array<String>){
    do {
        var sc = Scanner(System.`in`)

        println("Ievadiet pirmo skaitli: ")
        while (!sc.hasNextInt()){
            println("Ievadiet pareizu mainīgo: ")
            sc.next()
        }
        var n = sc.nextInt()

        println("Ievadiet otro skaitli: ")
        while (!sc.hasNextInt()){
            println("Ievadiet pareizu mainīgo: ")
            sc.next()

        }
        var m = sc.nextInt()

        println("$n inversā mod $m aprēķināšana..")
        dabutInverso(n, m)

        println("Veikt jaunu apr.?: (yes/no)")
        var end = sc.next()
    } while (end != "no")
}

fun dabutInverso(n: Int, m: Int){
    println("-----------------")
    var x = 1 //izmanto reizināšanai piem. 13*1, 13*2, 13*3
    while (x<m){
        var atk = (n*x) % m
        var pagRez = n*x
        println("$x. $n * $x = $pagRez ≡ $atk(atlikums)")
        if (atk == 1){
            println("-----------------")
            var reiz = (n*x) / m
            var pirmsAtl = reiz * m
            println("$n inverais no mod $m ir $x, jo $reiz * $m = $pirmsAtl un $pagRez - $pirmsAtl = 1")
            return //pabeidz metodi, jo inversais tika atrasts
        }
        x++
    }
    println("-----------------")
    println("Inversais neeksistē, jo nav lielākais kopīgais dalītājs ($n, $m) = 1")
}