package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class ItemCategory {
	None,
	Weapons,
	Tools,
	Armours,
	Jewelry,
	MasteryCapes,
	SkillingOutfits,
	Food,
	Potions,
	Consumables,
	Gemstones,
	Keys,
	OresAndBars,
	LogsAndPlanks,
	SeedsAndHarvest,
	EnchantmentScrolls,
	Activatables,
	Miscellaneous,
	Pets,
}