package com.glen.finalproject
import android.content.Context
import com.google.gson.reflect.TypeToken

object ManageForms {
    private const val FORMS_KEY = "FormsKey"

    private lateinit var prefsLoader: PrefsLoader
    private val formList = mutableListOf<FormData>()

    fun initialize(context: Context) {
        prefsLoader = PrefsLoader(context, "FormPrefs")
    }

    fun addForm(formData: FormData) {
        formList.add(formData)
        saveForms()
    }

    fun getAllForms(): List<FormData> {
        loadForms()
        return formList.toList()
    }

    fun deleteForm(position: Int) {
        formList.removeAt(position)
        saveForms()
    }

    private fun saveForms() {
        prefsLoader.saveData(FORMS_KEY, formList)
    }

    private fun loadForms() {
        formList.clear()
        val loadedForms: List<FormData>? =
            prefsLoader.loadData(FORMS_KEY, object : TypeToken<List<FormData>>() {})
        if (loadedForms != null) {
            formList.addAll(loadedForms.sortedByDescending { it.submissionDate })
        }
    }

}
