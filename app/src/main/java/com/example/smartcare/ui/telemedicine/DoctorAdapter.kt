package com.example.smartcare.ui.telemedicine

import com.smartcare.data.local.Doctor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartcare.R
import com.smartcare.databinding.ItemDoctorBinding

class DoctorAdapter(
    private val onDoctorClick: (Doctor) -> Unit
) : ListAdapter<Doctor, DoctorAdapter.DoctorViewHolder>(DoctorDiffCallback()) {

    var selectedDoctorId: String? = null
        private set

    fun setSelectedDoctor(id: String?) {
        val previousSelected = selectedDoctorId
        selectedDoctorId = id

        previousSelected?.let { oldId ->
            val oldIndex = currentList.indexOfFirst { it.id == oldId }
            if (oldIndex != -1) notifyItemChanged(oldIndex)
        }

        id?.let { newId ->
            val newIndex = currentList.indexOfFirst { it.id == newId }
            if (newIndex != -1) notifyItemChanged(newIndex)
        }
    }

    inner class DoctorViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doctor: Doctor) {
            binding.tvDoctorName.text = doctor.name
            binding.tvSpecialty.text = doctor.specialty

            val context = binding.root.context

            if (doctor.isAvailable) {
                binding.tvStatus.text = "Available"
                binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.teal_secondary))
            } else {
                binding.tvStatus.text = "Busy"
                binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red_alert))
            }

            // Highlight selected card
            val isSelected = selectedDoctorId == doctor.id
            binding.root.strokeColor = if (isSelected) {
                ContextCompat.getColor(context, R.color.teal_primary)
            } else {
                Color.TRANSPARENT
            }
            binding.root.setCardBackgroundColor(
                if (isSelected) ContextCompat.getColor(context, R.color.background_light)
                else ContextCompat.getColor(context, R.color.white_clinical)
            )

            binding.root.setOnClickListener {
                if (doctor.isAvailable) {
                    onDoctorClick(doctor)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DoctorDiffCallback : DiffUtil.ItemCallback<Doctor>() {
    override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem == newItem
    }
}
