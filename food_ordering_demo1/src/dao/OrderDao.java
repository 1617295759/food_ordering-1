package dao;

import domain.Administer;
import domain.Meal;
import domain.Order;
import util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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
            //1.编写sql
            String sql = "insert into ordering (orderID,userID,mealID,num,price,state,pickUpTime,orderTime) values (?,?,?,?,?,?,?,now())";
            //2.调用update方法
            int count = template.update(sql, registerOrder.getOrderID(),registerOrder.getUserID(),
                    registerOrder.getMealID(),registerOrder.getNum(),registerOrder.getPrice(),registerOrder.getState(),registerOrder.getPickUpTime());
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
     * 查询方法，检测订单数据是否在数据库中
     *
     * @param tempOrder
     * @return ordering包含订单全部数据, 没有查询到，返回null
     */
    public Order queryOrder(Order tempOrder) {
        try {
            //1.编写sql
            String sql = "select * from ordering where orderID = ?";
            //2.调用query方法
            Order order = template.queryForObject(sql,
                    new BeanPropertyRowMapper<Order>(Order.class),
                    tempOrder.getOrderID());
            return order;
        } catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return null;
        }
    }

    /**
     * 修改订单状态的方法，当该订单接单后其状态默认为0，完成后为待取餐状态为1，取餐后修改为2
     *
     * @param tempOrder,registerAdmin
     * @return 修改成功返回1，否则返回0
     */
    public int modifyOrderState(Order tempOrder, Administer registerAdmin) {
        try {
            //先用传入的管理员ID进行查询，判断该管理员ID是否存在
            String sql1 = "select * from admin where adminID = ? and phone = ?";
            Administer administer = template.queryForObject(sql1,
                    new BeanPropertyRowMapper<Administer>(Administer.class),
                    registerAdmin.getAdministerID(), registerAdmin.getPhone());
            try {
                if (administer != null) {
                    //1.编写sql
                    String sql = "update ordering set state = ? where orderID = ?";
                    //2.调用update方法
                    int count = template.update(sql, tempOrder.getState(), tempOrder.getOrderID());
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
