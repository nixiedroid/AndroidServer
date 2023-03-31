package com.nixiedroid.server.server;

import com.nixiedroid.logger.LoggerStub;
import com.nixiedroid.settings.LogLevel;
import com.nixiedroid.settings.ServerSettings;
import com.nixiedroid.sowftwareId.GeneratorStub;
import com.nixiedroid.sowftwareId.fallback.Generator;

public class AndroidSettings implements ServerSettings {
    private static final LoggerStub LOGGER = new LoggerStub(new AndroidLogger());
    private static final GeneratorStub GENERATOR = new GeneratorStub(new Generator());
    private LogLevel level = LogLevel.DEBUG;
    private int port = 1688;
    private int pingTime = 0x78;
    private int delayTime = 0x2760;
    private int clients = 0x32;
    private String hardwareID = "BAADD00DBEEFCAFE";
    private int lang = 3079; //GERMANY - AUSTRIA
    private String softwareID = null;

    @Override
    public GeneratorStub getGenerator() {
        return GENERATOR;
    }

    @Override
    public LoggerStub logger() {
        return LOGGER;
    }

    @Override
    public LogLevel getLevel() {
        return level;
    }

    @Override
    public int getServerPort() {
        return port;
    }

    @Override
    public int getPingTime() {
        return pingTime;
    }

    @Override
    public int getDelayTime() {
        return delayTime;
    }

    @Override
    public int getMinClientCount() {
        return clients;
    }

    @Override
    public int getLang() {
        return lang;
    }

    @Override
    public String getHardwareID() {
        return hardwareID;
    }

    @Override
    public String getSoftwareID() {
        return softwareID;
    }

    @Override
    public void setLevel(LogLevel logLevel) {
        level = logLevel;
    }

    @Override
    public void setPort(int i) {

    }

    @Override
    public void setPingTime(int i) {

    }

    @Override
    public void setDelayTime(int i) {

    }

    @Override
    public void setClientCount(int i) {

    }

    @Override
    public void setHardwareID(String s) {

    }

    @Override
    public void setLangCode(int i) {

    }

    @Override
    public void setSoftwareId(String s) {

    }
}
