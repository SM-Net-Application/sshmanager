package com.smnet.sshmanager.channel;

public enum SSHChannel {

    EXEC("exec"),
    SFTP("sftp");

    private String channel;

    SSHChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return channel;
    }
}
