package com.dm.onerosterapi.controller;

import com.dm.onerosterapi.exceptions.ClassOfCourseNotFoundException;
import com.dm.onerosterapi.exceptions.InvalidParameterException;
import com.dm.onerosterapi.exceptions.UserNotFoundException;
import com.dm.onerosterapi.model.ClassOfCourse;
import com.dm.onerosterapi.model.SimplePage;
import com.dm.onerosterapi.model.User;
import com.dm.onerosterapi.service.interfaces.ClassService;
import com.dm.onerosterapi.service.interfaces.UserService;
import com.dm.onerosterapi.utility.ApiResponseBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Validated
@Api(tags = "User Controller", description = "Set of endpoints for reading Users")
public class UserController {

    private UserService userService;
    private ClassService classService;


    @Autowired
    UserController(UserService userService, ClassService classService) {
        this.userService = userService;
        this.classService = classService;
    }

    @ApiOperation(value = "Return collection of users.", response = User.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = User.class, responseContainer = "List")
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public Object getAllUsers(
            @RequestParam("offset") Optional<String> offset,
            @RequestParam("limit") Optional<String> limit,
            @ApiIgnore HttpServletRequest request)
            throws UserNotFoundException, InvalidParameterException {

        SimplePage p = new SimplePage(request);
        return ApiResponseBuilder
                .buildApiResponse(userService.getAllUsers("any", p.getOffset(), p.getLimit()), p);

    }

    @RequestMapping(value = "/students", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return collection of students.", response = User.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = User.class, responseContainer = "List")
    })
    public Object getAllStudents(
            @RequestParam("offset") Optional<String> offset,
            @RequestParam("limit") Optional<String> limit,
            @ApiIgnore HttpServletRequest request)
            throws UserNotFoundException, InvalidParameterException {
        SimplePage p = new SimplePage(request);
        return ApiResponseBuilder
                .buildApiResponse(userService.getAllUsers("student", p.getOffset(), p.getLimit()), p);
    }

    @RequestMapping(value = "/teachers", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return collection of teachers.", response = User.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = User.class, responseContainer = "List")
    })
    public Object getAllTeachers(
            @RequestParam("offset") Optional<String> offset,
            @RequestParam("limit") Optional<String> limit,
            @ApiIgnore HttpServletRequest request)
            throws UserNotFoundException, InvalidParameterException {
        SimplePage p = new SimplePage(request);
        return ApiResponseBuilder
                .buildApiResponse(userService.getAllUsers("teacher", p.getOffset(), p.getLimit()), p);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return specific user.", response = User.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = User.class, responseContainer = "List")
    })
    public Object getUserById(
            @ApiParam(value = "String of the User to be selected.", required = true)
            @PathVariable("id") String id
    ) throws UserNotFoundException {
        return userService.getUserBySourcedId(id);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return specific student.", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = User.class)
    })
    public Object getStudentById(
            @ApiParam(value = "SourcedId of Student to be selected.", required = true)
            @PathVariable("id") String id
    ) throws UserNotFoundException {
        return userService.getStudentBySourcedId(id);
    }

    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return specific teacher.", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = User.class)
    })
    public Object getTeacherById(
            @ApiParam(value = "SourcedId of Teacher to be selected.", required = true)
            @PathVariable("id") String id
    ) throws UserNotFoundException {
        return userService.getTeacherBySourcedId(id);
    }

    @RequestMapping(value = "/users/{id}/classes", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return the collection of classes attended by this user.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = ClassOfCourse.class, responseContainer = "List")
    })
    public Object getClassesForUser(
            @ApiParam(value = "SourcedId of User to select Classes from.", required = true)
            @PathVariable("id") String id,
            @RequestParam("offset") Optional<String> offset,
            @RequestParam("limit") Optional<String> limit,
            @ApiIgnore HttpServletRequest request)
            throws ClassOfCourseNotFoundException, UserNotFoundException, InvalidParameterException {

        SimplePage p = new SimplePage(request);
        return ApiResponseBuilder
                .buildApiResponse(classService.getClassesByUser(id,"any", p.getOffset(), p.getLimit()), p);

    }

    @RequestMapping(value = "/students/{id}/classes", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return the collection of classes that this student is taking.",
            response = ClassOfCourse.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = ClassOfCourse.class, responseContainer = "List")
    })
    public Object getClassesForStudent(
            @ApiParam(value = "SourcedId of the Student to select Classes from", required = true)
            @PathVariable("id") String id,
            @RequestParam("offset") Optional<String> offset,
            @RequestParam("limit") Optional<String> limit,
            @ApiIgnore HttpServletRequest request)
            throws ClassOfCourseNotFoundException, UserNotFoundException, InvalidParameterException {

        SimplePage p = new SimplePage(request);
        return ApiResponseBuilder
                .buildApiResponse(classService.getClassesByUser(id,"student", p.getOffset(), p.getLimit()), p);
    }

    @RequestMapping(value = "/teachers/{id}/classes", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return the collection of classes that this teacher is teaching.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = ClassOfCourse.class, responseContainer = "List")
    })
    public Object getClassesForTeachers(
            @ApiParam(value = "SourcedId of the Teacher to select Classes from", required = true)
            @PathVariable("id") String id,
            @RequestParam("offset") Optional<String> offset,
            @RequestParam("limit") Optional<String> limit,
            @ApiIgnore HttpServletRequest request)
            throws ClassOfCourseNotFoundException, UserNotFoundException, InvalidParameterException {

        SimplePage p = new SimplePage(request);
        return ApiResponseBuilder
                .buildApiResponse(classService.getClassesByUser(id,"teacher", p.getOffset(), p.getLimit()), p);

    }

}
