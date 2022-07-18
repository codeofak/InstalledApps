package com.acode.installedapps

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.acode.installedapps.ui.theme.InstalledAppsTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstalledAppsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    val context = LocalContext.current

                    val listApps = installedApps()
                    val listAppsA = installedAppsA(context)
                    val listAppsB = installedAppsB()
                    //var listOfAppsName: List<String> = emptyList()
                    val listLabel: MutableList<String> = mutableListOf()
                    listApps.forEach {
                        listLabel += packageManager.getApplicationLabel(it).toString()
                    }


                    LazyColumn {
                        items(listAppsA){
                              //for listOfApps
                            //Text(text = it.applicationInfo.loadLabel(packageManager).toString())

                              // for InstalledAppsA()
                            Text(text = it.activityInfo.loadLabel(packageManager).toString())

                              // for listOfAppsB
                            //Text(text = it)
                            Divider()
                        }
                    }

                }
            }
        }
    }

    fun installedApps(): MutableList<ApplicationInfo> {
        /*return packageManager.getInstalledPackages(PackageManager.GET_META_DATA)*/
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun installedAppsA(context: Context): MutableList<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.ACTION_APPLICATION_PREFERENCES)

        return context.packageManager.queryIntentActivities(intent, 0)
    }


    //METHOD:2: Not working
    fun installedAppsB(): List<String> {
        val pm = packageManager
        val installedApps = pm.getInstalledApplications(0)
        var appList = emptyList<String>()
        
        for (aInfo in installedApps) {
            appList += aInfo.loadLabel(pm).toString()
        }
        return appList
    }

}



