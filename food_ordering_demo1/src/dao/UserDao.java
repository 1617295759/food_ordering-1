package dao;

import domain.User;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 操作数据库中User表的类
 */
public class UserDao {
    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 注册方法，将新增用户数据添加到user表中
     *
     * @param registerUser
     * @return 注册成功返回1，否则0
     */
    public int register(User registerUser) {
        try {
            //先用传入的学号进行查询，判断该学号是否已被注册
            String sql1 = "select * from user where userID = ?";
            try {
                User user = template.queryForObject(sql1,
                        new BeanPropertyRowMapper<User>(User.class),
                        registerUser.getUserID());
                if (user != null) {
                    return 0;//学号已被注册
                }
            } catch (EmptyResultDataAccessException e) {  //学号未被注册
                //1.编写sql
                String sql2 = "insert into user (userID,phone,userName,avatarURL) values (?,?,?,?)";
                //2.调用update方法
                int count = template.update(sql2, registerUser.getUserID(), registerUser.getPhone(), registerUser.getUserName(), registerUser.getAvatarURL());
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
     * 登录方法，检测用户数据是否在数据库中
     *
     * @param loginUser
     * @return user包含用户全部数据, 没有查询到，返回null
     */
    public User login(User loginUser) {
        try {
            //1.编写sql
            String sql = "select * from user where userID = ? and phone = ?";
            //2.调用query方法
            User user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUserID(), loginUser.getPhone());
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return null;
        }
    }
}
