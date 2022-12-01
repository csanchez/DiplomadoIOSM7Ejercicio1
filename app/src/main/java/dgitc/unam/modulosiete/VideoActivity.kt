package dgitc.unam.modulosiete

import android.os.Bundle
import android.widget.AdapterView
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import dgitc.unam.modulosiete.databinding.ActivityVideoBinding
import dgitc.unam.modulosiete.models.VideoAdapter
import dgitc.unam.modulosiete.models.VideoModel

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private lateinit var model: ArrayList<VideoModel>
    private lateinit var adap: VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val controller = MediaController(this)
        binding.surface.setMediaController(controller)
        controller.setAnchorView(binding.surface)
        fillList()
        binding.list.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val data: VideoModel = model.get(position)
            var ruta = ""
            when (data.type) {
                1 -> {
                    ruta =
                        "android.resource://" + packageName + "/raw/" + data.namefile.removeRange(
                            data.namefile.indexOf('.'),
                            data.namefile.length
                        )
                }
                2 -> {
                    ruta = data.namefile
                }
            }
            val rutaUri = ruta.toUri()
            binding.surface.setVideoURI(rutaUri)
            binding.surface.start()
            Toast.makeText(this, data.namefile, Toast.LENGTH_SHORT).show()
        })
    }
    private fun fillList() {
        model = ArrayList()
        model.add(VideoModel("video.3gp", R.drawable.video_uno,1))
        model.add(
            VideoModel(
                "https://archive.org/download/ElephantsDream/ed_hd.mp4",
                R.drawable.video_dos,2
            )
        )
        adap = VideoAdapter(this, model)
        binding.list.adapter = adap
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}