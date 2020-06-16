use productTracking


--Prodecdures 


--1) Add Customer and Account 
alter procedure costumerP (@Lusername as nvarchar(50), @Lcnic as nvarchar(50), @Lage as int, @Lname as nvarchar(50), @Lpassword as nvarchar(50), @Laddress as nvarchar(200), @Lphone as nvarchar(10))
as begin 

begin transaction costumertransaction
begin try 

insert into customer values(@Lusername, @Lcnic,@Lage,@Lname,@Lpassword,@Laddress,@Lphone,GETDATE())
insert into account values(@Lusername,0,0,GETDATE(),0)
commit transaction
end try 

begin catch 
rollback transaction costumertransaction
end catch 

end

--edit password
create procedure editCustomerPassword @password as nvarchar(50), @username as nvarchar(50)
as begin 
	update customer set customer.uPassword = @password where customer.username = @username
end

--edit address

alter procedure editCustomerAddress @address as nvarchar(200),@username as nvarchar(50)
as begin 
	update customer set customer.cAddress = @address where customer.username = @username
end

--edit phone

create procedure editCustomerPhone @username as nvarchar(50), @phone as nvarchar(10)
as begin 
	update customer set customer.phone = @phone where customer.username = @username
end 

--edit username

alter procedure editUsername @username as nvarchar(50), @newUsername as nvarchar(50)
as begin 
	update customer set customer.username = @newUsername where customer.username = @username
end 

--delete customer 
create procedure deleteCustomer @username as nvarchar(50)
as begin 

begin transaction delCustomer
begin try 
	delete from customer where customer.username = @username
commit transaction
end try 

begin catch 
	rollback transaction delCustomer 
end catch
end 

select * from customer 
exec editUsername 'rafia','rafzz'

--2) Order Creation
alter procedure orderCreation @O_pid as int,@O_quantity as int,@O_username as nvarchar(50), @destin as nvarchar(200)
as begin 

begin transaction orderT
begin try 

declare @quantity_p as int
declare @billing as money  
declare @unitC as int 
declare @w_id as nvarchar(200)
declare @price as money
declare @cost as money

select @quantity_p = product.qty, @unitC = product.costPerUnit, @cost = product.costPerUnit  from product where p_id = @O_pid

if @quantity_p >= @O_quantity
begin 
	update product set product.qty = product.qty - @O_quantity, product.lastStockUpdate = getDate() where product.p_id = @O_pid 
	insert into orders values(@O_pid,@O_username,@O_quantity,@cost * @O_quantity, getDate(),'Wait',@destin)
	update account set account.debit = account.debit + (@cost * @O_quantity) where @O_username = account.username
end

if @quantity_p < @O_quantity and @quantity_p != 0
begin 
	insert into orders values(@O_pid,@O_username,@O_quantity,@price * @quantity_p, getDate(),'Wait',@destin)
	update product set product.qty = 0, product.lastStockUpdate = getDate() where product.p_id = @O_pid 
	update account set account.debit = account.debit + (@cost * @O_quantity) where @O_username = account.username
end 

commit transaction 
end try

begin catch 
	rollback transaction orderT
end catch
end

--delete order
ALTER procedure [dbo].[deleteOrder] @orderId as int 
as begin
	begin transaction delOrder
	begin try

	delete from orderLot where orderLot.orderId = @orderId
	delete from orders where orders.o_id = @orderId

	commit transaction
	end try

	begin catch 
		rollback transaction delOrder
	end catch
end 


--3) Lot Creation 
alter procedure lotCreation @transporter_id as nvarchar(50), @plate as nvarchar(10), @Lcapacity as int
as begin

begin transaction lot_assignment 
begin try

declare @lotsInDay as int 
declare @numOfP as int 

select @lotsInDay = count(lot.lotId) from lot where convert(varchar, lot.creationDate, 1) = convert(varchar, getdate(), 1)
if @lotsInDay = 0 and datepart(hour,getDate()) < 15
begin
  insert into lot values (getDate(),@Lcapacity, @transporter_id,@plate,'Morning',0,'Not Dilivered')
