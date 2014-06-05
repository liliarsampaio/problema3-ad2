package funcional;

import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;
import play.test.Helpers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class IndexRouteTest {

	@Test
	public void rootRoute() {
		running(fakeApplication(), new Runnable() {
			@Override
			public void run() {
				Result result = Helpers.route(new FakeRequest(GET, "/"));
				// testa se a resultado da requisição à url "/" não é nula
				assertThat(result).isNotNull();
			}
		});
	}

	@Test
	public void badRoute() {
		running(fakeApplication(), new Runnable() {
			@Override
			public void run() {
				Result result = Helpers.route(new FakeRequest(GET, "/bad"));
				// com não existe uma route "/bad" o resultado deve ser nulo
				assertThat(result).isNull();
			}
		});
	}
}
