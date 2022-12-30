package com.cj3dreams.binchecker.di

import android.app.Application
import androidx.room.Room
import com.cj3dreams.binchecker.repo.DataRepositoryImpl
import com.cj3dreams.binchecker.source.local.AppDb
import com.cj3dreams.binchecker.source.local.BinHistoryDao
import com.cj3dreams.binchecker.source.local.LocalSourceImpl
import com.cj3dreams.binchecker.source.remote.BinApiRequest
import com.cj3dreams.binchecker.source.remote.RemoteSourceImpl
import com.cj3dreams.binchecker.utils.AppConstants.BASE_URL
import com.cj3dreams.binchecker.vm.BinViewModel
import com.cj3dreams.binchecker.vm.HistoryViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun <Api> provideRetrofit(api: Class<Api>) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)

    factory { provideRetrofit(BinApiRequest::class.java) }

}
val dataSourceModule = module {

    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, AppDb::class.java, "AppDb")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(appDb: AppDb) = appDb.binHistoryDao()

    fun provideDataRepository(api: BinApiRequest, dao: BinHistoryDao) =
        DataRepositoryImpl(RemoteSourceImpl(api), LocalSourceImpl(dao))

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
    single {provideDataRepository(get(), get())}

    viewModel {
        BinViewModel(get())
    }
}
val historyViewModel = module {
    viewModel {
        HistoryViewModel(get())
    }
}