end
if @lotsInDay = 1 
begin
  insert into lot values (getDate(),@Lcapacity, @transporter_id,@plate,'Evening',0,'Not Dilivered')
end 

if @lotsInDay = 0 and datepart(hour,getDate()) >= 15
begin 
	insert into lot values (getDate(),@Lcapacity, @transporter_id,@plate,'Evening',0,'Not Dilivered')
end 
commit transaction
end try 
	
begin catch 
	rollback transaction lot_assignment 
end catch
end 
--set as dilivered
create procedure setDilivered @lotId as int 
as begin 

begin transaction dilivered
begin try
	update lot set lot.l_status = 'Dilivered' where lot.lotId = @lotId
commit transaction
end try

begin catch 
rollback transaction dilivered 
end catch
end 

--update transporter Id
alter procedure updateTransporter @lotId as int, @transporterId as nvarchar(50)
as begin 
begin transaction updateT
begin try 
declare @alreadyAss as nvarchar(50)
	select @alreadyAss = lot.transporter_id from lot where lot.transporter_id = @transporterId
	if @alreadyAss is null
	begin 
		update lot set lot.transporter_id = @transporterId where lot.lotId = @lotId
	end 
commit transaction
end try 

begin catch 
	rollback transaction updateT
end catch 

end 

exec updateTransporter 2, '35201'

--update lot truck 
alter procedure updateTruck @lotId as int, @truckId as nvarchar(10)
as begin 
begin transaction updateT
begin try 
declare @alreadyAss as nvarchar(10) 
	select @alreadyAss = lot.plate from lot where lot.plate = @truckId
	if @alreadyAss is null
	begin 
		update lot set lot.plate = @truckId where lot.lotId = @lotId
	end 
commit transaction
end try 

begin catch 
	rollback transaction updateT
end catch 

end 


select * from lot 
select * from truck
exec updateTruck 2,'3617'




--4) Lot Assignment Against the Order 
alter procedure ord_lot_assignment @managerId as nvarchar(50),@M_LotId as int 
as begin

declare @con_oid int 
declare @num_of_prod int
declare @count_oid as int 
declare @warehouse_id as nvarchar(200)
declare @ppId nvarchar(50)
declare @qqty int 
select @count_oid = count(o_id) from orders where orders.o_status = 'Wait' 
and orders.p_id in 
(select product.p_id from product where product.warehouseID in
(select warehouse.w_location from warehouse where warehouse.manager_id = @managerId))


select @num_of_prod = lot.capacity from lot where lot.lotId = @M_LotId

select @warehouse_id = warehouse.w_location from warehouse where warehouse.manager_id = @managerId
declare @lot_status as nvarchar(50)
declare @counter as int = 1

while @counter <= @count_oid
begin

		if @counter > @num_of_prod
			break;


	select top 1 @con_oid = o_id from orders where orders.o_status = 'Wait' and orders.p_id in 
		(select product.p_id from product where product.warehouseID in
		(select warehouse.w_location from warehouse where warehouse.manager_id = @managerId))

	select @qqty = orders.o_qty from orders where orders.o_id = @con_oid

	select @lot_status = lot.l_status from lot where lot.lotId = @M_LotId
	if @lot_status = 'Not Dilivered'
	begin
	insert into orderLot values(@M_LotId,@con_oid,getDate(),@managerId)
	update orders set orders.o_status = 'Lotted' where orders.o_id = @con_oid
	update lot set occupied = occupied + @qqty where lot.lotId = @M_LotId
	end

	set @counter = @counter + 1
end;
end



--4) create a procedure to add new truck 

alter procedure addTruck @plate as nvarchar(10),@t_name as nvarchar(50),@t_company as nvarchar(50),@t_mdl as int 
as begin 
begin transaction addingTruck 
begin try 
	insert into truck(plate,Tname,company,model,purchaseDate) values(@plate,@t_name,@t_company,@t_mdl,getDate())
