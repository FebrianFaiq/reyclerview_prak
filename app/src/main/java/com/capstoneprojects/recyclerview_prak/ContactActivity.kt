package com.capstoneprojects.recyclerview_prak

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactActivity : AppCompatActivity() {
    private var option: TextView? = null
    private var layAddContact: LinearLayout? = null
    private var recyclerView: RecyclerView? = null
    private val contactList = ArrayList<ContactModel>()
    private var contactAdapter: ContactAdapter? = null
    private var etName: EditText? = null
    private var etNumber: EditText? = null
    private var etInstagram: EditText? = null
    private var etGroup: EditText? = null
    private var btnClear: Button? = null
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        layAddContact = findViewById(R.id.layout_add)
        etName = findViewById(R.id.et_name)
        recyclerView = findViewById(R.id.recycle_contact)
        etNumber = findViewById(R.id.et_number)
        etInstagram = findViewById(R.id.et_instagram)
        etGroup = findViewById(R.id.et_group)
        btnClear = findViewById(R.id.btn_clear)
        btnSubmit = findViewById(R.id.btn_submit)
        option = findViewById(R.id.tv_option)

        option?.setOnClickListener {
            if (recyclerView?.visibility == View.VISIBLE) {
                recyclerView?.visibility = View.GONE
                layAddContact?.visibility = View.VISIBLE
                clearData()
            } else {
                recyclerView?.visibility = View.VISIBLE
                layAddContact?.visibility = View.GONE
            }
        }

        btnClear?.setOnClickListener {
            clearData()
        }

        btnSubmit?.setOnClickListener {
            val name = etName?.text.toString()
            val number = etNumber?.text.toString()
            val instagram = etInstagram?.text.toString()
            val group = etGroup?.text.toString()

            if (name.isEmpty() || number.isEmpty() || instagram.isEmpty() || group.isEmpty()) {
                Toast.makeText(this, "Please fill in the entire form", Toast.LENGTH_SHORT).show()
            } else {
                contactList.add(ContactModel(name, number, group, instagram))
                contactAdapter = ContactAdapter(this, contactList)
                recyclerView?.adapter = contactAdapter
                recyclerView?.visibility = View.VISIBLE
                layAddContact?.visibility = View.GONE
            }
        }

        contactList.add(ContactModel("Jusuf Latifah", "+62878555504", "business", "bayerhilarious"))
        contactList.add(ContactModel("Burhanuddin Taufik", "+628785555041", "family", "integersjunior"))
        contactList.add(ContactModel("Latifah Bagus", "+628785555042", "study", "clearcarbon"))
        contactList.add(ContactModel("Agung Nurul", "+628785555043", "family", "opticalwwf"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))
        contactList.add(ContactModel("Cahaya Krisna", "+628785555044", "business", "gisremedy"))

        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this@ContactActivity)
        recyclerView?.layoutManager = layoutManager
        contactAdapter = ContactAdapter(this, contactList)

        contactAdapter?.setOnItemClickListener(object : ContactAdapter.ClickListener {
            override fun onItemClick(position: Int, v: View?) {
                contactList.removeAt(position)
                contactAdapter = ContactAdapter(this@ContactActivity, contactList)
                recyclerView?.adapter = contactAdapter
            }
        })

        recyclerView?.adapter = contactAdapter
    }

    private fun clearData() {
        etName?.setText("")
        etNumber?.setText("")
        etInstagram?.setText("")
        etGroup?.setText("")
    }
}
