package com.base.hilt.network

/**
 * Package Name : com.kotlinusermodule.network.model
 * Project Name : BrainvireStructure
 */

enum class HttpErrorCode(val code: Int, val message:String ="") {
    /**
     * error in connecting to repository (Server or Database)
     */
    NO_CONNECTION(1003),

    /**
     * error in getting value (Json Error, Server Error, etc)
     */
    BAD_RESPONSE(500),

    /**
     * Time out  error
     */
    TIMEOUT(1003),

    /**
     * no data available in repository
     */
    EMPTY_RESPONSE(422),

    /**
     * an unexpected error
     */
    NOT_DEFINED(500),

    /**
     * bad credential
     */
    UNAUTHORIZED(401),

    /**
     * bad credential
     */
    NEW_VERSION_FOUND(222),

    /**
     * Job Cancel
     */
    JOB_CANCEL(1980),

    SERVER_SIDE_VALIDATION(422)
}
