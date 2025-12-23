






CREATE TABLE IF NOT EXISTS users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL
);



CREATE TABLE IF NOT EXISTS members (
    email TEXT PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    clubs_joined INTEGER DEFAULT 0
);



CREATE TABLE IF NOT EXISTS clubs (
    name TEXT PRIMARY KEY,
    president_email TEXT NOT NULL,
    max_members INTEGER NOT NULL,
    activity_category TEXT,
    FOREIGN KEY (president_email) REFERENCES members(email)
);



CREATE TABLE IF NOT EXISTS member_clubs (
    email TEXT NOT NULL,
    club_name TEXT NOT NULL,
    role TEXT NOT NULL,
    PRIMARY KEY (email, club_name),
    FOREIGN KEY (email) REFERENCES members(email),
    FOREIGN KEY (club_name) REFERENCES clubs(name)
);



CREATE TABLE IF NOT EXISTS activities (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    activity_name TEXT NOT NULL,
    activity_type TEXT,
    activity_date TEXT,
    club_name TEXT NOT NULL,
    FOREIGN KEY (club_name) REFERENCES clubs(name)
);



CREATE INDEX IF NOT EXISTS idx_member_clubs_email ON member_clubs(email);
CREATE INDEX IF NOT EXISTS idx_member_clubs_club_name ON member_clubs(club_name);
CREATE INDEX IF NOT EXISTS idx_activities_club_name ON activities(club_name);
CREATE INDEX IF NOT EXISTS idx_clubs_president ON clubs(president_email);
