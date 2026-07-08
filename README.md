# Gym Membership System
### Advanced Programming Concepts – Assessment 2

A console-based Gym Membership System implemented in **two versions** for comparison in Task 2 of the AI-Assisted Programming assignment.

---

## Project Structure

```
src/
├── Version_A_Traditional/       ← Manually written (no AI)
│   └── src/
│       ├── model/
│       │   ├── Member.java
│       │   └── Attendance.java
│       ├── service/
│       │   └── GymService.java
│       ├── util/
│       │   └── FileManager.java
│       └── main/
│           └── Main.java
│
└── Version_B_AI_Assisted/       ← AI-assisted (ChatGPT)
    └── src/
        ├── model/
        │   ├── Member.java
        │   └── Attendance.java
        ├── service/
        │   ├── GymService.java
        │   └── MemberRepository.java
        ├── util/
        │   └── FileManager.java
        └── main/
            └── Main.java
```

---

## Features

| Feature                  | Version A | Version B |
|--------------------------|:---------:|:---------:|
| Register Member          | ✅        | ✅        |
| View All Members         | ✅        | ✅        |
| Search Member by ID      | ✅        | ✅        |
| Update Member Details    | ✅        | ✅        |
| Deactivate Member        | ✅        | ✅        |
| Record Attendance        | ✅        | ✅        |
| View Member Attendance   | ✅        | ✅        |
| Filter by Membership Type| ✅        | ✅        |
| File Persistence         | ✅ (IO)   | ✅ (NIO)  |
| Builder Pattern          | ❌        | ✅        |
| Repository Pattern       | ❌        | ✅        |
| Java Streams             | ❌        | ✅        |
| Optional / null safety   | ❌        | ✅        |
| Enum for Types           | ❌        | ✅        |
| Structured Exception Handling | Basic | ✅ Full  |

---

## How to Run

Run these commands from the **project root** directory.

### Version A – Traditional
```bash
javac -d out/A src/Version_A_Traditional/src/model/*.java src/Version_A_Traditional/src/util/*.java src/Version_A_Traditional/src/service/*.java src/Version_A_Traditional/src/main/*.java
java -cp out/A main.Main
```

### Version B – AI-Assisted
```bash
javac -d out/B src/Version_B_AI_Assisted/src/model/*.java src/Version_B_AI_Assisted/src/util/*.java src/Version_B_AI_Assisted/src/service/*.java src/Version_B_AI_Assisted/src/main/*.java
java -cp out/B main.Main
```

---

## AI Tools Used (Version B)
- **ChatGPT** – Used for code generation, refactoring, improving code quality, enhancing exception handling, and optimizing the overall implementation.
- AI prompts used during development are included in the project report.

---

## Key Differences (for Report Task 2)

| Criterion            | Version A                       | Version B                          |
|----------------------|---------------------------------|------------------------------------|
| Development Time     | Longer – manual design          | Faster – AI-assisted development   |
| Code Quality         | Functional, simpler structure   | Better OOP, design patterns        |
| Error Handling       | Basic null checks               | Exceptions + Optional              |
| Readability          | Clear but verbose               | Concise with streams/lambdas       |
| Maintainability      | Harder to extend                | Repository pattern = easier scale  |
