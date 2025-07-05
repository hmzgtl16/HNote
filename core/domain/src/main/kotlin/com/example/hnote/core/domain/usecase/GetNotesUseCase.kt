package com.example.hnote.core.domain.usecase

import com.example.hnote.core.data.repository.NoteRepository
import com.example.hnote.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    operator fun invoke(): Flow<Map<Boolean, List<Note>>> = noteRepository.notes
        .map {
            it
                .sortedByDescending(Note::updated)
                .groupBy(Note::pinned)
                .toSortedMap(compareByDescending(Boolean::not))
        }

}