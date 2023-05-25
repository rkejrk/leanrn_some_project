package com.example.demo.property;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * バッチをまとめる
 */

@Configuration
class BatchConfiguration {

    /*
     * csvファイルからモデルリストに変更する
     * https://spring.pleiades.io/spring-batch/docs/current/reference/html/appendix.html#listOfReadersAndWriters
     */
    @Bean
    public FlatFileItemReader<Property> reader() {
        return new FlatFileItemReaderBuilder<Property>()
                .name("propertyItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[] { "name", "image", "address" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Property>() {
                    {
                        setTargetType(Property.class);
                    }
                })
                .build();
    }

    /**
     * プロセスのinstanceを生成
     * 
     * @return
     */
    @Bean
    public PropertyItemProcessor processor() {
        return new PropertyItemProcessor();
    }

    /**
     * JDBCによって作成された dataSource のコピーを自動的に取得します
     * 
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<Property> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Property>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO property (name, image, address) VALUES (:name, :image, :address)")
                .dataSource(dataSource)
                .build();
    }
    

    /**
     * step1を定義
     * processor->writer
     * @param jobRepository
     * @param transactionManager
     * @param writer
     * @return
     */
    @Bean
    public Step step1(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Property> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Property, Property>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    /**
     * step2を定義
     * reader->processor->writer
     * @param jobRepository
     * @param transactionManager
     * @param writer
     * @return
     */
    @Bean
    public Step step2(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Property> writer) {
        return new StepBuilder("step2", jobRepository)
                .<Property, Property>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    /**
     * ジョブを定義
     * @param jobRepository
     * @param listener
     * @param step1
     * @return
     */
    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())//初期化
                .listener(listener)//リスナー紐づけ
                .flow(step1)//データ
                .end()
                .build();
    }
    /**
     * ジョブを定義
     * @param jobRepository
     * @param listener
     * @param step1
     * @return
     */
    // @Bean
    // public Job importUser2Job(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step2, Step step1) {
    //     return new JobBuilder("importUser2Job", jobRepository)
    //             .incrementer(new RunIdIncrementer())//初期化
    //             .listener(listener)//リスナー紐づけ
    //             .flow(step1)//データ
    //             .next(step2)
    //             .end()
    //             .build();
    // }
}