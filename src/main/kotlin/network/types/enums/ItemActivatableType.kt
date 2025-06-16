package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class ItemActivatableType {
	None,
	Premium,
	WeaponEffect,
	TreasureChest,
	Potion,
	BossHunter,
	Prestige,
	InventorySpaceToken,
	AutoLoadout,
	ClanVaultSpaceToken,
	BossPage,
	QuestingToken,
	ExterminatingToken,
}