package dao;

import domain.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.util.List;

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
     * @return 添加成功返回Meal，否则null
     */
    public Meal addMeal(final Meal registerMeal,String adminID) {
        final String sql = "insert into meal (name,location,price,adminID) values (?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement psst = connection.prepareStatement(sql, new String[]{"mealID"});
            psst.setString(1, registerMeal.getName());
            psst.setString(2, registerMeal.getLocation());
            psst.setDouble(3, registerMeal.getPrice());
            psst.setString(4, adminID);
            return psst;
        }, keyHolder);
        registerMeal.setMealID(keyHolder.getKey().intValue());
        return registerMeal;
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
     * 管理员查询自己负责的菜品的方法，检测菜品数据是否在数据库中
     *
     * @param adminID
     * @return List<Meal>包含全部菜品, 没有查询到，返回null
     */
    public List<Meal> queryAdminMeal(String adminID) {
        String sql = "SELECT m.mealID,m.name,m.location,m.price,m.state FROM meal AS m,management AS mana WHERE m.mealID = mana.mealID AND mana.adminID = ?";
        List<Meal> list = template.query(sql, new BeanPropertyRowMapper<Meal>(Meal.class),adminID);
        return list;
    }

    /**
     * 修改菜品价格及状态方法，当某一样菜品暂时下架时将其状态修改为1，重新上架后修改为0
     *
     * @param tempMeal,registerAdmin
     * @return 修改成功返回1，否则返回0
     */
    public int modifyState(Meal tempMeal, Administer registerAdmin) {
        try {
            //先用传入的管理员ID和mealID进行查询，判断该管理员ID是否可以修改此菜品
            String sql1 = "select * from management where adminID = ? and mealID = ?";
            Management management = template.queryForObject(sql1,
                    new BeanPropertyRowMapper<Management>(Management.class),
                    registerAdmin.getAdminID(), tempMeal.getMealID());
            try {
                if (management != null) {
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
        } catch (EmptyResultDataAccessException e) {  //管理员没有权限修改菜品
            return 0;
        }
        return 0;
    }

    /**
     * 展示每层所有菜品的方法
     *
     * @param location
     * @return List<Meal>包含全部菜品数据, 没有查询到，返回null
     */
    public List<Meal> showMeal(String location) {
        String sql = "select * from meal where location = ? and state = 0";
        List<Meal> list = template.query(sql, new BeanPropertyRowMapper<Meal>(Meal.class),location);
        return list;
    }
}
