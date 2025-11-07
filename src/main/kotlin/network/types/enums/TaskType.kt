package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class TaskType {
	None,
	Woodcutting,
	Fishing,
	Mining,
	Carpentry,
	Smelting,
	Smithing,
	Combat,
	Cooking,
	Foraging,
	Farming,
	Crafting,
	Agility,
	Plundering,
	Enchanting,
	Brewing,
	Exterminating,
	ItemCreation
}