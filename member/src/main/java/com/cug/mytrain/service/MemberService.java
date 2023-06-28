package com.cug.mytrain.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.cug.mytrain.domain.Member;
import com.cug.mytrain.domain.MemberExample;
import com.cug.mytrain.exception.BusinessException;
import com.cug.mytrain.exception.BusinessExceptionEnum;
import com.cug.mytrain.mapper.MemberMapper;
import com.cug.mytrain.req.MemberLoginReq;
import com.cug.mytrain.req.MemberRegisterReq;
import com.cug.mytrain.req.MemberSendCodeReq;
import com.cug.mytrain.resp.MemberLoginResp;
import com.cug.mytrain.util.JwtUtil;
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
        Member memberDB = getMembersByMobile(mobile);

        if (ObjectUtil.isNull(memberDB)) {
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
        Member memberDB = getMembersByMobile(mobile);


        //手机号不存在时则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
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

    public MemberLoginResp login(MemberLoginReq req) {
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = getMembersByMobile(mobile);

        //手机号不存在时则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        //校验短信验证码
        if (!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        MemberLoginResp resp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);

        String token = JwtUtil.createToken(resp.getId(), resp.getMobile());
        resp.setToken(token);
        return resp;

    }

    private Member getMembersByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
