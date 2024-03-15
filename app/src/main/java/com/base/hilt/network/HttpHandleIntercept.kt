package com.base.hilt.network

import com.base.hilt.utils.DebugLog
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class HttpHandleIntercept() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().headers(getJsonHeader()).build()
        val response: Response?

        response = chain.proceed(request)
        if (response.code == 401) {
            return generateCustomResponse(
                401, "",
                chain.request()
            )!!

        } else if (response.code == 500) {
            return generateCustomResponse(
                500, "",
                chain.request()
            )!!


        }

        return response
    }

    private fun getJsonHeader(): Headers {
        val builder = Headers.Builder()
        builder.add("Content-Type", "application/json")
        builder.add("Accept", "application/json")
        builder.add("is-mobile", "1")
        builder.add("lang-code", "en")

        return builder.build()
    }

    /**
     * generate custom response for exception
     */
    fun generateCustomResponse(code: Int, message: String, request: Request): Response? {
        try {
            val body = ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                getJSONObjectForException(message, code).toString()
            )
            return Response.Builder()
                .code(code)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body(body)
                .message(message)
                .build()
        } catch (ex: Exception) {
            DebugLog.print(ex)
            return null
        }

    }

    /**
     * generate JSON object for error case
     */
    private fun getJSONObjectForException(message: String, code: Int): JSONObject {

        try {
            val jsonMainObject = JSONObject()

            val `object` = JSONObject()
            `object`.put("status", false)
            `object`.put("message", message)
            `object`.put("message_code", code)
            `object`.put("status_code", code)

            jsonMainObject.put("meta", `object`)

            val obj = JSONObject()
            obj.put("error", JSONArray().put(message))

            jsonMainObject.put("errors", obj)

            return jsonMainObject
        } catch (e: JSONException) {
            DebugLog.print(e)
            return JSONObject()
        }
    }
}