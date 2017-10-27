package com.jetsen.pack.optram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * user service
 * Created by lenovo on 2017/10/25.
 */
@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer getAllUsers() {
        String channelcode = "0081";
        String playdate = "2017-07-21";
        String sql= "select i.ITEM_PLAYTIME,t.task_srctype,t.task_vercode,i.item_code," +
                "i.item_needpack,i.channel_packing_itemcode,p.ipk_comment,i.item_status ,p.IPK_PUBLISH_STATUS,p.ipk_id" +
                " from ppn_bcpk_item i inner join ppn_bcpk_itempack p on i.item_code=p.item_code inner join" +
                " ppn_bcpk_task t on t.task_id=i.task_id where t.task_channlecode='"+channelcode+"' and t.task_play_date = to_date('"+playdate+"','yyyy/mm/dd')" +
                " and i.item_needpack=1 and i.item_isdeleted=0 and t.task_srctype=0";
        List<Map<String, Object>> packinfoMap = jdbcTemplate.queryForList(sql);
        int size = packinfoMap.size();
        if(size>0){
            Map map = packinfoMap.get(0);
        }

        return jdbcTemplate.queryForObject("select count(1) from uum_user", Integer.class);
    }
}
