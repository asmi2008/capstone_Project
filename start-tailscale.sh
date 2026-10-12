#!/bin/sh
set -e

if [ -z "$TS_AUTHKEY" ]; then
    echo "TS_AUTHKEY is missing"
    exit 1
fi

tailscaled --tun=userspace-networking --state=mem: &
sleep 3

tailscale up \
  --authkey="$TS_AUTHKEY" \
  --hostname=asmi-render \
  --accept-routes
