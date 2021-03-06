package son.ysy.architecture.http

import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.koin.core.Koin
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import son.ysy.architecture.http.json.annotations.CheckResponseCode
import son.ysy.architecture.http.json.annotations.IgnoreParents
import son.ysy.architecture.http.json.annotations.SkipCheckResponseCode
import java.lang.reflect.Type
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

internal class MoshiConverterFactory(koin: Koin) : Converter.Factory() {

    private val delegateConverterFactory by lazy {
        MoshiConverterFactory.create(koin.get())
    }

    private val defaultCheckResponseCode: CheckResponseCode by koin.inject()

    private val defaultIgnoreParents: IgnoreParents by koin.inject()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val annotationList = annotations.toMutableList()

        val containsSkipCheckResponseCode = annotationList.any { it is SkipCheckResponseCode }
        val containsCheckResponseCode = annotationList.any { it is CheckResponseCode }

        when {
            containsSkipCheckResponseCode -> annotationList.removeAll { it is SkipCheckResponseCode }
            !containsCheckResponseCode -> {
                defaultCheckResponseCode.apply(annotationList::add)
                defaultIgnoreParents.apply(annotationList::add)
            }
        }

        val resultAnnotationArray = Array(annotationList.size) {
            annotationList[it]
        }

        return delegateConverterFactory.responseBodyConverter(type, resultAnnotationArray, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return delegateConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }
}