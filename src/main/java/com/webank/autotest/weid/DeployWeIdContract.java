package com.webank.autotest.weid;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.webank.weid.config.FiscoConfig;
import com.webank.constant.FileOperator;
import com.webank.weid.constant.WeIdConstant;
import com.webank.weid.contract.v2.AuthorityIssuerController;
import com.webank.weid.contract.v2.AuthorityIssuerData;
import com.webank.weid.contract.v2.CommitteeMemberController;
import com.webank.weid.contract.v2.CommitteeMemberData;
import com.webank.weid.contract.v2.CptController;
import com.webank.weid.contract.v2.CptData;
import com.webank.weid.contract.v2.EvidenceFactory;
import com.webank.weid.contract.v2.RoleController;
import com.webank.weid.contract.v2.SpecificIssuerController;
import com.webank.weid.contract.v2.SpecificIssuerData;
import com.webank.weid.contract.v2.WeIdContract;
import com.webank.weid.contract.deploy.*;
import com.webank.weid.exception.InitWeb3jException;
import com.webank.util.FileUtils;
import com.webank.weid.util.WeIdUtils;

/**
 * The Class DeployContract.
 *
 * @author tonychen
 */
public class DeployWeIdContract {

    /**
     * log4j.
     */
    private static final Logger logger = LoggerFactory.getLogger(DeployWeIdContract.class);

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

    	String privateKey = null;
        if (args != null && args.length > 0) {
            privateKey = args[0];
        }
        
        DeployContract.deployContract(privateKey);
        DeployEvidence.deployContract(privateKey, 1, true);
        
        System.exit(0);
    }
}