package com.nginapps.favorite.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import coil.load
import com.nginapps.capstone.detail.DetailActivity
import com.nginapps.core.model.Destination
import com.nginapps.favorite.R
import com.nginapps.favorite.detail.viewmodel.FavoriteDetailViewModel
import com.nginapps.favorite.favoriteModule
import kotlinx.android.synthetic.main.activity_favorites_detail.*
import kotlinx.android.synthetic.main.content_destination_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import java.lang.IllegalArgumentException

class FavoriteDetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<FavoriteDetailViewModel>()

    private var isFavorite = true
    private var menu: Menu? = null
    private lateinit var destination: Destination

    companion object {
        const val DESTINATION_ID = "destinationId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_detail)
        loadKoinModules(favoriteModule)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val id = intent.extras?.getInt(DetailActivity.DESTINATION_ID) ?: throw IllegalArgumentException("destination id must be non-null")
        initDestination(id)
    }

    private fun initDestination(id: Int) {
        viewModel.getDestinationById(id).observe(this) {
            destination = it
            titleTv.text = it.title
            descTv.text = it.desc
            imageView.load(it.photo)
            isFavorite = it.isFavorite?:false
            invalidateOptionsMenu()
        }
    }

    private fun handleIconFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, com.nginapps.capstone.R.drawable.ic_favorite)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, com.nginapps.capstone.R.drawable.ic_favorite_border)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.nginapps.capstone.R.menu.favorite_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        handleIconFavorite(isFavorite)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            com.nginapps.capstone.R.id.action_favorite -> {
                viewModel.setFavoriteDestination(destination, !isFavorite)
                true
            }
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }

            else -> true
        }
    }
}