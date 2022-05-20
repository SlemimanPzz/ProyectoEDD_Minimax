package minimax.mesa

import Estructuras.Lista

/**
 * Nodos que se utilizaran en [Tablero] para jugar el juego.
 *
 * @property i ID del Minimax.Mesa.Nodo.
 * @property valor La ficha contenida en el nodo, si es que la tiene.
 * @property vecindad Vecindad del nodo.
 */
class Nodo(val i : Int, var valor : Ficha?, val vecindad: Lista<Nodo> = Lista<Nodo>()){

    /**
     * Regresa una representación en cadena del nodo, tenga o no ficha.
     * @return una representación en cadena del nodo, tenga o no ficha.
     */
    override fun toString(): String {
        if(valor != null) return "|$valor|"
        if(i == 2) return " |(2)| "
        return "| ($i) |"
    }

    /**
     * Nos dice si el nodo esta atrapado.
     * @return `false` si no esta atrapado, `true` si lo esta.
     */
    fun atrapado() : Boolean {
        this.vecindad.forEach { if(it.valor == null)  return  false}
        return true
    }

    /**
     * Mueve la ficha al nodo disponible.
     * @return Regresa el nodo al que fue la ficha.
     */
    fun mueveFicha() : Nodo {
        this.vecindad.forEach { if(it.valor == null) {
            it.valor = this.valor
            this.valor = null
            return it
        }}
        throw RuntimeException()
    }

}