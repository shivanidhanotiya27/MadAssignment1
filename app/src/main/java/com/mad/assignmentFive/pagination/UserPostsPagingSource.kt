package com.mad.assignmentFive.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mad.assignmentFive.network.model.post.Post
import com.mad.assignmentFive.network.service.ApiEndPointService
import java.lang.Exception

class UserPostsPagingSource(
    private val apiEndPointService: ApiEndPointService,
private val userId: Int)
    : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = apiEndPointService.getPostsByUser(userId,
            pageNumber = page,
            pageSize = params.loadSize ?: DEFAULT_PAGE_SIZE
            )

            LoadResult.Page(
                data = response.body()!!.data.posts,
                prevKey = if(page == FIRST_PAGE) null else page - 1,
                nextKey = if(response.body()!!.data.posts.isEmpty()) null else page + 1
            )

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    companion object {
        const val FIRST_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 20
    }

}