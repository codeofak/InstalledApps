package com.acode.installedapps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
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


                    ListOfApps(context = context, viewModelApps = viewModelApps)

                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfApps(context: Context, viewModelApps: ViewModelApps) {

    val pm = context.packageManager
    //Getting List of installed Packages from viewModelApp
    val appPackages = viewModelApps.listOfApps.collectAsState().value


    val coroutineScope = rememberCoroutineScope()

    val intent = Intent(Intent.ACTION_DELETE)

    Surface {

        LazyColumn(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            state = rememberLazyListState()
        ) {
            items(appPackages) {

                Row(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                        .fillMaxWidth()
                        .combinedClickable(

                            //Launching app on clicking the row
                            onClick = {
                                coroutineScope.launch {
                                    context.startActivity(pm.getLaunchIntentForPackage(it.packageName))
                                }
                            },
                            //Uninstalling App on long clicked
                            onLongClick = {
                                coroutineScope.launch {
                                    context.startActivity(intent.setData(Uri.parse("package:${it.packageName}")))

                                }
                            }
                        )
                ) {

                    //App Icon
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = rememberAsyncImagePainter(model = it.loadIcon(pm),
                            filterQuality = FilterQuality.Low),
                        contentDescription = "App Icon"
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    //App Label
                    Text(
                        text = it.loadLabel(context.packageManager).toString(),
                        overflow = TextOverflow.Ellipsis

                    )


                }
            }
        }
    }
}



