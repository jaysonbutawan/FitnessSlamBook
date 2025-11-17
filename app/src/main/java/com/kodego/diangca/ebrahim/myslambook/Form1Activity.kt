package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityForm1Binding
import com.kodego.diangca.ebrahim.myslambook.model.Member

class Form1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityForm1Binding
    private lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent!=null) {
            member = ((intent?.getSerializableExtra("Member") as Member?)!!)
        }
        binding.btnBack.setOnClickListener {
            btnBackOnClickListener()
        }
        binding.btnNext.setOnClickListener {
            btnNextOnClickListener()
        }
        val member = intent.getParcelableExtra<Member>("Member")

        // Pass it to FormPageOneFragment
        val fragment = FormPageOneFragment().apply {
            arguments = Bundle().apply {
                putParcelable("Member", member)
            }
        }



    }


    private fun btnNextOnClickListener() {

        member.nickName = binding.nickName.text.toString()
        member.clubName = binding.clubName.text.toString()
        member.lastName = binding.lastName.text.toString()
        member.firstName = binding.firstName.text.toString()
        member.birthDate =
            "${binding.dateYear.text}-${binding.dateMonth.selectedItem}-${binding.dateDay.selectedItem}"
        member.gender = binding.gender.selectedItem.toString()
        member.status = binding.status.selectedItem.toString()
        member.email = binding.emailAdd.text.toString()
        member.contactNo = binding.contactNo.text.toString()
        member.address = binding.address.text.toString()


        var nextForm = Intent(this, Form2Activity::class.java)
        nextForm.putExtra("Member", member)
        startActivity(nextForm)
        finish()
    }

    private fun btnBackOnClickListener() {
        onBackPressedDispatcher.onBackPressed()

    }
}