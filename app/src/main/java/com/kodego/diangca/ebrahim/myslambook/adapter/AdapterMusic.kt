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
import com.kodego.diangca.ebrahim.myslambook.model.Music

class AdapterMusic(var context: Context, var music: ArrayList<Music>) :
RecyclerView.Adapter<AdapterMusic.SongViewHolder>(){


    inner class SongViewHolder(
        private val context: Context,
        private val itemBinding: RecycleViewSingleItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        var music = Music()

        fun bindStudent(music: Music) {
            this.music = music
            itemBinding.itemName.text = music.songName

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
        alertDialogBuilder.setMessage("Are you sure you want to delete ${music[positionAdapter].songName}")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, position: Int) {
                music.removeAt(positionAdapter)
                notifyItemRemoved(positionAdapter);
                notifyItemRangeChanged(positionAdapter, itemCount);
                Snackbar.make(
                    itemBinding.root,
                    "Workout Music has been successfully removed.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }).show()
    }
    private fun updateSong(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        val currentItem = music[positionAdapter]

        val editText = EditText(context)
        editText.setText(currentItem.songName)
        editText.hint = "Update workout music"

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Update Music")
        alertDialogBuilder.setView(editText)
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Save") { _, _ ->
            val updatedName = editText.text.toString().trim()
            if (updatedName.isNotEmpty()) {
                music[positionAdapter].songName = updatedName
                notifyItemChanged(positionAdapter)
                Snackbar.make(
                    itemBinding.root,
                    "Workout music updated successfully.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    itemBinding.root,
                    "Workout music cannot be empty.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        alertDialogBuilder.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemBinding =
            RecycleViewSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int {
        return music.size
        print("SIZE: ${music.size}")
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindStudent(music[position])
    }
}