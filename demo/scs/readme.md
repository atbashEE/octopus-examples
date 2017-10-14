SCS Octopus demo
=

TODO This is very brief instruction on how to setup 

* An octopus SSO Server (OpenId Connect protocol based)
* Define an application as Octopus SSO Server client.
* Call a JAX-RS endpoint with the authentication and authorization of the current logged on user or with a _System Account_.
* Define a JAX-RS endpoint which can only be called with authentication and authorization in the header.


Octopus SSO Server
==

1. Add the following Maven dependency within the pom.file

```xml
    <dependency>
        <groupId>be.c4j.ee.security.octopus.sso</groupId>
        <artifactId>octopus-server</artifactId>
        <version>${octopus.version}</version>
    </dependency>
```

2. Define some configuration, if needed, into the Octopus configuration file( like octopusConfig.properties).

For example not requiring SSL.

```
   SSO.cookie.secure=false
```

3. Implement **SecurityDataProvider** interface as CDI bean

It is used by Octopus to retrieve the data for authentication validation and load the authorization information.

4. Implement **ClientInfoRetriever** interface as CDI bean

It is used by Octopus to verify if the Octopus SSO client application supplied data.

They must mast with the configuration available at the client application (see Octopus SSO client)

5. Implement **SSOPermissionProvider** interface as CDI bean

It is used by octopus to send the permission information of the Client application and the User permission for the client application to the client application.

Octopus SSO Client
==

1. Add the following Maven dependency within the pom.file

```xml
    <dependency>
        <groupId>be.c4j.ee.security.octopus.sso</groupId>
        <artifactId>octopus-client</artifactId>
        <version>${octopus.version}</version>
    </dependency>
```

2. Define the configuration as SSO Client

```
   # Location of the SCS security app. (Octopus SSO Server)
   SSO.octopus.server=http://localhost:8080/security

   # code = Authorization code grant, token = implicit grant
   SSO.flow=code

   # prefix for xxx.SSO.clientId, xxx.SSO.clientSecret, etc ... (optional)
   SSO.application=sso-app

   # The following values must match values return by implementation of ClientInfoRetriever within SCS Security app.
   sso-app.SSO.clientId=sso-app-clientId
   sso-app.SSO.clientSecret=EUuHTrCEXsXbIbelvzzCLp1R1WleGfu+8gGIdJ7VbAE
   sso-app.SSO.idTokenSecret=zGhImX5I2BBZQfbOJmO0HOWTLnbNLoVAtwN+h0+bC1gJZZNgmLTx+Y8uP56u/nLz
```


JAX-RS communication protected with Octopus JWT credentials
==

The idea is that we are able to define some JAX-RS endpoint which are protected. So only authenticated and/or users with certain permission are allowed to call the endpoint.

There is a client foreseen which automatically adds the authentication and authorization info as JWT into the header of the JAX-RS call.

This JWT is used to create a securityContext on the server side and do the required security validation before the endpoint code is called.

Protected JAX-RS endpoints
==

1. Add the following Maven dependency within the pom.file
 
```xml
    <dependency>
        <groupId>be.c4j.ee.security.octopus.authentication</groupId>
        <artifactId>jwt-scs-server</artifactId>
        <version>${octopus.version}</version>
    </dependency>
```

2. Define the JAX-RS root

Create the following class (class name is of course free to choose)

```java
@ApplicationPath("/data")
public class DemoRestApplication extends Application {
}
```

3. Protect the JAX-RS endpoints

Put the following entry within _securedURLs.ini_ file.

```
   /data/** = noSessionCreation, scs 
```

4. Create JAX-RS method and protect it with Octopus Security annotations.

```java
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresUser
    public TestData sayHello() {
        TestData result = new TestData();
        result.setName(principal.getName());
        return result;
    }
```

5. define the HMAC secret for signing the JWT.

Within the Octopus configuration file, define the BASE64 encoded byte array used for signing the JWT

```
   jwt.hmac.secret=szxK-5_eJjs-aUj-64MpUZ-GPPzGLhYPLGl0wrYjYNVAGva2P0lLe6UGKGM7k8dWxsOVGutZWgvmY3l5oVPO3w 
```

Call Protected JAX-RS endpoints
==

1. Use customized JAX-RS client

Use the **OctopusSCSUserRestClient** CDI bean to call the JAX-RS endpoints. It is a wrapper around JAX-RS Client of Java EE 7 but adds the authentication and authorization info to the headers as JWT token.

```java
    TestData testData = restClient.get("http://localhost:8080/app2/data/hello", TestData.class);
```

2. define the HMAC secret for signing the JWT.

Within the Octopus configuration file, define the BASE64 encoded byte array used for signing the JWT

```
   jwt.hmac.secret=szxK-5_eJjs-aUj-64MpUZ-GPPzGLhYPLGl0wrYjYNVAGva2P0lLe6UGKGM7k8dWxsOVGutZWgvmY3l5oVPO3w 
```

Use of SystemAccounts
==

If you want to make the call to the JAX-RS endpoint with some machine account (system account) instead of the current logged on users this is also possible.

Since no user interaction is involved in system accounts, the JWT is more secure by using RSA keys for signing.

Configure it by following these steps.

1. Define the properties file with mapping between system account and RSA key ID.

Within the Octopus configuration file, add following parameter. File is located within the classpath.

```
   jwt.systemaccounts.map=systemAccounts.properties
```

2. Define the location opf the RSA keys

Within the Octopus configuration file, add following parameter. File is located somewhere on the host.

```
   jwk.file=/Users/rubus/atbash/octopus-examples/demo/scs/scs.jwkset
```

3. Use OctopusSCSSystemRestClient CDI bean

When using System accounts, use the **OctopusSCSSystemRestClient** CDI bean instead of the **OctopusSCSUserRestClient**.

Running the applications
==

1. Download the code
2. Change the location of jwk.file parameter within the octopusConfig.properties file to match the download location.
3. Deploy the applications with roots _security_, _app1_ and _app2_ on an Java EE 7 compatible application server.
 