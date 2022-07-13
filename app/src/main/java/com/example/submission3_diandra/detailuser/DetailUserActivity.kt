package com.example.submission3_diandra.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3_diandra.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val htmlUrl = intent.getStringExtra(EXTRA_HTML)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(
            DetailViewModel::class.java)

        binding.progressBar.visibility = View.VISIBLE

        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                binding.apply {
                    txtUsername.text = it.login
                    tvCompany.text = it.company
                    jmlRepository.text = "${it.publicRepos}"
                    jmlFollowers.text = "${it.followers}"

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(imageView)
                    jmlFollowing.text = "${it.following}"
                    txtName.text = it.name
                    tvLocation.text = it.location

                }
            }
        }

        var thisIsChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if (count > 0){
                        binding.favoriteToggle.isChecked = true
                        thisIsChecked = true
                    }else{
                        binding.favoriteToggle.isChecked = false
                        thisIsChecked = false
                    }
                }
            }
        }

        binding.favoriteToggle.setOnClickListener{
            thisIsChecked =! thisIsChecked
            if(thisIsChecked){
                viewModel.addToFavorite(username, avatarUrl!!, htmlUrl!!, id)
            }else{
                viewModel.removeFromFavorite(id)
            }
            binding.favoriteToggle.isChecked = thisIsChecked
        }

        val sectionPagerAdapter = PageAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_HTML = "extra_html"
    }

}