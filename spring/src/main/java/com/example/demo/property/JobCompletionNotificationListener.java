package com.example.demo.property;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 *  Jobの実行結果を表示
 *  JobExecutionListenerクラスでジョブの前後イベントを設定する
 */
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final PropertyRepository repository;
  private Date startDate;

  public JobCompletionNotificationListener(PropertyRepository repository) {
    this.repository = repository;
    this.startDate = new Date();
    log.info("!!! JOB start! {{}}", this.startDate.toString());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    // Jobが成功したら
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      Date now = new Date();
      log.info("!!! JOB FINISHED! {{}}", now.toString());
      Long diff_time = this.startDate.getTime() - now.getTime();
      log.info("かかった時間: {{}}", new Date(diff_time));

    //   実行結果
      repository.findAll().forEach(property -> log.info("Found <{{}}> in the database.", property));
    }
  }
}