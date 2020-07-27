package com.webank.autotest.weid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.webank.weid.constant.JsonSchemaConstant;
import com.webank.weid.constant.ProcessingMode;
import com.webank.weid.protocol.base.CptBaseInfo;
import com.webank.weid.protocol.base.Credential;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.CredentialPojoList;
import com.webank.weid.protocol.base.PublicKeyProperty;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdDocument;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.CptMapArgs;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CptService;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.EvidenceService;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.CptServiceImpl;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.service.impl.EvidenceServiceImpl;
import com.webank.weid.service.impl.WeIdServiceImpl;
import com.webank.weid.suite.api.transportation.TransportationFactory;
import com.webank.weid.suite.api.transportation.params.EncodeType;
import com.webank.weid.suite.api.transportation.params.ProtocolProperty;
import com.webank.weid.suite.api.transportation.params.TransMode;
import com.webank.weid.suite.api.transportation.params.TransportationType;

public class ThreadForCreateEvidence2 implements Callable<ResponseData<String>> {	
	
	private EvidenceService evidenceService;
	private CredentialPojo credential;
	private WeIdPrivateKey weIdPrivateKey;
	
	public ResponseData<String> responseCreateEvidence;

	public ResponseData<String> getResponseCreateEvidence() {
		return responseCreateEvidence;
	}

	public void setResponseCreateEvidence(ResponseData<String> responseCreateEvidence) {
		this.responseCreateEvidence = responseCreateEvidence;
	}

	public ThreadForCreateEvidence2(EvidenceService evidenceService, CredentialPojo credential, WeIdPrivateKey weIdPrivateKey) {
		this.evidenceService = evidenceService;
		this.credential = credential;
		this.weIdPrivateKey = weIdPrivateKey;
	}

	@Override
	public ResponseData<String> call() throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("#####################Begin线程"+Thread.currentThread().getName());
		
		//responseCreateEvidence = evidenceService.createEvidence(credential, weIdPrivateKey);
		
		responseCreateEvidence = evidenceService.createEvidenceWithLogAndCustomKey(credential, weIdPrivateKey, "test log"+System.currentTimeMillis(), String.valueOf(System.currentTimeMillis()));
		//System.out.println("#####createEvidence:"+responseCreateEvidence);
        //System.out.println("#####################End线程"+Thread.currentThread().getName());
        
        return responseCreateEvidence;
	}

}
