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
import kotlin.math.round


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

fun formatDate(inputDate : String) : String {
    //Parse Input Format
    val date = LocalDate.parse(inputDate)
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return date.format(dateFormatter)
}

fun Double.roundToDecimal(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
