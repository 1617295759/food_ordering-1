package dao;

import domain.User;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 * 操作数据库中management表的类
 */
public class ManagementDao {
    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 添加方法，将新增对应数据添加到management表中
     *
     * @param adminID,mealID
     * @return 添加成功返回1，否则0
     */
    public int addManagement(String adminID,int mealID) {
        try {
            //1.编写sql
            String sql = "insert into management (adminID,mealID) values (?,?)";
            //2.调用update方法
            int count = template.update(sql,adminID,mealID);
            if (count == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return 0;
        }
    }
}