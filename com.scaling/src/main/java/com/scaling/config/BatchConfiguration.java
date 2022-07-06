package com.scaling.config;

import com.scaling.model.Message;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

/**
 * The Batch configuration.
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {
    private StepBuilderFactory stepBuilderFactory;

    private JobBuilderFactory jobBuilderFactory;

    private DataSource dataSource;

    public BatchConfiguration(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, DataSource dataSource) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.dataSource = dataSource;
    }

    /**
     * Reading flat files for reading and parsing flat files.
     *
     * @return the flat file item reader
     */
    @Bean
    FlatFileItemReader<Message> itemReader() {

        return new FlatFileItemReaderBuilder<Message>()
                .name("messageItemReader")
                .resource(new ClassPathResource("message-data.csv"))
                .delimited()
                .delimiter(";")
                .names(new String[]{"username", "timeOfTheMessage", "valueMessage"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Message>() {{
                    setTargetType(Message.class);
                }})
                .build();
    }

    /**
     * ItemWriter that uses the batching features from NamedParameterJdbcTemplate to execute a batch of statements for all items provided.
     *
     * @return the jdbc batch item writer
     */
    @Bean
    JdbcBatchItemWriter<Message> itemWriter() {
        return new JdbcBatchItemWriterBuilder<Message>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO MESSAGE (username, time_Of_the_message, value_message) VALUES (:username, :timeOfTheMessage, :valueMessage)")
                .build();
    }

    /**
     * The method defines the job, and the second one defines a single step.
     * Jobs are built from steps, where each step can involve a reader, a processor, and a writer.
     *
     * @return the step
     */
    @Bean
    Step step() {
        return stepBuilderFactory.get("step")
                .<Message, Message>chunk(10)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    /**
     * The starter job.
     *
     * @return the job
     */
    @Bean
    Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }
}
