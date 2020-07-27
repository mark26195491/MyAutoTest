package com.webank.autotest.weid;

import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;

public class CreateWeid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeIdService service = new WeIdServiceImpl();
		ResponseData<CreateWeIdDataResult> rst = service.createWeId();
		//ResponseData(result=CreateWeIdDataResult(weId=did:weid:101:0xf82ef4dc430ab59c61b68e3f06dbc512760edb94, userWeIdPublicKey=WeIdPublicKey(publicKey=6446632685145886176836866410018848192872083828394563636778322511117271562573276767716591571160210767959109319633625651602047140479130525314349664915561664), userWeIdPrivateKey=WeIdPrivateKey(privateKey=30641780723964904718404003854184802630321566423467957897597395913799506697579)), errorCode=0, errorMessage=success, transactionInfo=TransactionInfo(blockNumber=3608, transactionHash=0xb176e59dddb9fd6e932540e6af3b76cd8e76ef1452374327056bd3d74b111c5a, transactionIndex=0))
		System.out.println(rst);
  
		System.out.println(System.currentTimeMillis());
	}

}
