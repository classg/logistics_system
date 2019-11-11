package cn.pei.logistics.service.impl;

import cn.pei.logistics.mapper.BaseDataMapper;
import cn.pei.logistics.pojo.BaseData;
import cn.pei.logistics.pojo.BaseDataExample;
import cn.pei.logistics.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.service.impl
 * @ClassName:BaseDataServiceImpl
 * @Description: 实现用户接口的实现类
 * @author:CJ
 * @date:2019/10/27 15:42
 */
@Service
public class BaseDataServiceImpl implements BaseDataService {

    @Autowired
    private BaseDataMapper baseDataMapper;

    @Override
    public int deleteByPrimaryKey(Long baseDataId) {
        return baseDataMapper.deleteByPrimaryKey(baseDataId);
    }

    @Override
    public int insert(BaseData record) {
        return baseDataMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BaseData record) {
        return baseDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<BaseData> selectByExample(BaseDataExample example) {
        return baseDataMapper.selectByExample(example);
    }

    @Override
    public BaseData selectByPrimaryKey(Long baseDataId) {
        return baseDataMapper.selectByPrimaryKey(baseDataId);
    }

    @Override
    public List<BaseData> selectBaseDataByParentName(String parentName) {
        return baseDataMapper.selectBaseDataByParentName(parentName);
    }
}
