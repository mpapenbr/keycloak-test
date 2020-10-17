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

# Implementation
This variant uses a session and public-client=true variant. A stateless version can be found under a different branch. 

# Notes
## Java 14
As of now (2020-10-17), do not use Java 14. This results in a 
```
Caused by: java.lang.ClassNotFoundException: java.security.acl.Group
```
exception.
Using Java 11 is fine.

See:
- https://stackoverflow.com/questions/61932188/keycloak-server-caused-by-java-lang-classnotfoundexception-java-security-acl-g
- https://issues.redhat.com/browse/KEYCLOAK-13690

## Insomnia

Couldn't get this done via Insomnia. Keycloak reports this:
```
19:14:15,290 WARN  [org.keycloak.events] (default task-79) type=IDENTITY_PROVIDER_LOGIN_ERROR, realmId=demo, clientId=null, userId=null, ipAddress=172.17.0.1, error=invalid_code, identity_provider=github
```
## Dev container
Remember: `localhost` may have different meanings!

The better solution is to work with `host.docker.internal` when dev containers are involved and you don't have setup ALL components in your dev environment. 

Example: 
From this dev container we contact the keycloak server via `host.docker.internal`. Keycloak uses this name as redirect URI for Github. In prior tests - when evaluating the Keycloak GUI - we used a `localhost` based callback URI in the Github OAuth Application settings. This caused problems with the Github authorization. Solution was to setup the callback in Github as http://host.docker.internal.... 

But....then there is Google. `host.docker.internal` is not considered a valid top level domain and cannot be used there. So be it. Currently no idea how to solve it. 




