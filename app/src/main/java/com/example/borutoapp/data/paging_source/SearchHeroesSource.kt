package com.example.borutoapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.domain.model.Hero
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchHeroesSource @Inject constructor(
    private val borutoApi: BorutoApi,
    private val query: String
) : PagingSource<Int, Hero>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        val position = params.key?:1
        return try {
            val remoteData = borutoApi.searchHeroes(position,query)
            val heroes = remoteData.heroes

            val nextKey = if(heroes.size<params.loadSize){
                null
            }else{
                position+1
            }
            LoadResult.Page(
                data = heroes,
                prevKey = if(position==1) null else position-1,
                nextKey = nextKey
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}