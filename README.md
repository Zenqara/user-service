# Микросервис пользователей

Микросервис предоставляет функциональность для управления пользователями в системе.

## User API Endpoints

Below are the API endpoints for the user service:

1. **Get all users**
    - **URL:** `http://localhost:8080/api/v1/users`
    - **Method:** GET
    - **Description:** Returns a list of all users.

2. **Find user by ID**
    - **URL:** `http://localhost:8080/api/v1/users/{userId}`
    - **Method:** GET
    - **Description:** Returns a single user by their ID.
    - **Path Parameter:**
        - `userId` (Long): The ID of the user to retrieve.
    - **Example URL:**
        - `http://localhost:8080/api/v1/users/1`

3. **Create a new user**
    - **URL:** `http://localhost:8080/api/v1/users`
    - **Method:** POST
    - **Description:** Creates a new user and returns the created user.
    - **Body Parameters:** Must include a valid `UserModifyDto` object.
    - **Example Request Body:**
        ```json
        {
          "username": "jane_doe",
          "email": "jane.doe@example.com",
          "phone": "+1 (555) 234-5678",
          "password": "S3cur3P@ssw0rd",
          "firstName": "Jane",
          "lastName": "Doe",
          "aboutMe": "A passionate software engineer.",
          "dateOfBirth": "1990-05-15"
        }
        ```
    - **Example URL:**
        - `http://localhost:8080/api/v1/users` (POST request with body)

4. **Update an existing user**
    - **URL:** `http://localhost:8080/api/v1/users/{userId}`
    - **Method:** PUT
    - **Description:** Updates an existing user by their ID.
    - **Path Parameter:**
        - `userId` (Long): The ID of the user to update.
    - **Body Parameters:** Must include a valid `UserModifyDto` object.
    - **Example Path Parameter:**
        - `userId`: `2`
    - **Example Request Body:**
        ```json
        {
          "username": "john_doe_updated",
          "email": "john.doe.updated@example.com",
          "phone": "+1 (555) 876-5432",
          "password": "N3wP@ssw0rd",
          "firstName": "John",
          "lastName": "Doe",
          "aboutMe": "Updated bio information.",
          "dateOfBirth": "1985-06-15"
        }
        ```
    - **Example URL:**
        - `http://localhost:8080/api/v1/users/2`

5. **Deactivate a user**
    - **URL:** `http://localhost:8080/api/v1/users/{userId}/deactivate`
    - **Method:** PUT
    - **Description:** Deactivates a user by their ID.
    - **Path Parameter:**
        - `userId` (Long): The ID of the user to deactivate.
    - **Example URL:**
        - `http://localhost:8080/api/v1/users/3/deactivate`

6. **Delete a user**
    - **URL:** `http://localhost:8080/api/v1/users/{userId}`
    - **Method:** DELETE
    - **Description:** Deletes a user by their ID.
    - **Path Parameter:**
        - `userId` (Long): The ID of the user to delete.
    - **Example URL:**
        - `http://localhost:8080/api/v1/users/4`

## Subscription API Endpoints

Below are the API endpoints for managing subscriptions:

1. **Follow a user**
    - **URL:** `http://localhost:8080/api/v1/subscription/{followerId}/follow/{followeeId}`
    - **Method:** POST
    - **Description:** Allows a user to follow another user by their IDs.
    - **Path Parameters:**
        - `followerId` (Long): The ID of the user who is following.
        - `followeeId` (Long): The ID of the user being followed.
    - **Example Path Parameters:**
        - `followerId`: `5`
        - `followeeId`: `1`
    - **Example URL:**
        - `http://localhost:8080/api/v1/subscription/5/follow/1`

2. **Unfollow a user**
    - **URL:** `http://localhost:8080/api/v1/subscription/{followerId}/unfollow/{followeeId}`
    - **Method:** DELETE
    - **Description:** Allows a user to unfollow another user by their IDs.
    - **Path Parameters:**
        - `followerId` (Long): The ID of the user who is unfollowing.
        - `followeeId` (Long): The ID of the user being unfollowed.
    - **Example Path Parameters:**
        - `followerId`: `5`
        - `followeeId`: `1`
    - **Example URL:**
        - `http://localhost:8080/api/v1/subscription/5/unfollow/1`

3. **Get followers**
    - **URL:** `http://localhost:8080/api/v1/subscription/{followeeId}/followers`
    - **Method:** GET
    - **Description:** Retrieves a list of users who are following the specified user.
    - **Path Parameter:**
        - `followeeId` (Long): The ID of the user whose followers are to be retrieved.
    - **Example Path Parameter:**
        - `followeeId`: `1`
    - **Example URL:**
        - `http://localhost:8080/api/v1/subscription/1/followers`

4. **Get followers count**
    - **URL:** `http://localhost:8080/api/v1/subscription/{followeeId}/followers/count`
    - **Method:** GET
    - **Description:** Retrieves the count of followers for a specific user.
    - **Path Parameter:**
        - `followeeId` (Long): The ID of the user whose follower count is to be retrieved.
    - **Example Path Parameter:**
        - `followeeId`: `1`
    - **Example URL:**
        - `http://localhost:8080/api/v1/subscription/1/followers/count`

5. **Get followees**
    - **URL:** `http://localhost:8080/api/v1/subscription/{followerId}/follows`
    - **Method:** GET
    - **Description:** Retrieves a list of users that the specified user is following.
    - **Path Parameter:**
        - `followerId` (Long): The ID of the user whose followees are to be retrieved.
    - **Example Path Parameter:**
        - `followerId`: `2`
    - **Example URL:**
        - `http://localhost:8080/api/v1/subscription/2/follows`

6. **Get followees count**
    - **URL:** `http://localhost:8080/api/v1/subscription/{followerId}/follows/count`
    - **Method:** GET
    - **Description:** Retrieves the count of users that a specific user is following.
    - **Path Parameter:**
        - `followerId` (Long): The ID of the user whose followees count is to be retrieved.
    - **Example Path Parameter:**
        - `followerId`: `2`
    - **Example URL:**
        - `http://localhost:8080/api/v1/subscription/2/follows/count`
