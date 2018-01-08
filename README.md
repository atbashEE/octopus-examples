# octopus-examples
Examples for the Octopus framework (Java EE Security framework)

## demo directory

Contains some code examples of features of Octopus.

/scs [Self-Contained System demo](https://www.atbash.be/2017/09/28/release_octopus_v097/) _see section Self-Contained Systems in the blog_

/sessionHijacking [Session Hijacking protection demo](https://www.atbash.be/2017/11/06/session-hijacking-protection-with-octopus-framework/)

/octopus-soteria [Java EE Security API integration with Octopus]( http://www.atbash.be/2018/01/08/java-ee-security-api-integration-with-octopus)

## gitbook directory

Contains the code examples which are created in the GitBook "Octopus cookbook".

/ex1 [Simple JSF Application](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter1.html) _Getting Started with Authentication_

/ex1_alt1 [Custom location for securedURLs.ini](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter1.html) Chapter1 alternative 1

/ex1_alt2 [Custom file for the login page](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter1.html) Chapter1 alternative 2

/ex1_alt3 [Alternative name for the loginBean](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter1.html) Chapter1 alternative 3

/ex2 [Securing JSF Components](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter2.html) _Adding Authorization_

/ex2_alt1 [Simple permissions](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter2.html) Chapter2 alternative 1

/ex2_alt2 [Named permissions (String)](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter2.html) Chapter2 alternative 2

/ex2_alt3 [Named permissions (Enum)](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter2.html) Chapter2 alternative 3

/ex3 [Add logout feature](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter3.html)

/ex3_part2 [Basic CDI events](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter3.html)

/ex3_alt1 [Specify logout page](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter3.html) Chapter3 alternative 1

/ex3_add1 [Audit user URL requests](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter3.html)

/ex4 [Securing EJB methods](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter4.html)

/ex5_srv [Server app with JAX-RS endpoint](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter5.html) _Building Octopus security context from JWT within header_

/ex5 [Client app calling JAX-RS endpoint](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter5.html) _Adding JWT with authentication/authorization info about user automatically to JAX-RS client call_

/ex6 [Authentication using Hashed passwords stored in DB](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter-6.html)

/ex6_alt1[Key derivation functions as passwords](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter-6.html) Chapter6 alternative 1

/ex6_alt2[HEX encoded hashed passwords](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter-6.html) Chapter6 alternative 2

## tests directory

Some additional projects for testing

/minimal: The minimal configuration required to make Octopus run.

/classpathURLFile: securedURLs.ini on classpath

/EE6_altConfig: alternatives 1, 2 and 3 for a Java EE 6 setup.

/classpathURLFile: URL patterns defined in a classpath file.

/permissionEnumMissing : JSF view uses name which doesn't exist in Enum.
