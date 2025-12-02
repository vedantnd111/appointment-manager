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

## Running with Docker

You can also run the application using Docker. This ensures a consistent environment and simplifies the setup process. The Docker setup includes both the application and a PostgreSQL database.

### Prerequisites

- **Docker** and **Docker Compose** installed on your machine.

#### Windows Setup

If you haven't set up Docker on Windows yet, follow these steps:

1.  **Enable Hyper-V and Virtual Machine Platform**:
    Run PowerShell as Administrator and execute:

    ```powershell
    dism.exe /online /enable-feature /featurename:Microsoft-Hyper-V /all /norestart
    dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
    ```

2.  **Install WSL 2**:

    ```powershell
    wsl --install
    wsl --set-default-version 2
    ```

    _Reboot your PC after this step._

3.  **Install Docker Desktop**:

    - Download from [Docker Official Site](https://www.docker.com/products/docker-desktop).
    - During installation, choose **Use WSL 2 instead of Hyper-V** (recommended).
    - Keep **Add shortcut to PATH** checked.

4.  **Verify Installation**:
    Open a new terminal and run:
    ```bash
    docker version
    docker run hello-world
    ```

### Steps

1.  **Clone the Repository** (if you haven't already):

    ```bash
    git clone git@github.com:vedantnd111/appointment-manager.git
    cd appointment-manager
    ```

2.  **Run with Docker Compose**:

    **On Windows (PowerShell/CMD):**

    ```bash
    docker compose up --build
    ```

    **On Linux/macOS:**

    ```bash
    docker compose up --build
    ```

    _(Note: You might need `sudo` on Linux depending on your Docker configuration)_

3.  **Access the Application**:
    Open your browser and navigate to: http://localhost:8080

4.  **Access PostgreSQL Database**:
    The PostgreSQL database is accessible on port **5433** with the following credentials:

    - **Host**: localhost
    - **Port**: 5433 (changed from default 5432 to avoid conflicts)
    - **Database**: appointmentdb
    - **Username**: postgres
    - **Password**: postgres123

    You can connect using any PostgreSQL client (e.g., pgAdmin, DBeaver, or psql):

    ```bash
    psql -h localhost -p 5433 -U postgres -d appointmentdb
    ```

    Or use Docker exec to access the database directly:

    ```bash
    docker exec -it appointment-manager-db psql -U postgres -d appointmentdb
    ```

5.  **Stop the Application**:
    Press `Ctrl+C` in the terminal or run:

    ```bash
    docker compose down
    ```

    To remove the database volume as well (⚠️ deletes all data):

    ```bash
    docker compose down -v
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
- **Database**: PostgreSQL 16
- **Testing**: JUnit Platform

## Gradle Commands

| Command             | Description               |
| ------------------- | ------------------------- |
| `./gradlew build`   | Builds the application    |
| `./gradlew bootRun` | Runs the application      |
| `./gradlew test`    | Runs tests                |
| `./gradlew clean`   | Cleans build artifacts    |
| `./gradlew tasks`   | Lists all available tasks |

## Troubleshooting

### Port 8080 Already in Use

If port 8080 is already occupied, you can change the port by adding this to `src/main/resources/application.properties`:

```properties
server.port=8081
```

### Port 5433 Already in Use (PostgreSQL)

If port 5433 is already occupied, you can change the PostgreSQL port in `docker-compose.yml`:

```yaml
postgres:
  ports:
    - "5434:5432" # Change 5434 to any available port
```

> **Note**: Port 5433 is used instead of the default 5432 to avoid conflicts with local PostgreSQL installations.

### Database Connection Issues

If the application fails to connect to the database:

1. Ensure PostgreSQL container is running:

   ```bash
   docker ps
   ```

2. Check PostgreSQL logs:

   ```bash
   docker logs appointment-manager-db
   ```

3. Verify database credentials in `application.properties` match those in `docker-compose.yml`

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
