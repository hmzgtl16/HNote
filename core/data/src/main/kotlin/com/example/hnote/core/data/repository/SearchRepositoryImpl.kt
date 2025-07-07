package com.example.hnote.core.data.repository

import com.example.hnote.core.data.util.toEntity
import com.example.hnote.core.data.util.toModel
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.dao.NoteFtsDao
import com.example.hnote.core.database.dao.SearchQueryDao
import com.example.hnote.core.database.model.NoteWithItems
import com.example.hnote.core.database.model.SearchQueryEntity
import com.example.hnote.core.model.SearchQuery
import com.example.hnote.core.model.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val searchQueryDao: SearchQueryDao,
    private val noteFtsDao: NoteFtsDao,
    private val itemFtsDao: ItemFtsDao
) : SearchRepository {
    override suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult> {
        val noteIds = noteFtsDao.searchAllNotes(text = "*${searchQuery.query}*")
        val itemIds = itemFtsDao.searchAllItems(text = "*${searchQuery.query}*")

        val noteFlow = noteIds
            .mapLatest(List<Long>::toSet)
            .distinctUntilChanged()
            .flatMapLatest(noteDao::getNotesByIds)
        val itemFlow = itemIds
            .mapLatest { it.toSet() }
            .distinctUntilChanged()
            .flatMapLatest(noteDao::getItemsByIds)

        return combine(
            flow = noteFlow,
            flow2 = itemFlow,
            transform = { noteList, itemList ->
                val notes = (noteList + itemList)
                    .distinctBy { it.note.id }
                    .map(NoteWithItems::toModel)

                SearchResult(notes = notes)
            }
        )
    }

    override fun getAllSearchQueries(limit: Int): Flow<List<SearchQuery>> =
        searchQueryDao.getSearchQueries(limit = limit)
            .map { it.map(SearchQueryEntity::toModel) }

    override suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery) =
        searchQueryDao.upsertSearchQuery(searchQuery = searchQuery.toEntity())

    override suspend fun delete(searchQuery: SearchQuery) =
        searchQueryDao.delete(searchQuery = searchQuery.toEntity())

    override suspend fun deleteAll() =
        searchQueryDao.deleteAll()
}