package dev.uraxys.idleclient.utils.extensions

import io.ktor.websocket.CloseReason
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close

class CloseCause(val code: Short, val reason: String) {
	constructor(code: CloseReason.Codes, reason: String) : this(code.code, reason)
}

suspend fun WebSocketSession.close(reason: CloseCause = CloseCause(1000, "")) {
	close(CloseReason(reason.code, reason.reason))
}

suspend fun WebSocketSession.close(code: CloseReason.Codes, reason: String = "") {
	close(CloseReason(code.code, reason))
}