package dev.uraxys.idleclient.network.types.data.task

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /IdleMMOServer/Scripts/Shared/Tasks

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
	ItemCreation,
	Invocation
}