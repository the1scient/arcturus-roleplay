package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class WhisperHotelCommand extends Command {
    public WhisperHotelCommand() {
        super("cmd_staffalert", Emulator.getTexts().getValue("commands.keys.cmd_whisperhotelalert").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length > 1) {
            StringBuilder message = new StringBuilder();
            for (int i = 1; i < params.length; i++) {
                message.append(params[i]).append(" ");
            }

            String userSend = gameClient.getHabbo().getHabboInfo().getUsername();
            Emulator.getGameEnvironment().getRoomManager().getActiveRooms().forEach(r -> r.getHabbos().forEach(h -> h.whisper("[ALERTA GLOBAL] " + userSend + ": " + message, RoomChatMessageBubbles.FRANK)));
        } else {
            gameClient.getHabbo().whisper("Digite algo para transmitir como alerta!", RoomChatMessageBubbles.ALERT);
        }

        return true;
    }
}
