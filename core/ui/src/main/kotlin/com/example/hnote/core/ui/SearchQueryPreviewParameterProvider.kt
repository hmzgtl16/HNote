package com.example.hnote.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.hnote.core.model.SearchQuery

class SearchQueryPreviewParameterProvider : PreviewParameterProvider<List<SearchQuery>> {

    override val values: Sequence<List<SearchQuery>> = sequenceOf(
        listOf(
            SearchQuery(query = "query01"),
            SearchQuery(query = "query02"),
            SearchQuery(query = "query03"),
            SearchQuery(query = "query04"),
            SearchQuery(query = "query05"),
            SearchQuery(query = "query06"),
            SearchQuery(query = "query07"),
            SearchQuery(query = "query08"),
            SearchQuery(query = "query09"),
            SearchQuery(query = "query10")
        )
    )
}