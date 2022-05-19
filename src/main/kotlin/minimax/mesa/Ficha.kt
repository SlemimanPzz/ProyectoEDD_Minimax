package minimax.mesa

import minimax.actores.Actor

/**
 * Fichas dentro del juego, con su [id] y [propietario].
 *
 * @property id ID de la ficha.
 * @property propietario Propietario de la ficha.
 */
class Ficha (val id: Int, private val propietario: Actor){

    /**
     * Regresa una representación en cadena de la ficha
     *
     * @return una representación en cadena de la ficha
     */
    override fun toString(): String {
        return "%01d $propietario".format(id)
    }

}