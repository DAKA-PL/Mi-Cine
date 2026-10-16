
package com.potter.micine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.potter.micine.data.local.MiCineDatabase
import com.potter.micine.ui.home.HomeScreenConViewModel
import com.potter.micine.ui.home.HomeViewModel
import com.potter.micine.ui.theme.MICINETheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MiCineDatabase::class.java,
            "mi_cine_database"
        ).build()
    }

    private val homeViewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                return HomeViewModel(
                    database.peliculaDao()
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MICINETheme {
                HomeScreenConViewModel(
                    viewModel = homeViewModel
                )
            }
        }
    }
}
