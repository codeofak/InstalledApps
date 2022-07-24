package com.acode.installedapps.viewModels

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import com.acode.installedapps.dataClass.AppClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ViewModelApps(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    //List of All Installed Packages
    private val _listOfApps = MutableStateFlow<List<ApplicationInfo>>(emptyList())
    val listOfApps = _listOfApps.asStateFlow()

    //list of Installed apps as of AppClass dataClass
    private val _listOfAppsB = MutableStateFlow<List<AppClass>>(emptyList())
    val listOfAppsB = _listOfAppsB.asStateFlow()


    init {

        loadAppPackages(context)

    }


    private fun loadAppPackages(context: Context) {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val appPackages: MutableList<ApplicationInfo> =
            emptyList<ApplicationInfo>()
                .toMutableList()

        val list: MutableList<AppClass> = mutableListOf()

        packages.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                appPackages += it
                list += AppClass(
                    name = it.loadLabel(pm).toString(),
                    icon = it.loadIcon(pm),
                    packageName = it.packageName
                )

            }
        }

        _listOfAppsB.value = list

        _listOfApps.value = appPackages
    }

}














