package com.capstoneprojects.recyclerview_prak

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val context: Context, contactList: ArrayList<ContactModel>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private val contactList: List<ContactModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.tvName.text = contact.name
        holder.tvNumber.text = contact.number
        holder.tvCall.setOnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + contact.number)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.CALL_PHONE), 1)
                return@setOnClickListener
            }
            context.startActivity(intent)
        }

        holder.tvMessage.setOnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("sms:" + contact.number)
            context.startActivity(intent)
        }

        holder.tvWhatsapp.setOnClickListener { v: View? ->
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.action = Intent.ACTION_VIEW
            sendIntent.setPackage("com.whatsapp")
            val url = "https://api.whatsapp.com/send?phone=" + contact.number + "&text="
            sendIntent.data = Uri.parse(url)
            context.startActivity(sendIntent)
        }

        holder.contactLayout.setOnClickListener { v: View? ->
            val dataName = holder.tvName.text.toString()
            val dataNumber = holder.tvNumber.text.toString()
            val dataInstagram = contact.instagram
            val dataGroup = contact.group
            val intent = Intent(context, DetailContactActivity::class.java)
            val bundle = Bundle()
            bundle.putString("cname", dataName)
            bundle.putString("cnumber", dataNumber)
            bundle.putString("cinstagram", dataInstagram)
            bundle.putString("cgroup", dataGroup)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ContactViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var contactLayout: LinearLayout
        var tvName: TextView
        var tvNumber: TextView
        var tvCall: TextView
        var tvMessage: TextView
        var tvWhatsapp: TextView
        var tvDelete: TextView

        init {
            contactLayout = itemView.findViewById(R.id.contact_layout)
            tvDelete = itemView.findViewById(R.id.tv_delete)
            tvName = itemView.findViewById(R.id.tv_name)
            tvNumber = itemView.findViewById(R.id.tv_number)
            tvCall = itemView.findViewById(R.id.tv_call)
            tvMessage = itemView.findViewById(R.id.tv_message)
            tvWhatsapp = itemView.findViewById(R.id.tv_whatsapp)
            tvDelete.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.tv_delete -> {
                    // Handle delete click
                }
                else -> {
                    clickListener?.onItemClick(adapterPosition, v)
                }
            }
        }
    }

    fun setOnItemClickListener(clickListener: ClickListener?) {
        Companion.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View?)
    }

    companion object {
        private var clickListener: ClickListener? = null
    }

    init {
        this.contactList = contactList
    }
}
