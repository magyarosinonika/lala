create table Dependency ( 
    id INT auto_increment PRIMARY KEY,
    condi VARCHAR(100), 
    status int(2) not null default 0 ,
    insertToken VARCHAR(600) 
); 

create table DependencyColumn( 
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dependencyId INT , columnName VARCHAR(1024),
    isDeterminantColumn tinyInt(1),
    value text default null,
    foreign key (dependencyId) references Dependency (id)
);

create table DependencySelected ( 
    id INT auto_increment PRIMARY KEY,
    condi VARCHAR(100), 
    status int(2) not null default 0 ,
    insertToken VARCHAR(600) 
); 

create table DependencyColumnSelected( 
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dependencyId INT , columnName VARCHAR(1024),
    isDeterminantColumn tinyInt(1),
    value text default null,
    foreign key (dependencyId) references Dependency (id)
);