
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._

class thrashBasicDigitalOcean extends Simulation {

	val httpConf = httpConfig
			.baseURL("http://146.185.134.234/")
			.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.acceptEncodingHeader("gzip, deflate")
			.acceptLanguageHeader("en-US,en;q=0.5")
			.connection("keep-alive")
			.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0")


	val headers_2 = Map(
			"Accept" -> """image/png,image/*;q=0.8,*/*;q=0.5"""
	)


	val scn = scenario("Request home page")
		.exec(http("request_1")
					.get("/")
			)
		.pause(368 milliseconds)
		.exec(http("request_2")
					.get("/favicon.ico")
					.headers(headers_2)
					.check(status.is(404))
			)
		.pause(210 milliseconds)
		.exec(http("request_3")
					.get("/favicon.ico")
					.check(status.is(404))
			)

	setUp(scn.users(500).protocolConfig(httpConf))
}
