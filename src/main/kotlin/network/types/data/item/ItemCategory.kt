package dev.uraxys.idleclient.network.types.data.item

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /IdleMMOServer/Scripts/Shared/Data/Items

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
	SkillingTicket,
}