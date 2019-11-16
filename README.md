# git-cmd

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/in.ashwanthkumar/git-cmd/badge.svg)](https://maven-badges.herokuapp.com/maven-central/in.ashwanthkumar/git-cmd)

`git-cmd` is a common Git helper module that all GoCD plugins needing to poll or interact with
Git repositories can use.

This is a fork of [srinivasupadhya/git-cmd](https://github.com/srinivasupadhya/git-cmd/), being maintained
in support of the several GoCD Git-based plugins.

## Compatibility
| Plugin Version | Java Version for GoCD |
|:--------------:|:---------------------:|
| < 2.0          | 7+                    |
| \>= 2.0        | 9+                    |

## Features

* Supports detection of whether the `git` command line client is installed, with fallback to 
[JGit](https://www.eclipse.org/jgit/) if it is not available
* Intended to support most operations of the upstream [GoCD Git Material](https://github.com/gocd/gocd/blob/master/domain/src/main/java/com/thoughtworks/go/config/materials/git/GitMaterial.java)
    and [GitCommand](https://github.com/gocd/gocd/blob/master/domain/src/main/java/com/thoughtworks/go/domain/materials/git/GitCommand.java) 
    upon which this library is based.
* Most regular `Git` operations required by such a plugin are supported
    * clone
        * **git command line only** `--depth=1` shallow clones, and unshallowing when necessary (since `2.0`)
        * **git command line only** `--no-checkout` mode
    * fetch from refSpec
    * clean working directories
    * hard reset
    * pull
    * find revision operations
        * latest revision
        * revisions since
        * revision details
        * get current revision
        * get all revisions
        * get commit count (when not on a shallow clone)
        * filter revision searches by repo subpaths
    * submodule support
    * add, commit, push

##  Usage
Add Dependency (to plugin project):

Maven
```xml
<dependency>
    <groupId>in.ashwanthkumar</groupId>
    <artifactId>git-cmd</artifactId>
    <version>2.0</version>
</dependency>
```

Gradle
```groovy
dependencies {
    implementation 'in.ashwanthkumar:git-cmd:2.0'
}
```

Use:
```java
GitConfig config = new GitConfig("git@github.com:ashwanthkumar/git-cmd.git");
GitHelper git = HelperFactory.git(gitConfig, new File(flyweightFolder));
git.cloneOrFetch();
//...
```

## Development

Building:
```
$ mvn clean install
```

