package br.com.atividade1n1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.atividade1n1.databinding.ActivityMainBinding
import br.com.atividade1n1.entity.Pizza
import br.com.atividade1n1.entity.PizzaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.view.Menu
import android.view.MenuItem
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var orderByNome = 1

    var orderByValor = 1

    private val b by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnNovaPizza.setOnClickListener {
            startActivity(Intent(this, NovaPizza::class.java))
        }

        b.lstPizzas.setOnItemLongClickListener { adapterView, view, i, l ->
            val pizzaSelected: Pizza = adapterView.adapter.getItem(i) as Pizza
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Exclusão de Pizza")
                .setMessage("Realmente deseja apagar a Pizza selecionada?")
                .setPositiveButton("Sim") { dialog, wich ->
                    Toast.makeText(this, "Produto removido!", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        PizzaDatabase.getInstance(this@MainActivity).PizzaDao().remove(pizzaSelected)
                        withContext(Dispatchers.Main) {
                            updateList()
                        }
                    }
                }
                .setNegativeButton("Não") { dialog, wich ->
                    Toast.makeText(this, "Produto não foi removido",
                        Toast.LENGTH_SHORT).show()
                }
                .show()
            true
        }
    }

    override fun onResume() {
        super.onResume()

        updateList()
    }

    fun updateList() {
        var pizzas: List<Pizza>

        CoroutineScope(Dispatchers.IO).launch {
            pizzas = PizzaDatabase.getInstance(this@MainActivity).PizzaDao().selectAll()

            withContext(Dispatchers.Main) {
                b.lstPizzas.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    pizzas
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_nome, menu)
        menuInflater.inflate(R.menu.menu_valor, menu)
        menuInflater.inflate(R.menu.menu_close, menu)
        menuInflater.inflate(R.menu.menu_scanner, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuOrderByNome) {
            var list: List<Pizza>? = null

            if (Math.floorMod(orderByNome, 2) == 0){
                CoroutineScope(Dispatchers.IO).launch {
                    list = PizzaDatabase.getInstance(this@MainActivity).PizzaDao().selectPizzaNomeDesc()

                    withContext(Dispatchers.Main) {
                        b.lstPizzas.adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_list_item_1,
                            list!!
                        )
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    list = PizzaDatabase.getInstance(this@MainActivity).PizzaDao().selectPizzaNomeAsc()

                    withContext(Dispatchers.Main) {
                        b.lstPizzas.adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_list_item_1,
                            list!!
                        )
                    }
                }
            }

            orderByNome++
        }

        if (item.itemId == R.id.menuOrderByValor) {
            var list: List<Pizza>? = null

            if (Math.floorMod(orderByValor,2) == 0){
                CoroutineScope(Dispatchers.IO).launch {
                    list = PizzaDatabase.getInstance(this@MainActivity).PizzaDao().selectPizzaValorDesc()

                    withContext(Dispatchers.Main) {
                        b.lstPizzas.adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_list_item_1,
                            list!!
                        )
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    list = PizzaDatabase.getInstance(this@MainActivity).PizzaDao().selectPizzaValorAsc()

                    withContext(Dispatchers.Main) {
                        b.lstPizzas.adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_list_item_1,
                            list!!
                        )
                    }
                }
            }
            orderByValor++
        }

        if (item.itemId == R.id.menuLerQrCode) {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }

        if (item.itemId == R.id.menuClose) {
            finishAffinity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            val result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                } else {
                    val codigo = result.contents.trim().toInt()
                    val pizza: Pizza = PizzaDatabase.getInstance(this@MainActivity).PizzaDao().selectPizzaById(codigo)

                    if(pizza == null) {
                        Toast.makeText(this, "Poduto não encontrado!", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, ActivityResultScanner::class.java)
                        intent.putExtra("id", pizza.Id)
                        intent.putExtra("nome", pizza.nome)
                        intent.putExtra("valor", pizza.valor)
                        startActivity(intent)
                    }

                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}