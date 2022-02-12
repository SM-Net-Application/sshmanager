package com.smnet.sshmanager.command.unix.lsdashld;

public class SSHCommandLSDashLDRow {

    private boolean directory;

    private String fullPath;

    private String name;

    public SSHCommandLSDashLDRow(boolean directory, String fullPath, char separator) {

        this.directory = directory;
        this.fullPath = fullPath;
        this.name = fullPath.substring(fullPath.lastIndexOf(separator) + 1);
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
