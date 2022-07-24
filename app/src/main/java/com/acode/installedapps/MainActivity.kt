package com.acode.installedapps

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.acode.installedapps.ui.theme.InstalledAppsTheme
import com.acode.installedapps.viewModels.ViewModelApps
import kotlinx.coroutines.launch

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
                    val viewModelApps: ViewModelApps by viewModels()


                    ListOfApps(context = context,viewModelApps = viewModelApps)

                }
            }
        }
    }
}


@Composable
fun ListOfApps(context: Context,viewModelApps: ViewModelApps) {

    val pm = context.packageManager
//    val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
//    val appPackages: MutableList<ApplicationInfo> = emptyList<ApplicationInfo>().toMutableList()

    val appPackages = viewModelApps.loadAppPackages(context)

    val coroutineScope = rememberCoroutineScope()


//    packages.forEach {
//        if (pm.getLaunchIntentForPackage(it.packageName) != null) {
//            appPackages += it
//        }
//    }

    val intent = Intent(Intent.ACTION_DELETE)

    Surface {

        LazyColumn {
            items(appPackages) {

                Row(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                context.startActivity(pm.getLaunchIntentForPackage(it.packageName))
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    //Icon
                    //AsyncImage(model = it.loadIcon(pm), contentDescription = null)   ---> Don't use it directly
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = rememberAsyncImagePainter(model = it.loadIcon(pm),
                            filterQuality = FilterQuality.Low),
                        contentDescription = "App Icon"
                    )

                    Text(text = it.loadLabel(context.packageManager).toString())

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                context.startActivity(intent.setData(Uri.parse("package:${it.packageName}")))
                            }

                        }
                    ) {
                        Text(text = "Uninstall")
                    }
                }
            }
        }
    }
}



