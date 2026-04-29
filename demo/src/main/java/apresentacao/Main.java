package apresentacao; 
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;

public class Main {
    public static void main(String[] args) {

        var app = Javalin.create(/*config*/)
            .get("/", ctx -> ctx.result("Hello World"))
            .start(7070);
    }
}