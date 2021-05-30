package com.example.notify;

/**
 * Created by congnguyen on 5/29/21.
 */
public final class StateManager {
    public final NotificationData notificationData;
    public static final StateManager INSTANCE = new StateManager();

    private StateManager() {
        this.notificationData = new NotificationData();
    }

    public static StateManager getInstance() {
        return INSTANCE;
    }

    public static class NotificationData {
        public String title;
        public String content;
    }
}
