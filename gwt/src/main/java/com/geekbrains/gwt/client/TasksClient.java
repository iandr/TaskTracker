package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Path("/tasks")
public interface TasksClient extends RestService {

    @GET
    void getAllTasks(MethodCallback<List<TaskDto>> tasks);

    @DELETE
    @Path("{id}")
    void removeTask(@PathParam("id") String id, MethodCallback<Void> result);

}
