package com.tw.go.plugin.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GitConfigTest {
    @Test
    public void shouldGetEffectiveBranch() {
        assertThat(new GitConfig("url", null, null, null).getEffectiveBranch(), is("master"));
        assertThat(new GitConfig("url", null, null, "branch").getEffectiveBranch(), is("branch"));
    }

    @Test
    public void isRemoteUrlShouldBeTrueForHttp() {
        GitConfig gitConfig = new GitConfig("http://url.test", "user", "password", "master");

        assertThat(gitConfig.isRemoteUrl(), is(Boolean.TRUE));
    }

    @Test
    public void isRemoteUrlShouldBeTrueForHttps() {
        GitConfig gitConfig = new GitConfig("https://url.test", "user", "password", "master");

        assertThat(gitConfig.isRemoteUrl(), is(Boolean.TRUE));
    }

    @Test
    public void isRemoteUrlShouldBeTrueForGitUrl() {
        GitConfig gitConfig = new GitConfig("git@github.com:test/sample.git", "user", "password", "master");

        assertThat(gitConfig.isRemoteUrl(), is(Boolean.FALSE));
    }

    @Test
    public void hasCredentialsShouldBeTrueIfUrlAndPasswordAreProvided() {
        GitConfig gitConfig = new GitConfig("https://url.test", "user", "password", "master");

        assertThat(gitConfig.hasCredentials(), is(Boolean.TRUE));
    }

    @Test
    public void hasCredentialsShouldBeFalseIfUrlIsEmptyWithValidPassword() {
        GitConfig gitConfig = new GitConfig("", "user", "password", "master");

        assertThat(gitConfig.hasCredentials(), is(Boolean.FALSE));
    }

    @Test
    public void hasCredentialsShouldBeFalseWithValidUrlAndEmptyPassword() {
        GitConfig gitConfig = new GitConfig("http://url.test", "user", "", "master");

        assertThat(gitConfig.hasCredentials(), is(Boolean.FALSE));
    }

    @Test
    public void shouldGetEffectiveUrl() {
        assertThat(new GitConfig("/tmp/git-repo", null, null, null).getEffectiveUrl(), is("/tmp/git-repo"));
        assertThat(new GitConfig("/tmp/git-repo", "username", "password", null).getEffectiveUrl(), is("/tmp/git-repo"));
        assertThat(new GitConfig("http://github.com/gocd/gocd", null, null, null).getEffectiveUrl(), is("http://github.com/gocd/gocd"));
        assertThat(new GitConfig("http://github.com/gocd/gocd", "username", "password", null).getEffectiveUrl(), is("http://username:password@github.com/gocd/gocd"));
        assertThat(new GitConfig("https://github.com/gocd/gocd", "username", "password", null).getEffectiveUrl(), is("https://username:password@github.com/gocd/gocd"));
    }

    @Test
    public void getEffectiveUrlShouldContainUserNameAndPasswordForRemoteUrlWithValidCredential() {
        GitConfig gitConfig = new GitConfig("http://url.test", "user", "password", "master");

        String effectiveUrl = gitConfig.getEffectiveUrl();
        assertThat(effectiveUrl, is("http://user:password@url.test"));
    }

    @Test
    public void getEffectiveUrlShouldNotContainUserNameAndPasswordForNonRemoteUrlWithValidCredential() {
        GitConfig gitConfig = new GitConfig("git@github.com:test/sample.git", "user", "password", "master");

        String effectiveUrl = gitConfig.getEffectiveUrl();
        assertThat(effectiveUrl, is("git@github.com:test/sample.git"));
    }

    @Test
    public void getEffectiveUrlShouldNotContainUserNameAndPasswordForRemoteUrlWithoutCredential() {
        GitConfig gitConfig = new GitConfig("http://url.test", "user", "", "master");

        String effectiveUrl = gitConfig.getEffectiveUrl();
        assertThat(effectiveUrl, is("http://url.test"));
    }

    @Test
    public void getEffectiveBranchShouldReturnTheSpecifiedBranch() {
        GitConfig gitConfig = new GitConfig("http://url.test", "user", "password", "staging");

        String effectiveBranch = gitConfig.getEffectiveBranch();

        assertThat(effectiveBranch, is("staging"));
    }

    @Test
    public void getEffectiveBranchShouldReturnMasterIfBranchisNotSpecified() {
        GitConfig gitConfig = new GitConfig("http://url.test", "user", "password", "");

        String effectiveBranch = gitConfig.getEffectiveBranch();

        assertThat(effectiveBranch, is("master"));
    }

    @Test
    public void shouldBeAbleToGetIsRecursiveSubModuleUpdate() {
        GitConfig gitConfig = new GitConfig("http://url.test", "username", "password", "branch");

        boolean recursiveSubModuleUpdate = gitConfig.isRecursiveSubModuleUpdate();

        assertThat(recursiveSubModuleUpdate, is(Boolean.TRUE));
    }

    @Test
    public void shouldGetUrl() {
        GitConfig gitConfig = new GitConfig("http://url.test", "username", "password", "branch");

        assertThat(gitConfig.getUrl(), is("http://url.test"));
    }

    @Test
    public void shouldGetUsername() {
        GitConfig gitConfig = new GitConfig("http://url.test", "username", "password", "branch");

        assertThat(gitConfig.getUsername(), is("username"));
    }

    @Test
    public void getPassword() {
        GitConfig gitConfig = new GitConfig("http://url.test", "username", "password", "branch");

        assertThat(gitConfig.getPassword(), is("password"));
    }

    @Test
    public void getBranch() {
        GitConfig gitConfig = new GitConfig("http://url.test", "username", "password", "branch");

        assertThat(gitConfig.getBranch(), is("branch"));
    }
}
