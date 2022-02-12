package com.smnet.sshmanager.session;

import java.nio.file.Path;
import java.util.HashMap;

public class SSHSessionCustom extends SSHSession {

    private HashMap<String, String> customProperties;

    public SSHSessionCustom(Path pathProperties, String... customProperties) throws Exception {
        super(pathProperties);

        this.customProperties = new HashMap<>();

        this.initIndividualParameters(customProperties);
    }

    public void initIndividualParameters(String... customProperties) throws Exception {

        for (String customPropertyKey : customProperties) {

            String customPropertyValue = super.getProperties().getProperty(customPropertyKey);

            if (customPropertyValue == null)
                throw new Exception(String.format("SSH Custom Session not valid! Missing property: %s!", customPropertyKey));

            if (customPropertyValue.isEmpty())
                throw new Exception(String.format("SSH Custom Session not valid! Empty property: %s!", customPropertyKey));

            this.customProperties.put(customPropertyKey, customPropertyValue);
        }
    }

    public String getCustomProperties(String propertyName) throws Exception {

        if (propertyName == null)
            throw new Exception("Get null property name not allowed!");

        if (propertyName.isEmpty())
            throw new Exception("Get empty property name not allowed!");

        if (!this.customProperties.containsKey(propertyName))
            throw new Exception(String.format("Get property %s failed!", propertyName));

        return this.customProperties.get(propertyName);
    }
}
