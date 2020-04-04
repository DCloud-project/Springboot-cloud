package com.example.dcloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.dcloud.entity.Dictionary;
import com.example.dcloud.entity.DictionaryDetail;
import com.example.dcloud.mapper.DictionaryDetailMapper;
import com.example.dcloud.mapper.DictionaryMapper;
import com.example.dcloud.service.DictionaryDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fifteen
 * @since 2020-04-01
 */
@Service
public class DictionaryDetailServiceImpl extends ServiceImpl<DictionaryDetailMapper, DictionaryDetail> implements DictionaryDetailService {

    @Autowired
    private DictionaryDetailMapper dictionaryDetailMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Override
    public Map<Object,List> getDetail(String code) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        List<Dictionary> list1 = dictionaryMapper.selectByMap(map);
        Map<String, Object> detailMap = new HashMap<String, Object>();
        detailMap.put("type_code", code);
        detailMap.put("is_delete",0);
        List<DictionaryDetail> list2 = dictionaryDetailMapper.selectByMap(detailMap);
        Map<Object,List> list = new HashMap<>();
        list.put("dict",list1);
        list.put("detail",list2);
        return list;
    }
}
