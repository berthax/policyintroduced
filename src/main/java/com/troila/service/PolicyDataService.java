package com.troila.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.troila.config.PolicySettings;
import com.troila.model.Policy;
import com.troila.model.PolicyDataResult;
import com.troila.model.PolicyExternal;
import com.troila.repository.PolicyExternalRepository;
import com.troila.repository.PolicyRepository;
import com.troila.util.MD5Util;
import com.troila.util.TimeUtils;

@Service
public class PolicyDataService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PolicySettings policySettings;
	
	@Autowired
	private PolicyRepository policyRepository;
	
	@Autowired
	private PolicyExternalRepository policyExternalRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Map<String,Policy> map = new HashMap<>();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 检查是否有新的政策，如果有，将新的政策信息返回
	 * @return
	 */
	public List<Policy> checkNewPolicyData() {
		try {
			logger.info("开始检测政策的更新内容……");
			//先将原来未读的政策纳入其中
			List<Policy> updateList = policyRepository.findByReadFlag(0);    
			// 首先获取当前政策一点通网站的政策信息的总数
			int total = getPolicyDataCount();
			if (total == -1) {
				return null;
			}
			// 根据政策总数获取所有的具体的记录
			List<PolicyExternal> listExternal = getPolicyDataByPage(total, 0);
			// 查询本地数据库中存储的政策记录
			List<Policy> listInner = policyRepository.findAll();
			// 根据本地数据库政策记录初始化map，用于之后的数据更新匹配
			map.clear();   //将map的记录清空，以免影响比较
			for (Policy policy : listInner) {
				map.put(policy.getId(), policy);
			}
						
			List<Policy> addList = new ArrayList<>();
			
			// 将新抓取的数据与数据库中的原有记录进行匹配
			for (PolicyExternal px : listExternal) {
				String pxId = MD5Util.getMD5(px.toString());
				if (!map.isEmpty() && map.containsKey(pxId)) {
					// 说明原来该记录就存在了，不是新增的,其实此处不必全部比较，因为新增的记录都是排在最前边的，比较到原来存在的记录之后，后面的就都是老的记录了，循环到此处停止即可
					// 但是由于取得数据取决于政策一点通网站，如果返回顺序有变化，可能影响比较结果，所以全部比较是最保险的，但是效率低一些
					continue;
//				break;
				}
				// 将最近新更新的数据存储起来
				updateList.add(convertPolicyExternal(px));
				addList.add(convertPolicyExternal(px));
			}
			// 将新更新的数据保存本地数据库
			policyRepository.saveAll(addList);   //将本次新增的内容保存进本地数据库
			//说明是第一次初始化数据，原来的本地库中是没有记录的，将所有数据都返回不合适，返回最近一个月的记录
			if(updateList.size() == total) {
				updateList.clear();
				updateList = queryLastMonthPolicyData();  
			}
			logger.info("检测政策的更新内容结束，成功返回结果，当次检测到更新以及未读的政策数为：{}",updateList.size());
			return updateList;   //包括原来未读的政策以及新增的政策放在一起，算为本地查询的新政策提醒，返回数据
		} catch (Exception e) {
			logger.error("查询政策数据更新的过程中发生错误，查询失败，请稍候再尝试",e);
			return null;
		}
	}
		
	/**
	 * 查询当前的政策记录总数
	 * @return -1说明请求过程出错了
	 */
	public int getPolicyDataCount() {
		String uri = policySettings.getServerUrl()+"limit=-1&offset=0";
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8); 
		HttpEntity<String> entity = new HttpEntity<String>(headers); 
		try {
			String res = restTemplate.exchange(uri, HttpMethod.GET, entity,String.class).getBody(); 
			PolicyDataResult result = mapper.readValue(res, PolicyDataResult.class);
			if(!"success".equals(result.getStatus())){
				return -1;
			}
			return result.getTotal();
		} catch (Exception e) {
			logger.error("查询当前的政策记录总数出错，异常信息如下：",e);
			return -1;
		}
	}
	
	/**
	 * 获取指定条目的政策搜索记录
	 * @return
	 */
	public List<PolicyExternal> getPolicyDataByPage(Integer limit,Integer offset) {
		if(limit == null || offset == null) {
			return null;
		}
		String uri = policySettings.getServerUrl()+"limit="+limit+"&offset="+offset;
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8); 
		HttpEntity<String> entity = new HttpEntity<String>(headers); 
		try {
			String res = restTemplate.exchange(uri, HttpMethod.GET, entity,String.class).getBody(); 
			PolicyDataResult result = mapper.readValue(res, PolicyDataResult.class);
			if(!"success".equals(result.getStatus())){
				return null;
			}
			return result.getRows();
		} catch (Exception e) {
			logger.error("获取指定条目记录limit={},offset={}的数据失败",limit,offset,e);
			return null;
		}
	}
	
	/**
	 * 获取最原始的政策记录，不加任何修改转换
	 * @param limit
	 * @param offset
	 * @return
	 */
	public PolicyDataResult getOriginExternalData(Integer limit,Integer offset) {
		String uri = policySettings.getServerUrl()+"limit="+limit+"&offset="+offset;
		System.out.println("查询地址为："+uri);
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8); 
		HttpEntity<String> entity = new HttpEntity<String>(headers); 
		try {
			String res = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class).getBody();
