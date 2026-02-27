package com.smartcare.ui.symptoms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartcare.databinding.FragmentSymptomCheckerBinding

class SymptomCheckerFragment : Fragment() {

    private var _binding: FragmentSymptomCheckerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomCheckerViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptomCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
        observeViewModel()
        setupSendButton()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true // Chat-like behavior
        }
        binding.rvChat.adapter = chatAdapter
    }

    private fun setupSpinner() {
        val mappedSymptoms = listOf(
            "Select Symptom / ਲੱਛਣ ਚੁਣੋ",
            "Chest Pain", "ਛਾਤੀ ਵਿੱਚ ਦਰਦ",
            "High Fever", "ਤੇਜ਼ ਬੁਖਾਰ",
            "Severe Abdominal Pain", "ਢਿੱਡ ਵਿੱਚ ਤੇਜ਼ ਦਰਦ",
            "Difficulty Breathing", "ਸਾਹ ਲੈਣ ਵਿੱਚ ਦਿੱਕਤ",
            "Mild Headache", "ਹਲਕਾ ਸਿਰਦਰਦ",
            "Cough", "ਖਾਂਸੀ",
            "Cold", "ਜ਼ੁਕਾਮ"
        )
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mappedSymptoms
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSymptoms.adapter = spinnerAdapter
    }

    private fun observeViewModel() {
        viewModel.chatMessages.observe(viewLifecycleOwner) { messages ->
            chatAdapter.submitList(messages) {
                // Scroll to the bottom exactly like a messaging app
                binding.rvChat.scrollToPosition(messages.size - 1)
            }
        }
    }

    private fun setupSendButton() {
        binding.btnSend.setOnClickListener {
            val selectedSymptom = binding.spinnerSymptoms.selectedItem.toString()
            if (selectedSymptom != "Select Symptom / ਲੱਛਣ ਚੁਣੋ") {
                viewModel.submitSymptom(selectedSymptom)
                // Reset Spinner
                binding.spinnerSymptoms.setSelection(0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
