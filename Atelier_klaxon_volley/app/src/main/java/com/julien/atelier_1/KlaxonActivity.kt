package com.julien.atelier_1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class KlaxonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_klaxon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crée la queue de requete
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"

// notre requete demande une string au server
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {response ->
                val listeProduits : ListeProduit = Klaxon().parse<ListeProduit>(response) ?: ListeProduit()
                Toast.makeText(this, listeProduits.articles.size.toString(), Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener { Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()  })

// Add the request to the RequestQueue.
        queue.add(stringRequest)




    }
}