package com.acode.installedapps

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.acode.installedapps.ui.theme.InstalledAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstalledAppsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    installedApps()

                    val listApps = installedApps()
                    //var listOfAppsName: List<String> = emptyList()


                    LazyColumn {
                        items(listApps){
                            Text(text = it.packageName)
                        }
                    }

                }
            }
        }
    }

    fun installedApps(): MutableList<PackageInfo> {
        val packinfo: MutableList<PackageInfo> =
            /*packageManager.getInstalledApplications(PackageManager.GET_META_DATA)*/
            packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        return packinfo

    }

}



