
# Rock Paper Scissors Application

Welcome to the **Rock Paper Scissors** application! This project is a implementation of the classic game, enhanced with user authentication, game statistics, and a leaderboard system. Built using **Java Spring Boot** for the back-end and **Angular** for the front-end.

---

## Features

### **Back-End**

The back-end, powered by Spring Boot, provides a total of **10 endpoints** divided into four controllers:

#### **AuthController**
- **`/login`**: Used for user login. Returns an `ApiToken` and a `RefreshApiToken`.
- **`/refresh`**: Refreshes the `ApiToken` once it expires (after 1 hour).
- **`/logout`**: Logs out the user, invalidating their `ApiToken` and `RefreshApiToken`.

#### **GameController**
- **`/play`**: Allows the user to play the game. Requires the user's move (`rock`, `paper`, or `scissors`). Returns:
  - The computer's move.
  - The result (`WIN`, `LOSE`, or `TIE`).
  - The updated score.
  - Consecutive wins count.
- **`/gameHistory`**: Retrieves the user's game history.

#### **StatsController**
- **`/stats`**: Retrieves detailed stats for the logged-in user:
  - Games won, lost, tied, and played.
  - Moves played (`rock`, `paper`, and `scissors`).
- **`/allStats`**: Retrieves stats for all users, including:
  - Game points and username.

#### **UserController**
- **`/user`**: Fetches user details (email and username).
- **`/updateUser`**: Updates the user's email, username, and password.
- **`/register`**: Registers a new user.

### **Database Structure**

Three tables are used to manage data:

1. **`UserDB`**:
   - Stores user information: `id`, `username`, `password`, `email`, `refreshToken`, and `apiToken`.

2. **`StatsDB`**:
   - Maintains user stats:
     - Games won, lost, tied, and played.
     - Moves played (`rock`, `paper`, `scissors`).
     - Total points and consecutive wins.
   - `user` field: One-to-One relationship with `UserDB`.

3. **`GameHistoryDB`**:
   - Tracks game history for each user:
     - User's move, computer's move, result, score, and timestamp.
   - `user` field: Many-to-One relationship with `UserDB`.

### **Error Handling**
Two custom exceptions are implemented:
- **`BusinessException`**: Handles common business logic errors.
- **`DatabaseOperationException`**: Handles database-related errors.

### **Security**
- JWT-based authentication ensures secure access to private endpoints.
- A custom JWT filter validates tokens for expiration and database consistency.

---

### **Front-End**

The front-end is built with **Angular** and uses **Redux** for state management. It offers the following features:

#### **State Management**
A total of five states are implemented:
1. **`Auth`**: Manages authentication data (`apiToken` and `refreshApiToken`).
2. **`User`**: Stores user details (username, email).
3. **`Game`**: Tracks the current game state.
4. **`Stats`**: Holds user stats and game history.
5. **`Rating`**: Contains leaderboard data for all users.

#### **Views**
The front-end includes six primary views:
1. **Login**: Allows users to log in.
2. **Play**: The main game interface.
3. **Rating**: Displays a leaderboard ranked by user points.
4. **UserStats**: Shows user stats and game history.
5. **Profile**: Allows users to update their details.
6. **Register**: Enables new user registration.

#### **Features**
- An **HTTP Interceptor** ensures that all private API requests include an `Authorization` header.
- R esponsive design**.
- **Angular Material** for enhanced styling and usability.

---

## Installation & Setup

### **Back-End**
1. Clone the repository and navigate to the back-end directory.
2. Set up the database and update the `application-local.yml` file with your database credentials.
3. Build and run the project:
   ```bash
   mvn spring-boot:run
### **Front-End**
1. Navigate to the front-end directory
2. Install dependencies and start the development server:
   ```bash
   npm install
3. Start the development server:
   ```bash
   ng serve
