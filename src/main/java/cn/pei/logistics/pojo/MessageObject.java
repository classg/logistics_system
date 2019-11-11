package cn.pei.logistics.pojo;

import java.io.Serializable;

/**
 * @PackageName:cn.pei.logistics.pojo
 * @ClassName:MessageObject
 * @Description: 封装消息对应的，以供ajax增删改操作返回ajax消息
 * @author:CJ
 * @date:2019/10/28 15:20
 */
public class MessageObject implements Serializable {
    private static final long serialVersionUID = -8881412423589804390L;
    private Boolean flag;
    private String msg;

    public MessageObject(Boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                '}';
    }
}
