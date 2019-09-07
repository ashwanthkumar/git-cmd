package com.tw.go.plugin.model;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class RevisionTest {
    private Revision revision;
    private Date date;

    @Before
    public void setUp() {
        date = DateTime.now().toDate();
        revision = new Revision("revision", date, "comments", "user", "email", null);
    }

    @Test
    public void createModifiedFile() {
        revision.createModifiedFile("fileName", "added");

        assertThat(revision.getModifiedFiles(), hasSize(1));
        assertThat(revision.getModifiedFiles(), contains(new ModifiedFile("fileName", "added")));
    }

    @Test
    public void getRevision() {
        assertThat(revision.getRevision(), is(equalTo("revision")));
    }

    @Test
    public void getTimestamp() {
        assertThat(revision.getTimestamp(), is(date));
    }

    @Test
    public void getComment() {
        assertThat(revision.getComment(), is(equalTo("comments")));
    }

    @Test
    public void getUser() {
        assertThat(revision.getUser(), is(equalTo("user")));
    }

    @Test
    public void getEmailId() {
        assertThat(revision.getEmailId(), is(equalTo("email")));
    }
}