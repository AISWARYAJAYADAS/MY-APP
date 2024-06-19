package com.example.myapplication.utils.manager

import com.example.myapplication.api.model.Output
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.ui.common.CommonRepository
import com.example.myapplication.ui.common.Config
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigurationManager @Inject constructor(
    private val sharedPrefs: SharedPref,
    private val repository: CommonRepository,
    private val jsonResourceManager: JsonResourceManager,
) {
    private var job: Job? = null
    private val completableJob = Job()
    private val scopeIO = CoroutineScope(completableJob + Dispatchers.IO)


    fun callMasterConfiguration(onCompletion: ((String?) -> Unit)? = null) {
        job?.cancel()
        job = scopeIO.launch {
            if (job?.isCancelled == false) {
                repository.fetchMaterConfig().collect { response ->
                    when (response) {
                        is Output.Success -> {
                            response.value?.data?.let { setConfigurationList(it) }
                            sharedPrefs.saveConfigUpdatedStatus(status = true)
                            onCompletion?.invoke(null)
                        }

                        is Output.Error -> {
                            if (sharedPrefs.isConfigUpdated().not()) {
                                jsonResourceManager.getConfigurationFromJson()?.let {
                                    setConfigurationList(it)
                                }
                                sharedPrefs.saveConfigUpdatedStatus(status = false)
                            }
                            onCompletion?.invoke(response.apiError.statusMessage)
                        }
                    }
                }
            }
        }
    }

    private fun getConfigAdapter(): JsonAdapter<List<Config>> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, Config::class.java)
        return moshi.adapter(listType)
    }

    private fun setConfigurationList(configList: List<Config>) {
        val json = getConfigAdapter().toJson(configList)
        sharedPrefs.saveConfiguration(json)
    }


}