# ClubApp — Club & Activity Management System

A **Java desktop application** (Swing GUI + SQLite) for managing student
clubs, their members, and club activities. Users register, join or create clubs,
and club presidents manage members and events — all persisted in a local SQLite
database through a hand-written JDBC/DAO layer.

![Java](https://img.shields.io/badge/java-17-007396?logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/gui-javax.swing-orange)
![SQLite](https://img.shields.io/badge/database-SQLite-003B57?logo=sqlite&logoColor=white)

---

## Table of contents

- [Overview](#overview)
- [Features](#features)
- [Roles & business rules](#roles--business-rules)
- [Architecture](#architecture)
- [Database schema](#database-schema)
- [Project structure](#project-structure)
- [Getting started](#getting-started)
- [Use case diagram](#use-case-diagram)
- [Tech stack](#tech-stack)
- [Known limitations](#known-limitations)

---

## Overview

ClubApp is a two-tier application:

- **Presentation layer** — a set of `JFrame` windows (`src.gui`) with a shared
  look-and-feel builder (`GuiUtils`).
- **Domain/Data layer** — plain-Java service classes (`src.backend.*`) backed by a
  JDBC DAO layer (`src.backend.dao`) talking to a local SQLite file (`club_app.db`).

Role-based routing sends users to either a **Normal User** dashboard or a **Club
President** dashboard after login.

---

## Features

**Authentication**

- Register with first name, last name and email → the app **generates a random
  8-character password**; email is used as the login username.
- Login with role detection (Club President vs. Normal User).

**Normal User dashboard**

- **See Events** — view activities from every club the user belongs to.
- **Join a Club** — browse and join an existing club.
- **Quit a Club** — leave a club (presidents cannot leave their own club).
- **Create a Club** — start a new club (name, category, max members) and become its
  president.

**Club President dashboard**

- **See Events** — view activities from joined clubs.
- **See Members** — view the members of the clubs the president owns.
- **Add Activity** — create activities (name, type, date) for a club.
- **Join / Quit** other clubs as a regular member.

**Utilities**

- `DatabaseInitializer` — create the SQLite schema and tables.
- `DataMigration` — standardize all stored emails to lowercase/trimmed.

---

## Roles & business rules

| Rule | Implementation |
|---|---|
| Emails are case-insensitive | Normalized to `lower(trim(...))` everywhere |
| A club name is unique | `ClubDAO.isClubNameTaken` |
| A user may join at most 2 clubs | `clubs_joined` counter checked in `ClubManager` |
| A president cannot quit their own club | `ClubManager.quitClub` guard |
| Registration assigns role | New member → `Normal User`; club creator → `Club President` |
| Club creation is transactional | `conn.setAutoCommit(false)` + commit/rollback |

Role names: `Club President`, `Normal User`.

---

## Architecture

```
┌──────────────────────────── GUI (src.gui) ────────────────────────────┐
│ Home · Login · Register · NormalUser · ClubPresident · ExploreClubs    │
│ CreateClub · QuitClub · AddActivityWindow · CalendarWindow ·           │
│ EventsWindow · ViewUsersWindow · DateSelector · GuiUtils               │
└───────────────────────────────┬───────────────────────────────────────┘
                                │ calls
┌──────────────────── Backend services (src.backend.*) ─────────────────┐
│ auth/Login · auth/Register · club/ClubManager                          │
│ activity/AddActivity · activity/Calendar · utils/*                      │
└───────────────────────────────┬───────────────────────────────────────┘
                                │ calls
┌──────────────────────── DAO layer (src.backend.dao) ──────────────────┐
│ UserDAO · MemberDAO · ClubDAO · DBConnection                           │
└───────────────────────────────┬───────────────────────────────────────┘
                                │ JDBC
                        SQLite  club_app.db
```

---

## Database schema

Tables (see [`clubapp.sql`](clubapp.sql)):

| Table | Columns |
|---|---|
| `users` | `username` (PK), `password` |
| `members` | `email` (PK), `first_name`, `last_name`, `clubs_joined` |
| `clubs` | `name` (PK), `president_email` (FK→members), `max_members`, `activity_category` |
| `member_clubs` | `email` (FK→members), `club_name` (FK→clubs), `role` — PK(email, club_name) |
| `activities` | `id` (PK autoincrement), `activity_name`, `activity_type`, `activity_date`, `club_name` (FK→clubs) |

Indexes: `member_clubs(email)`, `member_clubs(club_name)`, `activities(club_name)`,
`clubs(president_email)`.

---

## Project structure

```
Java_project-Final/
├── club_app.db                 # pre-built SQLite database
├── clubapp.sql                 # schema (CREATE TABLE ...)
├── docs/
│   └── UseCaseDiagram.png
├── lib/                        # bundled JDBC / logging jars
│   ├── sqlite-jdbc-3.45.0.0.jar
│   ├── ojdbc17.jar
│   ├── slf4j-api-2.0.9.jar
│   └── slf4j-simple-2.0.9.jar
└── src/
    ├── main/Main.java          # entry point → opens HomeWindow
    ├── gui/                    # Swing windows
    └── backend/
        ├── auth/               # Login, Register
        ├── club/               # ClubManager
        ├── activity/           # AddActivity, Calendar
        ├── dao/                # UserDAO, MemberDAO, ClubDAO, DBConnection
        └── utils/              # BackendUtils, DatabaseInitializer, DataMigration
```

---

## Getting started

### Prerequisites

- JDK 17+
- (No Maven/Gradle — dependencies are bundled in `lib/`)

### Run the app

The database (`club_app.db`) is committed, so the app runs immediately.

**From an IDE (VS Code / IntelliJ):** add every jar in `lib/` to the classpath and run
`src.main.Main`.

**From the command line (Windows):**

```bat
javac -cp "lib/*" -d out (dir /s /b src\*.java)
java  -cp "out;lib/*" src.main.Main
```

**From the command line (Linux / macOS):**

```bash
javac -cp "lib/*" -d out $(find src -name "*.java")
java  -cp "out:lib/*" src.main.Main
```

### Rebuild the database from schema

```bash
java -cp "out;lib/*" src.backend.utils.DatabaseInitializer
```

> `DatabaseInitializer` expects a `schema.sql` file in the working directory; the
> committed schema is `clubapp.sql` (rename or copy it to `schema.sql` first).

---

## Use case diagram

See [`docs/UseCaseDiagram.png`](docs/UseCaseDiagram.png).

---

## Tech stack

| Area | Technology |
|---|---|
| Language | Java 17 |
| GUI | Java Swing (`javax.swing`) |
| Persistence | JDBC + DAO pattern |
| Database | SQLite (`sqlite-jdbc`) |
| Logging | SLF4J |

---

## Known limitations

- Passwords are stored in **plaintext** in the `users` table and a random password is
  generated at registration (no hashing, no password reset).
- `lib/ojdbc17.jar` (Oracle) is bundled but the app uses SQLite only.
- `DatabaseInitializer` references `schema.sql` while the schema file is `clubapp.sql`.
- No automated tests and no build file (dependencies are vendored in `lib/`).

---

## License

Released under the [MIT License](LICENSE).

Copyright (c) 2026 Mohamed Aziz Madhbouh.
