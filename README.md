# Appointment Manager

A Spring Boot application for managing appointments.

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** or higher ([Download Java](https://www.oracle.com/java/technologies/downloads/))
- **Gradle** (included via Gradle Wrapper, no separate installation needed)
- **Git** (for cloning the repository)

## Getting Started

### 1. Clone the Repository

```bash
git clone git@github.com:vedantnd111/appointment-manager.git
cd appointment-manager
```

### 2. Verify Java Installation

Check if Java 21 is installed:

```bash
java -version
```

You should see output indicating Java version 21 or higher.

### 3. Build the Application

Use the Gradle wrapper to build the application:

**On macOS/Linux:**
```bash
./gradlew build
```

**On Windows:**
```bash
gradlew.bat build
```

### 4. Run the Application

**Option A: Using Gradle**
```bash
./gradlew bootRun
```

**Option B: Using the JAR file**
```bash
java -jar build/libs/appointment-manager-0.0.1-SNAPSHOT.jar
```

### 5. Access the Application

Once the application starts successfully, you should see output indicating the server is running. By default, Spring Boot applications run on port `8080`.

Open your browser and navigate to:
```
http://localhost:8080
```

## Running Tests

To run the test suite:

```bash
./gradlew test
```

## Project Structure

```
appointment-manager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aspirants/appointment_manager/
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/aspirants/appointment_manager/
├── build.gradle
├── settings.gradle
└── README.md
```

## Technology Stack

- **Java**: 21
- **Spring Boot**: 4.0.0
- **Build Tool**: Gradle
- **Testing**: JUnit Platform

## Gradle Commands

| Command | Description |
|---------|-------------|
| `./gradlew build` | Builds the application |
| `./gradlew bootRun` | Runs the application |
| `./gradlew test` | Runs tests |
| `./gradlew clean` | Cleans build artifacts |
| `./gradlew tasks` | Lists all available tasks |

## Troubleshooting

### Port 8080 Already in Use

If port 8080 is already occupied, you can change the port by adding this to `src/main/resources/application.properties`:

```properties
server.port=8081
```

### Java Version Mismatch

If you encounter Java version errors, ensure you have Java 21 installed and set as your JAVA_HOME:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please open an issue on GitHub.
