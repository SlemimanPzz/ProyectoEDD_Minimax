import Estructuras.Lista



class Tablero(private val fichas: Array<Ficha>) {


    private val lista :Lista<Arista> = Lista<Arista>()
    private val nodosTablero: Array<Nodo> = Array(5) {i -> Nodo(i, null)}



    private class Nodo(val i : Int, var valor : Ficha?){

        override fun toString(): String {
            if(valor != null) return "|$valor|"
            if(i == 2) return " |(2)| "
            return "| ($i) |"
        }

    }

    private  class Arista(val a : Int, val b: Int){
    }

    init {

        //Creamos las aristas del tablero
        lista.add(Arista(0, 1))
        lista.add(Arista(0, 2))
        lista.add(Arista(0, 3))
        lista.add(Arista(1, 2))
        lista.add(Arista(1, 4))
        lista.add(Arista(2, 3))
        lista.add(Arista(2, 4))

        nodosTablero[0].valor = fichas[0]
        nodosTablero[1].valor = fichas[3]
        nodosTablero[3].valor = fichas[2]
        nodosTablero[4].valor = fichas[1]


    }



    override fun toString(): String {
        val str = """
            ${nodosTablero[0]}---${nodosTablero[1]}
              |    \ /    |
              |  ${nodosTablero[2]}  |
              |    / \    |
            ${nodosTablero[3]}   ${nodosTablero[4]}
            """.trimIndent()
        return str
    }
}