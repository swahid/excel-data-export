package io.github.swahid.northend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Cell {

    private String content;
    private String textColor;
    private String bgColor;
    private String textSize;
    private String textWeight;

    public Cell(String content) {
        this.content = content;
    }
}
