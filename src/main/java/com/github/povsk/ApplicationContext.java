package com.github.povsk;

import com.github.povsk.core.TenScoreService;
import com.github.povsk.core.TextHandlerService;
import com.github.povsk.domain.DaoManager;
import com.github.povsk.vk.VKCore;
import com.github.povsk.vk.VKManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.util.Objects;

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

    private void configureProperties() {
        System.getenv().forEach((k, v) -> System.out.println("key:" + k + " value: " + v));
        Integer groupId = gerGroupId();
        String token = gerToken();
        String errorMessage = "";
        if (groupId == null) {
            errorMessage += "GroupId bot must not be null;\n";
        }
        if (isEmpty(token)) {
            errorMessage += "Token bot must not be null;\n";
        }
        if (!isEmpty(errorMessage)) {
            errorMessage += "Use environment variable's \"GROUP_ID\", \"TOKEN\" for it";
            throw new AssertionError(errorMessage);
        }
        properties = new AppProperties();
        properties.setGroupId(groupId);
        properties.setToken(token);
    }

    private Integer gerGroupId() {
        String groupIdStr = System.getenv("GROUP_ID");
        try {
            return Integer.parseInt(groupIdStr);
        } catch (NumberFormatException ignore) {
            return null;
        }
    }

    private String gerToken() {
        return System.getenv("TOKEN");
    }

    private boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
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
