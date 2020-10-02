package com.nginapps.favorite.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nginapps.capstone.extension.hide
import com.nginapps.capstone.extension.show
import com.nginapps.core.ui.DestinationAdapter
import com.nginapps.favorite.detail.FavoriteDetailActivity
import com.nginapps.favorite.R
import com.nginapps.favorite.favoriteModule
import com.nginapps.favorite.favorite.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.activity_favorites.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoritesActivity : AppCompatActivity() {

    private val viewModel by viewModel<FavoritesViewModel>()

    private lateinit var adapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(com.nginapps.capstone.R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        initLayout()
    }

    private fun initLayout() {
        supportActionBar?.title = "Favorite"

        loadKoinModules(favoriteModule)

        adapter = DestinationAdapter()

        initFavorite()

        with(favoriteRv) {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            setHasFixedSize(true)
            adapter = this@FavoritesActivity.adapter
        }

        adapter.onItemClick = { selectData ->
            val intent = Intent(this, FavoriteDetailActivity::class.java)
            intent.putExtra(FavoriteDetailActivity.DESTINATION_ID, selectData.id)
            startActivity(intent)
        }
    }

    private fun initFavorite() {
        viewModel.getFavoriteDestination().observe(this) {
            adapter.setData(it)
            handleStateRvAdapter()
        }
    }

    private fun handleStateRvAdapter() {
        if (adapter.itemCount == 0) {
            emptyLav.show()
            favoriteRv.hide()
            return
        }
        emptyLav.hide()
        favoriteRv.show()
    }
}