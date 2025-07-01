# 

## Model
www.msaez.io/#/163964407/storming/labcqrs-2310223

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- manageauthor
- adminsystem
- ebookplatform
- subscriber
- aisystem
- pointsystem


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- manageauthor
```
 http :8088/authors authorId="authorId"name="name"isApproved="isApproved"ebooks="ebooks"userId="userId"
```
- adminsystem
```
 http :8088/authors authorId="authorId"name="name"isApproved="isApproved"ebooks="ebooks"userId="userId"
 http :8088/eBooks ebookId="ebookId"title="title"authorId="authorId"content="content"coverImage="coverImage"summary="summary"price="price"category="category"countViews="countViews"
```
- ebookplatform
```
 http :8088/eBookPlatforms pid="pid"ebooks="ebooks"registeredAt="registeredAt"coverImage="coverImage"summary="summary"prices="prices"
```
- subscriber
```
 http :8088/subscribers subscriberId="subscriberId"userId="userId"subscriptionType="subscriptionType"startedAt="startedAt"expiredAt="expiredAt"password="password"
```
- aisystem
```
 http :8088/eBooks ebookId="ebookId"title="title"authorId="authorId"content="content"coverImage="coverImage"summary="summary"isPublicationApproved="isPublicationApproved"price="price"category="category"countViews="countViews"
```
- pointsystem
```
 http :8088/userPoints userId="userId"point="point"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