commit transaction
end try 
begin catch 
	rollback transaction addingTruck 
end catch 
end 

--delete truck
create procedure deleteTruck @plate as nvarchar(10)
as begin 
begin transaction delTruck
begin try
	delete from truck where truck.plate = @plate
commit transaction
end try

begin catch 
	rollback transaction delTruck 
end catch 
end 

exec deleteTruck '3617'
select * from truck 
select * from product
select * from transporter 
--5) Adding manager
alter procedure addManager @cnic as nvarchar(50),@m_name as nvarchar(50), @m_phone as nvarchar(10),@m_address as nvarchar(200) ,@m_age as int,@m_password as nvarchar(50)
as begin 
	insert into manager(manager.m_cnic,m_name,m_phone,m_address,m_age,m_employmentDate,m_password) values(@cnic,@m_name,@m_phone,@m_address,@m_age,getDate(),@m_password)
end 

select * from manager 

--6)adding trasporter 
alter procedure addTransporter @cnic as nvarchar(50),@tr_name as nvarchar(50), @tr_lisence as nvarchar(50),@tr_address as nvarchar(200) ,@tr_phone as nvarchar(10),@pass as nvarchar(50)
as begin 

begin transaction addingTransporter
begin try 
	insert into transporter(lisence,t_name,t_cnic,t_address,t_phone,tPassword) values(@tr_lisence,@tr_name,@cnic,@tr_address,@tr_phone,@pass)
commit transaction
end try 
	
begin catch 
	rollback transaction addingTransporter
end catch 
end 

--get currently assigned lot 
create procedure getCurrentAssignedLot @lisence as nvarchar(50)
as begin
begin transaction getLot
begin try
	select lot.lotId from lot where lot.transporter_id = @lisence and lot.l_status = 'Not Dilivered'
commit transaction 
end try

begin catch 
	rollback transaction 
end catch 
end 
--update transporter password
create procedure updateTransporterPassword @lisence as nvarchar(50), @newPass as nvarchar(50)
as begin 
begin transaction updateTPass
begin try
	update transporter set transporter.tPassword = @newPass where transporter.lisence = @lisence
commit transaction
end try

begin catch
	rollback transaction updateTPass
end catch
end

--update transporter address
create procedure updateTransporterAddress @lisence as nvarchar(50), @address as nvarchar(200)
as begin 
begin transaction updateTAdd
begin try
	update transporter set transporter.t_address = @address where transporter.lisence = @lisence
commit transaction
end try

begin catch
	rollback transaction updateTAdd
end catch
end


--update transporter phone number
create procedure updateTransporterPhone @lisence as nvarchar(50), @phone as nvarchar(10)
as begin 
begin transaction updateTPh
begin try
	update transporter set transporter.t_phone = @phone where transporter.lisence = @lisence
commit transaction
end try

begin catch
	rollback transaction updateTPh
end catch
end

--delete transporter
alter procedure deleteTransporter @lisence as nvarchar(50)
as begin 
begin transaction delTrans
begin try
	delete from notify_transporter where notify_transporter.t_cnic = @lisence
	delete from transporterLocation where transporterLocation.t_lisence = @lisence
	delete from lot where lot.transporter_id = @lisence
	delete from transporter where transporter.lisence = @lisence
commit transaction 
end try

begin catch 
	rollback transaction delTrans
end catch
end 

select * from notify_transporter
select * from lot 
select * from transporter
exec deleteTransporter '45201'

select * from transporterLocation

select * from transporter

--7)adding warehouse 
--0 if warehouse 
--1 if is Collector Point 
alter procedure addWarehouse @name as nvarchar(50),@wmanager_Id as nvarchar(50), @w_capacity as int, @w_type as nvarchar(50) ,@w_location as nvarchar(200), @wnext_location as nvarchar(200), @wprevious_location as nvarchar(200), @wdist_from_n as int, @wdist_from_p as int, @isCollector as bit
as begin 

