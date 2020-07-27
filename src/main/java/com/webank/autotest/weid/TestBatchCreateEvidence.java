package com.webank.autotest.weid;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PublicKeyProperty;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdDocument;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.service.impl.WeIdServiceImpl;
import com.webank.weid.service.impl.engine.EngineFactory;
import com.webank.weid.service.impl.engine.EvidenceServiceEngine;
import com.webank.weid.util.DataToolUtils;
import com.webank.weid.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBatchCreateEvidence {
	
	//private static String privateKey = "44079467096819052619157081111953056407816175586927914108453291030289569100785";
	private static final Logger logger = LoggerFactory.getLogger(TestBatchCreateEvidence.class);
	static WeIdService weidService = new WeIdServiceImpl();
	
	public static CredentialPojo createCredentialPojo(String _weId, String _pubKey, String _priKey) throws Exception {
		CredentialPojo credentialPojo = null;
		
		//String weId = "did:weid:1:0xcfe402eb29116a097b33dbdd978ec25294e5ca69";
		//privateKey = "65633145787303354095171215619460065782996855876064633257488747530161752926483";
        int cptId = 1002;
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = "2037-01-01 00:00:00";
        //String claimJsonStr = "{\"name\":\"wangyang\",\"gender\":\"M\",\"age\":30}";
        //String weIdPublicKeyId = "12724731741878682049605381013322611177408164140452286500590822995594588484551867837603421170286481280546631991121163003493592188178003400469882756379459361";

        Long timeStamp = DateUtils.converDateToTimeStamp(expirationDate);
        Map<String, Object> claimMap = new HashMap();
        claimMap.put("name", "wangyang");
        claimMap.put("gender", "M");
        claimMap.put("age", 30);

        
        CreateCredentialPojoArgs<Map<String, Object>> createCredentialPojoArgs = new CreateCredentialPojoArgs<Map<String, Object>>();
        createCredentialPojoArgs.setCptId(cptId);
        createCredentialPojoArgs.setIssuer(_weId);
        createCredentialPojoArgs.setExpirationDate(timeStamp);

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(_weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(_priKey);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        weIdAuthentication.setWeIdPublicKeyId(_pubKey);
        createCredentialPojoArgs.setWeIdAuthentication(weIdAuthentication);

        createCredentialPojoArgs.setClaim(claimMap);
        
        CredentialPojoService credentialPojoService = new CredentialPojoServiceImpl();
        ResponseData<CredentialPojo> credentialPojoResponse = credentialPojoService.createCredential(createCredentialPojoArgs);
        //System.out.println("#####credentialPojoResponse:" + credentialPojoResponse);
        
        credentialPojo = credentialPojoResponse.getResult();
		
		return credentialPojo;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int batchSize = 1000;
		
		if (args != null && args.length > 0) {
			batchSize = Integer.valueOf(args[0]);
		}

		CreateWeIdDataResult createWeIdResult = null;
		String weId = null;
		String priKey = null;
		String pubKey = null;
		
		List<String> hashValues = null;
		List<String> signatures = null;
		List<Long> timestamps = null;
		List<String> signers = null;
		List<String> logs = null;
		List<String> customKeys = null;
		
		//int batchSize = 1000;
		//int loopSize = 1000000000;
		while(true) {
			
			hashValues = new ArrayList<>();
	        signatures = new ArrayList<>();
	        timestamps = new ArrayList<>();
	        signers = new ArrayList<>();
	        logs = new ArrayList<>();
	        customKeys = new ArrayList<>();
			
			// 创建weid
		    createWeIdResult = weidService.createWeId().getResult();
		    System.out.println("createWeIdResult : " + createWeIdResult.toString());
		    weId = createWeIdResult.getWeId();
		    priKey = createWeIdResult.getUserWeIdPrivateKey().getPrivateKey();
		    pubKey = createWeIdResult.getUserWeIdPublicKey().getPublicKey();
		    
	        for (int i = 0; i < batchSize; i++) {
	            CredentialPojo credential = createCredentialPojo(weId, pubKey, priKey);
	            credential.setId(UUID.randomUUID().toString());
	            String hash = credential.getHash();
	            hashValues.add(credential.getHash());
	            signatures.add(new String(DataToolUtils.base64Encode(DataToolUtils
	                .simpleSignatureSerialization(DataToolUtils.signMessage(hash, priKey))),
	                StandardCharsets.UTF_8));
	            timestamps.add(System.currentTimeMillis());
	            signers.add(DataToolUtils.convertPrivateKeyToDefaultWeId(priKey));
	            logs.add("test log" + i);
	            if (i % 2 == 1) {
	                customKeys.add(String.valueOf(System.currentTimeMillis()));
	            } else {
	                customKeys.add(StringUtils.EMPTY);
	            }
	        }
	        //WeIdService weIdService = new WeIdServiceImpl();
	        EvidenceServiceEngine engine = EngineFactory.createEvidenceServiceEngine(1);
	        
	        // raw creation
	        Long start = System.currentTimeMillis();
	        ResponseData<List<Boolean>> resp = engine
	            .batchCreateEvidence(hashValues, signatures, logs, timestamps, signers, priKey);
	        Long end = System.currentTimeMillis();
	        System.out.println("#####Batch creation w/ size: " + batchSize + " takes time (ms): " + (String
	            .valueOf(end - start)));
	        List<Boolean> booleans = resp.getResult();
	        System.out.println("#####booleans.size():"+booleans.size()+"  hashValues.size():"+hashValues.size());
	        Boolean result = true;
	        for (int i = 0; i < booleans.size(); i++) {
	            result &= booleans.get(i);
	        }
	        System.out.println("#####batchCreateEvidence result:"+result);
	        
	        start = System.currentTimeMillis();
	        resp = engine
	            .batchCreateEvidenceWithCustomKey(hashValues, signatures, logs, timestamps, signers,
	                customKeys, priKey);
	        end = System.currentTimeMillis();
	        System.out.println(
	            "Batch creation w/ custom keys and size: " + batchSize + " takes time (ms): " + (String
	                .valueOf(end - start)));
	        booleans = resp.getResult();
	        System.out.println("#####booleans.size():"+booleans.size()+"  hashValues.size():"+hashValues.size());
	        result = true;
	        for (int i = 0; i < booleans.size(); i++) {
	            result &= booleans.get(i);
	        }
	        System.out.println("#####batchCreateEvidenceWithCustomKey result:"+result);
	
		}
	}

}
