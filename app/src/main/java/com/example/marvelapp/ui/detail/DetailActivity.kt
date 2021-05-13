package com.example.marvelapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.data.DataSource
import com.example.marvelapp.data.model.character.Character
import com.example.marvelapp.data.model.comic.Comic
import com.example.marvelapp.databinding.ActivityDetailBinding
import com.example.marvelapp.domain.RepoImpl
import com.example.marvelapp.ui.adapter.ComicAdapter
import com.example.marvelapp.ui.detail.viewmodel.DetailViewModel
import com.example.marvelapp.utils.Resource
import com.example.marvelapp.utils.VMFactory
import kotlinx.android.synthetic.main.toolbar_app.view.*

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailViewModel> {
        VMFactory(
            RepoImpl(DataSource())
        )
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(binding.toolbarApp as Toolbar)

        getCharacterDetail(intent.getIntExtra(characterId, 0))
    }

    private fun getCharacterDetail(id: Int) {
        viewModel.getCharacterByID(id).observe(this, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setDetailView(result.data.data.list[0])
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error getting character detail", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setDetailView(charac: Character) {
        Glide.with(this)
            .load(charac.thumbnail.path + extensionJpg)
            .fitCenter()
            .error(R.drawable.img_not_available)
            .into(binding.imgCharacDetail)

        if (charac.description == "") {
            binding.tvCharacDescDetail.text = resources.getString(R.string.no_description)
            binding.tvCharacDescDetail.gravity = Gravity.CENTER
        } else {
            binding.tvCharacDescDetail.text = charac.description
        }

        if (charac.comics.comicList.size > 0) {
            setComicAdapter(charac.comics.comicList)
        } else {
            binding.tvAppearsComicDetail.visibility = View.GONE
            binding.rvComicsDetail.visibility = View.GONE
        }

        binding.toolbarApp.tv_toolbar_title.text = charac.name
    }

    private fun setComicAdapter(comics: ArrayList<Comic>) {
        binding.rvComicsDetail.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.rvComicsDetail.layoutManager = LinearLayoutManager(this)
        binding.rvComicsDetail.isNestedScrollingEnabled = false
        binding.rvComicsDetail.setHasFixedSize(true)
        binding.rvComicsDetail.adapter = ComicAdapter(this, comics)
    }

    companion object {
        const val extensionJpg = ".jpg"

        private const val characterId = "character_id"

        fun createIntent(context: Context, id: Int): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(characterId, id)
            }
        }
    }
}

