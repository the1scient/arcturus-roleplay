package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;


public class StaffDutyCommand extends Command {
    public StaffDutyCommand() {
        super("cmd_staffduty", Emulator.getTexts().getValue("commands.keys.cmd_staffduty").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        gameClient.getHabbo().shout("* Entra em plantão Staff *", RoomChatMessageBubbles.STAFF);



        gameClient.getHabbo().getHabboStats().chatColor = RoomChatMessageBubbles.STAFF;
        gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(gameClient.getHabbo(), 102, 100000000);

        return true;
    }
}