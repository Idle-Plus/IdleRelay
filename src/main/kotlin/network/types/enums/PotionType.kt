package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

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
}