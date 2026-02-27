package com.smartcare.ui.records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartcare.data.local.HealthRecord
import com.smartcare.databinding.ItemRecordCardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordAdapter : ListAdapter<HealthRecord, RecordAdapter.RecordViewHolder>(RecordDiffCallback()) {

    inner class RecordViewHolder(private val binding: ItemRecordCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: HealthRecord) {
            binding.tvDiagnosis.text = record.diagnosis
            binding.tvDoctor.text = "${record.doctorName} (${record.hospitalName})"
            binding.tvPrescription.text = record.prescriptionText

            // Format Timestamp
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            binding.tvDate.text = sdf.format(Date(record.date))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemRecordCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RecordDiffCallback : DiffUtil.ItemCallback<HealthRecord>() {
    override fun areItemsTheSame(oldItem: HealthRecord, newItem: HealthRecord): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HealthRecord, newItem: HealthRecord): Boolean {
        return oldItem == newItem
    }
}
