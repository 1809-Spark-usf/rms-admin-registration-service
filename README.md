# rms-admin-registration-service
1. This service requires the config service to be running for it to work.

2. The email service must be running for the registerAdmin endpoint to work since a verification email is sent whenever a new admin registers.

3. Admins stored in the registration database will be sent to the login database after they are verified by clicking on a link sent to their email.

4. The service contains a registerAdmin post endpoint to save a new admin to the database when received from the front-end and a verifyAdmin get endpoint when a verification link is clicked on to change an admin's status to verified, send the new admin to the login database, and redirect to the login page.
