package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserTalkComposer;

public class SuperPullCommand extends Command {
    public SuperPullCommand() {
        super("cmd_superpull", Emulator.getTexts().getValue("commands.keys.cmd_superpull").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length == 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);

            if (habbo == null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.not_found").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                return true;
            } else if (habbo == gameClient.getHabbo()) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.pull_self"), RoomChatMessageBubbles.ALERT);
                return true;
            } else {
                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTileInFront(gameClient.getHabbo().getRoomUnit().getCurrentLocation(), gameClient.getHabbo().getRoomUnit().getBodyRotation().getValue());

                if (tile != null && tile.isWalkable()) {
                    if (tile == gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getDoorTile()) {
                        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.invalid").replace("%username%", params[1]), RoomChatMessageBubbles.ALERT);
                        return true;
                    }
                    habbo.getRoomUnit().setGoalLocation(tile);
                    gameClient.getHabbo().talk("* Usa seus poderes de Staff e puxa " + params[1] + " para sua frente *", RoomChatMessageBubbles.STAFF);
                }
            }
            return true;
        }

        return true;
    }
}
