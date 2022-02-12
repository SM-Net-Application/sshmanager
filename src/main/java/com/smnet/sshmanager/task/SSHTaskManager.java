package com.smnet.sshmanager.task;

import com.smnet.sshmanager.session.SSHSessionPool;

public class SSHTaskManager {

    private SSHSessionPool sessionPool;

    public SSHTaskManager(SSHSessionPool sessionPool) {
        this.sessionPool = sessionPool;
    }

    public void run(SSHTask task) throws Exception {

        if (this.sessionPool == null)
            throw new Exception("Session pool is null!");

        if (this.sessionPool.isEmpty())
            throw new Exception("Session pool is empty!");

        Exception exception = null;

        try {
            this.sessionPool.connect();
            task.run(this.sessionPool);
        } catch (Exception e) {
            exception = e;
        } finally {
            this.sessionPool.disconnect();
        }

        if (exception != null)
            throw exception;
    }
}
