```
888b     d888  .d8888b.         888    d8P   .d88888b.   .d8888b.
8888b   d8888 d88P  Y88b        888   d8P   d88P" "Y88b d88P  Y88b
88888b.d88888 Y88b.             888  d8P    888     888 Y88b.
888Y88888P888  "Y888b.          888d88K     888     888  "Y888b.
888 Y888P 888     "Y88b.        8888888b    888     888     "Y88b.
888  Y8P  888       "888 888888 888  Y88b   888     888       "888
888   "   888 Y88b  d88P        888   Y88b  Y88b. .d88P Y88b  d88P
888       888  "Y8888P"         888    Y88b  "Y88888P"   "Y8888P"
```

# Introduction

MS-KOS [em ˈes keɪ əʊz] are Micro Services in Chaos

# Instructions

## Service: living-colors

Commands should be run in `living-colors` subdirectory:

`cd living-colors`

### Run service with Docker

build Docker image:

`sudo docker build -t living-colors .`

run Docker container:

`sudo docker run -P living-colors`

## Consul

### Start consul server cluster

Start first server node, expecting 3 nodes to form a cluster:

`docker run -d --name node1 -h node1 progrium/consul -server -bootstrap-expect 3`

Put first server node's IP in environment variable:

`JOIN_IP="$(docker inspect -f '{{.NetworkSettings.IPAddress}}' node1)"`

Start second and third nodes:

`docker run -d --name node2 -h node2 progrium/consul -server -join $JOIN_IP`

`docker run -d --name node3 -h node3 progrium/consul -server -join $JOIN_IP`

Start a consul agent node with port mapping:

```
sudo docker run -d -p 8300:8300 -p 8301:8301 -p 8301:8301/udp \
-p 8302:8302 -p 8302:8302/udp -p 8400:8400 -p 8500:8500 -p 8600:53/udp \
--name node4 -h node4 progrium/consul -advertise <HOST-IP> -join $JOIN_IP
```

### Inspect cluster manually

Show members of current cluster via CLI (eventually consistent):

`consul members` optionally use `-detailed` flag, requires `consul` to be
installed locally

or HTTP API (strongly consistent):

`curl localhost:8500/v1/catalog/nodes`

or DNS:

`dig @127.0.0.1 -p 8600 <YOUR HOST NAME>.node.consul`

## Registrator

Good tutorial for using Registrator: http://jlordiales.me/2015/02/03/registrator/

Start Registrator:

```
sudo docker run -d \
-v /var/run/docker.sock:/tmp/docker.sock \
--name registrator -h registrator \
progrium/registrator:latest consul://<HOST-IP>:8500
```

## Prometheus

Set the IP in `prometheus.yml` under the key
`scrape_configs/consul/target_groups/targets` to the IP of your Docker host.

Run Prometheus and provide a `prometheus.yml` for it (needs to use absolute path):

`sudo docker run -P -v /path/to/prometheus.yml:/etc/prometheus/prometheus.yml:ro prom/prometheus`

### Consul Exporter

The Consul Exporter provides metrics from the Consul cluster to Prometheus. If
you change the bound port here, you also need to adjust it in `prometheus.yml`.

`sudo docker run -d -p 9107:9107 prom/consul-exporter -consul.server=localhost:8500`

# License

GNU Affero General Public License v3

![AGPLv3](https://gnu.org/graphics/agplv3-155x51.png)
