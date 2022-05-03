package com.example.plan.domain;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("studant")
public class Student {

    @ExcelCell(0)
    private String name;
    @ExcelCell(1)
    private String email;
    @ExcelCell(2)
    private String age;
    @ExcelCell(3)
    private String salary;
/*

    @ExcelCellName("Nome")
    private String name;
    @ExcelCellName("Email")
    private String email;
    @ExcelCellName("Idade")
    private String age;
    @ExcelCellName("Salario")
    private String salary;
    */
}
