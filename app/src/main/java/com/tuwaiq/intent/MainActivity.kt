package com.tuwaiq.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {


    private lateinit var openContacts: Button
    private lateinit var showContact: TextView



    private val getContactLauncher = registerForActivityResult(ActivityResultContracts.PickContact()){ conteactURI->


        val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        val courser = conteactURI?.let {
            this.contentResolver.query(
                it,queryFields,null,null,null
            )
        }

        courser?.let {
            if (it.count == 0){return@let}

            it.moveToFirst()

            val name = it.getString(0)

            showContact.text = name
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        openContacts = findViewById(R.id.open_contact_btn)
        showContact = findViewById(R.id.contact_name_tv)



        openContacts.setOnClickListener {
            getContactLauncher.launch(null)
        }
    }
}