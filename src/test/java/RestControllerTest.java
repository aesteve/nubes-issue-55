import com.github.aesteve.vertx.nubes.VertxNubes;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RestControllerTest {

	private JsonObject config;
	private VertxNubes nubes;
	private HttpServer server;

	protected Vertx vertx;

	@Before
	public void setupServer(TestContext context) {
		vertx = Vertx.vertx();
		config = new JsonObject();
		config.put("controller-packages", new JsonArray().add("controllers"));
		nubes = new VertxNubes(vertx, config);
		nubes.bootstrap(context.asyncAssertSuccess(router -> {
			server = vertx.createHttpServer();
			server.requestHandler(router::accept);
			server.listen(9000, context.asyncAssertSuccess());
		}));
	}

	@After
	public void clean(TestContext context) {
		if (server != null) {
			server.close(context.asyncAssertSuccess());
		}
	}

	@Test
	public void testRestController(TestContext context) {
		Async async = context.async();
		client().getNow("/data/dashboard", response -> {
			context.assertEquals(200, response.statusCode());
			response.bodyHandler( buff -> {
				context.assertEquals("hello", received);
				async.complete();
			});
		});
	}

	private HttpClient client() {
		HttpClientOptions clientOptions = new HttpClientOptions();
		clientOptions.setDefaultHost("localhost");
		clientOptions.setDefaultPort(9000);
		return vertx.createHttpClient(clientOptions);
	}

}
