package com.example.plan.domain;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("teacher")
public class Teacher {

    @ExcelCell(0)
    private String name;
    @ExcelCell(1)
    private String subject;
    @ExcelCell(2)
    private String rate;
}
