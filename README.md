# Credit Simulator

A Java MVC application for vehicle loan credit simulation.

## Build and Run
### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Run script (recommended)
```bash
# Tanpa file
./bin/credit_simulator

# Dengan file input
./bin/credit_simulator file_inputs.txt
```

### Run with Docker Image 
```bash
# Pull image
docker pull renicp/credit_simulator:latest

# Run interactively
docker run --rm -it renicp/credit_simulator

# Run with input file mounted
docker run --rm -it -v "$(pwd)/file_inputs.txt:/app/file_inputs.txt" renicp/credit_simulator file_inputs.txt
```

### Run locally (Maven)
```bash
# Build
mvn compile

# Run application
mvn exec:java

# OR Run application with file
mvn exec:java -Dexec.args="file_inputs.txt"

# Package as JAR
mvn package

# Clean build artifacts
mvn clean

# Run Unit test
mvn test
```

### Build & Run Docker locally

```bash
# Build image
docker build -t credit-simulator .

# Run container
docker run --rm -it credit-simulator

# OR Run container with file
docker run --rm -it -v "$(pwd)/file_inputs.txt:/app/file_inputs.txt" credit-simulator file_inputs.txt
```

## Features

- Interactive credit calculation
- File-based input processing
- Server data loading via HTTP client
- Support for cars and motorcycles

## Usage

1. Run the application
2. Type 'show' to see available commands
3. Use 'new' for interactive calculation
4. Use 'upload' to load data from file input
5. Use 'load' to load from external api
6. Use 'exit' to quit