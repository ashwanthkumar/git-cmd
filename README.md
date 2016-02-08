# git-cmd

Fork of [srinivasupadhya/git-cmd](https://github.com/srinivasupadhya/git-cmd/), maintaining it so that I can publish this to maven. 

<hr />

Common module that all Go CD plugins to poll Git repository can use.

*Usage:*
Inside `git-cmd` project:
```
$ mvn clean install -DskipTests
```

Add Dependency (to plugin project):
```
<dependency>
    <groupId>in.ashwanthkumar</groupId>
    <artifactId>git-cmd</artifactId>
    <version>1.0</version>
</dependency>
```

Use:
```
GitHelper git = HelperFactory.git(gitConfig, new File(flyweightFolder));
git.cloneOrFetch();
...
```

`HelperFactory.git(gitConfig, new File(flyweightFolder));` detects & uses git if installed else falls back on jgit implementation.
