package com.note.core.data.network

import com.note.core.data.BuildConfig
import com.note.core.domain.AuthStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation


class AuthInterceptor (
    private val authStorage: AuthStorage
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestInvocation = request.tag(Invocation::class.java)

        // 인증이 필요한 Api인지 체크 (인증이 필요한 API라면 로그인 URL로 연결 및 인증 헤더 추가 하지 않음)
        val isAuthHeaderRequired = requestInvocation?.method()?.annotations?.none {
            it.annotationClass == NotAuthenticated::class
        } ?: false

        //각각 다른 앤드포인트를 가지고 있기 때문에 BaseUrl의 Host를 변경해줘야 함
        //Retrofit 객체를 2개 만들어서 Annotation으로 나누는 방법도 있지만 로그인 API는 하나만 있기 때문에 이러한 방법을 선택함
        val newUrl = if(isAuthHeaderRequired) {
            request.url.newBuilder()
                .host(BuildConfig.API_HOST)
                .build()
        }else {
            request.url.newBuilder()
                .host(BuildConfig.AUTH_HOST)
                .build()
        }

        val newRequestBuilder = request.newBuilder().url(newUrl)

        newRequestBuilder.addHeader("Accept", "application/json")

        if(isAuthHeaderRequired) {
            val authInfo = authStorage.get()

            if(authInfo != null && authInfo.isValid()){
                newRequestBuilder.addHeader("Authorization", "${authInfo.tokenType} ${authInfo.accessToken}")
            }
        }

        return chain.proceed(newRequestBuilder.build())
    }
}