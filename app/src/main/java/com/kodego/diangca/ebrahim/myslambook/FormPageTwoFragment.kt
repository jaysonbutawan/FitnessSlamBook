package com.kodego.diangca.ebrahim.myslambook

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterEvents
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterContent
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterSkill
import com.kodego.diangca.ebrahim.myslambook.adapter.AdapterMusic
import com.kodego.diangca.ebrahim.myslambook.databinding.FragmentFormPageTwoBinding
import com.kodego.diangca.ebrahim.myslambook.model.*


class FormPageTwoFragment() : Fragment() {

    private lateinit var binding: FragmentFormPageTwoBinding

    private lateinit var member: Member
    private lateinit var adapterMusic: AdapterMusic
    private var music: ArrayList<Music> = ArrayList()

    private lateinit var adapterContent: AdapterContent
    private var contents: ArrayList<Content> = ArrayList()

    private lateinit var adapterEvents: AdapterEvents
    private var hobbies: ArrayList<Event> = ArrayList()

    private lateinit var adapterSkill: AdapterSkill
    private var skills: ArrayList<Skill> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!=null) {
            member = (arguments?.getParcelable("Member")!!)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("ON_RESUME", "RESUME_PAGE_TWO")
        if (arguments!=null) {
            member = (arguments?.getParcelable("Member")!!)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ON_ATTACH", "ATTACH_PAGE_TWO")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFormPageTwoBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        restoreFields()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun restoreFields() {

        music.clear()
        contents.clear()
        hobbies.clear()
        skills.clear()

        member.workoutMusics.let {
            music.addAll(it)
        }
        member.sportsContents.let {
            contents.addAll(it)
        }
        member.events.let {
            hobbies.addAll(it)
        }
        member.skillsWithRate?.let {
            skills.addAll(it)
        }

        binding.favSongList.adapter?.notifyDataSetChanged()
        binding.favMovieList.adapter?.notifyDataSetChanged()
        binding.hobbiesList.adapter?.notifyDataSetChanged()
        binding.skillList.adapter?.notifyDataSetChanged()

    }


    private fun initComponents() {

        with(binding) {

            adapterMusic = AdapterMusic(root.context, music)
            binding.favSongList.layoutManager = LinearLayoutManager(root.context)
            binding.favSongList.adapter = adapterMusic

            adapterContent = AdapterContent(root.context, contents)
            binding.favMovieList.layoutManager = LinearLayoutManager(root.context)
            binding.favMovieList.adapter = adapterContent

            adapterEvents = AdapterEvents(root.context, this@FormPageTwoFragment.hobbies)
            binding.hobbiesList.layoutManager = LinearLayoutManager(root.context)
            binding.hobbiesList.adapter = adapterEvents

            adapterSkill = AdapterSkill(root.context, skills)
            binding.skillList.layoutManager = LinearLayoutManager(root.context)
            binding.skillList.adapter = adapterSkill

            btnAddFavSong.setOnClickListener {
                btnAddOnClickListener(binding.root, "Music", binding.workOutMusic, binding.favSongList)
            }
            btnAddFavMov.setOnClickListener {
                btnAddOnClickListener(binding.root, "Content", binding.sportsContent, binding.favMovieList)
            }

            btnAddHobbies.setOnClickListener {
                btnAddOnClickListener(binding.root, "Event", binding.events, binding.hobbiesList)
            }

            btnAddSkill.setOnClickListener {
                btnAddOnClickListener(binding.root, "Skills", binding.skill, binding.skillList)
            }

            btnBack.setOnClickListener {
                btnBackOnClickListener()
            }
            btnNext.setOnClickListener {
                btnNextOnClickListener()
            }

        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val bundle = Bundle()
                bundle.putParcelable("Member", member)
                findNavController().navigate(R.id.action_formPageTwoFragment_to_formPageOneFragment, bundle)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun btnAddOnClickListener(
        view: View?,
        type: String,
        field: TextInputEditText,
        recyclerView: RecyclerView
    ) {
        val text = field.text.toString().trim()

        if (text.isEmpty()) {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Heads up")
                .setMessage("Please check empty fields.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
            return
        }

        var added = false

        when (type) {
            "Music" -> {
                if (music.any { it.songName.equals(text, true) }) {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Duplicate Entry")
                        .setMessage("Workout Music already exists.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    return
                }
                music.add(Music(text))
                added = true
            }

            "Content" -> {
                if (contents.any { it.contentName.equals(text, true) }) {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Duplicate Entry")
                        .setMessage("Sport content already exists.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    return
                }
                contents.add(Content(text))
                added = true
            }

            "Event" -> {
                if (hobbies.any { it.event.equals(text, true) }) {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Duplicate Entry")
                        .setMessage("Event already exists.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    return
                }
                hobbies.add(Event(text))
                added = true
            }

            "Skills" -> {
                val rate = binding.skillRate.selectedItemPosition
                if (rate == 0) {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Missing Selection")
                        .setMessage("Please select a skill rate first.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    return
                }
                if (skills.any { it.skill.equals(text, true) }) {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Duplicate Entry")
                        .setMessage("Skill already exists.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    return
                }
                skills.add(Skill(text, rate))
                added = true
            }
        }

        if (added) {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Success")
                .setMessage("Data has been successfully added.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

            field.setText("")

            recyclerView.adapter?.notifyDataSetChanged()

            view?.let {
                val inputMethodManager =
                    binding.root.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }

            recyclerView.requestFocus()
        } else {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Error")
                .setMessage("Failed to add data.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }



    private fun btnNextOnClickListener() {

        member.workoutMusics = music
        member.sportsContents = contents
        member.events = hobbies
        member.skillsWithRate = skills

        Log.d("PAGE_TWO_DEBUG", "Music list size: ${music.size}")
        music.forEachIndexed { index, m ->
            Log.d("PAGE_TWO_DEBUG", "Music[$index]: ${m.songName}")
        }

        Log.d("PAGE_TWO_DEBUG", "Content list size: ${contents.size}")
        contents.forEachIndexed { index, c ->
            Log.d("PAGE_TWO_DEBUG", "Content[$index]: ${c.contentName}")
        }

        Log.d("PAGE_TWO_DEBUG", "Events list size: ${hobbies.size}")
        hobbies.forEachIndexed { index, e ->
            Log.d("PAGE_TWO_DEBUG", "Event[$index]: ${e.event}")
        }

        Log.d("PAGE_TWO_DEBUG", "Skills list size: ${skills.size}")
        skills.forEachIndexed { index, s ->
            Log.d("PAGE_TWO_DEBUG", "Skill[$index]: ${s.skill}, Rate=${s.rate}")
        }


        val bundle = Bundle()
        bundle.putParcelable("Member", member)
        findNavController().navigate(R.id.action_formPageTwoFragment_to_formPageThreeFragment, bundle)
    }

    private fun btnBackOnClickListener() {
        // Update member with the latest lists before going back
        member.workoutMusics = music
        member.sportsContents = contents
        member.events = hobbies
        member.skillsWithRate = skills

        val bundle = Bundle()
        bundle.putParcelable("Member", member)

        findNavController().navigate(R.id.action_formPageTwoFragment_to_formPageOneFragment, bundle)
    }


}