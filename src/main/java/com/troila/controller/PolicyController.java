package com.troila.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.troila.constant.ErrorInfo;
import com.troila.exception.HandlerException;
import com.troila.model.Policy;
import com.troila.model.PolicyDataResult;
import com.troila.model.PolicyExternal;
import com.troila.scheduled.UpdatePolicyDateSchedule;
import com.troila.service.PolicyDataService;

@Controller
@RequestMapping("/policy")
public class PolicyController {
	
	@Autowired
	private PolicyDataService policyDataService;
	
	/**
	 * 获取指定条目的外部原始政策记录
	 * @param limit
	 * @param offset
	 * @return
	 */
	@RequestMapping(value="/origin", method=RequestMethod.GET)
	public ResponseEntity<PolicyDataResult> getExternalData(@RequestParam("limit") int limit,@RequestParam("offset") int offset) {
		PolicyDataResult res = policyDataService.getOriginExternalData(limit, offset);		
		if(res == null) {
			System.out.println("尝试抛出异常，请跟踪处理过程……");
			throw new HandlerException(ErrorInfo.NOTFOUND);
		}
		return ResponseEntity.ok(res);			
	}
	
	/**
	 * 获取指定条目的政策记录，返回简化解析后的内容
	 * @param limit
	 * @param offset
	 * @return
	 */
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<List<PolicyExternal>> getExternalDataByPage(@RequestParam("limit") int limit,@RequestParam("offset") int offset) {
		if(limit <= 0) {
			limit = 15;
		}
		if(offset <= 0 ) {
			offset = 0;
		}
		List<PolicyExternal> list = policyDataService.getPolicyDataByPage(limit, offset);
		if(null == list) {
			return null;			
		}
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public ResponseEntity<Integer> getExternalDataCount() {
//		policyDataService.getPolicyDataCount();		
		return ResponseEntity.ok(policyDataService.getPolicyDataCount());
	}
	
	@RequestMapping(value="/checknew", method=RequestMethod.GET)
	public ResponseEntity<List<Policy>> checkUpdatePolicy(){		
		List<Policy> updateList = UpdatePolicyDateSchedule.updateList;
		if(updateList.isEmpty()) {
			//可能定时任务还没有启动的时候进行查询，需要自己手动查询一遍
			updateList = policyDataService.checkNewPolicyData();
		}
		return ResponseEntity.ok(updateList);
	}
	
	/**
	 * 保存指定条目的外部政策记录
	 * @param limit
	 * @param offset
	 * @return
	 */
	@RequestMapping(value="/external/save", method=RequestMethod.POST)
	public ResponseEntity<List<PolicyExternal>> saveExternalData(@RequestParam("limit") int limit,@RequestParam("offset") int offset) {				
		List<PolicyExternal> list = policyDataService.getPolicyDataByPage(limit,offset);		
		return ResponseEntity.ok(policyDataService.saveBatchPolicyExternalData(list));
	}
	
	@RequestMapping(value="/inner/save", method=RequestMethod.POST)
	public ResponseEntity<List<Policy>> saveInnerData(@RequestParam("limit") int limit,@RequestParam("offset") int offset) {				
		List<PolicyExternal> list = policyDataService.getPolicyDataByPage(limit,offset);		
		return ResponseEntity.ok(policyDataService.saveBatchPolicyInnerData(list));
	}
	
	/**
	 * 将相关记录更新为已读状态
	 * @return
	 */
	@RequestMapping(value="/inner/update", method=RequestMethod.POST)
	public ResponseEntity<List<Policy>> updateReadFlag(@RequestBody List<String> ids){
		return ResponseEntity.ok(policyDataService.updateReadFlagBatch(ids, 1));
	}

}
