package database;

import items.Ball;
import items.Obstaculo;
import physicballs.Space;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pepe on 29/03/2017.
 */
public class DBHandler {

    public DBHandler() {
    }


    public List<Space> getSpaceList(){

        DBPhysicBall mysql = new DBPhysicBall();
        Connection cn = mysql.conectar();
        PreparedStatement st;
        ResultSet resultSet;
        List<Space> list = new ArrayList<>();
        String vSQL = "select * from space;";


        try {

            st = cn.prepareStatement(vSQL);
            resultSet = st.executeQuery();

            while (resultSet.next()) {
                Space space = null;
                //space.setId(resultSet.getInt(0));
                //space.setName(resultSet.getString(1));
                list.add(space);
            }


            st.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Space> lista =list;


        return lista;
    }

    public List<Obstaculo> getObstacleList(){

        DBPhysicBall mysql = new DBPhysicBall();
        Connection cn = mysql.conectar();
        PreparedStatement st;
        ResultSet resultSet;
        List<Obstaculo> list = new ArrayList<>();
        String vSQL = "select * from obstacle;";


        try {

            st = cn.prepareStatement(vSQL);
            resultSet = st.executeQuery();

            while (resultSet.next()) {
                Obstaculo obstaculo = null;
                //obstaculo.setX(resultSet.getFloat(1));
                //obstaculo.setY(resultSet.getFloat(2));
                //obstaculo.setWidth(resultSet.getFloat(3));
                //obstaculo.setSpaceId(resultSet.getString(4));


                list.add(obstaculo);
            }


            st.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Obstaculo> lista =list;


        return lista;
    }

    public List<Ball> getBallList(int id) {
        DBPhysicBall mysql = new DBPhysicBall();
        Connection cn = mysql.conectar();
        PreparedStatement st;
        ResultSet resultSet;
        List<Ball> list = new ArrayList<>();
        String vSQL = "select * from bola where bola.id_mapa= (select " + id + " from mapa);";


        try {

            st = cn.prepareStatement(vSQL);
            resultSet = st.executeQuery();

            while (resultSet.next()) {
                //b=new Ball(con * 55 + 20, con * 40 + 20, 2, 1, 10+con*2, 10+con*2, con*20, this, "N");
                Ball ball= null;

                ball.setX(resultSet.getFloat(1));
                ball.setY(resultSet.getFloat(2));
                // ball.setSpeed(resultSet.getFloat(3));
                ball.setAccel(resultSet.getFloat(4));
                ball.setRadius(resultSet.getFloat(5));
                ball.setMass(resultSet.getFloat(6));
                //ball.setangle(resultSet.getFloat(7));
                ball.setType(resultSet.getString(8));
                //ball.setId(resultSet.getInt(9));

                list.add(ball);
            }


            st.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Ball> lista =list;
        return lista;
    }


//
//    public int getCountId() {
//        DBPhysicBall mysql = new DBPhysicBall();
//        Connection cn = mysql.conectar();
//        PreparedStatement st;
//        ResultSet resultSet;
//        String vSQL = "SELECT count(id) FROM map;";
//        int count = 0;
//
//        try {
//            st = cn.prepareStatement(vSQL);
//            resultSet = st.executeQuery();
//
//            while (resultSet.next()) {
//                count = resultSet.getInt(0);
//            }
//            st.close();
//            resultSet.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }



    public void inserSpace(List<Space> list) {

        DBPhysicBall mysql = new DBPhysicBall();
        Connection cn = mysql.conectar();

//        int id = getCountId()+1;
        PreparedStatement st = null;

        try {

            st = cn.prepareStatement("INSERT INTO space VALUES(NULL,?,?,?,?,?)");



            for (Space space : list) {
                // x = 1, y= 2, mass = 3, velx= 4, vely = 5, acex= 6, acey=7, dens=8, fricc = 9, tipo= 10, image=11, id_map = 12
                //Ball(float x, float y, float speed, float accel, float radius, float mass, float angle, Space parent, String type)

                //st.setInt(0,space.getId());
               // st.setString(1, space.getName());
                //st.Float(2, space.getGravity());
                //st.setFloat(3, space.getFriccion());
                st.setFloat(4, space.getHeight());
                st.setFloat(5, space.getWidth());
                st.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public void insertObstacle(List<Obstaculo> list) {

        DBPhysicBall mysql = new DBPhysicBall();
        Connection cn = mysql.conectar();

//        int id = getCountId()+1;
        PreparedStatement st = null;

        try {

            st = cn.prepareStatement("INSERT INTO bola VALUES(NULL,?,?,?,?,?)");



            for (Obstaculo obstaculo : list) {
                // x = 1, y= 2, mass = 3, velx= 4, vely = 5, acex= 6, acey=7, dens=8, fricc = 9, tipo= 10, image=11, id_map = 12
                //Ball(float x, float y, float speed, float accel, float radius, float mass, float angle, Space parent, String type)

                //st.setInt(0, obstaculo.Id());
                st.setFloat(1, obstaculo.getX());
                st.setFloat(2, obstaculo.getY());
                st.setFloat(3, obstaculo.getWidth());
               // st.setInt(4, obstaculo.getSpaceId());
                st.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertBall(List<Ball> list) {

        DBPhysicBall mysql = new DBPhysicBall();
        Connection cn = mysql.conectar();

//        int id = getCountId()+1;
        PreparedStatement st = null;

        try {

            st = cn.prepareStatement("INSERT INTO bola VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?)");



            for (Ball ball : list) {
                // x = 1, y= 2, mass = 3, velx= 4, vely = 5, acex= 6, acey=7, dens=8, fricc = 9, tipo= 10, image=11, id_map = 12
                //Ball(float x, float y, float speed, float accel, float radius, float mass, float angle, Space parent, String type)

                st.setFloat(1, ball.getX());
                st.setFloat(2, ball.getY());
                st.setDouble(3, ball.getSpeed());
                st.setFloat(4, ball.getAccel());
                st.setFloat(5, ball.getRadius());
                st.setFloat(6, ball.getMass());
                st.setFloat(7, ball.getAngle());
                st.setString(9, ball.getType().toString());
                //st.setInt(9, ball.getSpaceId());

                st.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

