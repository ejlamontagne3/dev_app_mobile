package com.julien.tp_musique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MediaPlayerActivity : AppCompatActivity(), ObservateurChangement {

    var chanson : Musique? = null
    var position : Int = -1
    var estBoutonPlayAppuye = false
    var player : ExoPlayer? = null
    lateinit var playerView : PlayerView
    lateinit var playBtn : ImageButton
    lateinit var suivantBtn : ImageButton
    lateinit var precedantBtn : ImageButton
    lateinit var plusBtn : ImageButton
    lateinit var moinsBtn : ImageButton
    lateinit var maxSeekBar : TextView
    lateinit var duree : TextView
    lateinit var champArtiste : TextView
    lateinit var champChanson : TextView
    lateinit var seekBar: SeekBar
    var ec : Ecouteur? = null
    var minuterie: Minuterie? = null
    var tempsChanson: Int = 0
    var tempsRestant: Int = 0
    var listeParIndex: ArrayList<Int>? = null
    var indexLecture: Int? = null

    lateinit var artisteLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        artisteLayout = findViewById(R.id.artisteLayout)
        suivantBtn = findViewById(R.id.suivantBtn)
        precedantBtn = findViewById(R.id.precedantBtn)
        moinsBtn = findViewById(R.id.moinsBtn)
        plusBtn = findViewById(R.id.plusBtn)
        playBtn = findViewById(R.id.playBtn)
        duree = findViewById(R.id.duree)
        champArtiste = findViewById(R.id.champArtiste)
        champChanson = findViewById(R.id.champChanson)
        maxSeekBar = findViewById(R.id.maxSeekBar)
        seekBar = findViewById(R.id.seekBar)
        playerView = findViewById(R.id.playerView)
        playerView.setUseController(false)

        listeParIndex = ArrayList()

        ec = Ecouteur()

        player = ExoPlayer.Builder(this).build()
        position = intent.getIntExtra("chanson", -1)
        listeParIndex = intent.getIntegerArrayListExtra("listeIndex")


        if (position != -1){
            //On va chercher la chanson choisi par rapport au filtre "genre"
            indexLecture = listeParIndex!!.get(position)
            chanson = SingletonListeMusique.singletonListe.get(indexLecture!!)
        }else{
            finish()
        }

        playBtn.setOnClickListener(ec)
        suivantBtn.setOnClickListener(ec)
        precedantBtn.setOnClickListener(ec)
        plusBtn.setOnClickListener(ec)
        moinsBtn.setOnClickListener(ec)
        artisteLayout.setOnClickListener(ec)
        seekBar.setOnSeekBarChangeListener(ec)


        seekBar.max = chanson!!.duration
        maxSeekBar.setText(seekBar.max.toString())
        tempsRestant = chanson!!.duration
    }

    fun desactiveBtn(){

        suivantBtn.isEnabled = false
        precedantBtn.isEnabled = false
        playBtn.isEnabled = false
        plusBtn.isEnabled = false
        moinsBtn.isEnabled = false
    }

    fun activeBtn(){
        suivantBtn.isEnabled = true
        precedantBtn.isEnabled = true
        playBtn.isEnabled = true
        moinsBtn.isEnabled = true
        plusBtn.isEnabled = true
    }

    fun nouvelleChanson(){
        chanson = SingletonListeMusique.singletonListe.get(indexLecture!!)
        seekBar.progress = 0
        player!!.seekTo(indexLecture!!, 0)
        player!!.play()
        minuterie!!.cancel()
        minuterie = Minuterie((chanson!!.duration*1000).toLong(), 1000)
        minuterie!!.start()
        seekBar.max = chanson!!.duration
        maxSeekBar.text = chanson!!.duration.toString()
        champChanson.text = chanson!!.title
        champArtiste.text = chanson!!.artist
    }

    fun mettreSurPause(){
        playBtn.setImageResource(R.drawable.baseline_pause_24)
        estBoutonPlayAppuye = true
    }

    inner class Ecouteur : View.OnClickListener, SeekBar.OnSeekBarChangeListener{
        override fun onClick(source: View?) {
            if (source == playBtn){
                source.isEnabled = false

                if (estBoutonPlayAppuye){
                    playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
                    player!!.pause()
                    minuterie!!.cancel()
                    estBoutonPlayAppuye = false
                }else{
                    mettreSurPause()
                    player!!.play()
                    minuterie = Minuterie((tempsRestant*1000).toLong(), 1000)
                    minuterie!!.start()
                    maxSeekBar.text = seekBar.max.toString()

                }

                playBtn.isEnabled = true
            }
            else if (source == suivantBtn){
                desactiveBtn()
                position++
                mettreSurPause()

                if (position > listeParIndex!!.size-1){
                    //On remet ca au debut de la playlist
                    position = 0
                    indexLecture = listeParIndex?.get(position)
                }else{
                    indexLecture = listeParIndex?.get(position)
                }

                nouvelleChanson()
                activeBtn()
            }
            else if (source == precedantBtn){
                desactiveBtn()
                position--
                mettreSurPause()

                if (position < 0){
                    //On reste a la premiere chanson de la playlist
                    position = 0
                    indexLecture = listeParIndex?.get(position)
                }else{
                    indexLecture = listeParIndex?.get(position)
                }

                nouvelleChanson()
                activeBtn()
            }
            else if (source == plusBtn){
                desactiveBtn()
                if (tempsChanson +10 >= chanson!!.duration){
                    minuterie!!.onFinish()
                }else{
                    tempsRestant -= 10
                    minuterie!!.cancel()
                    minuterie = Minuterie(((tempsRestant*1000).toLong()), 1000)
                    minuterie!!.start()
                    player!!.seekTo(player!!.currentPosition + 10_000)
                    seekBar.progress = tempsChanson
                    duree.text = seekBar.progress.toString()
                }

                activeBtn()
            }
            else if (source == moinsBtn){
                desactiveBtn()
                if (tempsChanson -10 <= 0){
                    tempsRestant = chanson!!.duration
                    minuterie!!.cancel()
                    minuterie = Minuterie(((tempsRestant*1000).toLong()), 1000)
                    minuterie!!.start()
                    player!!.seekTo(0)
                    seekBar.progress = tempsChanson
                    duree.text = seekBar.progress.toString()
                }else{
                    tempsRestant += 10
                    minuterie!!.cancel()
                    minuterie = Minuterie(((tempsRestant*1000).toLong()), 1000)
                    minuterie!!.start()
                    player!!.seekTo(player!!.currentPosition - 10_000)
                    seekBar.progress = tempsChanson
                    duree.text = seekBar.progress.toString()
                }

                activeBtn()
            }
            else if (source == artisteLayout){
                val i = Intent( Intent.ACTION_VIEW, Uri.parse(chanson?.site))
                startActivity(i)
            }

        }

        override fun onProgressChanged(
            source: SeekBar?,
            value: Int,
            fromUser: Boolean
        ) {
            if (fromUser){
                minuterie?.cancel()
                duree.setText(value.toString())
                tempsChanson = value
                tempsRestant = chanson!!.duration - value
                player?.seekTo( (value*1000).toLong() )
                minuterie = Minuterie((tempsRestant*1000).toLong(), 1000)
                minuterie?.start()
                player?.play()
            }

        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }

    }

    inner class Minuterie(dureeChanson : Long, appel: Long) : CountDownTimer(dureeChanson, appel) {
        override fun onFinish() {
            desactiveBtn()
            position++
            mettreSurPause()

            if (position > listeParIndex!!.size-1){
                position = 0
                indexLecture = listeParIndex?.get(position)
            }else{
                indexLecture = listeParIndex?.get(position)
            }
            nouvelleChanson()
            activeBtn()

        }

        override fun onTick(temps: Long) {

            tempsChanson = (player?.currentPosition!!/1000).toInt()
            tempsRestant = chanson!!.duration - tempsChanson
            seekBar.progress = tempsChanson
            duree.text = tempsChanson.toString()
        }
    }

    fun serealiserChanson(){
        val e = EtatChanson(chanson!!.title, (player!!.currentPosition/1000).toInt())
        val fos : FileOutputStream = openFileOutput("chanson.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream (fos)
        oos.use {
            oos.writeObject(e)
        }
    }

    fun deserealiseChanson() : EtatChanson {

        val fis: FileInputStream = openFileInput("chanson.ser")
        val ois = ObjectInputStream(fis)
        ois.use {
            val e = ois.readObject() as EtatChanson
            return e
        }
    }



    override fun onStart() {
        super.onStart()

        try {
            val e = deserealiseChanson()
            var index = 0
            //On recherche l'index de la derniere chanson joué
            for (chanson in SingletonListeMusique.singletonListe){
                if (chanson.title == e.titre){
                    break
                }
                index += 1
            }
            //ca veut dire quon veut reprendre la chanson la où on était rendu
            if (indexLecture == index){
                this.chanson = SingletonListeMusique.singletonListe.get(indexLecture!!)
                tempsChanson = e.tempsLectureActuel
                tempsRestant = chanson!!.duration - tempsChanson
            }else{
                chanson = SingletonListeMusique.singletonListe.get(indexLecture!!)
            }

        }
        catch (e : Exception){

            chanson = SingletonListeMusique.singletonListe.get(indexLecture!!)

        }

        playerView.player = player

        //On ajoute toutes les chansons au player
        for (chanson in SingletonListeMusique.singletonListe){
            var mp3url = chanson!!.source
            var mediaItem = MediaItem.fromUri(mp3url)
            player?.addMediaItem(mediaItem)
        }

        player?.prepare()
        player?.seekTo(indexLecture!!, 0)
        champChanson.text = chanson!!.title
        champArtiste.text = chanson!!.artist
        mettreSurPause()

        if (tempsRestant < chanson!!.duration){
            player!!.seekTo((tempsChanson*1000).toLong())
            player!!.play()
            minuterie = Minuterie((tempsRestant*1000).toLong(), 1000)
            minuterie!!.start()
            seekBar.max = chanson!!.duration
            duree.text = tempsChanson.toString()
            maxSeekBar.text = chanson!!.duration.toString()
            seekBar.progress = tempsChanson
        }else{
            player!!.play()
            minuterie = Minuterie((chanson!!.duration*1000).toLong(), 1000)
            minuterie!!.start()
            seekBar.max = chanson!!.duration
            maxSeekBar.text = chanson!!.duration.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        serealiserChanson()
        minuterie?.cancel()
        minuterie = null
        player?.release()
        player = null
    }
}