package com.example.dovedrop.data.repository


interface AuthRepository {
    suspend fun LogUserIn(emailAddress: String, password: String)
}

//class AuthRepositoryImpl(
//    private val supabase: SupabaseClient
//) :AuthRepository{
//    override suspend fun LogUserIn(emailAddress: String, passWord: String) {
//        supabase.auth.signInWith(Email) {
//            email = emailAddress
//            password = passWord
//        }
//    }
//}