package com.geekbrains.server.mappers;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.server.entities.Task;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { UserMapper.class })
public interface TaskMapper {
    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);

    Task toTask(TaskDto taskDto);

    @InheritInverseConfiguration
    TaskDto fromTask(Task task);
}
