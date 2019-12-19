package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/tasks")
public interface TasksClient extends RestService {

    @GET
    void getAllTasks(@HeaderParam("Authorization") String token, MethodCallback<List<TaskDto>> tasks);

    @DELETE
    @Path("{id}")
    void removeTask(@HeaderParam("Authorization") String token, @PathParam("id") String id, MethodCallback<Void> result);

    @POST
    void createTask(@HeaderParam("Authorization") String token, TaskDto taskDto, MethodCallback<Void> result);

}
