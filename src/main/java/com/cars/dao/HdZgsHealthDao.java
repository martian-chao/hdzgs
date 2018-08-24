package com.cars.dao;

import com.cars.model.HdJkjcModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**货调总公司 健康检查
 * Created by liuyanchao
 * on 2018/8/17.
 */
@Repository
public class HdZgsHealthDao {
    @Value("#{jdbcTemplate}")
    private JdbcTemplate jdbcTemplate;
    @Value("#{namedParameterJdbcTemplate}")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * 获取各个路局的 健康检查地址
     * @return
     */
    public List<HdJkjcModel> listLjJkjc() {
        String sql = "select ljdm,ljmc,ljurl,flag from td_hd_jkjc where 1=1";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(HdJkjcModel.class));
    }

    /**
     * 根据路局代码更新监控表
     * @param hdJkjcModel
     * @return
     */
    public int updateByLjdm(HdJkjcModel hdJkjcModel) {
        String sql = "update td_hd_jkjc set flag=:flag where ljdm=:ljdm";
        return namedParameterJdbcTemplate.update(sql,new BeanPropertySqlParameterSource(hdJkjcModel));
    }
}
