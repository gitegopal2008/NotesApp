package com.notesapp.core.extension

import com.notesapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.withResource(): Flow<Resource<T>> {
    return this.map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading) }
        .catch { e -> emit(Resource.Error(e.message ?: "Unknown error", e)) }
}
