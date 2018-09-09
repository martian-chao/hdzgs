package com.cars.dao;

import com.cars.model.SysSmsSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**短信发送记录Dao
 * Created by liuyanchao
 * on 2018/9/7.
 */
@Repository
public class SysSmsSendDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * 添加 短信记录
     * @param sysSmsSend
     * @return
     */
    public  int addSysSmsSend(SysSmsSend sysSmsSend) {
        String sql = "insert into hd_sm_send (id,send_name,send_mobile,send_ljdm,send_ljmc,send_date,send_content,send_status)" +
                "   values(seq_hd_sm_send.nextval,:sendName,:sendMobile,:send_ljdm,:send_ljmc,sysdate,:sendContent,:sendStatus)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rc = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(sysSmsSend), keyHolder, new String[] { "id" });
        if (rc > 0) {
            return keyHolder.getKey().intValue();
        } else {
            return 0;
        }
    }

    /**
     * 更新状态
     */
    public int updateStatus(Integer id) {
        String sql = "update hd_sm_send set send_status =1 where id=?";
        return jdbcTemplate.update(sql,id);
    }
}
