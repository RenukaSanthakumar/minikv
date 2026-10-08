# MiniKV

MiniKV is a lightweight key-value store built using Java.

It demonstrates practical Java development concepts such as data structures, file persistence, Write-Ahead Logging, concurrency, TTL expiration, LRU caching, CLI interaction, REST API development, unit testing, and performance benchmarking.

## Features

* Key-value storage
* PUT, GET, and DELETE operations
* Key validation
* Custom exceptions
* File-based persistence
* Write-Ahead Log (WAL)
* Concurrent access support
* TTL (Time-To-Live) expiration
* LRU Cache
* Command Line Interface (CLI)
* REST API
* Unit testing
* Performance benchmarking

## Technologies Used

* Java 21
* Maven
* Spring Boot
* JUnit 5
* Git & GitHub
* ConcurrentHashMap
* LinkedHashMap

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/
│           └── renuka/
│               ├── App.java
│               ├── MiniKVApplication.java
│               ├── MiniKVController.java
│               ├── MiniKVCLI.java
│               ├── MiniKVBenchmark.java
│               └── minikv/
│                   ├── KeyValueStore.java
│                   ├── MiniKV.java
│                   ├── InvalidKeyException.java
│                   ├── WriteAheadLog.java
│                   └── LRUCache.java
│
└── test/
    └── java/
        └── com/
            └── renuka/
                └── minikv/
                    └── MiniKVTest.java
```

## REST API

Start the application:

```bash
mvn spring-boot:run
```

The server runs on:

```text
http://localhost:8080
```

### PUT

```text
PUT /kv/{key}
```

Example:

```text
PUT /kv/name
```

Request body:

```text
Renu
```

### GET

```text
GET /kv/{key}
```

Example:

```text
GET /kv/name
```

### DELETE

```text
DELETE /kv/{key}
```

Example:

```text
DELETE /kv/name
```

### SIZE

```text
GET /kv
```

Returns the number of stored keys.

## Command Line Interface

Run the CLI using:

```bash
mvn exec:java -Dexec.mainClass=com.renuka.MiniKVCLI
```

Available commands:

```text
PUT key value
GET key
DELETE key
CONTAINS key
SIZE
EXIT
```

## TTL

MiniKV supports Time-To-Live (TTL) expiration.

Example:

```java
kv.put("OTP", "583921", 3);
```

The key automatically expires after 3 seconds.

## LRU Cache

MiniKV includes an LRU (Least Recently Used) cache implemented using `LinkedHashMap`.

Example:

```java
LRUCache cache = new LRUCache(3);

cache.put("A", "Apple");
cache.put("B", "Banana");
cache.put("C", "Cherry");

cache.get("A");

cache.put("D", "Durian");
```

When the cache exceeds its capacity, the least recently used entry is removed.

## Write-Ahead Log

MiniKV uses a Write-Ahead Log (WAL) to record key-value operations before modifying the stored data.

Example operations:

```text
PUT|name|Renu
DELETE|name|
```

## Testing

Run all unit tests using:

```bash
mvn clean test
```

The tests cover:

* PUT and GET
* Updating existing keys
* DELETE
* Key existence
* Size
* Invalid keys
* Null keys
* Null values
* Concurrent operations

## Benchmarking

Run the benchmark using:

```bash
mvn exec:java -Dexec.mainClass=com.renuka.MiniKVBenchmark
```

Example result:

```text
=== MiniKV Benchmark ===
Operations: 1000
PUT time: 1372.2417 ms
GET time: 1.1809 ms
Final size: 1000
```

Benchmark results may vary depending on the computer and disk performance.

## Key Concepts Demonstrated

* Java OOP
* Interfaces and polymorphism
* Collections
* ConcurrentHashMap
* LinkedHashMap
* Exception handling
* File I/O
* Data persistence
* Write-Ahead Logging
* Multithreading
* Synchronization
* TTL
* Caching
* REST API development
* Spring Boot
* JUnit testing
* Maven
* Git and GitHub

## Future Improvements

* JSON request and response support
* Improved persistence format
* Authentication
* Docker deployment
* Distributed storage
* Advanced benchmarking
* Monitoring and metrics

