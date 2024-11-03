package com.note.note.data

import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.note.domain.model.NoteLink
import com.note.note.domain.repository.LinkMetaDataFetcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.UnsupportedMimeTypeException
import org.jsoup.nodes.Document
import java.net.MalformedURLException
import javax.inject.Inject

internal class JsoupLinkMetaDataFetcher @Inject constructor(
    @Dispatcher(NoteDispatcher.IO) private val dispatcher: CoroutineDispatcher
): LinkMetaDataFetcher {
    override suspend fun fetch(link: String): Result<NoteLink, DataError.Parse> {
        val parseLink = if (link.startsWith("http://") || link.startsWith("https://")) {
            link
        } else {
            "https://$link"
        }

        return withContext(dispatcher) {
            try{
                val document = Jsoup.connect(parseLink)
                    .ignoreContentType(true)
                    .userAgent("Mozilla")
                    .referrer("http://www.google.com")
                    .timeout(10000)
                    .followRedirects(true)
                    .get()

                val parseData = parseMetaData(document)

                if (parseData.isValid()) {
                    Result.Success(parseData)
                } else {
                    Result.Error(DataError.Parse.DATA_PARSING_FAILED)
                }
            }catch (e: MalformedURLException) {
                Result.Error(DataError.Parse.WRONG_URL_FORMAT)
            }catch (e: UnsupportedMimeTypeException) {
                Result.Error(DataError.Parse.UNSUPPORTED_DATA_TYPE)
            }catch (e: Exception) {
                Result.Error(DataError.Parse.UNKNOWN)
            }
        }
    }

    private fun parseMetaData(document: Document): NoteLink {
        val metaTags = document.select("meta[property^=og:]")
        return NoteLink(
            image = metaTags.firstOrNull { it.attr("property") == "og:image" }?.attr("content") ?: "",
            description = metaTags.firstOrNull { it.attr("property") == "og:description" }?.attr("content") ?: "",
            link = metaTags.firstOrNull { it.attr("property") == "og:url" }?.attr("content") ?: "",
            title = metaTags.firstOrNull { it.attr("property") == "og:title" }?.attr("content") ?: ""
        )
    }
}