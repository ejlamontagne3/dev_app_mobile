package com.julien.tp_musique

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity(), ObservateurChangement {

    lateinit var listViewChanson : ListView
    lateinit var chercherBtn : Button
    lateinit var champGenre : EditText
    var leModele: Sujet? = null
    var genre: String? = null
    var v:ArrayList<HashMap<String, Any>> = ArrayList()
    var listeMusiqueGlobal : ListeMusique? = null
    var listeParIndex : ArrayList<Int>? = null
    lateinit var lanceur: ActivityResultLauncher<Intent>
    var u: Utilisateur? = null

    lateinit var texteSalutation: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackIdentification()
        )

        intent = Intent(this, MediaPlayerActivity::class.java)
        listeParIndex = ArrayList()

        listViewChanson = findViewById(R.id.listViewChanson)
        chercherBtn = findViewById(R.id.chercherBtn)
        champGenre = findViewById(R.id.champGenre)
        texteSalutation = findViewById(R.id.texteSalutation)

        listViewChanson.setOnItemClickListener { parent, view, position, id ->

            intent.putExtra("chanson", position)
            intent.putIntegerArrayListExtra("listeIndex", listeParIndex)
            startActivity(intent)
        }

        chercherBtn.setOnClickListener {

            if (champGenre.text.toString().isNotEmpty()){

                genre = champGenre.text.toString().lowercase()
                mettreAJourListeLecture()

            }
        }

        //Boomrang pour s'identifier
        texteSalutation.setOnClickListener {
            val intent = Intent(this, IdentificationActivity::class.java)
            lanceur.launch(intent)
        }

        //Recuperer l'identifiant dabs le saveInstanceState
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            u = savedInstanceState?.getSerializable("util", Utilisateur::class.java)

        }else{
            u = savedInstanceState?.getSerializable("util") as Utilisateur
        }

        if (u?.nom != null){
            texteSalutation.text = "Bienvenue ${u?.nom}"
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("util", u)
    }

    inner class CallBackIdentification : ActivityResultCallback<ActivityResult> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onActivityResult(result: ActivityResult) {

            if (result.resultCode == RESULT_OK) {
                val intent = result.data

                val utilisateur = intent?.getSerializableExtra("utilisateur", Utilisateur::class.java)

                if (utilisateur != null) {
                    u = utilisateur
                    texteSalutation.text = "Bienvenue ${u?.nom}"
                }
            }
        }
    }


    fun mettreAJourListeLecture() {
        champGenre.setText("")
        val listeDonnees = ArrayList<HashMap<String, Any>>()
        listeParIndex = ArrayList()
        val titresAjoutes = HashSet<String>()

        for (i in 0 .. SingletonListeMusique.singletonListe.size-1) {
            val item = SingletonListeMusique.singletonListe[i]
            if (item.genre.lowercase() == genre) {
                val titre = item.title.trim()
                if (titresAjoutes.add(titre)) { // pour eviter un bug de doublons
                    val chanson = HashMap<String, Any>()
                    chanson["title"] = titre
                    chanson["artist"] = item.artist
                    chanson["genre"] = item.genre
                    chanson["image"] = item.image
                    listeDonnees.add(chanson)
                    listeParIndex?.add(i)
                }
            }
        }

        val adapter = MonAdapter(
            this, listeDonnees, R.layout.simple_item,
            arrayOf("title","artist","genre","image"),
            intArrayOf(R.id.titleLabel,R.id.artistLabel,R.id.genreLabel,R.id.imageLabel)
        )

        listViewChanson.adapter = adapter
    }

    fun remplirListe(liste : ListeMusique): ArrayList<HashMap<String, Any>>{
        listeParIndex = ArrayList()
        var listeDonnees = ArrayList<HashMap<String, Any>>()

        for (i in 0..liste.music.size-1){
            val chanson = HashMap<String, Any>()
            chanson.put("title", liste.music.get(i).title)
            chanson.put("artist", liste.music.get(i).artist)
            chanson.put("genre", liste.music.get(i).genre)
            chanson.put("image", liste.music.get(i).image)

            listeDonnees.add(chanson)
            listeParIndex?.add(i)

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
        listeMusiqueGlobal = musiqueRecu

        v = remplirListe(musiqueRecu!!)
        val adapter = MonAdapter(this, v, R.layout.simple_item, arrayOf("title", "artist", "genre", "image"), intArrayOf(R.id.titleLabel, R.id.artistLabel, R.id.genreLabel, R.id.imageLabel))

        SingletonListeMusique.ajoutListe(musiqueRecu)

        listViewChanson.adapter = adapter
    }
}