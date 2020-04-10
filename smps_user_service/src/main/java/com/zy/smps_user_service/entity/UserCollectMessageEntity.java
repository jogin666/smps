package com.zy.smps_user_service.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user_collect_message", schema = "smps", catalog = "")
@IdClass(UserCollectMessageEntityPK.class)
public class UserCollectMessageEntity implements Serializable {
    private String uId;
    private String mId;
    private String account;
    private Timestamp date;

    @Id
    @Column(name = "u_id", nullable = false, length = 32)
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Id
    @Column(name = "m_id", nullable = false, length = 32)
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Basic
    @Column(name = "account", nullable = true, length = 11)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "collect_time", nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCollectMessageEntity that = (UserCollectMessageEntity) o;
        return Objects.equals(uId, that.uId) &&
                Objects.equals(mId, that.mId) &&
                Objects.equals(account, that.account) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, mId, account, date);
    }
}
