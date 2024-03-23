## Endpoints

| HTTP Method | Path                        | Description                                           | Parameters                                                  |
|-------------|-----------------------------|-------------------------------------------------------|-------------------------------------------------------------|
| POST        | /jobs/create                | Create a job                                          | `clientId` (query), `workerId` (query), `jobDetails` (body) |
| GET         | /jobs/skills/{skill}        | Get job based on skills                               | `skill` (path)                                              |
| POST        | /jobs/{jobId}/complete      | Complete a job                                        | `jobId` (path)                                              |
| POST        | /jobs/{jobId}/rating        | Give a new rating to a job                            | `jobId` (path), `rating` (body)                             |
| POST        | /messages/send              | Send a message                                        | `message` (body)                                            |
| GET         | /messages/user/{userId}     | Get messages for a user                               | `userId` (path), `workerId` (query)                         |
| GET         | /messages/worker/{workerId} | Get messages for a worker                             | `workerId` (path), `userId` (query)                         |
| GET         | /users                      | Get all users                                         | None                                                        |
| GET         | /users/{userId}/messages    | Get all messages for a user                           | `userId` (path), `workerId` (query)                         |
| GET         | /users/{id}                 | Get a single user by ID                               | `id` (path)                                                 |
| POST        | /users                      | Create a new user                                     | `user` (body)                                               |
| PUT         | /users/{id}                 | Update an existing user                               | `id` (path), `userDetails` (body)                           |
| POST        | /login                      | login an existing user                                | `username` (body) or `email` (body), `password` (body)      |
| GET         | /workers                    | Get all workers                                       | None                                                        |
| GET         | /workers/{id}               | Get a single worker by ID                             | `id` (path)                                                 |
| GET         | /workers/skills/{skill}     | Get workers based on skills                           | `skill` (path)                                              |
| GET         | /workers/rating/{rating}    | Get workers based on rating                           | `rating` (path)                                             |
| POST        | /workers                    | Create a new worker                                   | `worker` (body)                                             |
| POST        | /workers/{id}/rate          | Give a new rate based on the worker ID                | `id` (path), `rating` (query)                               |
| PUT         | /workers/{id}               | Update an existing worker                             | `id` (path), `workerDetails` (body)                         |
| GET         | /workers/{id}/jobs          | Get a worker's past jobs                              | `id` (path)                                                 |
| GET         | /workers/{id}/contact       | Get a worker's contact info based on their preference | `id` (path)                                                 |
| GET         | /workers/{id}/transactions  | Get a worker's transaction history                    | `id` (path)                                                 |
| GET         | /workers/{id}/working-hours | Get a worker's working hours                          | `id` (path)                                                 |
