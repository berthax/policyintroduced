package com.troila.scheduled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.troila.model.Policy;
import com.troila.service.PolicyDataService;

@Component
public class UpdatePolicyDateSchedule {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private PolicyDataService policyDataService;
	
	/**
	 * 存储当前情况下，检测到的最新的政策内容，属于线程安全的list
	 */
	public static List<Policy> updateList = Collections.synchronizedList(new ArrayList<>());
	/**
	 * 定期检查政策的更新内容
	 * 如果是第一次检查(即本地没有任何记录)，需要先初始化所有数据，返回当前所能取到的最新的政策内容
	 * 每隔30分钟查看一次
	 */
	@Scheduled(cron="0 0/30 * * * ?")
	public void checkPolicyDataNew() {
		logger.info("进入定期检查政策更新的部分……");
		try {
			updateList = policyDataService.checkNewPolicyData();
			if(updateList == null) {
				logger.error("查询更新的政策内容失败，请稍候尝试");
			}
		} catch (Exception e) {
			logger.error("查询更新的政策内容失败，发生异常",e);
		}
	}
}
