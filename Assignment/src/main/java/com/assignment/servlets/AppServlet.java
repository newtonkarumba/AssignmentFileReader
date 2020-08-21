package com.assignment.servlets;
import com.assignment.model.Organization;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;





    @WebServlet("/Organization")
    public class AppServlet extends HttpServlet {

        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            ServletContext scx = getServletContext();
            Connection dbConnection = (Connection) scx.getAttribute("dbConnection");
            resp.setContentType("text/plain");

           // save the users in an arrayList

            Collection<Organization> organizations = new ArrayList<Organization>();

            try {
                PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM Assignment");
                statement.execute();
                ResultSet result = statement.getResultSet();

                while (result.next()){
                    Organization organization = new Organization();
                    organization.setName(result.getString("name"));
                    organization.setAge(result.getString("age"));
                    organization.setTown(result.getString("town"));


                    organizations.add(organization);
                }

            }catch (SQLException sqlEx){
                sqlEx.printStackTrace();
            }

            ObjectMapper mapper = new ObjectMapper();
            resp.getWriter().print(mapper.writeValueAsString(organizations));

        }

        protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            ServletContext scx = getServletContext();
            Connection dbConnection = (Connection) scx.getAttribute("dbConnection");

            String name = request.getParameter("name");
            String age = request.getParameter("age");
            String town = request.getParameter("town");


            try {
                PreparedStatement statement = dbConnection.prepareStatement("insert into organization(name,address) values(?,?)");
                statement.setString(1, name==null?null: name.toUpperCase());
                statement.setString(2, age==null?null: age.toUpperCase());
                statement.setString(2, town==null?null: town.toUpperCase());
                statement.executeUpdate();

                response.getWriter().print("OK");

            }catch (SQLException sqlEx){
                sqlEx.printStackTrace();
            }

        }
    }



