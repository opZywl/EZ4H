# Project discontinued.

# EZ4H [![releases](https://img.shields.io/github/v/release/opZywl/EZ4H?display_name=tag&color=%231ab1ad)](https://github.com/opZywl/EZ4H/releases) [![players](https://img.shields.io/bstats/players/10109)](https://bstats.org/plugin/bukkit/EZ4H/10109) [![servers](https://img.shields.io/bstats/servers/10109)](https://bstats.org/plugin/bukkit/EZ4H/10109) [![license](https://img.shields.io/github/license/opZywl/EZ4H)](https://github.com/opZywl/EZ4H/blob/main/LICENSE)

EZ4H is a bridge/proxy between Minecraft: JE and Minecraft: BE, which allows JE-clients (1.12.2) to connect to BE-servers (1.16.210) .

## 🎉Features
- [x] Login
- [x] Chat
- [x] Command
- [X] Xbox Auth
- [X] Chunks
- [X] Move
- [X] Players
- [X] Entitys
- [X] Block Entitys
- [X] Item Entitys
- [X] Entity Metadata
- [X] Entity Interact
- [X] Block Interact
- [X] Titles and other Messages
- [X] Inventory Action
- [ ] UIes
- [ ] Particles
- [x] Form UI
- [X] Level Events
- [ ] Sounds

## Credits
It would basically be impossible to build EZ4H without these open-source projects, wheather its just looking how thing works inorder to reverse translate them, looking at their code to see how thing work or copying a little bit of their code. We apperiate all these projects. 
- [MCProtocolLib](https://github.com/Steveice10/MCProtocolLib)
- [Bedrock-Protocol](https://github.com/CloudburstMC/Protocol)
- [TunnelMC](https://github.com/THEREALWWEFAN231/TunnelMC)
- [Nukkit](https://github.com/CloudburstMC/Nukkit/)

# How to use?
As with most servers written in Java, you need a startup `.BAT` to start it.  
~~~  powershell
@echo
java -jar ez4h.jar
pause
~~~
Double click to run it. 
When there is a `Done!` It was started successfully. 

After this, you will see some files generated under the EZ4H root directory. Let's open the `config.json` .

~~~json
{
  "je_host": "127.0.0.1",
  "je_port": 25565,
  "be_host": "127.0.0.1",
  "be_port": 19132,
  "player-list": "A §bEZ§a4§bH§f§r Proxyed Server!\nhttps://github.com/opZywl/EZ4H",
  "advanced": {
    "debug": 0,
    "save-authdata": false,
    "xbox-auth": false
  }
}
~~~
I think you already know how to configure this, but I'm going to go through it briefly.

**`je_host` & `je_port`**  are the local IP and port used by JE players to connect to the server, which can be configured according to needs. 

**`be_host` & `be_port`** are the IP and port of the desired BE server to connect to. EZ4H supports proxy external servers. You can fill in either the LAN IP, i.e. the local BE server address, or the IP of the online server to proxy the BE server you want to play on through EZ4H.

**`player-list`**  is the text content displayed when JE players press the tab key.

**`xbox-auth`** indicates if xbox authentication is enabled. Turning it on prevents JE players from logging in offline, but introduces some restrictions; if set to false, you need to make sure the xbox authentication of the BE server you want to connect to is also off.
