package com.tw.go.plugin.git;

import com.tw.go.plugin.AbstractGitHelperTest;
import com.tw.go.plugin.GitHelper;
import com.tw.go.plugin.HelperFactory;
import com.tw.go.plugin.Pair;
import com.tw.go.plugin.cmd.InMemoryConsumer;
import com.tw.go.plugin.cmd.ProcessOutputStreamConsumer;
import com.tw.go.plugin.model.GitConfig;
import com.tw.go.plugin.model.Revision;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GitCmdHelperTest extends AbstractGitHelperTest {
    @Override
    protected GitHelper getHelper(GitConfig gitConfig, File workingDir) {
        return HelperFactory.gitCmd(
                gitConfig,
                workingDir,
                new ProcessOutputStreamConsumer(new InMemoryConsumer() {
                    @Override
                    public void consumeLine(String line) {
                        System.out.println(line);
                    }
                }),
                new ProcessOutputStreamConsumer(new InMemoryConsumer() {
                    @Override
                    public void consumeLine(String line) {
                       System.err.println(line);
                    }
                }));
    }

    @Test
    public void shouldShallowClone() throws Exception {
        extractToTmp("/sample-repository/simple-git-repository-2.zip");
        GitConfig config = new GitConfig("file://" + simpleGitRepository.getAbsolutePath());
        config.setShallowClone(true);
        GitHelper git = getHelper(config, testRepository);

        git.cloneOrFetch();

        assertThat(git.getCommitCount(), is(1));

        Revision revision = git.getLatestRevision();
        verifyRevision(revision, "24ce45d1a1427b643ae859777417bbc9f0d7cec8", "3\ntest multiline\ncomment", 1422189618000L, List.of(new Pair("a.txt", "added"), new Pair("b.txt", "added")));

        // poll again
        git.cloneOrFetch();

        List<Revision> newerRevisions = git.getRevisionsSince("24ce45d1a1427b643ae859777417bbc9f0d7cec8");

        assertThat(newerRevisions.isEmpty(), is(true));
    }

    @Test
    public void shouldCloneWithNoCheckout() throws Exception {
        extractToTmp("/sample-repository/simple-git-repository-2.zip");

        GitConfig config = new GitConfig("file://" + simpleGitRepository.getAbsolutePath());
        config.setNoCheckout(true);
        GitHelper git = getHelper(config, testRepository);

        git.cloneOrFetch();
        assertThat(List.of(testRepository.list()), contains(".git"));

        assertThat(git.getCommitCount(), is(3));

        Revision revision = git.getLatestRevision();
        verifyRevision(revision, "24ce45d1a1427b643ae859777417bbc9f0d7cec8", "3\ntest multiline\ncomment", 1422189618000L, List.of(new Pair("a.txt", "modified"), new Pair("b.txt", "added")));

        // poll again
        git.cloneOrFetch();
        assertThat(List.of(testRepository.list()), contains(".git"));

        List<Revision> newerRevisions = git.getRevisionsSince("24ce45d1a1427b643ae859777417bbc9f0d7cec8");

        assertThat(newerRevisions.isEmpty(), is(true));
    }
}
