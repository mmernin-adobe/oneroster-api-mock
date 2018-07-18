package com.dm.onerosterapi.controller;

import com.dm.onerosterapi.exceptions.ClassOfCourseNotFoundException;
import com.dm.onerosterapi.exceptions.UserNotFoundException;
import com.dm.onerosterapi.model.ClassOfCourse;
import com.dm.onerosterapi.model.User;
import com.dm.onerosterapi.service.interfaces.ClassService;
import com.dm.onerosterapi.service.interfaces.UserService;
import com.dm.onerosterapi.utility.AllowedTypes;
import com.dm.onerosterapi.utility.ApiResponseHandler;
import com.dm.onerosterapi.utility.SimplePage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "Class Controller", description = "Set of endpoints for reading Classes")
public class ClassController {

    private ClassService classService;
    private UserService userService;

    @Autowired
    ClassController(ClassService classService, UserService userService) {
        this.userService = userService;
        this.classService = classService;
    }

    @RequestMapping(value = "/classes", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return collection of classes.",
            response = ClassOfCourse.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success",
                    response = ClassOfCourse.class, responseContainer = "List")
    })
    public List<?> getAllClasses() throws ClassOfCourseNotFoundException {
        return classService.getAllClasses();
    }

    @RequestMapping(value = "/classes/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return specific class.", response = ClassOfCourse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassOfCourse.class)
    })
    public Object getClassById(
            @ApiParam(value = "SourcedId of Class to be selected", required = true)
            @PathVariable("id") String id
    ) throws ClassOfCourseNotFoundException {
        return classService.getBySourcedId(id);
    }

    @RequestMapping(value = "/classes/{id}/students", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return the collection of students that are taking this class.",
            response = User.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = User.class, responseContainer = "List")
    })
    public Object getStudentsForClass(
            @ApiParam(value = "SourcedId of Class to be selected", required = true)
            @PathVariable("id") String id,
            @RequestParam("offset") Optional<Integer> offset,
            @RequestParam("limit") Optional<Integer> limit)
            throws UserNotFoundException, ClassOfCourseNotFoundException {

        SimplePage p = new SimplePage(offset, limit, AllowedTypes.User);
        return ApiResponseHandler
                .buildApiResponse(userService.getStudentsByClass(id, p.getOffset(), p.getLimit()), p);
    }

    @RequestMapping(value = "/classes/{id}/teachers", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return the collection of teachers that are teaching this class.",
            response = User.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = User.class, responseContainer = "List")
    })
    public Object getTeachersForClass(
            @ApiParam(value = "SourcedId of Class to be selected", required = true)
            @PathVariable("id") String id,
            @RequestParam("offset") Optional<Integer> offset,
            @RequestParam("limit") Optional<Integer> limit)
            throws UserNotFoundException, ClassOfCourseNotFoundException {

        SimplePage p = new SimplePage(offset, limit, AllowedTypes.User);
        return ApiResponseHandler
                .buildApiResponse(userService.getTeachersByClass(id, p.getOffset(), p.getLimit()), p);
    }
}
