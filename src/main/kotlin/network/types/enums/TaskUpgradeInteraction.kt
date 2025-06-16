package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class TaskUpgradeInteraction {
	None,
	UpgradeSaveCosts,
	UpgradeAutoCook,
	UpgradePlankGoldDecrease,
	UpgradeWoodcuttingExtraPlank,
	LampExtraCoal,
}