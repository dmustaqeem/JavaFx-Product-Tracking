create database productTracking 




--1)
create table customer
(
	username nvarchar(50) not null,
	constraint pk_username primary key(username), 
	cnic nvarchar(50),
	age int,
	uName nvarchar(50),
	uPassword nvarchar(50),
	cAddress nvarchar(200),
	phone nvarchar(10),
	registrationDate datetime
)



--2)
create table account
(
	username nvarchar(50),
	foreign key (username) references customer(username),
	credit money,
	balance money,
	accountNo int identity(1,1) not null,
	constraint pk_accNo primary key(accountNo),
	creationDate datetime,
	debit money 
)


--3)
create table product
(
	p_id int identity(1,1) not null,
	constraint pk_pid primary key(p_id),
	pType nvarchar(50),
	pName nvarchar(50),
	costPerUnit money,
	qty int,
	lastStockUpdate datetime,
	warehouseID nvarchar(200),
	foreign key (warehouseID) references warehouse(w_location)
)

--4)
create table orders
(
	o_id int Identity(1,1) not null primary key,
	p_id int,
	foreign key (p_id) references product(p_id),
	username nvarchar(50) not null,
	foreign key (username) references customer(username),
	o_qty int,
	bill money,
	orderDate datetime,
	o_status nvarchar(20),
	--WAIT, LOTTED, NOT-DISPATCHED, DILIVERED
	destination nvarchar(200)
)



--5)
create table manager
(
	m_cnic nvarchar(50) not null,
	constraint pk_mcnic primary key(m_cnic),
	m_name nvarchar(50),
	m_phone nvarchar(10),
	m_address nvarchar(200),
	m_age int,
	m_employmentDate datetime,
	m_password nvarchar(50) not null,
   SysStartTime datetime2 GENERATED ALWAYS AS ROW START NOT NULL,
   SysEndTime datetime2 GENERATED ALWAYS AS ROW END NOT NULL,
   PERIOD FOR SYSTEM_TIME (SysStartTime,SysEndTime)) WITH (SYSTEM_VERSIONING = ON (HISTORY_TABLE = dbo.ManagerHistory, DATA_CONSISTENCY_CHECK = ON)
)

--6)
create table transporter
(
	lisence nvarchar(50),
	constraint pk_Lisence primary key (lisence), 
	t_name nvarchar(50),
	t_cnic nvarchar(50),
	t_address nvarchar(200),
	t_phone nvarchar(10),
	SysStartTime datetime2 GENERATED ALWAYS AS ROW START NOT NULL,
    SysEndTime datetime2 GENERATED ALWAYS AS ROW END NOT NULL,
    PERIOD FOR SYSTEM_TIME (SysStartTime,SysEndTime)) WITH (SYSTEM_VERSIONING = ON (HISTORY_TABLE = dbo.TransporterHistory, DATA_CONSISTENCY_CHECK = ON),
	password nvarchar(50)
)

alter table transporter add tPassword nvarchar(50) 


--7)
create table truck
(
	plate nvarchar(10) not null,
	constraint pk_plate primary key(plate),
	Tname nvarchar(50),
	company nvarchar(50),
	model int,
	purchaseDate datetime,
	SysStartTime datetime2 GENERATED ALWAYS AS ROW START NOT NULL,
    SysEndTime datetime2 GENERATED ALWAYS AS ROW END NOT NULL,
    PERIOD FOR SYSTEM_TIME (SysStartTime,SysEndTime)) WITH (SYSTEM_VERSIONING = ON (HISTORY_TABLE = dbo.TruckHistory, DATA_CONSISTENCY_CHECK = ON)
)

--8)
--warehouse is dependend on transporter, manager, product,warehousetype
--rev 1.0 
create table warehouse
(
	w_name nvarchar(50)not null,
	manager_id nvarchar(50),
	foreign key(manager_id) references manager(m_cnic),
	capacity int,
	warehousetype nvarchar(50),
	w_location nvarchar(200) not null primary key,
	next_location nvarchar(200),
	previous_location nvarchar(200),
	distance_from_prev_loc int,
	distance_from_next_loc int,
	collectorPoint bit,
	SysStartTime datetime2 GENERATED ALWAYS AS ROW START NOT NULL,
    SysEndTime datetime2 GENERATED ALWAYS AS ROW END NOT NULL,
    PERIOD FOR SYSTEM_TIME (SysStartTime,SysEndTime)) WITH (SYSTEM_VERSIONING = ON (HISTORY_TABLE = dbo.WarehouseHistory, DATA_CONSISTENCY_CHECK = ON)
)


--0 if not Collector Point 1 if it is Collector Point 
--9)
create table lot
(
	lotId int identity(1,1),
	constraint pk_lot primary key(lotId),
	creationDate datetime,
	capacity int,
	transporter_id nvarchar(50),
	foreign key(transporter_id) references transporter(lisence),
	plate nvarchar(10),
	foreign key(plate) references truck(plate),
	timeSlot nvarchar(20),
	occupied int 
)
alter table lot add l_status nvarchar(50)

--Rev 1.0 



--10)
--Rev 1.0 
--surrogate key added 
--current location reffer to collector point and warehouse 
create table transporterLocation
(
	tLocationId int identity(1,1) not null primary key,
	Current_Location nvarchar(200),
	foreign key (Current_Location) references warehouse(w_location),
	t_lisence nvarchar(50),
	foreign key (t_lisence) references transporter(lisence),
	reaching_time datetime,
	plate nvarchar(10),
	foreign key(plate) references truck(plate),
	lot_id int,
	foreign key(lot_id) references lot(lotId)
)




--11)
create table orderLot
(
	lotId int,
	foreign key (lotId) references lot(lotId),
	orderId int,
	foreign key (orderId) references orders(o_id),
	constraint pk_orlot primary key(lotId,orderId),
	additionDate datetime,
	managerId nvarchar(50),
	foreign key (managerId) references manager(m_cnic)
)

--12)
create table notify_costumer
(
	m_id nvarchar(50),
	foreign key(m_id) references manager(m_cnic),
	n_id int identity(1,1) not null primary key,
	c_id nvarchar(50),
	foreign key(c_id) references customer(username),
	txt nvarchar(1000),
	nDate datetime,
	nc_status nvarchar(20),
	--NOTIFY STATUS 
	--READ, NOT READ 
	lotId int,
	foreign key(lotId) references lot(lotId)
)

--13)
create table notify_transporter
(
    n_tM int identity(1,1) primary key,
	t_cnic nvarchar(50),
	foreign key(t_cnic) references transporter(lisence),
	m_id nvarchar(50),
	foreign key (m_id) references manager(m_cnic),
	txt_msg nvarchar(1000),
	nTDate datetime,
	nStatus nvarchar(20),
	--NOTIFY STATUS 
	--READ, NOT-READ
	lotId int,
	foreign key(lotId) references lot(lotId)
)

alter table notify_transporter add lotId int foreign key(lotId) references lot(lotId) 



drop table notify_costumer
drop table notify_transporter
drop table transporterLocation
drop table orderLot 
drop table orders 
drop table lot 
drop table truck 
drop table transporter
drop table product 
drop table warehouse 
drop table manager 