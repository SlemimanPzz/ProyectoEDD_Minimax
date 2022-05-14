import Estructuras.Lista

class Nodo(private val i : Int, var valor : Ficha?, val vecindad: Lista<Nodo> = Lista<Nodo>()){

    /**
     * Regresa una representation en cadena del nodo, tenga o no ficha
     *
     * @return el nodo
     */
    override fun toString(): String {
        if(valor != null) return "|$valor|"
        if(i == 2) return " |(2)| "
        return "| ($i) |"
    }

    fun atrapado() : Boolean {
        this.vecindad.forEach { if(it.valor == null)  return  false}
        return true
    }

    fun mueveFicha() : Nodo {
        this.vecindad.forEach { if(it.valor == null) {
            it.valor = this.valor
            this.valor = null
            return it
        }}
        return this
    }

}