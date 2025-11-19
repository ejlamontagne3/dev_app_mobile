package com.julien.tp_musique

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


class MainActivity : AppCompatActivity() {

    var maMusique : ListeMusique? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Crée la queue de requete
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"

        // notre requete demande une string au server
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {response ->
                maMusique = Klaxon().parse<ListeMusique>(response) ?: ListeMusique()
                Toast.makeText(this, maMusique!!.music.size.toString(), Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener { Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()  })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

        var nombreChason : Int = lire(maMusique)
    }

    fun lire(liste : ListeMusique?): Int{
        return maMusique!!.music!!.size
    }

}