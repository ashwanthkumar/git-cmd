package com.tw.go.plugin.jgit;

import com.tw.go.plugin.AbstractGitHelperTest;
import com.tw.go.plugin.GitHelper;
import com.tw.go.plugin.HelperFactory;
import com.tw.go.plugin.cmd.InMemoryConsumer;
import com.tw.go.plugin.cmd.ProcessOutputStreamConsumer;
import com.tw.go.plugin.model.GitConfig;

import java.io.File;

public class JGitHelperTest extends AbstractGitHelperTest {
    @Override
    protected GitHelper getHelper(GitConfig gitConfig, File workingDir) {
        return HelperFactory.jGit(gitConfig, workingDir,
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
}
