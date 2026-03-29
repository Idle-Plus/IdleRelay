package dev.uraxys.idleclient.network.types.packets

open class NetworkMessage(
	var MsgType: Int = -1,
	var OriginServerId: String? = null,
)