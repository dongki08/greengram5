package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    private int ifeedComment;
    @JsonIgnore
    private int iuser;
    
    @Min(value = 1, message = "ifeed값은 1이상이여야 합니다")
    private int ifeed;

    @Size(min = 3, message = "댓글 내용을 3자리 이상이여야 합니다.")
    @NotEmpty(message = "댓글 내용을 입력하세요.")
    private String comment;
}

