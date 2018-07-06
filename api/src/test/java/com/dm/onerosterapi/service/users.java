package com.dm.onerosterapi.service;

import com.dm.onerosterapi.model.User;
import com.dm.onerosterapi.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class users {

	@Autowired
    UserService userService;

	@Test
	public void getAllUsers(){

        List<User> userList = userService.getAllUsers();
        assertEquals(userList.size(),300);

        List<User> studentList = userService.getAllStudents();
        assertEquals(studentList.size(),292);

        for (User u : studentList){
            assertEquals(u.getRole(),"student");
        }

        List<User> teacherList = userService.getAllTeachers();
        assertEquals(teacherList.size(),8);

        for (User u : teacherList){
            assertEquals(u.getRole(),"teacher");
        }
	}

    @Test
    public void getUserById(){

        User u = userService.getUserById(65);

        assertEquals(u.getUserId(),65);
        assertEquals(u.getSourcedId(), "8057df9d-72a3-419a-98b5-6eab87ec0a6d");

    }


    // Custom Queries

    @Test
    public void getUsersByClass(){

        List<User> userList = userService.getUsersByClass("cee2f870-852c-47e8-988a-73e2c296fc77");
        assertEquals(userList.size(),55);

        List<User> studentList = userService.getStudentsByClass("cee2f870-852c-47e8-988a-73e2c296fc77");
        List<User> teacherList = userService.getTeachersByClass("cee2f870-852c-47e8-988a-73e2c296fc77");

        assertEquals(studentList.size(),47);
        assertEquals(teacherList.size(),8);

    }
}