package com.kodego.diangca.ebrahim.myslambook.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.RecycleViewSingleItemBinding
import com.kodego.diangca.ebrahim.myslambook.model.Event

class AdapterEvents(var context: Context, var events: ArrayList<Event>) :
RecyclerView.Adapter<AdapterEvents.HobbiesViewHolder>(){


    inner class HobbiesViewHolder(
        private val context: Context,
        private val itemBinding: RecycleViewSingleItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        var event = Event()

        fun bindStudent(event : Event) {
            this.event = event
            itemBinding.itemName.text = "${event.event}"

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(itemBinding, adapterPosition)
            }
            itemBinding.updateBtn.setOnClickListener {
                btnUpdateOnClickListener(itemBinding, adapterPosition)
            }
        }

        override fun onClick(view: View?) {
            if (view!=null)
                Snackbar.make(
                    itemBinding.root,
                    "${itemBinding.itemName}",
                    Snackbar.LENGTH_SHORT
                ).show()
        }

    }

    private fun btnUpdateOnClickListener(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        updateSong(itemBinding, positionAdapter)
    }

    private fun btnRemoveOnClickListener(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {

        removeSong(itemBinding, positionAdapter)
    }

    private fun removeSong(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        var alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete?")
        alertDialogBuilder.setMessage("Are you sure you want to delete ${events[positionAdapter].event}")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, position: Int) {
                events.removeAt(positionAdapter)
                notifyItemRemoved(positionAdapter);
                notifyItemRangeChanged(positionAdapter, itemCount);
                Snackbar.make(
                    itemBinding.root,
                    "Event has been successfully removed.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }).show()
    }
    private fun updateSong(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        val currentItem = events[positionAdapter]

        // Create a dialog with an EditText for the update
        val editText = EditText(context)
        editText.setText(currentItem.event)
        editText.hint = "Update event"

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Update event")
        alertDialogBuilder.setView(editText)
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Save") { _, _ ->
            val updatedName = editText.text.toString().trim()
            if (updatedName.isNotEmpty()) {
                events[positionAdapter].event = updatedName
                notifyItemChanged(positionAdapter)
                Snackbar.make(
                    itemBinding.root,
                    "Events updated successfully.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    itemBinding.root,
                    "Events cannot be empty.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        alertDialogBuilder.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbiesViewHolder {
        val itemBinding =
            RecycleViewSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbiesViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int {
        return events.size
        print("SIZE: ${events.size}")
    }

    override fun onBindViewHolder(holder: HobbiesViewHolder, position: Int) {
        holder.bindStudent(events[position])
    }
}