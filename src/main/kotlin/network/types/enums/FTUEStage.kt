package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class FTUEStage {
	BeginTutorialStage,
	MiningStage,
	SmeltingStage,
	WoodcuttingStage,
	ArrowCraftingStage,
	PurchaseBowStage,
	EquipItemsStage,
	KillTurkeyStage,
	Completed,
}