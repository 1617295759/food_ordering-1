package dao;

import domain.Administer;
import domain.User;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 操作数据库中admin表的类
 */
public class AdminDao {
    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 注册方法，将新增管理员数据添加到user表中
     *
     * @param registerAdmin
     * @return 注册成功返回1，否则0
     */
    public int register(Administer registerAdmin) {
        try {
            //先用传入的管理员ID进行查询，判断该管理员ID是否已被注册
            String sql1 = "select * from admin where adminID = ?";
            try {
                Administer administer = template.queryForObject(sql1,
                        new BeanPropertyRowMapper<Administer>(Administer.class),
                        registerAdmin.getAdminID());
                if (administer != null) {
                    return 0;//管理员ID已被注册
                }
            } catch (EmptyResultDataAccessException e) {  //管理员ID未被注册
                //1.编写sql
                String sql2 = "insert into admin (adminID,phone,adminName,avatarURL) values (?,?,?,?)";
                //2.调用update方法
                int count = template.update(sql2, registerAdmin.getAdminID(),
                        registerAdmin.getPhone(), registerAdmin.getAdminName(),
                        registerAdmin.getAvatarUrl());
                if (count == 1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return 0;
        }
        return 0;
    }

    /**
     * 登录方法，检测管理员数据是否在数据库中
     *
     * @param loginAdmin
     * @return user包含用户全部数据, 没有查询到，返回null
     */
    public Administer login(Administer loginAdmin) {
        try {
            //1.编写sql
            String sql = "select * from admin where adminID = ? and phone = ?";
            //2.调用query方法
            Administer administer = template.queryForObject(sql,
                    new BeanPropertyRowMapper<Administer>(Administer.class),
                    loginAdmin.getAdminID(),loginAdmin.getPhone());
            return administer;
        } catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return null;
        }
    }
}
