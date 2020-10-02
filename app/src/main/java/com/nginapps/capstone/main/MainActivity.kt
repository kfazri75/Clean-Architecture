package com.nginapps.capstone.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nginapps.capstone.R
import com.nginapps.capstone.detail.DetailActivity
import com.nginapps.capstone.extension.hide
import com.nginapps.capstone.extension.show
import com.nginapps.capstone.extension.showAlertDialog
import com.nginapps.capstone.main.viewmodel.MainViewModel
import com.nginapps.core.ui.DestinationAdapter
import com.nginapps.capstone.utils.NetworkUtils
import com.nginapps.core.utils.State
import com.shreyaspatil.MaterialDialog.MaterialDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var adapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()
    }

    private fun initLayout() {

        adapter = DestinationAdapter()

        initObserver()

        handleNetworkChanges()

        with(mainRv) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        adapter.onItemClick = { selectData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.DESTINATION_ID, selectData.id)
            startActivity(intent)
        }
    }

    private fun initObserver() {
        viewModel.destinationsLiveData.observe(this) { state ->
            when(state) {
                is State.Loading -> progress_bar.show()
                is State.Success -> {
                    adapter.setData(state.data)
                    handleStateRvAdapter()
                    progress_bar.hide()
                }
                is State.Error -> {
                    showAlertDialog("error", state.message)
                    progress_bar.hide()
                }
            }
        }
    }

    private fun handleStateRvAdapter() {
        if (adapter.itemCount == 0) {
            emptyLav.show()
            mainRv.hide()
            return
        }
        emptyLav.hide()
        mainRv.show()
    }

    @SuppressLint("TimberArgCount")
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                return@observe
            } else {
                if (viewModel.destinationsLiveData.value is State.Error || adapter.itemCount == 0) {
                    viewModel.getDestinations()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, Class.forName("com.nginapps.favorite.favorite.FavoritesActivity")))
                true
            }
            else -> true
        }
    }

    override fun onBackPressed() {
        MaterialDialog.Builder(this)
            .setTitle("Exit?")
            .setMessage("Are you sure want to exit?")
            .setPositiveButton("Yes") { dialogInterface, _ ->
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .build()
            .show()
    }
}