//			JsonNode rootNode = mapper.readTree(res);
//			JsonNode rowsNode = rootNode.path("rows");
//			//转换成数组(注意此处转换成数据的用法，仅记录一下)
//			List<PolicyExternal> list = mapper.readValue(mapper.writeValueAsString(rowsNode), new TypeReference<List<PolicyExternal>>(){ });
			PolicyDataResult result = mapper.readValue(res, PolicyDataResult.class);
			return result;
		} catch (Exception e) {
			logger.error("从网站获取原始指定条目政策记录limit={},offset={}的数据失败",limit,offset,e);
			return null;
		}		
	}
	
	/**
	 * 将外部数据格式转换成内部数据库存储的格式
	 * @param policyExternal
	 * @return
	 */
	public Policy convertPolicyExternal(PolicyExternal policyExternal) {
		Policy policy = new Policy();
		policy.setId(MD5Util.getMD5(policyExternal.toString()));  //使用该对象生成的MD5字符串作为id唯一标识
		policy.setTitle(policyExternal.getFilename());
		policy.setContent(policyExternal.getThecontent());   	  //取回来的文件中没有文件内容项，此项一般为空
		policy.setPublishUrl(policyExternal.getDocpuburl());
		policy.setPublishUnit(policyExternal.getChnldesc());
		policy.setPublishNo(policyExternal.getFileno());
		policy.setPublishDate(policyExternal.getTitledate());
//		policy.setCategory(policyExternal.getThemeclass());        //主题分类
		policy.setCategory(policyExternal.getPolpacclass());
		policy.setSource("天津政策一点通");
		policy.setCreateTime(new Date());
		policy.setUpdateTime(new Date());
		policy.setReadFlag(0);
		return policy;
	}
	
	/**
	 * 将一个外部爬取的数据存储到数据库中
	 * @param policyExternal
	 * @return
	 */
	public Policy savePolicyInnerData(PolicyExternal policyExternal) {
		if(policyExternal == null) {
			return null;
		}
		Policy policy = convertPolicyExternal(policyExternal);
		try {
			Policy res = policyRepository.save(policy);
			return res;
		} catch (Exception e) {
			// 插入过程中出错，插入失败
			logger.error("向数据库插入数据{}发生异常，插入失败",policyExternal.toString(),e);
			return null;
		}
	}
	
	/**
	 * 批量存储解析后的政策数据
	 * @param list
	 * @return
	 */
	public List<Policy> saveBatchPolicyInnerData(List<PolicyExternal> list){
		List<Policy> innerList = new ArrayList<>();
		try {			
				for(PolicyExternal px : list) {
					innerList.add(convertPolicyExternal(px));
				}
				return policyRepository.saveAll(innerList); 
			} catch (Exception e) {
				logger.error("向数据库批量插入数据发生异常，插入失败",e);
				return null;
			}
	}	
	
	/**
	 * 批量存储原始政策数据
	 * @param list
	 * @return
	 */
	public List<PolicyExternal> saveBatchPolicyExternalData(List<PolicyExternal> list) {
		if(list == null) {
			return null;			
		}
		try {
			for(PolicyExternal px : list) {
				px.setId(MD5Util.getMD5(px.toString()));
			}
			List<PolicyExternal> list_res = policyExternalRepository.saveAll(list);
			return list_res;		
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("向数据库批量插入数据发生异常，插入失败",e);
			return null;			
		}
	}	
	
	/**
	 * 查询本地数据库中的所有的政策记录
	 */
	public List<Policy> queryPolicyInnerDataAll(){
		try {
			List<Policy> list = policyRepository.findAll();
			return list;
		} catch (Exception e) {
			logger.error("查询本地数据库中的所有的政策记录发生异常，查询失败",e);
			return null;
		}
		
	}
	
	/**
	 * 查询至今为止一个月之内的政策记录（一个月之前的，不再认为是新近的记录）
	 * @return
	 */
	public List<Policy> queryLastMonthPolicyData(){
        return policyRepository.findByPublishDateBetween(TimeUtils.getLastMonth(),new Date());
	}	
	
	/**
	 * 查询指定阅读状态的记录
	 * @param readFlag 1是已读的，0是未读的
	 * @return
	 */
	public List<Policy> queryByReadFlag(int readFlag){
		try {			
			return policyRepository.findByReadFlag(readFlag);
		} catch (Exception e) {
			logger.error("查询阅读状态为{}的记录发生异常，查询失败",readFlag,e);
			return null;
		}
	}
	
	/**
	 * 批量更新阅读状态
	 * @param ids
	 * @param readFlag 1是已读的 0是未读的
	 * @return
	 */
	public List<Policy> updateReadFlagBatch(List<String> ids, int readFlag){
		List<Policy> list = policyRepository.findAllById(ids);
		try {
			for(Policy policy : list) {
				policy.setReadFlag(readFlag);
				policy.setUpdateTime(new Date());
			}
			List<Policy> res = policyRepository.saveAll(list);
			return res;
		} catch (Exception e) {
			logger.error("批量修改阅读状态发生异常，修改失败",e);
			return null;
		}
		
	}
}
