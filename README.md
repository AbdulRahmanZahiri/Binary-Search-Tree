# Binary Search Tree (Spring Boot)

A Spring Boot web app that builds Binary Search Trees (BST) from user input, can optionally build a **balanced** BST, returns a JSON representation, and persists each run to a MySQL database. Includes MySQL‑backed tests.

---

## ⚙️ Tech Stack
- Java 17, Spring Boot 3.2
- Spring Web, Data JPA, Thymeleaf, Validation
- MySQL 8 (via Docker Compose)
- Maven for build/test

---

## ✅ Reviewer Quick Start 

> App runs at **http://localhost:8081** by default.

1) **Start the database (Docker)**
```bash
docker compose up -d
```
> The Docker Compose file in the repo defines the database, user, and password. No manual setup is required for review.

2) **Run the application**
```bash
mvn spring-boot:run
```
Open:
- `http://localhost:8081/enter-numbers` – main input page
- `http://localhost:8081/previous-trees` – list of stored runs

3) **Run tests (use the same MySQL from Docker)**
```bash
docker compose up -d
mvn -q -DskipTests=false clean test
```
Tests are transactional; they roll back DB writes.

---

## 🔌 Endpoints
### UI
- `GET /enter-numbers` – input form and submit
- `GET /previous-trees` – history of previous runs

### API
- `POST /process-numbers`
  **Request body (example):**
  ```json
  { "numbers": [7,3,9,1,5], "balanced": false }
  ```

---

## 🗃️ Database
- Table: `tree_data`  
  Columns: `id` (PK), `input_numbers`, `tree_json`, `is_balanced`, `created_at`.

> DB credentials are defined **only** inside `docker-compose.yml` for local review. The application reads them from `src/main/resources/application.properties` without exposing personal information in this README.

(Optional) If you prefer a GUI, phpMyAdmin is available at `http://localhost:8080` when defined in the Compose file.

---

## 🔧 Configuration (non-sensitive)
Application config lives at `src/main/resources/application.properties`. Relevant non-secret settings:
```properties
server.port=8081
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```
Database connection details are sourced from the repo’s Compose setup and shipped properties file for local review only.

---


## 🧱 Project Structure (key files)
```
src/
  main/
    java/com/bst/
      BstApplication.java
      controller/TreeController.java
      service/BinarySearchTreeService.java
      model/TreeNode.java
      model/TreeResult.java
      entity/TreeData.java
      repository/TreeDataRepository.java
    resources/
      templates/
        enter-numbers.html
        previous-trees.html
        tree-result.html
      application.properties
  test/
    java/com/bst/
      BinarySearchTreeServiceTest.java
docker-compose.yml
pom.xml
```


## 📄 Assignment Checklist Mapping
- `/enter-numbers` input page ✅
- `/process-numbers` builds BST, returns JSON, persists to DB ✅
- `/previous-trees` history ✅
- ≥ 3 integration tests ✅
- Balanced BST option ✅

---

## 📝 License
Educational project. No personal secrets are included in this README.
