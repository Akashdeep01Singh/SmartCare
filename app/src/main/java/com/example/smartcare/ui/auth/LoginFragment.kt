package com.smartcare.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smartcare.R
import com.smartcare.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    // Language Map mapping UI element to English/Punjabi strings
    private val englishStrings = mapOf(
        "title" to "SmartCare Nabha",
        "phone_hint" to "Phone Number",
        "otp_hint" to "6-Digit OTP",
        "send_otp" to "Send OTP",
        "verify_otp" to "Verify OTP"
    )

    private val punjabiStrings = mapOf(
        "title" to "ਸਮਾਰਟਕੇਅਰ ਨਾਭਾ",
        "phone_hint" to "ਮੋਬਾਈਲ ਨੰਬਰ",
        "otp_hint" to "6-ਅੰਕਾਂ ਦਾ ਓ.ਟੀ.ਪੀ",
        "send_otp" to "ਓ.ਟੀ.ਪੀ ਭੇਜੋ",
        "verify_otp" to "ਪੁਸ਼ਟੀ ਕਰੋ"
    )

    private var isOtpSent = false
    private var isPunjabiSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLanguageToggle()
        setupActionButtons()
        observeViewModel()
    }

    private fun setupLanguageToggle() {
        // Init default language (English)
        binding.toggleGroupLanguage.check(R.id.btn_lang_en)

        binding.toggleGroupLanguage.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                isPunjabiSelected = checkedId == R.id.btn_lang_pa
                updateUiStrings()
            }
        }
    }

    private fun updateUiStrings() {
        val strings = if (isPunjabiSelected) punjabiStrings else englishStrings

        binding.tvTitle.text = strings["title"]
        binding.tilPhone.hint = strings["phone_hint"]
        binding.tilOtp.hint = strings["otp_hint"]

        // Determine correct button text based on state
        binding.btnAction.text = if (isOtpSent) strings["verify_otp"] else strings["send_otp"]
    }

    private fun setupActionButtons() {
        binding.btnAction.setOnClickListener {
            if (!isOtpSent) {
                val phone = binding.etPhone.text.toString()
                viewModel.sendOtp(phone)
            } else {
                val otp = binding.etOtp.text.toString()
                viewModel.verifyOtp(otp)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnAction.isEnabled = true
                }
                is AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnAction.isEnabled = false
                }
                is AuthState.OtpSent -> {
                    isOtpSent = true
                    binding.progressBar.visibility = View.GONE
                    binding.btnAction.isEnabled = true

                    // Show OTP fields, hide Phone fields
                    binding.tilPhone.isEnabled = false
                    binding.tilOtp.visibility = View.VISIBLE
                    updateUiStrings() // Refresh button string to "Verify OTP"
                }
                is AuthState.Verified -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to Main Dashboard (Home)
                    // findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is AuthState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnAction.isEnabled = true
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
