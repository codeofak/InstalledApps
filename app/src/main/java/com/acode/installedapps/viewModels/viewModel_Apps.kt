package com.acode.installedapps.viewModels

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ViewModelApps(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _listOfApps = MutableStateFlow<List<ApplicationInfo>>(emptyList())
    val listOfApps = _listOfApps.asStateFlow()


    init {
        viewModelScope.launch {
            loadAppPackages(context)
        }
    }


    private fun loadAppPackages(context: Context) {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val appPackages: MutableList<ApplicationInfo> =
            emptyList<ApplicationInfo>()
                .toMutableList()

        packages.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                appPackages += it

            }
        }

        _listOfApps.value = appPackages
    }

}














