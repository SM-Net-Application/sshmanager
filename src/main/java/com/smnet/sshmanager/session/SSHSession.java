package com.smnet.sshmanager.session;

import com.jcraft.jsch.*;
import com.smnet.sshmanager.channel.SSHChannel;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class SSHSession {

    private Properties properties;

    private String host;

    private String username;
    private String password;
    private int port;
    private boolean connectionCanBeEstablished;

    private Session session;

    public SSHSession(Path pathProperties) throws Exception {

        if (pathProperties == null)
            throw new NullPointerException("Properties file is required!");

        File fileProperties = pathProperties.toFile();
        if (!fileProperties.exists())
            throw new IOException("Properties file not found!");

        this.initConnectionParameters(pathProperties);
    }

    private void initConnectionParameters(Path pathProperties) throws Exception {

        this.properties = new Properties();
        this.properties.load(new FileInputStream(pathProperties.toFile()));

        String host = this.properties.getProperty("host");
        String username = this.properties.getProperty("username");
        String password = this.properties.getProperty("password");
        String port = this.properties.getProperty("port");

        this.host = (host != null) ? host : "";
        this.username = (username != null) ? username : "";
        this.password = (password != null) ? password : "";

        String stringPort = (port != null) ? port : "";
        if (!stringPort.isEmpty())
            try {
                this.port = Integer.valueOf(stringPort);
            } catch (Exception e) {
                this.port = -1;
            }
        else
            this.port = -1;

        this.connectionCanBeEstablished = this.validateConnectionParameters();
        if (!this.connectionCanBeEstablished)
            throw new Exception("Connection cannot be estabilished! Missing connection parameters!");
    }

    private boolean validateConnectionParameters() {

        if (this.host.isEmpty())
            return false;
        if (this.username.isEmpty())
            return false;
        if (this.password.isEmpty())
            return false;
        if (this.port == -1)
            return false;

        return true;
    }

    public void connect() throws Exception {

        if (this.session != null)
            throw new Exception("Connection already established");

        this.session = new JSch().getSession(username, host, port);
        this.session.setPassword(password);
        this.session.setConfig("StrictHostKeyChecking", "no");
        this.session.connect();
    }

    public void disconnect() {

        if (this.session != null)
            this.session.disconnect();
    }

    public boolean isConnectionCanBeEstablished() {
        return connectionCanBeEstablished;
    }

    public void setConnectionCanBeEstablished(boolean connectionCanBeEstablished) {
        this.connectionCanBeEstablished = connectionCanBeEstablished;
    }

    public Properties getProperties() {
        return properties;
    }

    public String execute(String command) throws Exception {

        if (command == null)
            throw new Exception("A null command cannot be executed!");

        command = command.trim();

        if (command.isEmpty())
            throw new Exception("An empty command cannot be executed!");

        String output = "";
        Exception exception = null;
        ChannelExec channel = null;
        try {

            channel = (ChannelExec) this.session.openChannel(SSHChannel.EXEC.toString());
            channel.setCommand(command);

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected())
                Thread.sleep(100);

            output = new String(responseStream.toByteArray());

        } catch (JSchException | InterruptedException e) {
            exception = e;
        } finally {
            if (channel != null)
                channel.disconnect();
        }

        if (exception != null)
            throw exception;

        return output;
    }

    public String readTextFile(String fileAbsolutePath) throws Exception {

        if (fileAbsolutePath == null)
            throw new Exception("A null file absolute path cannot be read!");

        fileAbsolutePath = fileAbsolutePath.trim();

        if (fileAbsolutePath.isEmpty())
            throw new Exception("An empty file absolute path cannot be read!");

        String output = "";
        Exception exception = null;
        ChannelSftp channel = null;

        try {

            channel = (ChannelSftp) this.session.openChannel(SSHChannel.SFTP.toString());
            channel.connect();

            InputStream inputStream = channel.get(fileAbsolutePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            try {

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(stringBuilder.length() == 0
                            ? line
                            : "\n" + line);

                output = stringBuilder.toString();

            } catch (IOException e) {
                exception = e;
            } finally {
                if (inputStream != null)
                    inputStream.close();
            }

        } catch (JSchException | SftpException | IOException e) {
            exception = e;
        } finally {
            if (channel != null)
                channel.disconnect();
        }

        if (exception != null)
            throw exception;

        return output;
    }
}
