# Foolproof

Below is a list of everything Foolproof changes, including a reason why users were confused before.

General Minecraft Changes:
- Adds anti-AI message to crash reports: whenever users paste an error message into ChatGPT, it will instruct the AI to direct them to Discord for support (AI will often blame Connector, leading to users removing it and bricking the modpack)
- Replaces "Available Resourcepacks" with "Optional Resourcepacks" (users thought they were off by mistake and needed to enable them)
- Replaces error message when the user tries to use commands in survival (by default, it just says "Unknown Command", even if the command exists)
- Replace "Connection timed out: getsockopt" with a more descriptive error about how to fix it (happens often when connecting to a server behind a firewall)
- Prints a message in chat 5 seconds after the server has started, saying it's online and ready to join (many users wouldn't know if it was fully done)
- Makes the "Can't keep up!" message not show for the first two minutes (servers usually are under heavy load during startup, and users will think they're lagging when it's normal)

Mod Changes:
- When a grave mod is installed (YIGD, Universal Graves, Corpse) and the user dies in the void, it will print a message in chat saying their grave still exists (many users do not go back for it!)
- When BetterEnd or BetterNether are installed, it will add a prompt before the user is able to cycle the "BetterX" world type in the settings (this can break worlds very easily)

FTB Quests Changes:
- Reminds the user that the Quest Book exists via ImmersiveMessages when they first join a world (many new players do not know it exists!)
- Removes auto-pin button (very confusing to users, serves no real purpose)
- Makes the sidebar default to being open (many users remain stuck on the starting screen)