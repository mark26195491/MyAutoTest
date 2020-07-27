package com.webank.autotest.weid;

import com.webank.weid.protocol.base.Challenge;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.base.PresentationPolicyE;
import com.webank.weid.service.impl.callback.PresentationPolicyService;

/**
 * Created by v_wbyangwang on 2019/9/3.
 */
public class PolicyServiceImpl extends PresentationPolicyService {

    private String policyJson;

    public PolicyServiceImpl(String str) {
        this.policyJson = str;
    }

    @Override
    public PolicyAndChallenge policyAndChallengeOnPush(String policyId, String targetWeId) {

        //获取presentationPolicyE
        PresentationPolicyE presentationPolicyE = PresentationPolicyE.fromJson(policyJson);

        //获取Challenge
        Challenge challenge = Challenge.create(targetWeId, String.valueOf(System.currentTimeMillis()));

        //保存challenge到数据库
        //DbUtils.save(challenge.getNonce(), challenge);

        PolicyAndChallenge policyAndChallenge = new PolicyAndChallenge();
        policyAndChallenge.setChallenge(challenge);
        policyAndChallenge.setPresentationPolicyE(presentationPolicyE);

        return policyAndChallenge;
    }
}
