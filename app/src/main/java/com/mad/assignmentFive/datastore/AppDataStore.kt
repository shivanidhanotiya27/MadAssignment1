package com.mad.assignmentFive.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore("app_datastore")

class AppDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val isUserAdded = booleanPreferencesKey("user_exists")

    fun getUserStatus(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[isUserAdded] ?: false
        }
    }

    suspend fun addUserStatus(status: Boolean) {
        context.dataStore.edit {
            it[isUserAdded] = status
        }
    }
}