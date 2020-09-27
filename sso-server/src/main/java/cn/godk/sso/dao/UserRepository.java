package cn.godk.sso.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 *  cas 校验  dao
 * @author wt
 * @program cas-server
 * @create 2019-12-26  16:33
 */
@Repository("userRepository")
public class UserRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;
    /**
     *   sql 查询方法
     * @param sql  sql
     * @param args  参数
     * @return
     */
    public Map<String,Object> select(String sql,Object... args){
        try {
           return  jdbcTemplate.queryForMap(sql, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
