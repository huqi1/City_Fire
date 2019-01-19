package com.hq.modules.operate.controller;

import com.alibaba.fastjson.JSONObject;
import com.hq.common.utils.R;
import com.hq.modules.operate.entity.EquipmentStatistics;
import com.hq.modules.operate.entity.LocationStatistics;
import com.hq.modules.operate.service.StatisticsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 列表
     */
    @RequestMapping("/equipment")
    @RequiresPermissions("operate:equipment:list")
    public R equipment(@RequestParam Map<String, Object> params){
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        if(startDate == null){
            Calendar now  = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            endDate = format.format(now);
            now.add(Calendar.DAY_OF_MONTH,-7);
            startDate = format.format(now);
        }
        List<EquipmentStatistics> equipmentStatisticsList = statisticsService.EquipmentStatistics(startDate,endDate);
        TreeSet<String> Xlist = new TreeSet<>();
        Set<String> Communitylist = new LinkedHashSet<>();
        Boolean aBoolean = getXYData(equipmentStatisticsList,Xlist,Communitylist);
        Iterator communityiterator = Communitylist.iterator();
        int j = 0;
        int size = equipmentStatisticsList.size();
        JSONObject communityObject = new JSONObject();
        while (communityiterator.hasNext()){
            String community = (String) communityiterator.next();
            JSONObject dateJson = new JSONObject();
            if (community.equals(equipmentStatisticsList.get(j).getCommunity())){
                Iterator dateiterator = Xlist.iterator();
                int EquSum = 0;
                while (dateiterator.hasNext()){
                    Map<String,String> map = new HashMap<>();
                    String date = new String((String)dateiterator.next());
                    // 如果已经是最后一个元素了，仍然对所有横轴数据填0补充完整
                    if (j ==size ){
                        map.put("addEqunum","0");
                    }else{
                        if (community.equals(equipmentStatisticsList.get(j).getCommunity()) && date.equals(equipmentStatisticsList.get(j).getDate())){
                            map.put("addEqunum",equipmentStatisticsList.get(j).getAddEqunum());
                            EquSum = EquSum+ Integer.parseInt(equipmentStatisticsList.get(j).getAddEqunum());
                            j++;
                        }else {
                            map.put("addEqunum","0");
                        }
                    }
                    map.put("sumEqunum",EquSum+"");
                    dateJson.put(date,map);
                }
            }
            communityObject.put(community,dateJson);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("page", communityObject);
        map.put("date",Xlist);
        map.put("community",Communitylist);
        return R.ok(map);
    }

    private Boolean getXYData(List<EquipmentStatistics> equipmentStatisticsList, TreeSet<String> xlist, Set<String> communitylist) {
        if (equipmentStatisticsList == null)
            return false;
        int  size = equipmentStatisticsList.size();
        for (int i = 0;i<size;i++){
            xlist.add(equipmentStatisticsList.get(i).getDate());
            communitylist.add(equipmentStatisticsList.get(i).getCommunity());
        }
        xlist.comparator();
        return true;
    }

    /**
     * 列表
     */
    @RequestMapping("/location")
    @RequiresPermissions("operate:location:list")
    public R location(@RequestParam Map<String, Object> params){
        List<LocationStatistics> locationStatistics = statisticsService.getLocationStatistics();
        Map<String,Object> map = new HashMap<>();
        map.put("page", locationStatistics);
        return R.ok(map);
    }
}
