package com.fazalulabid.samespacemusic.domain.usecase

import com.fazalulabid.samespacemusic.R
import com.fazalulabid.samespacemusic.core.util.Resource
import com.fazalulabid.samespacemusic.presentation.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

abstract class BaseUseCase<Params, OutputType> {
    abstract suspend fun execute(params: Params): OutputType

    suspend operator fun invoke(params: Params): Flow<Resource<OutputType>> = flow {
        try {
            emit(Resource.Loading())
            val result = execute(params)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private suspend fun FlowCollector<Resource<OutputType>>.handleException(e: Exception) {
        when (e) {
            is HttpException -> emit(
                Resource.Error(
                    e.localizedMessage?.let { UiText.DynamicString(it) }
                        ?: UiText.StringResource(R.string.an_unexpected_error_occurred)
                )
            )

            is IOException -> emit(Resource.Error(UiText.StringResource(R.string.couldn_t_reach_server)))
            else -> {
                // Handle other types of exceptions or log them
                emit(Resource.Error(UiText.StringResource(R.string.error_unknown)))
            }
        }
    }
}