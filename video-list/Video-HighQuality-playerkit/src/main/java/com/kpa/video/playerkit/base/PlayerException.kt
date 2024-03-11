package com.kpa.video.playerkit.base

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class PlayerException : Exception {
    val CODE_ERROR_ACTION = 1
    val CODE_SOURCE_SET_ERROR = 2
    val CODE_TRACK_SELECT_ERROR = 3
    val CODE_SOURCE_LOAD_ERROR = 4
    val CODE_SUBTITLE_PARSE_ERROR = 5
    val CODE_BUFFERING_TIME_OUT = 6

    private var code = 0

    constructor(code: Int, message: String?) : super("code:$code; msg:$message") {
        this.code = code
    }

    constructor(code: Int, message: String?, cause: Throwable) : super(
        "code:$code; msg:$message",
        cause
    ) {
        this.code = code
    }

    fun getCode(): Int {
        return code
    }
}