create sequence hibernate_sequence start with 120 increment by 1;

create table Address
(
    id      bigint not null,
    city    varchar(255),
    street  varchar(255),
    zipCode varchar(255),
    primary key (id)
);

create table Customer
(
    id          bigint not null,
    dateOfBirth date,
    firstName   varchar(255),
    lastName    varchar(255),
    address_id  bigint,
    primary key (id)
);

create table Employee
(
    id            bigint not null,
    dateOfBirth   date,
    firstName     varchar(255),
    lastName      varchar(255),
    address_id    bigint,
    hourlyRate_id bigint,
    primary key (id)
);

create table HourlyRate
(
    id               bigint not null,
    employeeCategory varchar(255),
    rate             double not null,
    ryear            integer,
    primary key (id)
);

create table LogbookEntry
(
    id          bigint not null,
    activity    varchar(255),
    costType    varchar(255),
    endTime     timestamp,
    startTime   timestamp,
    employee_id bigint,
    project_id  bigint,
    primary key (id)
);

create table Project
(
    id          bigint not null,
    name        varchar(255),
    customer_id bigint,
    primary key (id)
);

create table Project_Employee
(
    projects_id bigint not null,
    members_id  bigint not null,
    primary key (projects_id, members_id)
);

alter table Customer
    add constraint FKfok4ytcqy7lovuiilldbebpd9 foreign key (address_id) references Address;
alter table Employee
    add constraint FK759vmxo1jn0ql3orqinrieynp foreign key (address_id) references Address;
alter table Employee
    add constraint FK9w3n5bd8mjtisavdle3rolg3p foreign key (hourlyRate_id) references HourlyRate;
alter table LogbookEntry
    add constraint FK375u29uup72hx3b91ff2bbmib foreign key (employee_id) references Employee;
alter table LogbookEntry
    add constraint FKoj4t3llhfe7pwi9mr1s1lhsnr foreign key (project_id) references Project;
alter table Project
    add constraint FKsbm64qmwf17w5fdaueknmxbji foreign key (customer_id) references Customer;
alter table Project_Employee
    add constraint FKd53k4mv84jrifvlpk4iccc0xw foreign key (members_id) references Employee;
alter table Project_Employee
    add constraint FKb9hoqfgcsa2nmlavaxaeid6hp foreign key (projects_id) references Project;