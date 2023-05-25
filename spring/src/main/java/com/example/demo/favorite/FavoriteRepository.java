package com.example.demo.favorite;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * DBの操作方法
 * 
 * @author tanaka
 *
 */
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

	/**
	 * ユーザーが持つお気に入りのリスト
	 * @param uid
	 * @return
	 */
	List<Favorite> findByUid(Long uid);

	/**
	 * お気に入りが存在するかどうかを返却
	 * @param uid
	 * @param pid
	 * @return trueの場合、存在する
	 */
	Boolean existsByUidAndPid(Long uid, Long pid);

	/**
	 * お気に入り情報のIDを参照
	 * @param uid
	 * @param pid
	 * @return
	 */
	List<Favorite> findByUidAndPid(Long uid, Long pid);

}