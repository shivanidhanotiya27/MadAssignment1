package com.mad.assignment4.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mad.assignment4.network.retrofit.model.post.PostItem
import com.mad.assignment4.network.retrofit.service.ApiEndPointServiceForPost
import java.lang.Exception

class PostsPagingSource(val apiEndPointServiceForPost: ApiEndPointServiceForPost) : PagingSource<Int, PostItem>() {
    override fun getRefreshKey(state: PagingState<Int, PostItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostItem> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val pageSize = params.loadSize ?: DEFAULT_PAGE_SIZE
            val postResponse = apiEndPointServiceForPost.getPosts(page, pageSize)

            LoadResult.Page(
                data = postResponse.body()!!,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (postResponse.body()?.isEmpty()!!) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val FIRST_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 20
    }
}