begin transaction addingWarehouse
begin try 

declare @countManagers as int = 0
select @countManagers = count(warehouse.manager_id) from warehouse where warehouse.manager_id = @wmanager_Id

if @wnext_location is not NULL
begin 
	update warehouse set warehouse.previous_location = @w_location, warehouse.distance_from_prev_loc = @wdist_from_p where warehouse.w_location = @wnext_location 
end 

if @wprevious_location is not NULL
begin 
	update warehouse set warehouse.next_location = @w_location, warehouse.distance_from_next_loc = @wdist_from_n where warehouse.w_location = @wprevious_location
end
if @countManagers = 0
begin
	insert into warehouse(w_name,manager_id,capacity,warehousetype,w_location,next_location,previous_location,distance_from_prev_loc,distance_from_next_loc,collectorPoint) values(@name,@wmanager_Id,@w_capacity,@w_type,@w_location,@wnext_location,@wprevious_location, @wdist_from_p ,@wdist_from_n,@isCollector)
end
commit transaction
end try 
	
begin catch 
	rollback transaction addingWarehouse
end catch 
end 


--delete Warehouse Procedure 
alter procedure deleteWarehouse @currLocation as nvarchar(200)
as begin
declare @prevLocaiton as nvarchar(200)
declare @nextLocation as nvarchar(200)
declare @distNext as int 
declare @distPrev as int 

select @prevLocaiton = warehouse.previous_location, @nextLocation = warehouse.next_location, @distNext = warehouse.distance_from_next_loc, @distPrev = warehouse.distance_from_prev_loc from warehouse where warehouse.w_location = @currLocation
if @prevLocaiton is NULL and @nextLocation is not NULL
begin 
   update warehouse set warehouse.previous_location = NULL,warehouse.distance_from_prev_loc = NULL where warehouse.w_location = @nextLocation
end 
if @nextLocation is NULL and @prevLocaiton is not NULL
begin 
	update warehouse set warehouse.next_location = NULL, warehouse.distance_from_next_loc = NULL where warehouse.w_location = @prevLocaiton
end
if @nextLocation is not NUll and @prevLocaiton is not NULL
begin 
	update warehouse set warehouse.next_location = @nextLocation, warehouse.distance_from_next_loc = @distNext + @distPrev where warehouse.w_location = @prevLocaiton
	update warehouse set warehouse.previous_location = @prevLocaiton, warehouse.distance_from_prev_loc = @distNext + @distPrev where warehouse.w_location = @nextLocation
end
delete from warehouse where warehouse.w_location = @currLocation
end 


--Set new Manager to Warehouse 
alter procedure setNewManager @managerId as nvarchar(50), @wLocation as nvarchar(200)
as begin 
begin transaction settingManager 
begin try 
declare @countManagers as int = 0
select @countManagers = count(warehouse.manager_id) from warehouse where warehouse.manager_id = @managerId 
if @countManagers = 0
begin
	update warehouse set warehouse.manager_id = @managerId where warehouse.w_location = @wLocation
end
commit transaction 
end try 
begin catch
	rollback transaction settingManager 
end catch 
end 


--8) Addition of Products 
alter procedure addProducts @p_Type as nvarchar(50), @p_Name as nvarchar(50), @p_costPerUnit as money, @p_qty as int, @pw_id as nvarchar(200)
as begin
begin transaction addingProducts
begin try 
declare @typeP as nvarchar(50)
	select @typeP = warehouse.warehousetype from warehouse where warehouse.w_location = @pw_id
	
	if @typeP = @p_Type
	begin
	insert into product values(@p_Type,@p_Name,@p_costPerUnit,@p_qty,getDate(),@pw_id)
	end

commit transaction
end try 
	
begin catch 
	rollback transaction addingProducts 
end catch 
end 

--delete product 
create procedure deleteProduct @productID as int 
as begin

