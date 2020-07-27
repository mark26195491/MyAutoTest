package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.*;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.suite.api.transportation.TransportationFactory;
import com.webank.weid.suite.api.transportation.inf.JsonTransportation;
import com.webank.weid.suite.api.transportation.params.EncodeType;
import com.webank.weid.suite.api.transportation.params.ProtocolProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by v_wbyangwang on 2019/8/21.
 */
public class TestJsonTransportation {

    private static final Logger logger = LoggerFactory.getLogger(TestJsonTransportation.class);

    public static void main(String[] args) throws Exception {

        //logger.info("args = {}", Arrays.toString(args));
        logger.info("Show the args : {}", Arrays.toString(args));
        //System.out.printf("Show the args : %s\n", Arrays.toString(args));

        //String[] command = args[0].split("\\|");
        String[] command = args;
        //did:weid:1:0xddf4f93f098d0341a2bd0961bff0d6d6c97538a0
        logger.info("Begin to execute method : {}",command[0]);
        //System.out.printf("Begin to execute method : %s\n", command[0]);

        try {
            switch (command[0]) {
                case "specify":
                    testSpecify(command);
                    break;
                case "serialize":
                    testSerialize(command);
                    break;
                case "deserialize":
                    testDeserialize(command);
                    break;
                default:
                    System.out.println("Not found the method, please check.");
                    break;
            }
        } catch (Exception e) {
            //logger.error("execute {} failed.{}",Arrays.toString(command), e.getMessage());
            System.out.println("Execute failed, please check the log.");
            System.exit(1);
        }

        System.exit(0);
    }

    private static void testSpecify(String[] command) throws Exception {
        String weIds = command[1];

        String[] weIdList = weIds.split(";");

        JsonTransportation jsonTransportation = TransportationFactory.newJsonTransportation();

        List<String> verifierWeIdList = new ArrayList<String>();
        for(String weId : weIdList) {
            verifierWeIdList.add(weId);
        }

        //jsonTransportation = jsonTransportation.specify(verifierWeIdList);

        System.out.println("OK");
        //System.out.println(JSONObject.toJSONString(jsonTransportation));
    }

    private static void testSerialize(String[] command) throws Exception {
        /*String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];
        String weIdPublicKeyId = command[6];
        String policyJson = command[7];*/
        String encodeType = command[8];
        String verifierWeIds = command[9];

        String[] weIdList = verifierWeIds.split(";");

        List<String> verifierWeIdList = new ArrayList<String>();
        for(String tmpWeId : weIdList) {
            verifierWeIdList.add(tmpWeId);
        }

        PresentationE presentation = WeIdCommon.createPresentation(command).getResult();

        ResponseData<String> response;

        if("ORIGINAL".equals(encodeType)) {
            //原文方式调用
            response = TransportationFactory
                            .newJsonTransportation()
                            .specify(verifierWeIdList)
                            .serialize(presentation,new ProtocolProperty(EncodeType.ORIGINAL));
        } else if("CIPHER".equals(encodeType)) {
            //密文方式调用
            response = TransportationFactory
                    .newJsonTransportation()
                    .specify(verifierWeIdList)
                    .serialize(presentation,new ProtocolProperty(EncodeType.CIPHER));
        } else {
            throw new Exception("The encodeType is invalid, please check.");
        }

        System.out.println(response.getResult());
    }

    private static void testDeserialize(String[] command) throws Exception {
        String weIds = command[1];
        String encodeType = command[2];
        String transString = command[3];

        String[] weIdList = weIds.split(";");

        List<String> verifierWeIdList = new ArrayList<String>();
        for(String weId : weIdList) {
            verifierWeIdList.add(weId);
        }

        ResponseData<PresentationE> response = null;

        if("ORIGINAL".equals(encodeType)) {
            //原文方式调用反序列化
            response =
                    TransportationFactory
                            .newJsonTransportation()
                            .specify(verifierWeIdList)
                            .deserialize(transString,PresentationE.class);
        } else if("CIPHER".equals(encodeType)) {
            //密文方式调用反序列化
            response =
                    TransportationFactory
                            .newJsonTransportation()
                            .specify(verifierWeIdList)
                            .deserialize(transString,PresentationE.class);
        } else {
            throw new Exception("The encodeType is invalid, please check.");
        }

        System.out.println(JSONObject.toJSONString(response));
    }
}
