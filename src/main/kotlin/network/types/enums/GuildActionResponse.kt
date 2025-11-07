package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class GuildActionResponse {
	guild_already_exists,
	guild_invalid_characters,
	guild_name_too_long,
	guild_name_too_short,
	guild_creation_requirements_not_met,
	invited_player_is_already_in_a_guild,
	other_player_is_offline,
	guild_is_full,
	application_already_active,
	application_has_expired,
	invitation_has_expired,
	guild_not_found,
	guild_application_sent,
	guild_invitation_sent,
	guild_not_recruiting,
	guild_gamemode_mismatch,
	guild_not_high_enough_rank,
}