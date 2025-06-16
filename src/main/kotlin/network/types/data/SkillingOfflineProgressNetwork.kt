package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.network.types.enums.Skill
import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class SkillingOfflineProgressNetwork(
	val receivedItemIds: IntArray?,
	val receivedItemAmounts: IntArray?,
	val itemsLost: IntArray?,
	val itemsLostAmounts: IntArray?,
	val offlineProgressSkills: Array<Skill>?,
	val offlineExperiences: FloatArray?,
	val taskTypeToContinue: TaskType,
	val taskIdToContinue: Byte,
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as SkillingOfflineProgressNetwork

		if (taskIdToContinue != other.taskIdToContinue) return false
		if (!receivedItemIds.contentEquals(other.receivedItemIds)) return false
		if (!receivedItemAmounts.contentEquals(other.receivedItemAmounts)) return false
		if (!itemsLost.contentEquals(other.itemsLost)) return false
		if (!itemsLostAmounts.contentEquals(other.itemsLostAmounts)) return false
		if (!offlineProgressSkills.contentEquals(other.offlineProgressSkills)) return false
		if (!offlineExperiences.contentEquals(other.offlineExperiences)) return false
		if (taskTypeToContinue != other.taskTypeToContinue) return false

		return true
	}

	override fun hashCode(): Int {
		var result: Int = taskIdToContinue.toInt()
		result = 31 * result + receivedItemIds.contentHashCode()
		result = 31 * result + receivedItemAmounts.contentHashCode()
		result = 31 * result + itemsLost.contentHashCode()
		result = 31 * result + itemsLostAmounts.contentHashCode()
		result = 31 * result + offlineProgressSkills.contentHashCode()
		result = 31 * result + offlineExperiences.contentHashCode()
		result = 31 * result + taskTypeToContinue.hashCode()
		return result
	}
}