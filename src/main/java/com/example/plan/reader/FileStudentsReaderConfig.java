package com.example.plan.reader;

import com.example.plan.domain.Student;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
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
public class FileStudentsReaderConfig implements ItemReader<Student> {

    @Value("${batch.studentsEnrollmentsJobStep.chunksize}")
    private int chunkSize;

    private List<Student> studentsFromFile = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private int studentIndex = 0;

    @Override
    public Student read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Student student;

        if (studentIndex < students.size()) {
            student = students.get(studentIndex);
        } else {
            student = null;
        }
        studentIndex++;

        return student;
    }

    @BeforeStep
    void beforeStep(StepExecution stepExecution) throws FileNotFoundException {
        String fileName = stepExecution.getJobParameters().getString("fileName");

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerCount(0)
                .build();

        File file = new File("files/" + fileName);
        this.studentsFromFile = Poiji.fromExcel(new FileInputStream(file), PoijiExcelType.XLSX, Student.class, options);
    }


    @BeforeChunk
    private void before(ChunkContext context) {
        students.addAll(getStudents());
    }

    private List<Student> getStudents() {
        return studentsFromFile.stream().skip(studentIndex).limit(chunkSize).collect(Collectors.toList());
    }
}
