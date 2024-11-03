package com.note.core.data.network

import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException


inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T, DataError.Network> {
    val response = try{
        execute.invoke()
    }catch (e: UnknownHostException){
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    }catch (e: SerializationException){
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    }catch (e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

inline fun <reified T> responseToResult(response: Response<T>): Result<T, DataError.Network> {
    return when(response.code()) {
        in 200..299 -> {
            val body = response.body()
            if(body == null || body is Unit) {
                Result.Success(Unit as T)
            }else{
                Result.Success(body)
            }
        }
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        403 -> Result.Error(DataError.Network.FORBIDDEN)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}