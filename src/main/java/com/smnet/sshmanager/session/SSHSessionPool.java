package com.smnet.sshmanager.session;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class SSHSessionPool {

    private HashMap<String, SSHSessionCustom> sessionPool;

    public SSHSessionPool() {
        this.sessionPool = new HashMap<>();
    }

    public void createSSHSession(String sessionName, SSHSessionCustom session) {
        this.sessionPool.put(sessionName, session);
    }

    public void createSSHSession(String sessionName, Path pathProperties, String... customProperties) throws Exception {
        SSHSessionCustom session = new SSHSessionCustom(pathProperties, customProperties);
        this.createSSHSession(sessionName, session);
    }

    public void createSSHSession(String sessionName, String stringPathProperties, String... customProperties) throws Exception {
        Path pathProperties = Paths.get(stringPathProperties);
        this.createSSHSession(sessionName, pathProperties, customProperties);
    }

    public void connect() throws Exception {

        if (this.sessionPool == null)
            return;

        for (String key : this.sessionPool.keySet()) {
            this.sessionPool.get(key).connect();
        }
    }

    public void disconnect() {
        if (this.sessionPool == null)
            return;
        this.sessionPool.keySet().forEach(sessionName -> this.sessionPool.get(sessionName).disconnect());
    }

    public boolean isEmpty() {
        return this.sessionPool.isEmpty();
    }

    public SSHSessionCustom get(String sessionName) {
        return this.sessionPool.get(sessionName);
    }
}
