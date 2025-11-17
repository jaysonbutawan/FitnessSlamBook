package com.kodego.diangca.ebrahim.myslambook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityForm2Binding
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterEvents
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterContent
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterSkill
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterMusic
import com.kodego.diangca.ebrahim.myslambook.model.*

class Form2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityForm2Binding
    private lateinit var member: Member


    private lateinit var adapterMusic: AdapterMusic
    private var music: ArrayList<Music> = ArrayList()

    private lateinit var adapterContent: AdapterContent
    private var contents: ArrayList<Content> = ArrayList()

    private lateinit var adapterEvents: AdapterEvents
    private var events: ArrayList<Event> = ArrayList()

    private lateinit var adapterSkill: AdapterSkill
    private var skills: ArrayList<Skill> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        adapterMusic = AdapterMusic(this, music)
        binding.favSongList.layoutManager = LinearLayoutManager(applicationContext)
        binding.favSongList.adapter = adapterMusic

        adapterContent = AdapterContent(this, contents)
        binding.favMovieList.layoutManager = LinearLayoutManager(applicationContext)
        binding.favMovieList.adapter = adapterContent

        adapterEvents = AdapterEvents(this, events)
        binding.hobbiesList.layoutManager = LinearLayoutManager(applicationContext)
        binding.hobbiesList.adapter = adapterEvents

        adapterSkill = AdapterSkill(this, skills)
        binding.skillList.layoutManager = LinearLayoutManager(applicationContext)
        binding.skillList.adapter = adapterSkill

        if (intent!=null) {
            member = ((intent?.extras?.getSerializable("Member") as Member?)!!)
        }

        Snackbar.make(
            binding.root,
            "Hi ${member.lastName}, ${member.lastName}!, Please complete the following fields. Thank you!",
            Snackbar.LENGTH_SHORT
        ).show()

        binding.btnAddFavSong.setOnClickListener {
            btnAddOnClickListener(binding.root, "Music", binding.songName, binding.favSongList)
        }
        binding.btnAddFavMov.setOnClickListener {
            btnAddOnClickListener(binding.root, "Content", binding.movieName, binding.favMovieList)
        }

        binding.btnAddHobbies.setOnClickListener {
            btnAddOnClickListener(binding.root, "Event", binding.hobbies, binding.hobbiesList)
        }

        binding.btnAddSkill.setOnClickListener {
            btnAddOnClickListener(binding.root, "Skills", binding.skill, binding.skillList)
        }


        binding.btnBack.setOnClickListener {
            btnBackOnClickListener()
        }
        binding.btnNext.setOnClickListener {
            btnNextOnClickListener()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun btnAddOnClickListener(
        view: View?,
        type: String,
        field: TextInputEditText,

        recyclerView: RecyclerView
    ) {
        var text = field.text.toString()

        if (text.isEmpty()) {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Heads up")
                .setMessage("Please check empty fields.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            return
        }
        when (type) {
            "Music" -> {
                music.add(Music(text))
            }
            "Content" -> {
                contents.add(Content(text))
            }
            "Event" -> {
                events.add(Event(text))
            }
            "Skills" -> {
                if(binding.skillRate.selectedItemPosition == 0){
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Heads up")
                        .setMessage("Please select rate first.ss.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    return
                }
                skills.add(Skill(text, binding.skillRate.selectedItemPosition))
            }
        }
        AlertDialog.Builder(binding.root.context)
            .setTitle("Success")
            .setMessage("Data has been successfully added.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()

        field.setText("")
        recyclerView.adapter!!.notifyDataSetChanged()

        if (view!=null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            recyclerView.requestFocus()
        }
    }


    private fun btnNextOnClickListener() {
        var nextForm = Intent(this, Form3Activity::class.java)
        startActivity(nextForm)
        finish()
    }

    private fun btnBackOnClickListener() {
        startActivity(Intent(this, Form1Activity::class.java))
        finish()
    }
}