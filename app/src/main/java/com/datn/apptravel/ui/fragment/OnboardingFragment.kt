package com.datn.apptravel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.datn.apptravel.databinding.FragmentOnboardingBinding
import com.datn.apptravel.ui.base.BaseFragment
import com.datn.apptravel.ui.viewmodel.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>() {
    
    override val viewModel: OnboardingViewModel by sharedViewModel()
    
    private var position: Int = 0
    
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingBinding = 
        FragmentOnboardingBinding.inflate(inflater, container, false)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt(ARG_POSITION, 0) ?: 0
    }
    
    override fun setupUI() {
        viewModel.onboardingData.observe(viewLifecycleOwner) { onboardingDataList ->
            if (position < onboardingDataList.size) {
                val data = onboardingDataList[position]
                
                binding.apply {
                    ivOnboarding.setImageResource(data.imageRes)
                    tvTitle.text = data.title
                    tvDescription.text = data.description
                }
            }
        }
    }
    
    companion object {
        private const val ARG_POSITION = "position"
        
        fun newInstance(position: Int): OnboardingFragment {
            return OnboardingFragment().apply {
                arguments = bundleOf(ARG_POSITION to position)
            }
        }
    }
}