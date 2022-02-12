package com.smnet.sshmanager.command;

public class SSHCommandBuilder {

    private StringBuilder command;

    public SSHCommandBuilder() {
        this.command = new StringBuilder();
    }

    public static SSHCommandBuilder newInstance() {
        return new SSHCommandBuilder();
    }

    public SSHCommandBuilder withCommand(String command) {

        if (command == null)
            return this;

        command = command.trim();

        if (command.isEmpty())
            return this;

        this.command.append((this.command.length() == 0)
                ? command
                : ";".concat(command));

        return this;
    }

    public String build() throws Exception {

        String command = this.command.toString();

        if (command.isEmpty())
            throw new Exception("An empty SSH command cannot be executed!");

        return command;
    }
}
