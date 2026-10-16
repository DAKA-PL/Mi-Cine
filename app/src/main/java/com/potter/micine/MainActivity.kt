
package com.potter.micine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.potter.micine.ui.home.HomeScreenConViewModel
import com.potter.micine.ui.home.HomeViewModel
import com.potter.micine.ui.theme.MICINETheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

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
