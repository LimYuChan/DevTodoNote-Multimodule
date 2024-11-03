package com.note.core.database.extension

import android.database.SQLException
import android.database.sqlite.SQLiteFullException
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result

suspend fun <T> safeDbCall(action: suspend () -> T): Result<T, DataError.Local> {
    return try {
        Result.Success(action())
    } catch (e: SQLiteFullException) {
        Result.Error(DataError.Local.DISK_FULL)
    } catch (e: SQLException) {
        Result.Error(DataError.Local.SQL_EXCEPTION)
    } catch (e: Exception) {
        Result.Error(DataError.Local.UNKNOWN)
    }
}