package com.example.restaurantapp.model

class CuentaMesa {

    private val items = mutableListOf<ItemMesa>()

    var aceptaPropina = true

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina)
            (calcularTotalSinPropina() * 0.10).toInt()
        else
            0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}