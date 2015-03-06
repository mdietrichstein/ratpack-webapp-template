package template;

import com.google.common.base.Throwables;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.jackson.JacksonModule;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import ratpack.server.ServerEnvironment;
import ratpack.server.internal.DefaultServerConfigBuilder;
import template.guice.ApplicationModule;
import template.web.ratpack.handler.api.ApplicationConfig;
import template.web.ratpack.handler.api.HelloWorldApiHander;
import template.web.ratpack.handler.api.guice.ApiHandlerModule;

import javax.inject.Inject;
import java.nio.file.Paths;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Injector injector = com.google.inject.Guice.createInjector(new ApplicationModule());
        Application app = injector.getInstance(Application.class);
        app.run();
    }

    // impl

    private final ApplicationConfig applicationConfig;

    @Inject
    public Application(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public void run() {
        startWebServer();
        startApiServer();
    }

    private void startWebServer() {
        ServerConfig.Builder serverConfig =
            DefaultServerConfigBuilder
                .baseDir(ServerEnvironment.env(), Paths.get(applicationConfig.webappBaseDir()))
                .development(applicationConfig.isDevMode())
                .port(applicationConfig.webappPort());

        try {
            RatpackServer.of(definition -> definition
                .serverConfig(serverConfig)
                .handlers(chain -> chain
                    .assets(applicationConfig.webappAssetsLocation(), applicationConfig.webappIndexFiles())
                )
            ).start();
        } catch (Exception e) {
            log.error("unable to start webserver", e);
            throw Throwables.propagate(e);
        }
    }

    private void startApiServer() {
        ServerConfig.Builder serverConfig =
            DefaultServerConfigBuilder
                .noBaseDir(ServerEnvironment.env())
                .development(applicationConfig.isDevMode())
                .port(applicationConfig.apiPort());

        try {
            RatpackServer.of(definition -> definition
                .serverConfig(serverConfig)
                .registry(Guice.registry(b -> {
                    b.add(new JacksonModule());
                    b.add(new ApiHandlerModule());
                }))
                .handlers(chain -> chain
                    .prefix("api/v1", api ->
                        api.get("hello", HelloWorldApiHander.class)
                    )
                )
            ).start();
        } catch (Exception e) {
            log.error("unable to start api server", e);
            throw Throwables.propagate(e);
        }
    }
}