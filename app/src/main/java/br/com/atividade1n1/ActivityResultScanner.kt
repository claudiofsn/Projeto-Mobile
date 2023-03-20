package br.com.atividade1n1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import br.com.atividade1n1.databinding.ActivityResultScannerBinding

class ActivityResultScanner : AppCompatActivity() {
    private val b by lazy {
        ActivityResultScannerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState,)
        setContentView(b.root)

        val pizzaId = intent.getIntExtra("id", 0)
        val pizzaNome = intent.getStringExtra("nome")
        val pizzaValor = intent.getDoubleExtra("valor", 0.00)

        b.txtId.text = pizzaId.toString()
        b.txtNome.text = pizzaNome
        b.txtValor.text = pizzaValor.toString()

    }
}