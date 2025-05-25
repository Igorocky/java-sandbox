package org.igye.sandbox.examplewebapp.impl;

import org.igye.sandbox.examplewebapp.AppConfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AppConfigImpl implements AppConfig {
    private static AppConfig appConfig;

    private final Context context;

    public AppConfigImpl() {
        try {
            this.context = InitialContext.doLookup("java:comp/env");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getStrProp(String name) {
        try {
            return (String) context.lookup(name);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static AppConfig getInstance() {
        if (appConfig == null) {
            synchronized (AppConfigImpl.class) {
                if (appConfig == null) {
                    appConfig = new AppConfigImpl();
                }
            }
        }
        return appConfig;
    }
}
