package com.fazalulabid.samespacemusic.domain.usecase

import com.fazalulabid.samespacemusic.domain.model.MusicTrack
import com.fazalulabid.samespacemusic.domain.repository.MusicTrackRepository
import javax.inject.Inject

class GetAllMusicTracksUseCase @Inject constructor(
    private val repository: MusicTrackRepository
) : BaseUseCase<Unit, List<MusicTrack>>() {

    override suspend fun execute(params: Unit): List<MusicTrack> {
        return repository.getAllSongs()
    }
}