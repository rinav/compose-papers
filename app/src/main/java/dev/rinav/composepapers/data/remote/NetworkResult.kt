package dev.rinav.composepapers.data.remote

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

//sealed class NetworkResult<T : Any> {
//    class Success<T : Any>(val data: T) : NetworkResult<T>()
//    class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
//    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
//}

sealed class PapersException(message: String, cause: Throwable? = null) {
    class NoInternetException : PapersException("No internet connection")
    class ApiException(message: String, cause: Throwable?): PapersException(message, cause)
    class UnexpectedException(cause: Throwable) : PapersException("Unexpected error", cause)
}

suspend fun <T : Any> Response<T>.bodyOrError(): Result<T, PapersException> {
    return try {
        if (this.isSuccessful && this.body() != null) {
            Ok(this.body()!!)
        } else {
            Err(PapersException.ApiException(
                message = "API call failed with code ${this.code()} and message: ${this.message()}",
                cause = null
            ))
        }
    } catch (e: IOException) {
        Err(PapersException.NoInternetException())
    } catch (e: HttpException) {
        Err(PapersException.ApiException(
            message = "HTTP error with code ${e.code()} and message: ${e.message()}",
            cause = e
        ))
    } catch (e: Exception) {
        Err(PapersException.UnexpectedException(e))
    }
}