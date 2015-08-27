package template.web.ratpack.handler.api.guice;

import com.google.inject.AbstractModule;
import template.web.ratpack.handler.api.HelloWorldApiHandler;

public class ApiHandlerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloWorldApiHandler.class);
    }
}
