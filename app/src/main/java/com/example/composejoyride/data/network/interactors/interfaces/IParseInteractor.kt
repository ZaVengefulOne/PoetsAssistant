package com.example.composejoyride.data.network.interactors.interfaces

import com.example.composejoyride.di.models.Article

interface IParseInteractor {
    suspend fun getRandomArticle(): Article
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(url: String): Article
}
