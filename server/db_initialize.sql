create table t_roles
(id integer not null,
 name varchar2(255) not null,
 CONSTRAINT ROLES_PK primary key (id)
);
 
CREATE TABLE USERS 
(id integer NOT NULL,
 name varchar2(255) not null,
 username varchar2(255) not null,
 password varchar2(255) not null,
 CONSTRAINT USERS_PK primary key (id)
);

CREATE TABLE users_roles 
(user_id integer NOT NULL,
 role_id integer NOT NULL,
 CONSTRAINT users_roles_PK primary key (user_id, role_id),
 constraint users_roles_users_fk
    FOREIGN KEY (user_id)
    REFERENCES USERS (id)  ,
 constraint users_roles_roles_fk
    FOREIGN KEY (role_id)
    REFERENCES t_roles (id)  
);
CREATE TABLE TASKS 
(id integer NOT NULL,
 title varchar2(255) not null,
 owner integer not null,
 executor integer not null,
 description varchar2(255) not null,
 status varchar2(255) not null,
 CONSTRAINT TASKS_PK primary key (id),
 constraint tasks_users_owner_fk
    FOREIGN KEY (owner)
    REFERENCES USERS (id)  ,
 constraint tasks_users_executor_fk
    FOREIGN KEY (executor)
    REFERENCES USERS (id)  
);
create sequence s_task_id;
create sequence s_user_id;
insert into t_roles values (1, 'ROLE_ADMIN');
insert into t_roles values (2, 'ROLE_USER');