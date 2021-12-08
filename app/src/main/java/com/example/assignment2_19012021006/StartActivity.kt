package com.example.assignment2_19012021006

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var btn_start : Button = findViewById(R.id.button)

        btn_start.setOnClickListener{
            val intent : Intent = Intent(this , MapsActivity::class.java)
            startActivity(intent)
        }
    }
}