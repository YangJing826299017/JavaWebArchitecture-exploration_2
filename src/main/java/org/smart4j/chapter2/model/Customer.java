package org.smart4j.chapter2.model;

public class Customer {
    private Long id;
    private String name;
    private String contact;
    private String telphone;
    private String email;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", contact=" + contact + ", telphone=" + telphone + ", email="
                + email + ", remark=" + remark + "]";
    }
    
    

}
