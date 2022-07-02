package com.developeralamin.frame

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.developeralamin.frame.databinding.ActivityEditorBinding
import java.lang.Exception

class EditorActivity : AppCompatActivity(), OnClick {

    private lateinit var binding: ActivityEditorBinding
    private lateinit var list: MutableList<FramModel>

    private val drw = arrayOf(R.drawable.frame_0,
        R.drawable.frame_1,
        R.drawable.frame_2,
        R.drawable.frame_3,
        R.drawable.frame_4,
        R.drawable.frame_5,
        R.drawable.frame_6,
        R.drawable.frame_7,
        R.drawable.frame_8,
        R.drawable.frame_9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("path")

        Glide.with(this).load(path).into(binding.userImage)
        list = ArrayList()

        initList()

        binding.frameRecyclerView.adapter = FramAdapter(this, list, this)

        binding.saveBtn.setOnClickListener {
            store(getScreenshort(binding.screenshort))
        }
    }

    private fun initList() {
        for (j in drw) {
            list.add(FramModel(j))
        }
    }

    override fun frameClick(position: Int) {
        Glide.with(this).load(list[position].drawab).into(binding.frameContainer)
    }


    private fun getScreenshort(view: View): Bitmap {

        view.isDrawingCacheEnabled = true

        val bitmap = Bitmap.createBitmap(view.getDrawingCache())
        return bitmap

        view.isDrawingCacheEnabled = false
    }

    private fun store(bitmap: Bitmap) {
        var uri: Uri? = null

        if (SDK_INT >= Build.VERSION_CODES.R)
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contenValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        val u = contentResolver.insert(uri, contenValues)

        try {
            val outputStream = contentResolver.openOutputStream(u!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Image Not Saved", Toast.LENGTH_SHORT).show()
        }
    }

}