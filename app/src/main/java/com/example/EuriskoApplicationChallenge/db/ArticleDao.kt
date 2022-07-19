package com.example.EuriskoApplicationChallenge.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.EuriskoApplicationChallenge.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}