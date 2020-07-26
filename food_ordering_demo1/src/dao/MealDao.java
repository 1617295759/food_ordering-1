package dao;

import domain.Administer;
import domain.Meal;
import domain.Order;
import domain.User;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 操作数据库中Meal表的类
 */
public class MealDao {
    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 添加菜品方法，管理员将新增菜品数据添加到meal表中
     *
     * @param registerMeal
     * @return 添加成功返回1，否则0
     */
    public int addMeal(Meal registerMeal) {
        try {
            //1.编写sql
            String sql = "insert into meal (name,location,price) values (?,?,?)";
            //2.调用update方法
            int count = template.update(sql, registerMeal.getName(), registerMeal.getLocation(), registerMeal.getPrice());
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

    /**
     * 查询方法，检测菜品数据是否在数据库中
     *
     * @param tempMeal
     * @return meal包含菜品全部数据, 没有查询到，返回null
     */
    public Meal queryMeal(Meal tempMeal) {
        try {
            //1.编写sql
            String sql = "select * from meal where name = ? and location = ?";
            //2.调用query方法
            Meal meal = template.queryForObject(sql,
                    new BeanPropertyRowMapper<Meal>(Meal.class),
                    tempMeal.getName(), tempMeal.getLocation());
            return meal;
        } catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return null;
        }
    }

    /**
     * 修改菜品价格及状态方法，当某一样菜品暂时下架时将其状态修改为1，重新上架后修改为0
     *
     * @param tempMeal,registerAdmin
     * @return 修改成功返回1，否则返回0
     */
    public int modifyState(Meal tempMeal, Administer registerAdmin) {
        try {
            //先用传入的管理员ID进行查询，判断该管理员ID是否存在
            String sql1 = "select * from admin where adminID = ? and phone = ?";
            Administer administer = template.queryForObject(sql1,
                    new BeanPropertyRowMapper<Administer>(Administer.class),
                    registerAdmin.getAdministerID(), registerAdmin.getPhone());
            try {
                if (administer != null) {
                    //1.编写sql
                    String sql = "update meal set price = ? , state = ? where mealID = ?";
                    //2.调用update方法
                    int count = template.update(sql, tempMeal.getPrice(), tempMeal.getState(), tempMeal.getMealID());
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
        } catch (EmptyResultDataAccessException e) {  //管理员不存在
            return 0;
        }
        return 0;
    }
}
