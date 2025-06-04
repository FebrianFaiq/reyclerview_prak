package com.capstoneprojects.recyclerview_prak

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest

class DetailContactActivity : AppCompatActivity() {

    private var tvName: TextView? = null
    private var tvNumber: TextView? = null
    private var tvInstagram: TextView? = null
    private var tvGroup: TextView? = null
    private var backLink: TextView? = null
    private var btnEdit: TextView? = null
    private var btnCall: TextView? = null
    private var btnMessage: TextView? = null
    private var btnInstagram: TextView? = null
    private var layWhatsapp: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_contact)

        backLink = findViewById(R.id.back_link)
        tvName = findViewById(R.id.tv_name)
        tvNumber = findViewById(R.id.tv_number)
        tvInstagram = findViewById(R.id.tv_instagram)
        tvGroup = findViewById(R.id.tv_group)
        btnCall = findViewById(R.id.btn_call)
        btnMessage = findViewById(R.id.btn_message)
        btnInstagram = findViewById(R.id.btn_instagram)
        layWhatsapp = findViewById(R.id.layout_whatsapp)

        intent.extras?.let { bundle ->
            val getCName = bundle.getString("cname")
            val getCNumber = bundle.getString("cnumber")
            val getCInstagram = bundle.getString("cinstagram")
            val getCGroup = bundle.getString("cgroup")

            tvName?.text = getCName
            tvNumber?.text = getCNumber
            tvInstagram?.text = getCInstagram
            tvGroup?.text = getCGroup

            btnCall?.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$getCNumber")
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
                    return@setOnClickListener
                }
                startActivity(intent)
            }

            btnMessage?.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("sms:$getCNumber")
                startActivity(intent)
            }

            layWhatsapp?.setOnClickListener {
                val sendIntent = Intent(Intent.ACTION_VIEW)
                sendIntent.setPackage("com.whatsapp")
                val url = "https://api.whatsapp.com/send?phone=$getCNumber&text="
                sendIntent.data = Uri.parse(url)
                startActivity(sendIntent)
            }

            btnInstagram?.setOnClickListener {
                val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/$getCInstagram"))
                startActivity(instagramIntent)
            }
        }

        btnEdit?.setOnClickListener {
            Toast.makeText(this, "Edit Clicked", Toast.LENGTH_SHORT).show()
        }

        backLink?.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}
