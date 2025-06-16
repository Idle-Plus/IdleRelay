package dev.uraxys.idleclient.utils

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Suppress("FunctionName")
fun SmartThreadPoolExecutor(
	corePoolSize: Int,
	maximumPoolSize: Int,
	keepAliveTime: Long,
	unit: TimeUnit
): ThreadPoolExecutor {
	return ThreadPoolExecutor(
		corePoolSize,
		maximumPoolSize,
		keepAliveTime,
		unit,
		ThreadPoolQueue(),
		QueuingRejectedExecutionHandler()
	)
}

class ThreadPoolQueue : LinkedBlockingQueue<Runnable>() {

	override fun offer(e: Runnable): Boolean {
		// Offer it to the queue if there is 0 items already queued, else
		// return false so the TPE will add another thread. If we return false
		// and max threads have been reached then the RejectedExecutionHandler
		// will be called which will do the put into the queue.
		if (isEmpty()) return super.offer(e)
		else return false
	}
}

class QueuingRejectedExecutionHandler : RejectedExecutionHandler {

	override fun rejectedExecution(r: Runnable, executor: ThreadPoolExecutor) {
		try {
			// This does the actual put into the queue. Once the max threads
			//  have been reached, the tasks will then queue up.
			executor.queue.put(r)
			// we do this after the put() to stop race conditions
			if (executor.isShutdown)
				throw RejectedExecutionException("Task $r rejected from $executor")
		} catch (e: InterruptedException) {
			Thread.currentThread().interrupt()
			return
		}
	}
}