package com.learning.tmdb_movie.Util

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun Response<*>.getErrorMessage() : String =
    "Error: ${this.errorBody()?.string()}"

//Toast
fun Context.showToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//API Call
suspend fun <T, R> safeApiCall(
    apiCall : suspend () -> Response<T>,
    onSuccess : (T) -> R
) : Result <R> {
    return try {
        val response = apiCall()
        val body = response.body()
        when{
            response.isSuccessful && body != null -> {
                Result.success(onSuccess(body!!))
            }
            else -> {
                val errorMsg = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception(errorMsg))
            }
        }
    }
    catch (e : Exception){
        Result.failure(Exception(e.message))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(inputDate : String) : String {
    //Parse Input Format
    val date = LocalDate.parse(inputDate)
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d,yyyy")
    return date.format(dateFormatter)
}