begin transaction delProd
begin try
	delete from orders where orders.p_id = @productID
	delete from product where product.p_id = @productID
commit transaction
end try

begin catch
	rollback transaction delProd
end catch
end




--9)adding trasnporter location in Tlocation Table 

alter procedure addTLocation @tt_currentLocation as nvarchar(200), @tLisence as nvarchar(50), @tLplate as nvarchar(10),@trLot_id as int
as begin
begin transaction addingTLocation
begin try 
declare @totalNotifications as int 
declare @status as nvarchar(50)
declare @chk as bit = 0
declare @count as int = 1

select @totalNotifications = count(n_tM) from notify_transporter where @tLisence = notify_transporter.t_cnic and @trLot_id = notify_transporter.lotId

if @totalNotifications = 0
begin 
	insert into transporterLocation values (@tt_currentLocation,@tLisence,getdate(),@tLplate,@trLot_id)
end 

else
begin

while @count <@totalNotifications
begin
    select @status = notify_transporter.nStatus from notify_transporter where @tLisence = notify_transporter.t_cnic and @trLot_id = notify_transporter.lotId
    if @status = 'Unread'
	begin
		set @chk = 1
		break;
	end 
	set @count = @count + 1  
end;
if @chk = 0
begin 
	insert into transporterLocation values (@tt_currentLocation,@tLisence,getdate(),@tLplate,@trLot_id)
end

end
commit transaction 
end try 

begin catch 
	rollback transaction addingTLocation 
end catch 
end;


create trigger tranLoc1
on transporterLocation
after insert 
as 
begin 
declare @tr_id nvarchar(50)
declare @mr_id nvarchar(50)
declare @trcurrentL nvarchar(200)
declare @cp_number as bit
declare @lotid as int  
declare @orderDest as nvarchar(200)

select top 1 @tr_id = transporterLocation.t_lisence, @trcurrentL = Current_Location, @lotid = transporterLocation.lot_id from transporterLocation order by transporterLocation.tLocationId desc
select @mr_id = warehouse.manager_id from warehouse where warehouse.w_location = @trcurrentL
select @cp_number = warehouse.[collectorPoint] from warehouse where @trcurrentL = warehouse.w_location
select @orderDest = orders.destination from orders where orders.o_id = (select orderLot.orderId from orderLot where 5 = orderLot.lotId)

if @cp_number = 0
begin
  insert into notify_transporter values(@tr_id,@mr_id,'Lot is Reached At Warehouse',getdate(),'Unread',@lotid)
end

declare @count2 as int =0
declare @ordersDestin as int 
	

if @cp_number = 1
begin 
  insert into notify_transporter values(@tr_id,@mr_id,'Lot is Reached At Collector Point',getdate(),'Unread',@lotid)
 if @orderDest = @trcurrentL
 begin
  select @ordersDestin = count(orders.o_id) from orders where orders.o_id in
  (select orderLot.orderId from orderLot where @lotid = orderLot.lotId) and orders.destination = @trcurrentL

   update lot set lot.occupied = lot.occupied - @ordersDestin where lot.lotId = @lotid 

   end 
end 
end 

--delete notification from transporter
ALTER procedure [dbo].[deleteNotifTransporter] @notifId as int 
as begin 

	delete from notify_transporter where notify_transporter.n_tM = @notifId
end 


select * from transporterLocation

--10)notify the costumer 

alter procedure notifyCustomer @mr_id as nvarchar(50)
as begin 
declare @msg nvarchar(1000)
declare @numOfLots as int 
declare @chkLot as int 
declare @userName as nvarchar(50)
declare @currLocation as nvarchar(200)
declare @notifTranspId as int
declare @orderDest as nvarchar(200)
declare @ordsInLot as int 
declare @orderId as int 
declare @o_status as nvarchar(20)

select @numOfLots = count(notify_transporter.n_tM) from notify_transporter where notify_transporter.m_id = @mr_id and notify_transporter.nStatus = 'Unread'

