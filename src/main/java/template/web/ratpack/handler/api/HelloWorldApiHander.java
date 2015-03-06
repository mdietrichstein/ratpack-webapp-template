package template.web.ratpack.handler.api;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class HelloWorldApiHander implements Handler {

    @Override
    public void handle(Context context) throws Exception {
        context.render("Hello World");
    }
}
