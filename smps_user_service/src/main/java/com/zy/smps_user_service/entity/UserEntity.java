package com.zy.smps_user_service.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "smps")
public class UserEntity implements Serializable {
    private String userId;
    @ExcelProperty("account")
    private String account;
    @ExcelProperty("password")
    private String password;
    @ExcelProperty("name")
    private String name;
    @ExcelProperty("phone")
    private String phone;
    @ExcelProperty("age")
    private int age;
    @ExcelProperty("gender")
    private String gender;
    private String image;
    @ExcelProperty("email")
    private String email;
    @ExcelProperty("state")
    private int state;
    @ExcelProperty("is_admin")
    private int isAdmin;
    @ExcelProperty("class/dept")
    private String className;

    private String roleName;

    @Id
    @GenericGenerator(name="my-uuid",strategy = "uuid")
    @GeneratedValue(generator = "my-uuid")
    @Column(name = "u_id", nullable = false, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "account", nullable = false, length = 11)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 25)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "gender", nullable = false, length = 8)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "image", nullable = true, length = 50)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Basic
    @Column(name = "class_name",length = 36)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return age == that.age &&
                state == that.state &&
                isAdmin == that.isAdmin &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(account, that.account) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(image, that.image) &&
                Objects.equals(className, that.className) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, account, password, name, phone, age, gender, image, email, state, isAdmin,className);
    }
}
