package com.acode.installedapps

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
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

                    val listApps = installedApps()
                    val listAppsA = installedAppsA(context)
                    val listAppsB = installedAppsB()
                    //var listOfAppsName: List<String> = emptyList()
                    val listLabel: MutableList<String> = mutableListOf()

                    //Method 3 ----Working Good
                    //val listAppsC = listAppsC(context)

                    listApps.forEach {
                        listLabel += packageManager.getApplicationLabel(it).toString()
                    }


//                    LazyColumn {
//                        items(listAppsB) {
//                            //for listOfApps
//                            //Text(text = it.applicationInfo.loadLabel(packageManager).toString())
//
//                            // for InstalledAppsA()
//                            //Text(text = it.activityInfo.loadLabel(packageManager).toString())
//
//                            //for listAppsC
//                            //Text(text = it)
//
//                            // for listOfAppsB
//                            //Text(text = it)
//                            Divider()
//                        }
//                    }
                    
                    ListOfApps(context = context, viewModelApps = viewModelApps)

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

    //MEtHOD: 3 ---- Working good
//    fun listAppsC(context: Context): List<String> {
//        val appInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
//
//        var string: List<String> = emptyList()
//
//        appInfoList.forEach {
//            if (context.getPackageManager().getLaunchIntentForPackage(it.packageName) != null){
//
//                string = string.plus(it.loadLabel(packageManager).toString())
//            }
//
//        }
//        return string
//
//    }

}

@Composable
fun ListOfApps(context: Context, viewModelApps: ViewModelApps){
    var list = viewModelApps.listAppsC(context = context)
    
    LazyColumn(){
        items(list){
            Text(text = it)
            Divider()
        }
    }
}