declare @count int = 1

while @count <= @numOfLots
begin 
	select top 1 @chkLot = notify_transporter.lotId, @msg = notify_transporter.txt_msg, @notifTranspId = notify_transporter.n_tM  from notify_transporter where notify_transporter.m_id = @mr_id and notify_transporter.nStatus = 'Unread'
	
	select @ordsInLot = count(orderLot.orderId) from orderLot where @chkLot = orderLot.lotId 
	
	declare @count2 int = 1

	while @count2 <= @ordsInLot
	begin

	select @orderId = aa.orderId from  
	(select orderLot.orderId ,row_number() over(order by orderLot.orderId) as RowNumber from orderLot where orderLot.lotId = @chkLot)aa where @count2 = aa.RowNumber

	select @o_status = orders.o_status from orders where @orderId = orders.o_id

	
	if @o_status != 'Dilivered'
	begin
	select @userName = orders.username from orders where orders.o_id = @orderId
	select @currLocation = warehouse.w_location from warehouse where warehouse.manager_id = @mr_id
	select @orderDest = orders.destination from orders where orders.o_id = @orderId

	if @msg = 'Lot is Reached At Warehouse'
	begin
	insert into notify_costumer values(@mr_id,@userName,concat('The Product has Reached Warehouse in ', @currLocation),getDate(),'Unread',@chkLot)
	update notify_transporter set nStatus = 'Read' where @notifTranspId = notify_transporter.n_tM
	end 

	if @msg = 'Lot is Reached At Collector Point' 
	begin 
	update notify_transporter set nStatus = 'Read' where @notifTranspId = notify_transporter.n_tM

	if @orderDest = @currLocation
	begin
	insert into notify_costumer values(@mr_id, @userName,concat('The Product is Reached at Collector Point in ',@currLocation,' ...Please collect'),getDate(),'Unread',@chkLot)
	update orders set o_status = 'Dilivered' where orders.o_id = @orderId
	end
    end 
	
	end

	set @count2 = @count2 + 1
	end;

set @count = @count + 1 
end;
end 


--Notify Customer Simple 

alter procedure notifyCustomerAgainstLot @mr_id as nvarchar(50),@lotId as int, @notifTransporter as int 
as begin 
declare @msg nvarchar(1000)
declare @numOfLots as int  
declare @userName as nvarchar(50)
declare @currLocation as nvarchar(200)
declare @notifTranspId as int
declare @orderDest as nvarchar(200)
declare @ordsInLot as int 
declare @orderId as int 
declare @o_status as nvarchar(20)


	select @msg = notify_transporter.txt_msg from notify_transporter where notify_transporter.n_tM = @notifTransporter
	
	select @ordsInLot = count(orderLot.orderId) from orderLot where @lotId = orderLot.lotId 
	
	declare @count2 int = 1

	while @count2 <= @ordsInLot
	begin

	select @orderId = aa.orderId from  
	(select orderLot.orderId ,row_number() over(order by orderLot.orderId) as RowNumber from orderLot where orderLot.lotId = @lotId)aa where @count2 = aa.RowNumber

	select @o_status = orders.o_status from orders where @orderId = orders.o_id

	
	if @o_status != 'Dilivered'
	begin
	select @userName = orders.username from orders where orders.o_id = @orderId
	select @currLocation = warehouse.w_location from warehouse where warehouse.manager_id = @mr_id
	select @orderDest = orders.destination from orders where orders.o_id = @orderId

	if @msg = 'Lot is Reached At Warehouse'
	begin
	insert into notify_costumer values(@mr_id,@userName,concat('The Product has Reached Warehouse in ', @currLocation),getDate(),'Unread',@lotId)
	end 

	if @msg = 'Lot is Reached At Collector Point' 
	begin 

	if @orderDest = @currLocation
	begin
	insert into notify_costumer values(@mr_id, @userName,concat('The Product is Reached at Collector Point in ',@currLocation,' ...Please collect'),getDate(),'Unread',@lotId)
	update orders set o_status = 'Dilivered' where orders.o_id = @orderId
	end
    end 
	
	end

	set @count2 = @count2 + 1
	end;

