package template.web.ratpack.handler.api;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources( {
        "classpath:app.conf",
        "classpath:default.properties"
})
public interface ApplicationConfig extends Config {

    @Key("application.dev_mode")
    @DefaultValue("true")
    boolean isDevMode();

    @Key("webapp.base_dir")
    @DefaultValue("src/main/resources/")
    String webappBaseDir();

    @Key("webapp.assets_location")
    @DefaultValue("webapp")
    String webappAssetsLocation();

    @Key("webapp.port")
    @DefaultValue("5050")
    int webappPort();

    @Key("webapp.index_files")
    @DefaultValue("index.html")
    String[] webappIndexFiles();

    @Key("api.port")
    @DefaultValue("5050")
    int apiPort();
}
