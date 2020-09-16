package com.github.povsk;

import com.github.povsk.core.TenScoreService;
import com.github.povsk.core.TextHandlerService;
import com.github.povsk.domain.DaoManager;
import com.github.povsk.vk.VKCore;
import com.github.povsk.vk.VKManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

public class ApplicationContext implements AutoCloseable {

    private AppProperties properties;
    private ConnectionSource connectionSource;
    private DaoManager daoManager;
    private VKManager vkManager;
    private TextHandlerService textHandlerService;
    private TenScoreService tenScoreService;
    private VKCore vkCore;

    public DaoManager getDaoManager() {
        return daoManager;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public AppProperties getProperties() {
        return properties;
    }

    public VKManager getVkManager() {
        return vkManager;
    }

    public TextHandlerService getTextHandlerService() {
        return textHandlerService;
    }

    public TenScoreService getTenScoreService() {
        return tenScoreService;
    }

    public VKCore getVkCore() {
        return vkCore;
    }

    ApplicationContext() throws Exception {
    }

    private void configureProperties() throws IOException {
        Yaml yaml = new Yaml(new Constructor(AppProperties.class));
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.yml")) {
            properties = yaml.load(inputStream);
        }
    }

    private void configureDaoManager() throws Exception {
        connectionSource = new JdbcPooledConnectionSource("jdbc:h2:mem:bot;DB_CLOSE_DELAY=-1");
        daoManager = DaoManager.configure(connectionSource);
    }

    private static ApplicationContext instance;

    public static ApplicationContext getInstance() {
        return instance;
    }

    private static void setBeanContainer(ApplicationContext instance) {
        if (instance != null) {
            ApplicationContext.instance = instance;
        }
    }

    void configure() throws Exception {
        configureProperties();
        configureDaoManager();
        textHandlerService = new TextHandlerService();
        tenScoreService = new TenScoreService();
        vkCore = new VKCore();
        vkManager = new VKManager();
    }

    static ApplicationContext instance() throws Exception {
        ApplicationContext context = new ApplicationContext();
        ApplicationContext.setBeanContainer(context);
        return context;
    }

    @Override
    public void close() throws Exception {
        connectionSource.closeQuietly();
    }
}
