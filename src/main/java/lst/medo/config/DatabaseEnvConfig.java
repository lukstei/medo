package lst.medo.config;

public class DatabaseEnvConfig {
    String mUrl;
    String mUserName;
    String mPassword;

    public DatabaseEnvConfig() {
        mUrl = System.getProperty("medo.db.url");
        mUserName = System.getProperty("medo.db.username");
        mPassword = System.getProperty("medo.db.password");
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }
}
