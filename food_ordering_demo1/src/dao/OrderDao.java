package dao;

import domain.Administer;
import domain.Meal;
import domain.Order;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 操作数据库中Order表的类
 */
public class OrderDao {
    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 添加订单方法，将新增订单数据添加到order表中
     *
     * @param registerOrder
     * @return 添加成功返回1，否则0
     */
    public int addOrder(Order registerOrder) {
        try {
            //先去Meal表里根据mealID查询到对应的菜品名称和价格

            //1.编写sql
            String sql1 = "select * from meal where mealID = ?";
            //2.调用query方法
            Meal meal = template.queryForObject(sql1,
                    new BeanPropertyRowMapper<Meal>(Meal.class),
                    registerOrder.getMealID());

            //1.编写sql
            String sql = "insert into ordering (userID,mealID,state,pickUpTime,name,price,orderTime) values (?,?,?,?,?,?,now())";
            //2.调用update方法
            int count = template.update(sql,registerOrder.getUserID(),
                    registerOrder.getMealID(),registerOrder.getState(),registerOrder.getPickUpTime(),meal.getName(),meal.getPrice());
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
     * 用户查询自己订单的方法，检测订单数据是否在数据库中
     *
     * @param userID
     * @return List<Order>包含全部订单, 没有查询到，返回null
     */
    public List<Order> queryOrder(String userID) {
        String sql = "select * from ordering where userID = ?";
        List<Order> list = template.query(sql, new BeanPropertyRowMapper<Order>(Order.class),userID);
        return list;
    }

    /**
     * 管理员查询自己负责的订单的方法，检测订单数据是否在数据库中
     *
     * @param adminID
     * @return List<Order>包含全部订单, 没有查询到，返回null
     */
    public List<Order> queryAdminOrder(String adminID) {
        String sql = "SELECT o.orderID,o.userID,o.mealId,o.orderTime,o.pickUpTime,o.state,o.name,o.price FROM ordering as o, management as mana ,meal as meal WHERE o.mealID = meal.mealID and meal.mealID = mana.mealID and mana.adminID = ?";
        List<Order> list = template.query(sql, new BeanPropertyRowMapper<Order>(Order.class),adminID);
        return list;
    }


    /**
     * 修改订单状态的方法，当该订单接单后其状态默认为0，完成后为待取餐状态为1，取餐后修改为2
     *
     * @param tempOrder,registerAdmin
     * @return 修改成功返回1，否则返回0
     */
    public int modifyOrderState(Order tempOrder) {
        try {
            //1.编写sql
            String sql = "update ordering set state = ? where orderID = ?";
            //2.调用update方法
            int count = template.update(sql, tempOrder.getState(), tempOrder.getOrderID());
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
