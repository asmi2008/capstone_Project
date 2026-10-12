#!/bin/sh
set -e

if [ -z "$TS_AUTHKEY" ]; then
    echo "ERROR: TS_AUTHKEY is missing"
    exit 1
fi

# Start Tailscale in userspace mode with SOCKS5 proxy
tailscaled \
  --tun=userspace-networking \
  --socks5-server=127.0.0.1:1055 \
  --state=mem: &

# Wait for Tailscale daemon
sleep 3

# Connect Render to Tailscale
tailscale up \
  --authkey="$TS_AUTHKEY" \
  --hostname=asmi-render \
  --accept-routes

echo "Tailscale started successfully"
