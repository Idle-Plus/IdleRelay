# Idle Relay

Idle Relay is a WebSocket relay server for Idle Client, the browser-based Idle Clans client. It serves as a bridge
between the browser and the Idle Clans game server, enabling browser-based clients to connect to the game. The Relay is 
written in Kotlin, using Ktor, Jackson and Java 21.

## Overview

The relay server is necessary because web browsers cannot establish direct TCP connections to game servers. 
Instead, the relay:

1. Accepts WebSocket connections from web clients.
2. Establishes TCP connections to the Idle Clans game servers.
3. Forwards packets between clients and servers.
4. Handles packet serialization/deserialization.
5. Re-hydrates network packets (adding default values that are stripped by the server).

## Project Structure

- `src/main/kotlin/`:  Kotlin source code.
  - `Main.kt`: Application entry point.
  - `ClientRelay.kt`: Core relay functionality.
  - `network/`: Network-related code.
    - `internal/`: Code for connecting to the game server.
    - `web/`: Code for handling WebSocket connections.
    - `types/`: Packet and game type definitions.
  - `tools/`: Helper tools, mainly used to generate TypeScript code from the type definitions.

## Configuration

The relay server is configured using a `config.properties` file with the following settings:

```properties
# General settings

# The port the relay should listen to.
general.port=8080

# If WSS should be enabled or not.
general.secure=false
# Only required if WSS is enabled.
# The name of the keystore file.
general.key-store-file=keystore.p12
# Only required if WSS is enabled.
# The name of the keystore.
general.key-store-name=RelayStore
# Only required if WSS is enabled.
# The password to the keystore.
general.key-store-password=change_me

# The address of the game server.
network.game-server.address=123.123.123.123
# The port used by the game server.
network.game-server.port=10515

# The address of the chat server.
network.chat-server.address=123.123.123.123
# The port used by the chat server.
network.chat-server.port=10516
```

## SSL Configuration

For secure WebSocket connections (WSS), you need to:

1. Set `general.secure=true` in the config.
2. Provide a valid PKCS12 keystore file.
3. Configure the keystore settings in the config file.

## License

This project is licensed under the GNU General Public License v3.0. See the [COPYING](https://github.com/Idle-Plus/IdleRelay/blob/master/COPYING)  file for details.
