package dev.uraxys.idleclient.utils.extensions

import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
internal suspend fun ChannelFuture.coroutineAwait(): ChannelFuture {
	val future = this
	if (future.isDone) return future

	return suspendCancellableCoroutine { cont ->
		future.addListener(ChannelFutureListener {
			if (it.isSuccess) cont.resume(it, null)
			else {
				val cause = it.cause()
				if (cause != null) cont.resumeWithException(cause)
				else cont.resumeWithException(RuntimeException("ChannelFuture failed"))
			}
		})
	}
}