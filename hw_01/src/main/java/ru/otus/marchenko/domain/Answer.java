package ru.otus.marchenko.domain;

import java.util.Objects;

public class Answer {
    private final String reply;
    private final boolean isRight;

    public Answer(String reply, boolean isRight) {
        this.reply = reply;
        this.isRight = isRight;
    }

    public String getReply() {
        return reply;
    }

    public boolean isRight() {
        return isRight;
    }

    @Override
    public String toString() {
        return reply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer answer)) return false;
        return isRight == answer.isRight && reply.equals(answer.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reply, isRight);
    }
}
