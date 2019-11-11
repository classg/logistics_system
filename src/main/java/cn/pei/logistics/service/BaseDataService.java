package cn.pei.logistics.service;

import cn.pei.logistics.pojo.BaseData;
import cn.pei.logistics.pojo.BaseDataExample;

import java.util.List;

/*
该接口为用户接口
 */
public interface BaseDataService {

    int deleteByPrimaryKey(Long baseDataId);

    int insert(BaseData record);

    int updateByPrimaryKeySelective(BaseData record);

    List<BaseData> selectByExample(BaseDataExample example);

    BaseData selectByPrimaryKey(Long baseDataId);

    List<BaseData> selectBaseDataByParentName(String parentName);
}
