package template;

import com.google.common.base.Throwables;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import ratpack.server.ServerConfigBuilder;
import template.guice.ApplicationModule;
import template.web.ratpack.handler.api.ApplicationConfig;
import template.web.ratpack.handler.api.HelloWorldApiHandler;
import template.web.ratpack.handler.api.guice.ApiHandlerModule;

import javax.inject.Inject;
import java.nio.file.Path;
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
        Path baseAppDir = Paths.get(".", applicationConfig.webappBaseDir()).toAbsolutePath().normalize();

        ServerConfigBuilder serverConfig =
            ServerConfig.builder()
                .baseDir(baseAppDir)
                .development(applicationConfig.isDevMode())
                .port(applicationConfig.webappPort());

        try {
            RatpackServer.of(definition -> definition
                .serverConfig(serverConfig)
                .handlers(chain -> chain
                    .files(f -> f.dir(applicationConfig.webappAssetsLocation()).indexFiles(applicationConfig.webappIndexFiles()))
                )
            ).start();
        } catch (Exception e) {
            log.error("unable to start webserver", e);
            throw Throwables.propagate(e);
        }
    }

    private void startApiServer() {
        ServerConfigBuilder serverConfig =
                ServerConfig.builder()
                .development(applicationConfig.isDevMode())
                .port(applicationConfig.apiPort());

        try {
            RatpackServer.of(definition -> definition
                .serverConfig(serverConfig)
                .registry(Guice.registry(b -> {
                    b.module(ApiHandlerModule.class);
                }))
                .handlers(chain -> chain
                    .prefix("api/v1", api ->
                        api.get("hello", HelloWorldApiHandler.class)
                    )
                )
            ).start();
        } catch (Exception e) {
            log.error("unable to start api server", e);
            throw Throwables.propagate(e);
        }
    }
}