end 

--set customer notification as read 
alter procedure setCustomerNotifRead @notifId as int 
as begin 
begin transaction setRead
begin try 
	update notify_costumer set nc_status = 'Read' where n_id = @notifId
commit transaction 
end try 

begin catch 
	rollback transaction setRead 
end catch 
end 

exec setCustomerNotifRead 1003


--delete customer notification 
create procedure deleteCustomerNotification @username as nvarchar(50), @ntID as int
as begin 
begin transaction delCustomerNotif
begin try 
	delete from notify_costumer where notify_costumer.c_id = @username and notify_costumer.n_id = @ntID
commit transaction 
end try

begin catch 
	rollback transaction delCustomerNotif
end catch
end 

exec deleteCustomerNotification 'ali',1004


--Set Notification Status from transporter 

create procedure setNotificationRead @notificationId as int
as begin 

begin transaction setAsRead
begin try 
	update notify_transporter set notify_transporter.nStatus = 'Read' where @notificationId = notify_transporter.n_tM
commit transaction
end try 

begin catch 
rollback transaction setAsRead
end catch 
end 


--11) to add credit in customer account 
create procedure addCredit @username as nvarchar(50),@credit as money 
as begin 

begin transaction 
begin try

update account set account.credit = account.credit + @credit where @username = account.username

commit transaction addingCredit
end try 

begin catch 
	rollback transaction 
end catch 

end 

create procedure updateAccount @username as nvarchar(50)
as begin 

begin transaction
begin try 
	
	declare @debit as money 
	declare @credit as money 
	select @debit = account.debit, @credit = account.credit from account where account.username = @username 

	if @debit >= @credit 
	begin 
		update account set account.debit = account.debit - @credit, account.credit = 0 where account.username = @username
	end 

	if @debit < @credit 
	begin
		update account set account.credit = account.credit - @debit, account.debit = 0 where account.username = @username
	end 

commit transaction 
end try 

begin catch 
	rollback transaction 
end catch 
end 




--there are 2 notifications generated against the customer when the transporter updates location to Collector Point 
--Customer Account Management where the amount of money will be subtracted from the customer account 
--Customer reading the notification 
select * from notify_costumer
select * from product
select * from customer 
select * from account 
select * from orders
select * from manager
select * from warehouse 
select * from transporter 
select * from transporterLocation
select * from notify_transporter
select * from truck 
select * from lot 
select * from notify_costumer

--Least Changed Data 

--1)UserData
--1)Username 2) Cnic 3) Age 4) Name 5) Password 6) Address 7) Phone 
exec costumerP 'ali','C110xx',22,'Ali Hassan','ali123','Airport','0309xxx'
exec costumerP 'dawar','C120xx',21,'Dawar Mustaqeem','dawar123','Cavalry','0301xxx'
exec costumerP 'moeez','C130xx',23,'Moeez','moeez123','LDA','0320xxx'
select * from customer 

--2) add Manager 
--1-Cnic 2- Name 3-Phone 4-Address 5-Age 
exec addManager '124xx','Alpha','0309xx','123 S St',44,'ali1'
exec addManager '125xx','Vains','0300xx','Walton',30,'vains1'
exec addManager '123xx','Rafay','0333xx','123 N St',25,'rafay1999'
exec addManager '122xx','Omar','0301xx','Askari X',25,'157157'
exec addManager '127xx','Wqs','0308xx','RA Bazar',25,'waqas1'
exec addManager '128xx','Bilal','0305','Askari 1',25,'bilal1'
select * from manager 

--3) add transporter 
--1-Cnic 2- Name 3- Lisence 4- Address 5- Phone 

exec addTransporter 'T130xx','Kami','35201','township','0333xx'
exec addTransporter 'T131xx','Frank','45201','DHA','0339xx'


