package com.example.demo.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Urlをもとに実行される
 * @author tanaka
 *
 */
@RestController
class ServiceUserController {

	private final ServiceUserRepository repository;

	ServiceUserController(ServiceUserRepository repository) {
		this.repository = repository;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/users")
	List<ServiceUser> all() {
		return (List<ServiceUser>) repository.findAll();
	}

	@GetMapping("/users/{phone}")
	ServiceUser getUser(@PathVariable String phone) {

		if(repository.findByPhone(phone).isEmpty()) {
		    repository.save(new ServiceUser(phone));
		}
		return repository.findByPhone(phone).get(0);
		//      .map(serviceUser -> {
		//    	serviceUser.setFirstName(newServiceUser.getFirstName());
		//    	serviceUser.setFirstName(newServiceUser.getFirstName());
		//        return repository.save(serviceUser);
		//      })
		//      .orElseGet(() -> {
		//    	newServiceUser.setId(id);
		//        return repository.save(newServiceUser);
		//      });
	}
	@GetMapping("/users/d_{id}")
	String getUser(@PathVariable Long id) {

		if(!repository.findById(id).isEmpty()) {
		    repository.deleteById(id);
		}
		return "saccess";
	}

	
	/**
	 * NotFoundエラー
	 * @author tanaka
	 *
	 */
	class UserNotFoundException extends RuntimeException {

		UserNotFoundException(Long id) {
			super("Could not find user " + id);
		}
	}

	/**
	 * エラー時のハンドリング
	 * @author tanaka
	 *
	 */
	@ControllerAdvice
	class UserNotFoundAdvice {

		@ResponseBody
		@ExceptionHandler(UserNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String userNotFoundHandler(UserNotFoundException ex) {
			return ex.getMessage();
		}
	}

}
