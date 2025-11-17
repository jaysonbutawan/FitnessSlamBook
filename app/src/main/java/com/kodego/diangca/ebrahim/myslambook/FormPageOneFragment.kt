package com.kodego.diangca.ebrahim.myslambook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.kodego.diangca.ebrahim.myslambook.databinding.FragmentFormPageOneBinding
import com.kodego.diangca.ebrahim.myslambook.model.Member
import java.text.SimpleDateFormat
import java.util.*


class FormPageOneFragment() : Fragment() {

    private lateinit var binding: FragmentFormPageOneBinding

    private lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.d("ON_RESUME", "RESUME_PAGE_ONE")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ON_ATTACH", "ATTACH_PAGE_ONE")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFormPageOneBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun initComponents() {
        if (arguments != null) {
            member = ((arguments?.getParcelable("Member") as Member?)!!)
            restoreField()
        } else {
            member = Member()
            binding.btnNext.isEnabled = false
            binding.btnNext.alpha = 0.5f

            addInputListeners()
        }


        with(binding) {
            btnBack.setOnClickListener {
                btnBackOnClickListener()
            }
            btnNext.setOnClickListener {
                btnNextOnClickListener()
            }
            binding.dateMonth.prompt = "Please enter Birth Month"
            binding.dateDay.prompt = "Please enter Birth day"
            binding.gender.prompt = "Please enter Gender"
            binding.status.prompt = "Please Choose your Membership"
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun restoreField() {

        binding.apply {
            nickName.setText(member.nickName)
            clubName.setText(member.clubName)
            lastName.setText(member.lastName)
            firstName.setText(member.firstName)

            if (!member.birthDate.isNullOrEmpty()) {
                val arrayMonth = resources.getStringArray(R.array.monthName)
                val arrayDay = resources.getStringArray(R.array.monthDay)
                val birtDate = Date(member.birthDate)

                dateMonth.setSelection(arrayMonth.indexOf(SimpleDateFormat("MMMM").format(birtDate)));
                dateDay.setSelection(arrayDay.indexOf(SimpleDateFormat("dd").format(birtDate)));
                dateYear.setText(SimpleDateFormat("yyyy").format(birtDate));
            }

            if (!member.gender.isNullOrEmpty()) {
                val arrayGender = resources.getStringArray(R.array.gender)
                gender.setSelection(arrayGender.indexOf(member.gender))
            }

            if (!member.status.isNullOrEmpty()) {
                val arrayStatus = resources.getStringArray(R.array.status)
                status.setSelection(arrayStatus.indexOf(member.status))
            }
            emailAdd.setText(member.email)
            contactNo.setText(member.contactNo)
            address.setText(member.address)

        }
    }

    private fun addInputListeners() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = checkAllFieldsFilled()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.nickName.addTextChangedListener(textWatcher)
        binding.clubName.addTextChangedListener(textWatcher)
        binding.lastName.addTextChangedListener(textWatcher)
        binding.firstName.addTextChangedListener(textWatcher)
        binding.dateYear.addTextChangedListener(textWatcher)
        binding.emailAdd.addTextChangedListener(textWatcher)
        binding.contactNo.addTextChangedListener(textWatcher)
        binding.address.addTextChangedListener(textWatcher)

        val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long
            ) = checkAllFieldsFilled()

            override fun onNothingSelected(parent: AdapterView<*>?) = checkAllFieldsFilled()
        }

        binding.dateMonth.onItemSelectedListener = itemSelectedListener
        binding.dateDay.onItemSelectedListener = itemSelectedListener
        binding.gender.onItemSelectedListener = itemSelectedListener
        binding.status.onItemSelectedListener = itemSelectedListener
    }

    private fun checkAllFieldsFilled() {
        val allFilled =
            binding.nickName.text!!.isNotEmpty() &&
                    binding.clubName.text!!.isNotEmpty() &&
                    binding.lastName.text!!.isNotEmpty() &&
                    binding.firstName.text!!.isNotEmpty() &&
                    binding.dateMonth.selectedItemPosition != 0 &&
                    binding.dateDay.selectedItemPosition != 0 &&
                    binding.dateYear.text!!.isNotEmpty() &&
                    binding.gender.selectedItemPosition != 0 &&
                    binding.status.selectedItemPosition != 0 &&
                    binding.emailAdd.text!!.isNotEmpty() &&
                    binding.contactNo.text!!.isNotEmpty() &&
                    binding.address.text!!.isNotEmpty()

        binding.btnNext.isEnabled = allFilled
        binding.btnNext.alpha = if (allFilled) 1f else 0.5f
    }


    private fun btnNextOnClickListener() {
        val yearText = binding.dateYear.text.toString().trim()
        val validYear = yearText.length == 4 && yearText.all { it.isDigit() }
        if (!validYear) {
            binding.dateYear.error ="(e.g.,2001)"
            return
        }
        val emailText = binding.emailAdd.text.toString().trim()
        val validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
        if (!validEmail) {
            binding.emailAdd.error = "Invalid email address"
            return
        }
        val contactText = binding.contactNo.text.toString().trim()

        if (contactText.length !in 10..13) {
            binding.contactNo.error = "Please enter a valid contact number"
            return
        }

        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val number = phoneUtil.parse(contactText, "PH")
            val isValid = phoneUtil.isValidNumber(number)
            if (!isValid) {
                binding.contactNo.error = "Invalid phone number"
                return
            }
        } catch (e: Exception) {
            binding.contactNo.error = "Invalid input format"
        }




        member.nickName = binding.nickName.text.toString()
        member.clubName = binding.clubName.text.toString()
        member.lastName = binding.lastName.text.toString()
        member.firstName = binding.firstName.text.toString()
        member.birthDate =
            "${binding.dateMonth.selectedItem} ${binding.dateDay.selectedItem}, ${binding.dateYear.text}"
        member.gender = binding.gender.selectedItem.toString()
        member.status = binding.status.selectedItem.toString()
        member.email = binding.emailAdd.text.toString()
        member.contactNo = binding.contactNo.text.toString()
        member.address = binding.address.text.toString()

        var bundle = Bundle()
        bundle.putParcelable("Member", member)

        findNavController().navigate(R.id.action_formPageOneFragment_to_formPageTwoFragment, bundle)

    }

    private fun btnBackOnClickListener() {
            findNavController().navigate(R.id.action_formPageOneFragment_to_MenuActivity)
    }
}