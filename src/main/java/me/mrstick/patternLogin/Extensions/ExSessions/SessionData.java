package me.mrstick.patternLogin.Extensions.ExSessions;

public class SessionData {
    private final String data;
    private int cooldown;


    public SessionData(String data, int cooldown) {
        this.data = data;
        this.cooldown = cooldown;
    }

    // Getters & Setters
    public String GetIp() {
        return data;
    }

    public int GetCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    // Decrements cooldown by 1 second
    public void decrementCooldown() {
        this.cooldown--;
    }

    // Checks if session expired
    public boolean isExpired() {
        return cooldown <= 0;
    }
}