package ru.discrimy.yablog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteStatus {
    private Long postId;
    private Long userId;
    private Status status;

    public enum Status {
        OK,
        ALREADY_DONE,
    }
}
