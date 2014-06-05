package funcional;


import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F;
import play.libs.WS;
import play.mvc.Result;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import static play.test.Helpers.testServer;

public class ApplicationControllerTest {

    @Test
    public void callIndex() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        
        //assertThat(contentAsString(result)).contains("hello world");
        // TODO 
    }

    @Test
    public void testIndexWithFakeApp() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                // // TODO
            }
        });
    }

    @Test
    public void testIndexWithTestServerRunnable() {
        running(testServer(3333), new Runnable() {
            @Override
            public void run() {
                assertThat(
                		WS.url("http://localhost:3333").get().get(1000L).getStatus()
                ).isEqualTo(OK);
            }
        });
    }

    @Test
    public void runInBrowser() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback() {
			@Override
			public void invoke(Object arg0) throws Throwable {
				TestBrowser browser = (TestBrowser) arg0;
				browser.goTo("http://localhost:3333");
                //assertThat(browser.$("body").getTexts().get(0)).isEqualTo("hello world");
				// TODO
			}
        });
    }
}