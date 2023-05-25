package com.example.demo.favorite;

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
class FavoriteController {

	private final FavoriteRepository repository;

	FavoriteController(FavoriteRepository repository) {
		this.repository = repository;
	}

	/**
	 * お気に入り情報の初期表示用
	 * @return
	 */
	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/favorite/{uid}")
	List<Favorite> all(@PathVariable Long uid) {
		return (List<Favorite>) repository.findByUid(uid);
	}

	/**
	 * お気に入りフラグを返却
	 * 
	 * @param uid
	 * @param pid
	 * @return
	 */
	@GetMapping("/favorite/{uid}/{pid}")
	Boolean getUser(@PathVariable Long uid, @PathVariable Long pid) {

		if(repository.existsByUidAndPid(uid, pid)) {
			Favorite fav = repository.findByUidAndPid(uid, pid).get(0);
			repository.deleteById(fav.getId());
			return false;
		} else {
		    repository.save(new Favorite(uid, pid));
		    return true;
			
		}
	}

	
	/**
	 * NotFoundエラー
	 * @author tanaka
	 *
	 */
	class FavoriteNotFoundException extends RuntimeException {

		FavoriteNotFoundException(Long id) {
			super("Could not find favorite " + id);
		}
	}

	/**
	 * エラー時のハンドリング
	 * @author tanaka
	 *
	 */
	@ControllerAdvice
	class FavoriteNotFoundAdvice {

		@ResponseBody
		@ExceptionHandler(FavoriteNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String favoriteNotFoundHandler(FavoriteNotFoundException ex) {
			return ex.getMessage();
		}
	}

}
