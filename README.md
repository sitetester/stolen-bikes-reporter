It builds a service that will help the local police to solve bike thefts.

### Requirements

- [ ] The police wants to be able to add, edit and remove officers. (See data model suggestion below)
- [ ] Private citizens want to be able to report stolen bikes. (See data model suggestion below)
- [ ] The system should assign a police offers to handle stolen bike cases as they are being reported.
    - [ ] A police officer can only handle one case at a time.
- [ ] The police should be able to report bikes as found.
    - [ ] When the police finds a bike the case should be marked as resolved and the police officer in charge of the
      case should be marked as available to take a new case.
- [ ] The system should be able to assign unassigned cases to police officers as they become available.
- [ ] Police and private citizens both want to be able to list the reported bike thefts and their status.

### Suggested data model

### Police officers

| Column | Type    | Description     |
| ---    | ---     | ---             |
| id     | Integer |                 |
| name   | String  | Name of officer |

### Bike thefts

| Column      | Type     | Description                              |
| ---         | ---      | ---                                      |
| id          | Integer  |                                          |
| title       | String   | Title of report                          |
| brand       | String   | Brand of bike                            |
| city        | String   | City where theft occured                 |
| description | String   | Description of bike                      |
| reported    | DateTime | Date and time when theft was reported    |
| updated     | DateTime | Date and time when case was last updated |
| solved      | Boolean  | True when case has been solved           |
| officer     | Integer  | Officer in charge of the case            |
| image       | Blob     | Image of bike                            |