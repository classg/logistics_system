package cn.pei.logistics.pojo;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 9160463703935571802L;
    private Long roleId;

    private String rolename;

    private String remark;

    private String permissionIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", rolename='" + rolename + '\'' +
                ", remark='" + remark + '\'' +
                ", permissionIds='" + permissionIds + '\'' +
                '}';
    }
}