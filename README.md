# keycloak-test
Tests with keycloak and Spring

# Preparations

Have a running keycloak instance (outside the dev-container)
- add a realm "demo"
  - configure demo
    - Login: enable all except `Email as username` and `Edit username`
- configure GitHub Identity provider 
  In Keyloak: add Identity Providers and select GitHub    
  In GitHub: create an OAuth Application "Keycloak test" (Developer settings -> OAuth apps -> New OAuth app). The redirect uri should be set to the value of Redirect URI shown in Keycloak.
  

When the changes are saved try if everything worked so far an try a login. In Keycloak head over to Clients (of the Demo realm) and use the link shown for Client ID _account_ (should be something like http://localhost:18080/auth/realms/demo/account/)

Choose the Github login and you will be asked to authorize the "Keycloak test" appication to get your profile data. If you do, you will be prompted to add some more data (like your name). 

After that you will get an error regarding the `Email verification`. The account is created but not yet ready to be used since the email is not sent yet. 

Switch back to Keycloak adminstration and select Users for the Demo realm. You will probably see an empty list but hitting the `View all users` will show an entry. Follow the link and you will see the user name. Notice the entry Verify Email in `Required User Actions`.

We can manually override the values here and make the user ready to use: remove the Verify Email tag  and switch the toggle `Email verified`.

After these steps we have at least one user setup in Keycloak.


