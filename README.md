# octopus-examples
Examples for the Octopus framework (Java EE Security framework)

## demo directory

Contains some code examples of features of Octopus.

/scs [Self-Contained System demo](https://www.atbash.be/2017/09/28/release_octopus_v097/) _see section Self-Contained Systems in the blog_

/sessionHijacking [Session Hijacking protection demo](http://www.atbash.be/2017/11/06/session-hijacking-protection-with-octopus-framework/)

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

/ex3_add1 [Audit user uRL requests](https://rdebusscher.gitbooks.io/octopus-cookbook/content/chapter3.html)

## tests directory

Some additional projects for testing

/minimal: The minimal configuration required to make Octopus run.

/classpathURLFile: securedURLs.ini on classpath

/EE6_altConfig: alternatives 1, 2 and 3 for a Java EE 6 setup.

/classpathURLFile: URL patterns defined in a classpath file.

/permissionEnumMissing : JSF view uses name which doesn't exist in Enum.
