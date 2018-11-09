package com.troila.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.troila.model.Policy;

@Component
public interface PolicyRepository extends JpaRepository<Policy, String> {

	Optional<Policy> findById(String id);

	/**
	 * 查询标题是title的记录
	 * 
	 * @param title
	 * @return
	 */
	List<Policy> findByTitle(String title);

	/**
	 * 查询标题不是title的记录
	 * 
	 * @param tile
	 * @return
	 */
	List<Policy> findByTitleNot(String tile);
	
	/**
	 * 模糊匹配，查询内容中包含关键字的记录
	 * @param contentKeywords
	 * @return
	 */
	List<Policy> findByContentContaining(String contentKeywords);
	
	/**
	 * 查询指定发布时间内的政策记录
	 * @param min
	 * @param max
	 * @return
	 */
	List<Policy> findByPublishDateBetween(Date min, Date max);
	/**
	 * 查询指定阅读状态的记录
	 * @param readFlag
	 * @return
	 */
	List<Policy> findByReadFlag(int readFlag);
}
