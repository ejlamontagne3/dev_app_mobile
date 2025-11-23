package com.julien.tp_musique

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

var intent : Intent? = null

class MainActivity : AppCompatActivity(), ObservateurChangement {

    lateinit var listViewChanson : ListView
    var leModele: Sujet? = null
    var v:ArrayList<HashMap<String, Any>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        intent = Intent(this, MediaPlayerActivity::class.java)

        listViewChanson = findViewById(R.id.listViewChanson)
        listViewChanson.setOnItemClickListener { parent, view, position, id ->

            //Toast.makeText(this, v.get(position).getValue("title").toString(), Toast.LENGTH_SHORT).show()
            intent.putExtra("chanson", position)
            startActivity(intent)
        }
    }



    fun remplirListe(liste : ListeMusique): ArrayList<HashMap<String, Any>>{

        var listeDonnees = ArrayList<HashMap<String, Any>>()
        var chanson = HashMap<String, Any>()

        for (i in 0..liste.music.size-1){
            chanson = HashMap()
            //chanson.put("id", liste.music.get(i).id)
            chanson.put("title", liste.music.get(i).title)
            //chanson.put("album", liste.music.get(i).album)
            chanson.put("artist", liste.music.get(i).artist)
            //chanson.put("genre", liste.music.get(i).genre)
            //chanson.put("source", liste.music.get(i).source)
            chanson.put("image", liste.music.get(i).image)
            //chanson.put("trackNumber", liste.music.get(i).trackNumber)
            //chanson.put("totalTrackCount", liste.music.get(i).totalTrackCount)
            //chanson.put("duration", liste.music.get(i).duration)
            //chanson.put("site", liste.music.get(i).site)

            listeDonnees.add(chanson)

        }

        return listeDonnees
    }

    inner class MonAdapter (context: Context, data: List<Map<String, Any>>, resource: Int, from: Array<String>, to: IntArray) :SimpleAdapter( context,data, resource, from, to ){
        @Override
        override fun setViewImage (v: ImageView, value:String){
            Glide.with(this@MainActivity).load(value).into(v);
        }

    }

    override fun onStart() {
        super.onStart()
        leModele = Modele(this@MainActivity)
        (leModele as Modele).ajouterObservateur(this) // on ajouter l'observateur ( l'activité ) au modèle ( le sujet )
    }

    override fun changement(musiqueRecu: ListeMusique?, estBoutonPlayAppuye: Boolean) {
        v = remplirListe(musiqueRecu!!)
        val adapter = MonAdapter(this, v, R.layout.simple_item, arrayOf("title", "artist", "image"), intArrayOf(R.id.titleLabel, R.id.artistLabel, R.id.imageLabel))

        SingletonListeMusique.ajoutListe(musiqueRecu)

        listViewChanson.adapter = adapter
    }
}