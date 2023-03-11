package com.nixiedroid.server.server;

import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.settings.ServerSettingsStub;

public class ServerStarter {
    public static void start (){
        ConfigStub stub = new ConfigStub(new SecretConfig());
        ServerSettingsStub settingsStub = new ServerSettingsStub(new AndroidSettings());
        Program.setConfig(stub,settingsStub);
        Program.start();
    }
    public static void stop(){
        Program.stop();
    }
}
