package com.example.dcloud.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.dcloud.entity.Dictionary;
import com.example.dcloud.entity.DictionaryDetail;
import com.example.dcloud.entity.Result;
import com.example.dcloud.service.DictionaryDetailService;
import com.example.dcloud.service.DictionaryService;
import com.example.dcloud.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fifteen
 * @since 2020-04-01
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictionaryDetailService dictionaryDetailService;
    /**
     * 返回数据字典列表
     * @param jsonObject(page)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public String getList(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        int cur_page = parseInt(map.get("page").toString());
        return dictionaryService.pageList(cur_page);
    }
    /**
     * 查询
     * @param jsonObject（page,code,name）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    public String queryList(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        return dictionaryService.pageListforQuery(map);
    }
    /**
     * 编辑
     * @param jsonObject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public String edit(@RequestBody JSONObject jsonObject) {
        Map map = JSON.toJavaObject(jsonObject, Map.class);
        List<DictionaryDetail> entityList = new ArrayList<>();
        //判断new_code的唯一性
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", map.get("new_code").toString());
        int count = dictionaryService.count(queryWrapper);
        if(!map.get("new_code").toString().equals(map.get("old_code").toString())){
            if(count > 0){
                return ResultUtil.error("英文标识符已存在!");
            }
        }
        //判断new_name的唯一性
        QueryWrapper<Dictionary> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", map.get("new_name").toString());
        int count1 = dictionaryService.count(queryWrapper1);
        if(!map.get("new_name").toString().equals(map.get("old_name").toString())){
            if(count > 0){
                return ResultUtil.error("中文标识符已存在!");
            }
        }
        //数据项判断
        JSONArray detail=(JSONArray) JSONArray.toJSON(map.get("detail"));
        int defaultMark = 0;
        Set valueSet = new HashSet<>();
        Set nameSet = new HashSet<>();
        int beforeSize = valueSet.size();
        int beforeSize1 = nameSet.size();
        for(int i = 0; i < detail.size(); i++){
            JSONObject tempObject=(JSONObject) JSONObject.toJSON(detail.get(i));
            Map temp = JSON.toJavaObject(tempObject, Map.class);
            valueSet.add(temp.get("value").toString());
            int afterSize = valueSet.size();
            if(beforeSize == afterSize){
                return ResultUtil.error("数据项值重复！");
            }else {
                beforeSize = afterSize;
            }
            nameSet.add(temp.get("name").toString());
            int afterSize1 = nameSet.size();
            if(beforeSize1 == afterSize1){
                return ResultUtil.error("数据项文本重复！");
            }else {
                beforeSize1 = afterSize1;
            }
            if(parseInt(temp.get("is_default").toString()) == 1){
                defaultMark ++;
                if(defaultMark > 1){
                    return ResultUtil.error("多个数据项设置为默认值！");
                }
            }
            DictionaryDetail dictionaryDetail = new DictionaryDetail();
            if(temp.containsKey("id")){
                dictionaryDetail.setId(parseLong(temp.get("id").toString()));
            }
            dictionaryDetail.setCode(temp.get("code").toString());
            dictionaryDetail.setName(temp.get("name").toString());//唯一
            dictionaryDetail.setValue(temp.get("value").toString());//唯一
            dictionaryDetail.setDictOrder(parseInt(temp.get("dict_order").toString()));
            dictionaryDetail.setIsDefault(parseInt(temp.get("is_default").toString()));
            dictionaryDetail.setTypeCode(map.get("new_code").toString());
            dictionaryDetail.setIsDelete(0);
            entityList.add(dictionaryDetail);
        }
        //更新字典
        Dictionary dictionary = new Dictionary();
        dictionary.setId(parseLong(map.get("id").toString()));
        if(map.get("description").toString() == ""){
            dictionary.setDescription(" ");
        }else{
            dictionary.setDescription(map.get("description").toString());
        }
        dictionary.setName(map.get("new_name").toString());
        dictionary.setCode(map.get("new_code").toString());
        dictionary.setIsDelete(0);
        //更新数据项
        dictionaryService.updateById(dictionary);
        for(int i = 0; i < entityList.size(); i++){
                dictionaryDetailService.saveOrUpdate(entityList.get(i));
        }
        return ResultUtil.success();
    }
    /**
     * 删除
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delDict",method = RequestMethod.POST)
    public String delDict(@RequestBody List<Map> list) {
        for(int i = 0; i < list.size(); i++){
            String code = list.get(i).get("code").toString();
            Map<String,Object> dictmap = new HashMap<>();
            dictmap.put("code",code);
            boolean flag = dictionaryService.removeByMap(dictmap);
            //继续删除数据项
            Map<String,Object> detailMap = new HashMap<>();
            detailMap.put("type_code",code);
            boolean flag1 = dictionaryDetailService.removeByMap(detailMap);
        }
        return ResultUtil.success();
    }

}

