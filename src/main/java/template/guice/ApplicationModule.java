package template.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.aeonbits.owner.ConfigFactory;
import template.web.ratpack.handler.api.ApplicationConfig;

import javax.inject.Singleton;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {}

    @Provides
    @Singleton
    private ApplicationConfig provideApplicationConfig() {
        return ConfigFactory.create(ApplicationConfig.class);
    }
}
