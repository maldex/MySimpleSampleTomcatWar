copy'paste instruction for [dockers own install documentation](https://docs.docker.com/install/#supported-platforms)

# Fedora
```bash
sudo yum config-manager --add-repo https://download.docker.com/linux/fedora/docker-ce.repo
sudo yum install -y docker-ce

# allow your mortal user to use docker
sudo usermod -a -G docker user

# local engine: enable and start
sudo systemctl enable docker; sudo systemctl start docker
```

# Debian
```bash
sudo apt-get install \
     apt-transport-https \
     ca-certificates \
     curl \
     gnupg2 \
     software-properties-common

curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -

sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"

sudo apt-get update

sudo apt-get install docker-ce

# local engine: enable and start
sudo systemctl enable docker; sudo systemctl start docker
```

# other notes
## http/https proxy setup
```bash
mkdir ~/.docker
echo '{
 "proxies":
 {
   "default":
   {
     "httpProxy": "http://127.0.0.2:8080"   }
 }
}' > ~/.docker/config.json

sudo mkdir -p /etc/systemd/system/docker.service.d
echo "[Service]
Environment=\"HTTP_PROXY=${http_proxy}\"" > /etc/systemd/system/docker.service.d/http-proxy.conf
echo "[Service]
Environment=\"HTTPS_PROXY=${https_proxy}\"" > /etc/systemd/system/docker.service.d/https-proxy.conf
sudo systemctl daemon-reload
sudo systemctl restart docker
systemctl show --property=Environment docker
```