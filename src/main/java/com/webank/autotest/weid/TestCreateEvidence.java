package com.webank.autotest.weid;

import com.webank.weid.constant.ProcessingMode;
import com.webank.weid.protocol.base.Credential;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.CredentialWrapper;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.CreateCredentialArgs;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.CredentialService;
import com.webank.weid.rpc.EvidenceService;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.service.impl.CredentialServiceImpl;
import com.webank.weid.service.impl.EvidenceServiceImpl;
import com.webank.weid.service.impl.WeIdServiceImpl;
import com.webank.weid.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by v_wbyangwang on 2020/4/14.
 */
public class TestCreateEvidence {
    private static final Logger logger = LoggerFactory.getLogger(TestCreateEvidence.class);
    
    static WeIdService weidService = new WeIdServiceImpl();

    @SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws Exception {
    	
    	// 创建weid
        CreateWeIdDataResult result = weidService.createWeId().getResult();
        String weId = result.getWeId();
        String privateKey = result.getUserWeIdPrivateKey().getPrivateKey();
        int cptId = 1005;
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = "2037-01-01 00:00:00";

        //System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 100
        Long timeStamp = DateUtils.converDateToTimeStamp(expirationDate);
        Map<String, Object> claimMap = new HashMap();
        claimMap.put("name", "wangyang");
        claimMap.put("gender", "M");
        claimMap.put("age", 30);

        CredentialPojoService credentialService = new CredentialPojoServiceImpl();
        CreateCredentialPojoArgs<Map<String, Object>> createCredentialPojoArgs = new CreateCredentialPojoArgs<Map<String, Object>>();
        createCredentialPojoArgs.setClaim(claimMap);
        createCredentialPojoArgs.setCptId(cptId);
        createCredentialPojoArgs.setExpirationDate(timeStamp);
        createCredentialPojoArgs.setIssuer(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        createCredentialPojoArgs.setWeIdAuthentication(new WeIdAuthentication(weId, privateKey, weId));
        EvidenceService evidenceService = new EvidenceServiceImpl(ProcessingMode.IMMEDIATE, 1);
        
        ExecutorService pool = Executors.newFixedThreadPool(20);
        ThreadForCreateEvidence2 threadForCreateEvidence = null;
        Future<ResponseData<String>> future = null;
        ResponseData<CredentialPojo> credentialResponse = null;
        CredentialPojo credential = null;
        
        int batchSize = 100000;
        int errorCount = 0;
        int successCount = 0;
        Long start = null;
        Long end = null;
        ArrayList<Future<ResponseData<String>>> futureList = null;
        
        while(true) {
        	
        	start = System.currentTimeMillis();
        	futureList = new ArrayList<Future<ResponseData<String>>>();
        	successCount = 0;
        	errorCount = 0;
        	
	        for (int i = 1; i <= batchSize; i++) {
	        	credentialResponse = credentialService.createCredential(createCredentialPojoArgs);
	            //System.out.println(credentialResponse);
	            credential = credentialResponse.getResult();
	        	threadForCreateEvidence = new ThreadForCreateEvidence2(evidenceService, credential, weIdPrivateKey);
	        	future = (Future<ResponseData<String>>) pool.submit(threadForCreateEvidence);
	        	futureList.add(future);
	        }
	        
	        for(int j = 0;j < futureList.size();j++) {
		        if(futureList.get(j).get().getErrorCode() == 0) {
		        	successCount++;
		        } else {
		        	errorCount++;
		        }
	        }
	        
	        end = System.currentTimeMillis();
	        System.out.println("###############size: " + batchSize + " takes time (ms): " + (String
	                .valueOf(end - start)) + " successCount:" + successCount+" errorCount:" + errorCount);
	        
	        Thread.sleep(2000);
        }
    }
}
