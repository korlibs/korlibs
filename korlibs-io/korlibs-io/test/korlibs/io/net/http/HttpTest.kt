package korlibs.io.net.http

import korlibs.io.async.suspendTest
import korlibs.io.net.QueryString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HttpTest {
	@Test
	fun customMethodNormalizesName() {
		val method = Http.CustomMethod("  pAtCh  ")
		assertEquals("PATCH", method.name)
		assertEquals("PATCH", method.toString())
	}

	@Test
	fun unauthorizedBasicIncludesStatusAndHeader() {
		val e = assertFailsWith<Http.HttpException> {
			Http.HttpException.unauthorizedBasic(realm = "Private", msg = "Nope")
		}
		assertEquals(401, e.statusCode)
		assertEquals("Nope", e.msg)
		assertEquals("Basic realm=\"Private\"", e.headers["WWW-Authenticate"])
	}

	@Test
	fun authParseBasicAndEmpty() {
		val parsed = Http.Auth.parse("Basic dXNlcjpwYXNz")
		assertEquals(Http.Auth(user = "user", pass = "pass", digest = ""), parsed)

		val empty = Http.Auth.parse("")
		assertEquals(Http.Auth(user = "", pass = "", digest = ""), empty)
	}

	@Test
	fun authParseUnsupportedSchemeFails() {
		assertFailsWith<Throwable> {
			Http.Auth.parse("Digest abc")
		}
	}

	@Test
	fun authValidateChecksCredentials() {
		val auth = Http.Auth("user", "pass", "")
		assertEquals(true, auth.validate("user", "pass"))
		assertEquals(false, auth.validate("user", "bad"))
	}

	@Test
	fun checkBasicSuccessAndFailures() = suspendTest {
		Http.Auth("user", "pass", "").checkBasic { user == "user" && pass == "pass" }

		val emptyUser = assertFailsWith<Http.HttpException> {
			Http.Auth("", "pass", "").checkBasic { true }
		}
		assertEquals(401, emptyUser.statusCode)
		assertEquals("Basic realm=\"Domain\"", emptyUser.headers["WWW-Authenticate"])

		val invalid = assertFailsWith<Http.HttpException> {
			Http.Auth("user", "pass", "").checkBasic { false }
		}
		assertEquals(401, invalid.statusCode)
		assertEquals("Invalid auth", invalid.msg)
	}

	@Test
	fun requestParsesUriAndQuery() {
		val request = Http.Request("/hello/world?a=1&b=2", Http.Headers())

		assertEquals("/hello/world", request.path)
		assertEquals("a=1&b=2", request.queryString)
		assertEquals(QueryString.decode("a=1&b=2"), request.getParams)
		assertEquals("/hello/world?a=1&b=2", request.absoluteURI)
	}

	@Test
	fun responseHeaderAppendsPairsInOrder() {
		val response = Http.Response()
		response.header("Content-Type", "text/plain")
		response.header("X-Test", "1")

		assertEquals(listOf("Content-Type" to "text/plain", "X-Test" to "1"), response.headers)
	}
}