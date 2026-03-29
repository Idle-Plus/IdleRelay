package dev.uraxys.idleclient.network.types.data.event

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /IdleMMOServer/Scripts/Shared/HolidayEvents

@ClientData
enum class HolidayEvent {
	None,
	Halloween,
	Christmas,
	Valentines,
	Birthday,
	Summer,
}