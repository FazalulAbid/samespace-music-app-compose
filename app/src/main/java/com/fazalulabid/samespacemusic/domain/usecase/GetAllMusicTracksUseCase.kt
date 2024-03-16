package com.fazalulabid.samespacemusic.domain.usecase

import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.domain.repository.MusicTrackRepository
import javax.inject.Inject

class GetAllMusicTracksUseCase @Inject constructor(
    private val repository: MusicTrackRepository
) : BaseUseCase<GetAllMusicTracksUseCase.Params, List<MusicTrack>>() {

    data class Params(val needToFetchFromApi: Boolean)

    override suspend fun execute(params: Params): List<MusicTrack> {
        return repository.getAllSongs(params.needToFetchFromApi)
    }
}