--4) adding warehouse 
--1) name 2- M_id 3- Capacity(int) 4- Type nvarchar(50) 5- Location 6- Next Location 7- Previous Location 8- Distance Next 9- Distance Previous 
exec addWarehouse 'W1','122xx',5000,'Dairy','Islamabad','Islamabad Collector',NULL,5,NULL, 0
exec addWarehouse 'C1','123xx',5000,NULL,'Islamabad Collector','Lahore','Islamabad',350,5,1
exec addWarehouse 'W2','124xx',5000,'Electronics','Lahore','Lahore Collector','Islamabad Collector',10,350,0
exec addWarehouse 'C2','125xx',5000,NULL,'Lahore Collector','Multan','Lahore',600,10,1
exec addWarehouse 'W3','127xx',5000,'Bakery','Multan','Multan Collector','Lahore',20,600,0
exec addWarehouse 'C3','128xx',5000,NULL,'Multan Collector',NULL,'Multan',NULL,20,1
exec addWarehouse 'W4','130xx',5000,NULL,'Vehari',NULL,'Multan Collector',NULL,400,0
select * from warehouse 
select * from manager

--5) adding products 
--1-Prod Type 2- Prod Name 3-Cost/Unit 4- Prod Qty 5- Prod Warehouse Id 
exec addProducts 'Dairy','Milk',100,5000,'Islamabad'
exec addProducts 'Electronics','Tv',60000,10000,'Lahore'
exec addProducts 'Bakery','Cookies',12,10000,'Multan'
exec addProducts 'Backery','Stereo',400,50000,'Lahore'


select * from product

--6) add new truck 
--1-Plate 2- Name 3-Company 4- Model 
exec addTruck '3617','Fuso','Mitsubishi',2019
exec addTruck '1829','SL990','Benz',2019



--Order Flow
--Products are Ordered By User 

--1) Order Creation 

--1-product id 2-qty 3- username 4- destination 
exec orderCreation 4,2,'ali','Lahore Collector'
exec orderCreation 5,3,'dawar','Multan Collector'
exec orderCreation 6,1,'moeez','Multan Collector'

select * from customer
select * from orders 
select * from warehouse
select * from product 

--2) Lot Creation 


--occupied to be completed by orderlot table 
--1-transporter 2-plate 3- capacity
exec lotCreation '45201','1829',5
exec lotCreation '45201','1829',6

--3)Lot Order Assignment 
exec ord_lot_assignment '122xx',2
select * from lot 
select * from orderLot
select * from orders 
select * from lot 
--The FLow of Truck 


--4) adding tLocation 
select * from lot 
exec addTLocation 'Islamabad','45201','1829',1004
--the truck first goes to Islamabad Collector 
exec addTLocation 'Islamabad Collector', '45201','1829',2
exec notifyCustomerAgainstLot '123xx',2,4
select * from notify_transporter

select * from notify_transporter
select * from transporterLocation 
select * from notify_costumer
select * from orders
select * from lot 
--then truck goes to Lahore 
exec addTLocation 'Lahore', '45201','1829',2
exec ord_lot_assignment '124xx',1
exec notifyCustomer '124xx'
exec notifyCustomerAgainstLot '124xx',2,5




--then truck goes to Lahore Collector
exec addTLocation 'Lahore Collector', '45201','1829',1
exec notifyCustomer '125xx'


select * from lot 
select * from orders

select * from orderLot

select * from notify_transporter
select * from transporterLocation

select * from notify_costumer

--then truck goes to Multan
exec addTLocation 'Multan','45201','1829',7
exec ord_lot_assignment '127xx',7
exec notifyCustomer '127xx'


--then truck goes to Multan Collector 
exec addTLocation 'Multan Collector','45201','1829',7
exec notifyCustomer '128xx'

--removing all multan addition 
--1)notifyed manager 
--2)notifying customer 
--3)orderLot changing 
--4)order Status 

