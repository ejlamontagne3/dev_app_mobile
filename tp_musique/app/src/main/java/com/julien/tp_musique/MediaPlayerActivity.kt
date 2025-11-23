package com.julien.tp_musique

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


class MediaPlayerActivity : AppCompatActivity(), ObservateurChangement {

    var leModele: Sujet? = null
    var chanson : Musique? = null
    var position : Int = -1
    var estBoutonPlayAppuye = false
    var player : ExoPlayer? = null
    lateinit var playerView : PlayerView
    lateinit var playBtn : Button
    lateinit var suivantBtn : Button
    lateinit var precedantBtn : Button

    lateinit var maxSeekBar : TextView
    lateinit var duree : TextView
    lateinit var seekBar: SeekBar
    var ec : Ecouteur? = null
    var minuterie: Minuterie? = null
    var tempsChanson: Int = 0
    var tempsRestant: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        suivantBtn = findViewById(R.id.suivantBtn)
        precedantBtn = findViewById(R.id.precedantBtn)
        playBtn = findViewById(R.id.playBtn)
        duree = findViewById(R.id.duree)
        maxSeekBar = findViewById(R.id.maxSeekBar)
        seekBar = findViewById(R.id.seekBar)
        playerView = findViewById(R.id.playerView)
        playerView.setUseController(false)

        ec = Ecouteur()

        player = ExoPlayer.Builder(this).build()
        position = intent.getIntExtra("chanson", -1)

        if (position != -1){
            chanson = SingletonListeMusique.singletonListe.get(position)
        }else{
            finish()
        }

        playBtn.setOnClickListener(ec)
        suivantBtn.setOnClickListener(ec)
        precedantBtn.setOnClickListener(ec)
        //seekBar.setOnSeekBarChangeListener(ec)


        seekBar.max = chanson!!.duration
        maxSeekBar.setText(seekBar.max.toString())
        tempsRestant = chanson!!.duration
    }

    inner class Ecouteur : View.OnClickListener{
        override fun onClick(source: View?) {
            if (source == playBtn){
                source.isEnabled = false

                if (estBoutonPlayAppuye){
                    playBtn.text = "Play"
                    player!!.pause()
                    minuterie!!.cancel()
                    playBtn.isEnabled = true
                    estBoutonPlayAppuye = false
                }else{
                    playBtn.text = "Pause"
                    player!!.play()
                    minuterie = Minuterie((tempsRestant*1000).toLong(), 1000)
                    minuterie!!.start()
                    maxSeekBar.text = seekBar.max.toString()
                    playBtn.isEnabled = true
                    estBoutonPlayAppuye = true
                }
            }
            if (source == suivantBtn){
                suivantBtn.isEnabled = false
                precedantBtn.isEnabled = false
                playBtn.isEnabled = false
                position++
                playBtn.setText("Pause")
                estBoutonPlayAppuye = true
                seekBar.progress = 0

                if (position > SingletonListeMusique.singletonListe.size){
                    //On remet ca au debut de la playlist
                    position = 0
                }

                player!!.seekTo(position, 0)
                player!!.play()
                minuterie!!.cancel()
                minuterie = Minuterie((chanson!!.duration*1000).toLong(), 1000)
                minuterie!!.start()
                seekBar.max = chanson!!.duration
                suivantBtn.isEnabled = true
                precedantBtn.isEnabled = true
                playBtn.isEnabled = true
            }
            if (source == precedantBtn){
                suivantBtn.isEnabled = false
                precedantBtn.isEnabled = false
                playBtn.isEnabled = false
                position--
                playBtn.setText("Pause")
                estBoutonPlayAppuye = true
                seekBar.progress = 0

                if (position < SingletonListeMusique.singletonListe.size){
                    //On reste a la premiere chanson de la playlist
                    position = 0
                }

                player!!.seekTo(position, 0)
                player!!.play()
                minuterie!!.cancel()
                minuterie = Minuterie((chanson!!.duration*1000).toLong(), 1000)
                minuterie!!.start()
                seekBar.max = chanson!!.duration
                suivantBtn.isEnabled = true
                precedantBtn.isEnabled = true
                playBtn.isEnabled = true
            }
        }

        /*override fun onProgressChanged(
            p0: SeekBar?,
            p1: Int,
            p2: Boolean
        ) {
            TODO("Not yet implemented")
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
            TODO("Not yet implemented")
        }*/
    }

    inner class Minuterie(dureeChanson : Long, appel: Long) : CountDownTimer(dureeChanson, appel) {
        override fun onFinish() {
            suivantBtn.isEnabled = false
            precedantBtn.isEnabled = false
            playBtn.isEnabled = false
            position++
            playBtn.text = "Pause"
            estBoutonPlayAppuye = true

            if (position > SingletonListeMusique.singletonListe.size-1){
                position = 0
            }
            player!!.seekTo(position, 0)
            player!!.play()
            minuterie = Minuterie((chanson!!.duration*1000).toLong(), 1000)
            seekBar.progress = chanson!!.duration
            suivantBtn.isEnabled = true
            precedantBtn.isEnabled = true
            playBtn.isEnabled = true

        }

        override fun onTick(temps: Long) {
            tempsRestant = (temps /1000).toInt()
            tempsChanson = (player?.currentPosition!!/1000).toInt()
            seekBar.progress = tempsChanson
            duree.text = tempsChanson.toString()
        }


    }



    override fun onStart() {
        super.onStart()

        leModele = Modele(this)
        (leModele as Modele).ajouterObservateur(this) // on ajouter l'observateur ( l'activité ) au modèle ( le sujet )

        playerView.player = player

        //On ajoute toutes les chansons au player
        for (chanson in SingletonListeMusique.singletonListe){
            var mp3url = chanson!!.source
            var mediaItem = MediaItem.fromUri(mp3url)
            player?.addMediaItem(mediaItem)
        }

        player?.prepare()
        player?.seekTo(position, 0)
        //titleMediaLabel.setText(chanson!!.title)
    }

    /*override fun changement(musiqueRecu: ListeMusique?, estBoutonPlayAppuye: Boolean) {

    }*/


}