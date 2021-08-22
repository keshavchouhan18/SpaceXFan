package com.spacexfanapplication.di

import com.spacexfanapplication.repo.HomeRepo
import org.koin.dsl.module

val RepositoryModule = module {
    single { HomeRepo() }
}

val spaceXModules = listOf(RepositoryModule)