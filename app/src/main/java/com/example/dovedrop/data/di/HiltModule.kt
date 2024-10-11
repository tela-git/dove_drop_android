package com.example.dovedrop.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
//    @Provides
//    fun provideSupabaseClient(): SupabaseClient? {
//        try {
//            return createSupabaseClient(
//                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZueXBzYm15YWxiaHlscXhwbmtnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjg0NTc0OTQsImV4cCI6MjA0NDAzMzQ5NH0.dvbRzv6hlKi_qU8sz3tPHkytNkKvyemyHnvzyyrEV3A",
//                supabaseUrl = "https://vnypsbmyalbhylqxpnkg.supabase.co"
//            ) {
//                defaultSerializer = KotlinXSerializer(json = Json)
//                install(Postgrest)
//                install(Auth)
//            }
//        } catch (e: HttpRequestException) {
//            Log.e("LETTER", "Error Message: ${e.message}")
//            return null
//        }
//        catch (e: Exception) {
//            Log.e("LETTER", "Error Message: ${e.message}")
//           return null
//        }
//    }
}