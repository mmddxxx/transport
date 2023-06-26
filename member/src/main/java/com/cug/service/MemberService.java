package com.cug.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.cug.domain.Member;
import com.cug.domain.MemberExample;
import com.cug.exception.BusinessException;
import com.cug.exception.BusinessExceptionEnum;
import com.cug.mapper.MemberMapper;
import com.cug.req.MemberRegisterReq;
import com.cug.req.MemberSendCodeReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;

    public int count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(list)) {
//            return list.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
//        member.setId(System.currentTimeMillis());
        member.setId(IdUtil.getSnowflake(1, 1).nextId());
        member.setMobile(mobile);

        memberMapper.insert(member);
        return member.getId();
    }

    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);

        //手机号不存在时则插入一条记录
        if (CollUtil.isEmpty(list)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(IdUtil.getSnowflake(1, 1).nextId());
            member.setMobile(mobile);

            memberMapper.insert(member);
        } else {
            System.out.println("手机号存在，不插入 记录");
        }

        //生成验证码
        String code = RandomUtil.randomString(4);

        //保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间

        //对接短信通道，发送短信
    }
}
