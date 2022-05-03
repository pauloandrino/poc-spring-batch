package com.example.plan.reader;

import com.example.plan.domain.Student;
import com.example.plan.domain.Teacher;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileTeacherReaderConfig implements ItemReader<Teacher> {

    @Value("${batch.studentsEnrollmentsJobStep.chunksize}")
    private int chunkSize;

    private List<Teacher> teachersFromExcel = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private int teacherIndex = 0;

    @Override
    public Teacher read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Teacher teacher;

        if (teacherIndex < teachers.size()) {
            teacher = teachers.get(teacherIndex);
        } else {
            teacher = null;
        }
        teacherIndex++;

        return teacher;
    }

    @BeforeStep
    void beforeStep(StepExecution stepExecution) throws FileNotFoundException {
        String fileName = stepExecution.getJobParameters().getString("fileName");

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerCount(0)
                .build();

        File file = new File("files/" + fileName);
        this.teachersFromExcel = Poiji.fromExcel(new FileInputStream(file), PoijiExcelType.XLSX, Teacher.class, options);
    }


    @BeforeChunk
    private void before(ChunkContext context) {
        teachers.addAll(getTeachers());
    }

    private List<Teacher> getTeachers() {
        return teachersFromExcel.stream().skip(teacherIndex).limit(chunkSize).collect(Collectors.toList());
    }
}
