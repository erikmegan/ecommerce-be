infra-up: ### Run docker-compose
	docker-compose up -d
	mvn -U clean install
.PHONY: infra-up


run:
		java -jar target/*.jar
.PHONY: run