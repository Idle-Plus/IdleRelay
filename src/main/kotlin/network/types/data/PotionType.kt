package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /Player

@ClientData
enum class PotionType {
	None,
	Swiftness,
	Negotiation,
	Resurrection,
	Forgery,
	GreatSight,
	Trickery,
	DarkMagic,
	PurePower,
	AncientKnowledge,
	DragonfirePotion,
	Ascension
}