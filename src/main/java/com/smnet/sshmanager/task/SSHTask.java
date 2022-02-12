package com.smnet.sshmanager.task;

import com.smnet.sshmanager.session.SSHSessionPool;

public interface SSHTask {

    public void run(SSHSessionPool sessionPool) throws Exception;
}
