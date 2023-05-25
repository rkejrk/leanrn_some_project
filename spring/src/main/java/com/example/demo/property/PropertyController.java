package com.example.demo.property;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Urlをもとに実行される
 * @author tanaka
 *
 */
@RestController
class PropertyController {

	private final PropertyRepository repository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

	PropertyController(PropertyRepository repository) {
		this.repository = repository;
		repository.save(new Property("物件A", "http://sfjdks.com", "http://bukken/1"));
		repository.save(new Property("物件B", "http://dslkfls.com", "http://bukken/2"));
		repository.save(new Property("物件C", "http://fdklk;s.com", "http://bukken/3"));
		repository.save(new Property("物件D", "http://o i00vv.com", "http://bukken/4"));
		repository.save(new Property("物件E", "http://f,e,ppo-c-.com", "http://bukken/5"));
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/property/init")
	List<Property> all() {
		return (List<Property>) repository.findAll();
	}

	/**
	 * 手動ジョブ実行
	 * いろいろな実行方法
	 * https://spring.pleiades.io/spring-batch/docs/current/reference/html/job.html#runningAJob
	 * @throws Exception
	 */	
    @GetMapping("/property/job")
    public void handle() throws Exception{
        jobLauncher.run(job, new JobParameters());
    }

	// /**
	//  * スケジュールジョブ実行
	//  * @throws Exception
	//  */
    // @Scheduled(fixedDelay = 10000)
    // public void jobRooting() throws Exception{
    //     jobLauncher.run(job, new JobParameters());
    // }

	/**
	 * NotFoundエラー
	 * @author tanaka
	 *
	 */
	class PropertyNotFoundException extends RuntimeException {

		PropertyNotFoundException(Long id) {
			super("Could not find property " + id);
		}
	}

	/**
	 * エラー時のハンドリング
	 * @author tanaka
	 *
	 */
	@ControllerAdvice
	class PropertyNotFoundAdvice {

		@ResponseBody
		@ExceptionHandler(PropertyNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String propertyNotFoundHandler(PropertyNotFoundException ex) {
			return ex.getMessage();
		}
	}

}
