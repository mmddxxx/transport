package com.cug.mytrain.req;

//import com.cug.mytrain.req.PageReq;

//public class PassengerQueryReq extends PageReq {
public class PassengerQueryReq {

    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PassengerQueryReq{");
        sb.append("memberId=").append(memberId);
        sb.append('}');
        return sb.toString();
    }
}
