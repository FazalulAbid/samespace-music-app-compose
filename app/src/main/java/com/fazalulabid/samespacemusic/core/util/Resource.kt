package com.fazalulabid.samespacemusic.core.util

import com.fazalulabid.samespacemusic.presentation.util.UiText

sealed class Resource<T>(
    val data: T? = null,
    val uiText: UiText? = null,
    val error: Exception? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(uiText: UiText? = null, data: T? = null, error: Exception? = null) :
        Resource<T>(data, uiText, error)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}