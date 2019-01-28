package model;

public class Notification {
    String notificationMessage;
    String userId;

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Notification(String notificationMessage, String userId) {
        this.notificationMessage = notificationMessage;
        this.userId = userId;
    }
}
