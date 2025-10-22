package com.datn.apptravel.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.datn.apptravel.R
import com.datn.apptravel.databinding.ActivityOnboardingBinding
import com.datn.apptravel.ui.base.BaseActivity
import com.datn.apptravel.ui.fragment.OnboardingFragment
import com.datn.apptravel.ui.viewmodel.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>() {
    
    override val viewModel: OnboardingViewModel by viewModel()
    
    private lateinit var pagerAdapter: OnboardingPagerAdapter
    
    override fun getViewBinding(): ActivityOnboardingBinding = 
        ActivityOnboardingBinding.inflate(layoutInflater)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide action bar
        supportActionBar?.hide()
    }
    
    override fun setupUI() {
        setupViewPager()
        setupIndicators()
        setupClickListeners()
        observeCurrentPosition()
    }
    
    private fun setupViewPager() {
        pagerAdapter = OnboardingPagerAdapter(this)
        
        binding.viewPager.apply {
            adapter = pagerAdapter
            
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.updatePosition(position)
                }
            })
        }
    }
    
    private fun setupIndicators() {
        // Initial indicator state will be set by observeCurrentPosition
    }
    
    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.navigateToPreviousPage()
        }
        
        binding.btnNext.setOnClickListener {
            viewModel.navigateToNextPage()
        }
        
        binding.btnStart.setOnClickListener {
            // Navigate to SignIn activity
            startActivity(Intent(this, com.datn.apptravel.ui.auth.SignInActivity::class.java))
            finish()
        }
    }
    
    private fun observeCurrentPosition() {
        viewModel.currentPosition.observe(this) { position ->
            binding.viewPager.currentItem = position
            updateIndicators(position)
            updateUI(position)
        }
    }
    
    private fun updateUI(position: Int) {
        viewModel.onboardingData.value?.let { data ->
            if (position < data.size) {
                val currentData = data[position]
                
                binding.btnBack.visibility = if (currentData.showBackButton) View.VISIBLE else View.INVISIBLE
                binding.btnNext.visibility = if (currentData.showNextButton) View.VISIBLE else View.INVISIBLE
                binding.btnStart.visibility = if (currentData.showStartButton) View.VISIBLE else View.INVISIBLE
            }
        }
    }
    
    private fun updateIndicators(position: Int) {
        val indicators = listOf(binding.indicator1, binding.indicator2, binding.indicator3)
        
        indicators.forEachIndexed { index, indicator ->
            if (index == position) {
                indicator.setBackgroundResource(R.drawable.indicator_selected)
            } else {
                indicator.setBackgroundResource(R.drawable.indicator_unselected)
            }
        }
    }
    
    inner class OnboardingPagerAdapter(fragmentActivity: FragmentActivity) : 
        FragmentStateAdapter(fragmentActivity) {
        
        override fun getItemCount(): Int = viewModel.onboardingData.value?.size ?: 0
        
        override fun createFragment(position: Int): Fragment {
            return OnboardingFragment.newInstance(position)
        }
    }
}