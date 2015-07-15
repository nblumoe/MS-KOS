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

### Investigate cluster manually

Show members of current cluster via CLI (eventually consistent):

`consul members` optionally use `-detailed` flag

or HTTP API (strongly consistent):

`curl localhost:8500/v1/catalog/nodes`

or DNS:

`dig @127.0.0.1 -p 8600 <YOUR HOST NAME>.node.consul`

# License

GNU Affero General Public License v3

![AGPLv3](https://gnu.org/graphics/agplv3-155x51.png)
