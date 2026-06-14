package com.example.restaurantapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import android.widget.EditText
import android.widget.TextView
import com.example.restaurantapp.model.CuentaMesa
import com.example.restaurantapp.model.ItemMenu
import com.example.restaurantapp.model.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var edtPastel: EditText
    private lateinit var edtCazuela: EditText

    private lateinit var txtSubPastel: TextView
    private lateinit var txtSubCazuela: TextView

    private lateinit var txtComida: TextView
    private lateinit var txtPropina: TextView
    private lateinit var txtTotal: TextView

    private lateinit var swPropina: SwitchCompat

    private val formatoCL =
        NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtPastel = findViewById(R.id.edtPastel)
        edtCazuela = findViewById(R.id.edtCazuela)

        txtSubPastel = findViewById(R.id.txtSubPastel)
        txtSubCazuela = findViewById(R.id.txtSubCazuela)

        txtComida = findViewById(R.id.txtComida)
        txtPropina = findViewById(R.id.txtPropina)
        txtTotal = findViewById(R.id.txtTotal)

        swPropina = findViewById(R.id.swPropina)

        edtPastel.addTextChangedListener(textWatcher)
        edtCazuela.addTextChangedListener(textWatcher)

        swPropina.setOnCheckedChangeListener { _, _ ->
            calcularTotales()
        }

        calcularTotales()
    }

    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {}

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            calcularTotales()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun calcularTotales() {

        val cantidadPastel =
            edtPastel.text.toString().toIntOrNull() ?: 0

        val cantidadCazuela =
            edtCazuela.text.toString().toIntOrNull() ?: 0

        val pastel = ItemMenu(
            "Pastel de Choclo",
            12000
        )

        val cazuela = ItemMenu(
            "Cazuela",
            10000
        )

        val itemPastel = ItemMesa(
            pastel,
            cantidadPastel
        )

        val itemCazuela = ItemMesa(
            cazuela,
            cantidadCazuela
        )

        val cuenta = CuentaMesa()

        cuenta.aceptaPropina = swPropina.isChecked

        cuenta.agregarItem(itemPastel)
        cuenta.agregarItem(itemCazuela)

        txtSubPastel.text =
            formatoCL.format(itemPastel.calcularSubtotal())

        txtSubCazuela.text =
            formatoCL.format(itemCazuela.calcularSubtotal())

        txtComida.text =
            "Comida: ${formatoCL.format(cuenta.calcularTotalSinPropina())}"

        txtPropina.text =
            "Propina: ${formatoCL.format(cuenta.calcularPropina())}"

        txtTotal.text =
            "TOTAL: ${formatoCL.format(cuenta.calcularTotalConPropina())}"
    }
}