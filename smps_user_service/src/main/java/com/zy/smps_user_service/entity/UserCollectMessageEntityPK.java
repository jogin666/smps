package com.zy.smps_user_service.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserCollectMessageEntityPK implements Serializable {
    private String uId;
    private String mId;

    @Column(name = "u_id", nullable = false, length = 32)
    @Id
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Column(name = "m_id", nullable = false, length = 32)
    @Id
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCollectMessageEntityPK that = (UserCollectMessageEntityPK) o;
        return Objects.equals(uId, that.uId) &&
                Objects.equals(mId, that.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, mId);
    }
}
