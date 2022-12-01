package dgitc.unam.modulosiete

import android.Manifest
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dgitc.unam.modulosiete.databinding.ActivitySonidoBinding
import dgitc.unam.modulosiete.models.SonidoAdapter
import dgitc.unam.modulosiete.models.SonidoModelo

import java.io.File

class SonidoActivity : AppCompatActivity() {
    private lateinit var archivos: ArrayList<SonidoModelo>
    private lateinit var binding: ActivitySonidoBinding
    private lateinit var adap: SonidoAdapter
    private var mediaPlayer: MediaPlayer? = null
    private var indice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySonidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val stadoSD: String = Environment.getExternalStorageState()

        if (stadoSD == Environment.MEDIA_MOUNTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), 1
            )
            println(
                "sistema=: " +
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path
            )
            println("sistema2: " + this.getExternalFilesDir(null))
            println(Environment.DIRECTORY_MUSIC)
            //val ff = File(this.getExternalFilesDir(null)?.path ?: "")
            val ff =    File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MUSIC).path)
            print("ajksdjaklsjdkajdklas")
            if(ff === null)  {
                println("ff es nula")
            }
            else{
                println("No es nula")
                println(ff)
                println(ff.listFiles())
            }

            archivos = ArrayList()
            ff.listFiles()?.forEach {
                if (it.isFile) {
                    println(it)
                    archivos.add(SonidoModelo(it.name, R.drawable.musica_img, it.path))
                }
            }
            archivos.add(
                SonidoModelo(
                    "Magenta_Moon_Part_II.mp3", R.drawable.musica_img,
                    "https://files.freemusicarchive.org/storage-freemusicarchiveorg/music/no_curator/Line_Noise/Magenta_Moon/Line_Noise_-_01_-_Magenta_Moon_Part_II.mp3"
                )
            )
        }
        adap = SonidoAdapter(this, archivos)
        binding.list.adapter = adap
        binding.play.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(archivos.get(indice).path)
                    prepare()
                    start()
                }
            } else {
                mediaPlayer!!.start()
            }
        }
        binding.stop.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }
        }
        binding.pause.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer!!.pause()
            }
        }
        binding.path.setOnClickListener {
            Toast.makeText(this, archivos.get(indice).path, Toast.LENGTH_SHORT).show()
        }
        binding.list.setOnItemClickListener { parent, view, position, id ->
            val data: SonidoModelo = archivos.get(position)
            indice = position
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(data.path)
                    prepare()
                    start()
                }
            } else {
                mediaPlayer!!.release()
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(data.path)
                    prepare()
                    start()
                }
            }
            Toast.makeText(this, data.namefile, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}