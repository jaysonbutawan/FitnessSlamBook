package com.kodego.diangca.ebrahim.myslambook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityFormBinding
import com.kodego.diangca.ebrahim.myslambook.model.Member

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()
    }

    private fun initComponent() {

    }

}