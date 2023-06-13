package com.wenm.practice

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.wenm.permissioner.Permissioner
import com.wenm.xcamera.XCameraActivity
import com.wenm.xcamera.XCameraManager

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissioner = Permissioner(arrayOf(android.Manifest.permission.READ_CONTACTS)).apply {
            registerLauncher(this@MainActivity)
        }
        val permissioner2 = Permissioner(arrayOf(android.Manifest.permission.CAMERA)).apply {
            registerLauncher(this@MainActivity)
        }

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val uri = XCameraManager.parseResult(it)
            val s = ImageView(this)
            s.setImageURI(Uri.parse(uri))
        }

        findViewById<View>(R.id.text1).setOnClickListener {
            permissioner.launch { _, _ ->
                launcher.launch(Intent(this@MainActivity, XCameraActivity::class.java))
            }
        }
    }
}