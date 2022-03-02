package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUnitOnRollerComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserTalkComposer;

public class WarpToMeCommand extends Command {
    public WarpToMeCommand() {
        super("cmd_tptome", Emulator.getTexts().getValue("fun.cmd_tptome.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length == 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(strings[1]);
            if (habbo == null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("fun.error.not_found").replace("%user%", strings[1]), RoomChatMessageBubbles.ALERT);
                return true;
            } else if (habbo == gameClient.getHabbo()) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("fun.error.tp_self"), RoomChatMessageBubbles.ALERT);
                return true;
            } else {
                Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
                if (room != null) {


                    RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(gameClient.getHabbo().getRoomUnit().getX(), gameClient.getHabbo().getRoomUnit().getY());

                    if (tile != null && tile.isWalkable()) {
                        room.giveEffect(habbo, 0, -1);
                        habbo.getHabboInfo().getCurrentRoom().sendComposer(new RoomUnitOnRollerComposer(habbo.getRoomUnit(), tile, habbo.getHabboInfo().getCurrentRoom()).compose());
                        gameClient.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomUserTalkComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.success.cmd_tptome.tptome").replace("%user%", habbo.getHabboInfo().getUsername()).replace("%gender_name%", gameClient.getHabbo().getHabboInfo().getGender().equals(HabboGender.M) ? Emulator.getTexts().getValue("gender.him") : Emulator.getTexts().getValue("gender.her")), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.STAFF))).compose());
                    }
                }
                return true;
            }
        }
        return true;
    }
}
