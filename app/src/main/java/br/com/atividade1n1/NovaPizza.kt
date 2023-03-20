package br.com.atividade1n1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import br.com.atividade1n1.databinding.ActivityNovaPizzaBinding
import br.com.atividade1n1.entity.Pizza
import br.com.atividade1n1.entity.PizzaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NovaPizza : AppCompatActivity() {
    private val b by lazy {
        ActivityNovaPizzaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnSalvar.setOnClickListener {
            val pizza = Pizza(
                b.edtNome.text.toString(),
                b.edtValor.text.toString().toDouble()
            )

            CoroutineScope(Dispatchers.IO).launch {
                PizzaDatabase.getInstance(this@NovaPizza).PizzaDao().save(pizza)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NovaPizza, "Pizza salva com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}