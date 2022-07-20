package com.acode.installedapps

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.acode.installedapps.ui.theme.InstalledAppsTheme
import com.acode.installedapps.viewModels.ViewModelApps

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstalledAppsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    val viewModelApps: ViewModelApps by viewModels()

                    val context = LocalContext.current

                    ListOfApps(context = context, viewModelApps = viewModelApps)

                }
            }
        }
    }
}


@Composable
fun ListOfApps(context: Context, viewModelApps: ViewModelApps) {
    val list = viewModelApps.listAppsC(context = context)

    LazyColumn {
        items(list) {
            Text(text = it)
            Divider()
        }
    }
}



