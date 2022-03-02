package com.eu.habbo.messages.incoming.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserDataComposer;
import com.eu.habbo.plugin.events.users.UserSavedMottoEvent;

public class SaveMottoEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        String motto = this.packet.readString();
        UserSavedMottoEvent event = new UserSavedMottoEvent(this.client.getHabbo(), this.client.getHabbo().getHabboInfo().getMotto(), motto);
        Emulator.getPluginManager().fireEvent(event);
        motto = event.newMotto;

        if(motto.length() <= 38 && this.client.getHabbo().getHabboInfo().getRank().getId() >= 6) {
            this.client.getHabbo().getHabboInfo().setMotto(motto);
            this.client.getHabbo().getHabboInfo().run();
        }
        else {
            this.client.getHabbo().whisper("Você não pode fazer isso :(", RoomChatMessageBubbles.ALERT);
        }

        if (this.client.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            this.client.getHabbo().getHabboInfo().getCurrentRoom().sendComposer(new RoomUserDataComposer(this.client.getHabbo()).compose());
        } else {
            this.client.sendResponse(new RoomUserDataComposer(this.client.getHabbo()));
        }

        AchievementManager.progressAchievement(this.client.getHabbo(), Emulator.getGameEnvironment().getAchievementManager().getAchievement("Motto"));
    }
}
