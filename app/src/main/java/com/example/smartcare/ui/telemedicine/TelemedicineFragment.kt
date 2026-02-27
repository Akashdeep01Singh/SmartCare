package com.smartcare.ui.telemedicine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smartcare.ui.telemedicine.DoctorAdapter
import com.smartcare.data.local.MockData
import com.smartcare.databinding.FragmentTelemedicineBinding
import java.util.Calendar

class TelemedicineFragment : Fragment() {

    private var _binding: FragmentTelemedicineBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TelemedicineViewModel by viewModels()
    private lateinit var doctorAdapter: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTelemedicineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDoctorsList()
        setupDateTimePickers()
        setupBandwidthSwitch()
        setupStartCallButton()
    }

    private fun setupDoctorsList() {
        doctorAdapter = DoctorAdapter { doctor ->
            viewModel.selectDoctor(doctor)
            doctorAdapter.setSelectedDoctor(doctor.id)
        }
        binding.rvDoctors.adapter = doctorAdapter
        // Feed the mock data
        doctorAdapter.submitList(MockData.doctors)
    }

    private fun setupDateTimePickers() {
        // Initialize with default values
        val calendar = Calendar.getInstance()
        updateDateInViewModel(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            updateDateInViewModel(year, month, dayOfMonth)
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val amPm = if (hourOfDay >= 12) "PM" else "AM"
            val hour = if (hourOfDay > 12) hourOfDay - 12 else if (hourOfDay == 0) 12 else hourOfDay
            val minStr = minute.toString().padStart(2, '0')
            viewModel.setTime("$hour:$minStr $amPm")
        }

        // Initial setup for TimePicker
        val initialHour = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding.timePicker.hour
        } else {
            binding.timePicker.currentHour
        }
        val initialMinute = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding.timePicker.minute
        } else {
            binding.timePicker.currentMinute
        }

        val amPm = if (initialHour >= 12) "PM" else "AM"
        val displayHour = if (initialHour > 12) initialHour - 12 else if (initialHour == 0) 12 else initialHour
        viewModel.setTime("$displayHour:${initialMinute.toString().padStart(2, '0')} $amPm")
    }

    private fun updateDateInViewModel(year: Int, month: Int, day: Int) {
        val displayMonth = month + 1
        viewModel.setDate("$day/$displayMonth/$year")
    }

    private fun setupBandwidthSwitch() {
        binding.switchBandwidth.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setLowBandwidthMode(isChecked)
        }
    }

    private fun setupStartCallButton() {
        binding.btnStartCall.setOnClickListener {
            val doctor = viewModel.selectedDoctor.value
            val date = viewModel.selectedDateStr.value
            val time = viewModel.selectedTimeStr.value
            val lowBandwidth = viewModel.isLowBandwidth.value ?: true

            if (doctor == null) {
                Toast.makeText(requireContext(), "Please select a Doctor first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulating a call intent (In a real app, this would integrate Jitsi/WebRTC/Agora)
            val message = "Calling ${doctor.name}\nSlot: $date at $time\nLow Bandwidth: $lowBandwidth"
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

            // Trigger simulated intent for video call, dropping to audio only or low-res video if lowBandwidth
            val mockVideoCallIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://meet.jit.si/SmartCareNabhaConsult${doctor.id}") // Placeholder WebRTC URL
            }
            startActivity(mockVideoCallIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
