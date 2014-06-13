package funcional;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;
import play.test.Helpers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

public class IndexRouteTest {

	@Before
	public void setUp() throws Exception {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void rootRoute() {
		Result result = Helpers.route(new FakeRequest(GET, "/"));
		// testa se a resultado da requisição à url "/" não é nula
		assertThat(result).isNotNull();
	}

	@Test
	public void badRoute() {
		Result result = Helpers.route(new FakeRequest(GET, "/bad"));
		// com não existe uma route "/bad" o resultado deve ser nulo
		assertThat(result).isNull();
	}
}
