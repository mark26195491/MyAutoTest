package com.webank.autotest.weid;

import com.webank.util.ConfigUtils;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by v_wbyangwang on 2019/8/15.
 */
public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);
    private static WeIdService weIdService = null;
    static {
        weIdService = new WeIdServiceImpl();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("ok");
        String filePath = "fisco.properties";
        ConfigUtils.loadProperties(filePath);
        String weIdContactAddress = ConfigUtils.getProperty("weId.contractaddress");
        System.out.println(weIdContactAddress);
        ResponseData<CreateWeIdDataResult> createResponse = (ResponseData<CreateWeIdDataResult>)WeIdService.class.getDeclaredMethod("createWeId", new Class[]{}).invoke(weIdService, new Object[]{});

        //ResponseData<CreateWeIdDataResult> createResponse = weIdService.createWeId();
        if (!createResponse.getErrorCode().equals(ErrorCode.SUCCESS.getCode())) {
            logger.error("[CreateWeId] create WeID faild. error code : {}, error msg :{}",
                    createResponse.getErrorCode(), createResponse.getErrorMessage());
            System.out.println("[CreateWeId] create WeID failed.");
            System.exit(1);
        }

        //ResponseData<Boolean> checkWeIDResponse = weIdService.isWeIdExist("did:weid:1:0x67ab9bb19fd6855f754a148d951aeefb0f7c77aa");

        CreateWeIdDataResult weIdData = createResponse.getResult();

        //did:weid:1:0x67ab9bb19fd6855f754a148d951aeefb0f7c77aa
        System.out.println("WeId=" + weIdData.getWeId());
        //WeIdPrivateKey(privateKey=55559030924268539132679692723074429473170375254079614586557716127587563312174)
        System.out.println("WeIdPrivateKey=" + weIdData.getUserWeIdPrivateKey().getPrivateKey());
        //WeIdPublicKey(publicKey=7754726711498247797055860871445785814185359251749205542141633832591688700870696335184978419564905601180578548690397412926717265500900588334467370377126198)
        System.out.println("WeIdPublicKey=" + weIdData.getUserWeIdPublicKey().getPublicKey());

        ResponseData<String> weIdDocumentJson = weIdService.getWeIdDocumentJson(weIdData.getWeId());
        System.out.println("WeIdDocumentJson:");
        System.out.println(weIdDocumentJson.getResult());
        System.exit(0);
    }
}
