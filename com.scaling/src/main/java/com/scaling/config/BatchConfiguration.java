package com.scaling.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {
    private StepBuilderFactory stepBuilderFactory;

    private JobBuilderFactory jobBuilderFactory;

    private DataSource dataSource;

    public BatchConfig(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, DataSource dataSource) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    FlatFileItemReader<User> itemReader() {

        return new FlatFileItemReaderBuilder<User>()
                .name("userItemReader")
                .resource(new ClassPathResource("test-data.csv"))
                .delimited()
                .delimiter(";")
                .names("name", "surname", "address")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<User>(){{
                    setTargetType(User.class);
                }})
                .build();
    }

    @Bean
    JdbcBatchItemWriter<User> itemWriter() {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO USER (name, surname, address) VALUES (:name, :surname, :address)")
                .build();
    }

    @Bean
    Step step() {
        return stepBuilderFactory.get("step")
                .<User, User>chunk(10)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }
}
