
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._

class ProjectCapaplanLoadCpuSha1 extends Simulation {

        // set via JAVA_OPTS="-Dfoo=bar"
        val baseURL = System.getProperty("base_url")
        val nbUsers = Integer.getInteger("users", 1)
        val myRamp  = java.lang.Long.getLong("ramp", 0L)

	val httpConf = httpConfig
			.baseURL(baseURL)
			.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.acceptEncodingHeader("gzip, deflate")
			.acceptLanguageHeader("en-US,en;q=0.5")
			.connection("keep-alive")
			.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0")


	val headers_2 = Map(
			"Accept" -> """image/png,image/*;q=0.8,*/*;q=0.5"""
	)


	val scn = scenario("Scenario Name")
		.exec(http("request_1")
		.get("/load/cpu/sha1")
	)

	setUp(scn.users(nbUsers).ramp(myRamp).protocolConfig(httpConf))
}
